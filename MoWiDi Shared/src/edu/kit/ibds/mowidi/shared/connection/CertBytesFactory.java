package edu.kit.ibds.mowidi.shared.connection;

import edu.kit.ibds.mowidi.shared.connection.asn.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Map;

public class CertBytesFactory {

    private int serialNumber;
    private final String issuerName;
    private String subjectName;
    private Date validFrom;
    private Date validTo;
    private KeyPair keyPair;
    private final Map<String, Oid> mapToOid;

    public CertBytesFactory() {
        mapToOid = new HashMap<String, Oid>();
        mapToOid.put("C", Oid.CO);
        mapToOid.put("ST", Oid.ST);
        mapToOid.put("L", Oid.LN);
        mapToOid.put("O", Oid.ON);
        mapToOid.put("OU", Oid.OU);
        mapToOid.put("CN", Oid.CN);
        issuerName = "C=de,ST=bw,L=ka,O=kit,OU=ibds,CN=issuer";
        subjectName = "C=de,ST=bw,L=ka,O=kit,OU=ibds,CN=subject";
    }

    public void setSubjectName(String name) {
        subjectName = name;
    }

    private KeyPair genKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        final SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        return keyGen.genKeyPair();
    }

    private Asn1Seq parseName(String name) {
        final String[] name_comps = name.split(",");
        final Vector<Asn1Set> enc_comps = new Vector<Asn1Set>();
        final Asn1Set[] setType = new Asn1Set[0];
        for (String comp : name_comps) {
            final String[] c = comp.split("=");
            enc_comps.add(
                    new Asn1Set(
                    new Asn1Seq(
                    new Asn1Oid(mapToOid.get(c[0])),
                    new Asn1Str(c[1]))));
        }
        return (new Asn1Seq(enc_comps.toArray(setType)));
    }

    private byte[] genCertBytes() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        final Asn1Int versionInt = new Asn1Int(0, true);
        final Asn1Tag version = new Asn1Tag(Asn1Type.CtxCZero, versionInt);
        final Asn1Int serNo = new Asn1Int(serialNumber, true);
        final Asn1Seq algoId = new Asn1Seq(new Asn1Oid(Oid.DSA_WITH_SHA1), new Asn1Null());
        final Asn1Seq issuer = parseName(issuerName);
        final Asn1Seq valid = new Asn1Seq(
                new Asn1Utc(validFrom),
                new Asn1Utc(validTo));
        final Asn1Seq subject = parseName(subjectName);
        final Asn1Enc keyInfo = new Asn1Enc(keyPair.getPublic().getEncoded());
        final Asn1Seq certData = new Asn1Seq(
                version,
                serNo,
                algoId,
                issuer,
                valid,
                subject,
                keyInfo);
        final Signature signer = Signature.getInstance("SHA1withDSA");
        signer.initSign(keyPair.getPrivate());
        signer.update(certData.encodeDER());
        final byte[] sig = signer.sign();

        final Asn1Seq cert = new Asn1Seq(
                certData,
                algoId,
                new Asn1Bits(sig));

        return cert.encodeDER();
    }

    public void generate() throws NoSuchAlgorithmException, NoSuchProviderException {
        serialNumber = new Random().nextInt();
        validFrom = new Date();
        validTo = new Date(validFrom.getTime() + 24 * 60 * 60 * 1000);
        keyPair = genKeyPair();
    }

    public byte[] getCertBytes() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        return genCertBytes();
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}
