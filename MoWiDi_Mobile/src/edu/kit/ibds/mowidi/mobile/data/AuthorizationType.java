/*
 * @(#)AuthorizationType.java
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
 */
package edu.kit.ibds.mowidi.mobile.data;

/**
 * This enum represents the access rights for a PC.
 * @author Patrick Kuhn
 */
public enum AuthorizationType implements Comparable<AuthorizationType> {

    /** No access rights given yet. */
    PENDING(false, false),
    /** Only allowed to read. */
    READ_ONLY(true, false),
    /** Allowed to read and write. */
    READ_AND_WRITE(true, true);
    /** Is allowed to read. */
    private final boolean read;
    /** Is allowed to write. */
    private final boolean write;

    /**
     * Create an AuthorizationType.
     * @param r permission to read
     * @param w permission to write
     */
    private AuthorizationType(final boolean r, final boolean w) {
        assert !w || (w && r);
        this.read = r;
        this.write = w;
    }

    /**
     * Is this authorisation type allowed to read?
     * @return <tt>true</tt> if permission to read
     */
    public boolean mayRead() {
        return read;
    }

    /**
     * Is this authorisation type allowed to write?
     * @return <tt>true</tt> if permission to write
     */
    public boolean mayWrite() {
        return write;
    }
}
