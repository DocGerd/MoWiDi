/*
 * @(#)OperationRename.java
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
 * Operation to rename file.
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class OperationRename extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 4884830881852756517L;
    /** Path of file. */
    @SuppressWarnings("unused")
    private String from;
    /** Path of new file name. */
    @SuppressWarnings("unused")
    private String to;

    /**
     * Operation to rename a File or Directory.
     * @param from path including the name
     * @param to path including the name to which it shall be renamed
     */
    public OperationRename(final String from, final String to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("No support on client Side");
    }
}
