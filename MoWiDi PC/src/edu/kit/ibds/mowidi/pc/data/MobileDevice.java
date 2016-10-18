/*
 * @(#)MobileDevice.java
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
 * 2010-06-13   first revision CS
 * 2010-06-13   completed CS
 */
package edu.kit.ibds.mowidi.pc.data;

import edu.kit.ibds.mowidi.shared.data.AbstractDevice;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Represents a mobile phone which is connected to the computer.
 * @author Christopher Schuetze
 */
public final class MobileDevice extends AbstractDevice {

    /** serialVersionUID. */
    private static final long serialVersionUID = -4565620307107134578L;
    /** Path of the mount-point. **/
    private String mountPoint;
    /** IP Address of this mobile . */
    private InetAddress address;

    /**
     * Create a new mobile.
     * @param uid the unique ID.
     */
    public MobileDevice(final UUID uid) {
        super(uid);
    }

    /**
     * Get the address.
     * @return the address
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Set the IP address.
     * @param address the address
     */
    public void setAddress(final InetAddress address) {
        this.address = address;
    }

    /**
     * Get the mount-point.
     * @return the path
     */
    public String getMountPoint() {
        return mountPoint;
    }

    /**
     * Set the mount-point.
     * @param mountPoint the path
     */
    public void setMountPoint(final String mountPoint) {
        this.mountPoint = mountPoint;
    }
}
