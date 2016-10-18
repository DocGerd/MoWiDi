/*
 * @(#)Device.java
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
 *
 * 2010-06-11   Second revision PK
 */
package edu.kit.ibds.mowidi.shared.data;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.UUID;

/**
 * Represents an device which can connect to another.
 * @author Christopher Schuetze
 * @author Patrick Kuhn
 */
public abstract class AbstractDevice implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = -4812246894209745509L;
    /** Name of this device. **/
    private String givenName;
    /** True if the computer is allowed to connect automatically. **/
    private boolean permanent;
    /** Unique ID of a device. **/
    private final UUID uID;
    /** TimeStamp of first time added. **/
    private Date firstTimeAdded;
    /** Shows if the device is ready to connect. **/
    private boolean available;
    /** Certificate of a device. **/
    private Certificate certificate;
    /** Shows if the device is connected. **/
    private boolean connected;

    /**
     * Create a new device.
     * @param uid the unique ID
     */
    public AbstractDevice(final UUID uid) {
        this.uID = uid;
        givenName = null;
        permanent = false;
        firstTimeAdded = null;
        available = false;
        certificate = null;
        connected = false;
    }

    /**
     * Get the unique ID.
     * @return the UID
     */
    public final UUID getuID() {
        return uID;
    }

    /**
     * Get the certificate.
     * @return the certificate
     */
    public final Certificate getCertificate() {
        return certificate;
    }

    /**
     * Set the certificate.
     * @param certificate the certificate
     */
    public final void setCertificate(final Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     * Get the first time added.
     * @return first time added
     */
    public final Date getFirstTimeAdded() {
        return (Date) firstTimeAdded.clone();
    }

    /**
     * Set first time added.
     * @param firstTimeAdded first time added
     */
    public final void setFirstTimeAdded(final Date firstTimeAdded) {
        this.firstTimeAdded = (Date) firstTimeAdded.clone();
    }

    /**
     * Get given name.
     * @return the given name
     */
    public final String getGivenName() {
        return givenName;
    }

    /**
     * Set given name.
     * @param givenName the given name
     */
    public final void setGivenName(final String givenName) {
        this.givenName = givenName.trim();
    }

    /**
     * Check whether this device is available.
     * @return <tt>true</tt> if available
     */
    public final boolean isAvailable() {
        return available;
    }

    /**
     * Set this device's available state.
     * @param isAvailable <tt>true</tt> for available
     */
    public final void setAvailable(final boolean isAvailable) {
        this.available = isAvailable;
    }

    /**
     * Check whether this device is currently connected to us.
     * @return <tt>true</tt> if connected
     */
    public final boolean isConnected() {
        return connected;
    }

    /**
     * Set whether this device is currently connected to us.
     * @param isConnected <tt>true</tt> if connected
     */
    public final void setConnected(final boolean isConnected) {
        this.connected = isConnected;
    }

    /**
     * Check whether we keep information on this device even if not available anymore.
     * @return <tt>true</tt> if permanent
     */
    public final boolean isPermanent() {
        return permanent;
    }

    /**
     * Set this device's permanent state to <tt>true</tt> to keep it even if it is not available.
     * @param isPermanent <tt>true</tt> if permanent
     */
    public final void setPermanent(final boolean isPermanent) {
        this.permanent = isPermanent;
    }
}
