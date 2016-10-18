/*
 * @(#)OperationChangeMode.java
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
 * Operation for <tt>chmod</tt>.
 * @author Michael Auracher
 */
public final class OperationChangeMode extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 590127238358831718L;
    /** Path to file. */
    @SuppressWarnings("unused")
    private String path;
    /** Mode to set. */
    @SuppressWarnings("unused")
    private int mode;

    /**
     * Operation to change the mode of a file or directory.
     * @param path The path to the file or directory
     * @param mode the new mode
     */
    public OperationChangeMode(final String path, final int mode) {
        super();
        this.path = path;
        this.mode = mode;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
