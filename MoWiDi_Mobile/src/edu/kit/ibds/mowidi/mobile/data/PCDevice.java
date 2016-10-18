/*
 * @(#)PCDevice.java
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
 * 2010-06-21   apparently finished PK
 * 2010-07-08   get/set authType PK
 */
package edu.kit.ibds.mowidi.mobile.data;

import edu.kit.ibds.mowidi.shared.data.AbstractDevice;

import java.io.Serializable;
import java.util.UUID;

/**
 * A PCDevice for storage in model as a <tt>Device</tt>.
 * @author Patrick Kuhn
 */
public final class PCDevice extends AbstractDevice implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = -9123722520658278027L;
    /** Type of access rights of this PCDevice. */
    private AuthorizationType authType;

    /**
     * Create a new PCDevice device.
     * @param uid the unique ID
     */
    public PCDevice(final UUID uid) {
        super(uid);
        authType = AuthorizationType.PENDING;
    }

    /**
     * Get the authorisation type.
     * @return the authorisation type
     */
    public AuthorizationType getAuthType() {
        return authType;
    }

    /**
     * Set the authorisation type.
     * @param authType the authorisation type
     */
    public void setAuthType(final AuthorizationType authType) {
        this.authType = authType;
    }

}
