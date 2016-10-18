/*
 * @(#)OperationUtime.java
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
 * Operation to change utime.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationUtime extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 8881667335259659776L;
    /** The path. */
    @SuppressWarnings("unused")
    private String path;
    /** Access time. */
    @SuppressWarnings("unused")
    private int aTime;
    /** Modified time. */
    @SuppressWarnings("unused")
    private int mTime;

    /**
     * Operation to change the time attributes of a file.
     * @param path the path to the File from which the attributes need to be changed
     * @param aTime the new last access time
     * @param mTime the new last modification time
     */
    public OperationUtime(final String path, final int aTime, final int mTime) {
        super();
        this.path = path;
        this.aTime = aTime;
        this.mTime = mTime;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
