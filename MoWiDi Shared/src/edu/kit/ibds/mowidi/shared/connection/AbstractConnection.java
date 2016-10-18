/*
 * @(#)AbstractConnection.java
 *
 * 2010-06-08   first revision
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

import java.security.cert.X509Certificate;

/**
 * Represents a connection.
 * This abstract class only contains constants and
 * a certificate and its creator.
 * @author Patrick Kuhn
 */
public abstract class AbstractConnection {

    /** The standard port used by RMI and the SSLSocket.  */
    public static final int PORT = 4223;
    /** Certificate of the connection's far partner. */
    protected X509Certificate farCertificate;
    /** Certificate of this machine. */
    private X509Certificate myCertificate;

    /**
     * Get the far certificate.
     * @return the far certificate
     */
    public X509Certificate getFarCertificate() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    /**
     * Get our own certificate.
     * @return our certificate
     */
    public X509Certificate getOwnCertificate() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    /**
     * Release the connection.
     */
    public abstract void release();
}
