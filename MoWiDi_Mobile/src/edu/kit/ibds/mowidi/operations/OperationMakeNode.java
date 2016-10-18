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

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;

/**
 *
 * @author Michael Auracher
 */
public final class OperationMakeNode extends AbstractOperation {

    private static final long serialVersionUID = 8219102417428641136L;
    private String path;
    @SuppressWarnings("unused")
    private int mode;
    @SuppressWarnings("unused")
    private int rdev;

    static {
        minAuthorizationType = AuthorizationType.READ_AND_WRITE;
    }
    
    OperationMakeNode(String path) {
    	this.path = path;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        try {
            savedObject = file.createNewFile();
        } catch (IOException e) {
            savedObject = new FuseException("IO Error").initErrno(FuseException.EIO);
            return;
        }
    }

    @Override
    public String toString() {
        return "Operation MAKE NODE on " + path + " with result " + savedObject;
    }
}
