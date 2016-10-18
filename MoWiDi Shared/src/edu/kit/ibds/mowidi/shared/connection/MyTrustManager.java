/*
 * @(#)TrustManager.java
 *
 * 2010-06-11   creation PK
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

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

/**
 * The Trust Manager.
 * @author Patrick Kuhn
 */
public class MyTrustManager implements X509TrustManager {

    private final X509Certificate acceptedIssuers[];

    public MyTrustManager() {
        acceptedIssuers = new X509Certificate[0];
    }

    public static String[] cert2Strings(X509Certificate cert) throws NoSuchAlgorithmException {
        final String result[] = new String[2];
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        final X500Principal p = cert.getSubjectX500Principal();
        result[0] = "cert from " + p.getName();
        md.update(p.getEncoded());
        md.update(cert.getPublicKey().getEncoded());
        final byte fpr[] = md.digest();
        final StringBuilder sb = new StringBuilder("fingerprint: ");
        for (byte b : fpr) {
            sb.append(String.format("%x", b));
        }
        result[1] = sb.toString();
        return result;
    }

    @Override
    public void checkClientTrusted(X509Certificate chain[], String authType) throws CertificateException {
        try {
            for (String s : cert2Strings(chain[0])) {
                System.out.println(s);
            }
            System.out.print("trust it? ");
            final byte answer[] = new byte[2];
            System.in.read(answer);
            if (answer[0] != "y".getBytes()[0]) {
                throw new CertificateException("user don't like cert");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new CertificateException(e);
        } catch (IOException e) {
            throw new CertificateException(e);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate chain[], String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return acceptedIssuers.clone();
    }
}
