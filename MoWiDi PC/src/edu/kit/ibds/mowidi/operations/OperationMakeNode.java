/*
 * @(#)OperationMakeNode.java
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
 * Operation to make a node.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationMakeNode extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 8219102417428641136L;
    /** Path to file. */
    @SuppressWarnings("unused")
    private String path;
    /** Mode for the file. */
    @SuppressWarnings("unused")
    private int mode;
    /** Root device type. */
    @SuppressWarnings("unused")
    private int rdev;

    /**
     * Operation to create a new File.
     * @param path The path where the file shall be created
     * @param mode the mode with which the file shall be created
     * @param rdev the root device type
     */
    public OperationMakeNode(final String path, final int mode, final int rdev) {
        super();
        this.path = path;
        this.mode = mode;
        this.rdev = rdev;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
