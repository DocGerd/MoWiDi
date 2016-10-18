/*
 * @(#)KeyManager.java
 *
 * 2010-06-11   created PK
 * 2010-08-12   bla bla PK
 *
 * This file is part of MoWiDi.
 *
 * MoWiDi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoWiDi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MoWiDi. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2010 by PSE23-Team:
 *
 * Patrick Kuhn, Michael Auracher,
 * André Wengert, Kim Spieß, Christopher Schütze
 */
package edu.kit.ibds.mowidi.shared.connection;

import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.X509KeyManager;

/**
 * The Key Manager.
 * @author Patrick Kuhn
 */
public final class MyKeyManager implements X509KeyManager {

    private boolean isServer = false;
    private boolean isSet = false;
    private CertBytesFactory certBuilder;
    /** Contains both key-pairs and public keys of trusted sources. Must be locked on for thread-safety. */
    private KeyStore keyStore;
    /** Some password needed for keyStore. Hard-coded String will suffice. */
    private static final char[] KS_PASSWORD = "3.141592".toCharArray();
    /** Key-store save file name. */
    private static final String KEYSTORE_FILENAME = "keystore.data";

    public MyKeyManager() {
        certBuilder = new CertBytesFactory();
    }

    private void setRole(boolean server) {
        if (isSet == false) {
            isServer = server;
            isSet = true;

            certBuilder.setSubjectName(isServer ? "CN=server key" : "CN=client key");
            try {
                certBuilder.generate();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String[] getClientAliases(final String string, final Principal[] prncpls) {
        try {
            throw new Exception();
        } catch (Exception ex) {
            Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String chooseClientAlias(final String[] strings, final Principal[] prncpls, final Socket socket) {
        setRole(false);
        return "clientkey";
    }

    @Override
    public String[] getServerAliases(final String string, final Principal[] prncpls) {
        try {
            throw new Exception();
        } catch (Exception ex) {
            Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String chooseServerAlias(final String string, final Principal[] prncpls, final Socket socket) {
        setRole(true);
        return "serverkey";
    }

    @Override
    public X509Certificate[] getCertificateChain(final String alias) {
        // TODO
        X509Certificate ret[] = new X509Certificate[1];
        if (alias != null && isSet) {
            if (isServer && alias.equals("serverkey") || !isServer && alias.equals("clientkey")) {
                try {
                    ret[0] = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(certBuilder.getCertBytes()));
                    if (!isServer) {
                        System.out.println("my certificate is:");
                        for (String s : MyTrustManager.cert2Strings(ret[0])) {
                            System.out.println(s);
                        }
                    }
                    return ret;
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CertificateException ex) {
                    Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SignatureException ex) {
                    Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(MyKeyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(final String alias) {
        if (alias != null && isSet) {
            if (isServer && alias.equals("serverkey") || !isServer && alias.equals("clientkey")) {
                return certBuilder.getKeyPair().getPrivate();
            }
        }
        return null;
    }
}
