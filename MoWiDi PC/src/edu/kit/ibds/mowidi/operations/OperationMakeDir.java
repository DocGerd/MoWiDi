/*
 * @(#)OperationMakeDir.java
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
 * Operation to make a directory.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationMakeDir extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = -8743151987278838253L;
    /** Path to file. */
    @SuppressWarnings("unused")
    private String path;
    /** Mode for the directory. */
    @SuppressWarnings("unused")
    private int mode;

    /**
     * Operation to make a new Directory at the given path with the given mode.
     * @param path Path to the place where the directory shall be created
     * @param mode mode with which the directory shall be created (0-755)
     */
    public OperationMakeDir(final String path, final int mode) {
        super();
        this.path = path;
        this.mode = mode;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
