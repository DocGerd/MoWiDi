/*
 * @(#)OperationRead.java
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
 * Operation to read.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationRead extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = -7872535549381513790L;
    /** Path to file. */
    @SuppressWarnings("unused")
    private String path;
    /** Offset of read. */
    @SuppressWarnings("unused")
    private long offset;
    /** The buffer. */
    @SuppressWarnings("unused")
    private byte[] bytes;

    /**
     * Operation to read a specific file at the specific offset.
     * @param path The path to the file which shall be read
     * @param offset the offset within the file for reading
     * @param buf the buffer
     */
    public OperationRead(final String path, final byte[] buf, final long offset) {
        super();
        this.path = path;
        this.offset = offset;
        this.bytes = buf;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
