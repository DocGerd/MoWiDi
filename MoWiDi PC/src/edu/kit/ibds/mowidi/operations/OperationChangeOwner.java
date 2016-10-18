/*
 * @(#)OperationChangeOwner.java
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
package edu.kit.ibds.mowidi.operations;

/**
 * Operation to do <tt>chown</tt>.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationChangeOwner extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 7938493604730358986L;
    /** Path to file. */
    @SuppressWarnings("unused")
    private String path;
    /** User ID. */
    @SuppressWarnings("unused")
    private int uID;
    /** Group ID. */
    @SuppressWarnings("unused")
    private int gID;

    /**
     * Change the owner of a file or directory.
     * @param path The path to the file or directory
     * @param uID the new owner id
     * @param gID the new group id
     */
    public OperationChangeOwner(final String path, final int uID, final int gID) {
        super();
        this.path = path;
        this.uID = uID;
        this.gID = gID;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
