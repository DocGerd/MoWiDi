/*
 * @(#)OperationOpen.java
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

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FilesystemConstants;
import fuse.FuseException;

/**
 *
 * @author Michael Auracher
 */
public final class OperationOpen extends AbstractOperation {

    private static final long serialVersionUID = 3881520887307398383L;
    private String path;
    private int flags;

    static {
        minAuthorizationType = AuthorizationType.READ_ONLY;
    }
    
    OperationOpen(String path, int flags) {
    	this.path = path;
    	this.flags = flags;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        if (flags == FilesystemConstants.O_WRONLY || flags == FilesystemConstants.O_RDWR) {
            if (file.canWrite()) {
                savedObject = null;
            } else {
                savedObject = new FuseException("Read Only").initErrno(FuseException.EACCES);
            }
            return;
        } else {
            if (file.canRead()) {
                savedObject = null;
            } else {
                savedObject = new FuseException("No Permission").initErrno(FuseException.EACCES);
            }
            return;
        }
    }

    @Override
    public String toString() {
        return "Operation OPEN on " + path + " with result " + savedObject;
    }
}
