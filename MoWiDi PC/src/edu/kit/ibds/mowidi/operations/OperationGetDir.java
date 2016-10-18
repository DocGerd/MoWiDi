/*
 * @(#)OperationGetDir.java
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
 * Operation to get the directory.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationGetDir extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 4254142205189650228L;
    /** Path to file. */
    @SuppressWarnings("unused")
    private String path;

    /**
     * Operation to read the contents of a Directory.
     * @param path The path of the directory
     */
    public OperationGetDir(final String path) {
        super();
        this.path = path;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
