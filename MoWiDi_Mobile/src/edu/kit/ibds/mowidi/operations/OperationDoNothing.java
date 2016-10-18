/*
 * @(#)OperationDoNothing.java
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

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;

/**
 *
 * @author Michael Auracher
 */
public final class OperationDoNothing extends AbstractOperation {

    private static final long serialVersionUID = 7357392710537569242L;

    static {
        minAuthorizationType = AuthorizationType.PENDING;
    }

    @Override
    public void doOperation() {
        savedObject = null;
    }

    @Override
    public String toString() {
        return "Operation DO NOTHING with result " + savedObject;
    }
}
