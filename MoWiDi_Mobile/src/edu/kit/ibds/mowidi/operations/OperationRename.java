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

import java.io.File;

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;

/**
 *
 * @author Michael Auracher
 */
public class OperationRename extends AbstractOperation {

    private static final long serialVersionUID = 4884830881852756517L;
    private String from;
    private String to;

    static {
        minAuthorizationType = AuthorizationType.READ_AND_WRITE;
    }
    
    OperationRename(String from, String to) {
    	this.from = from;
    	this.to = to;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, from);
        if (!file.exists()) {
            savedObject = new FuseException("Does not exist").initErrno(FuseException.ENOENT);
            return;
        }
        File toFile = new File(root, to);
        if (toFile.exists() && !toFile.canWrite()) {
            savedObject = new FuseException("No permission").initErrno(FuseException.EACCES);
            return;
        }
        savedObject = file.renameTo(toFile);
    }

    @Override
    public String toString() {
        return "Operation RENAME on " + from + " with result " + savedObject;
    }
}
