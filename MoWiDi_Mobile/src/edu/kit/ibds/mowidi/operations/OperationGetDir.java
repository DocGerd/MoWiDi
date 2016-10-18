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

import java.io.File;

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import fuse.FuseFtype;
import fuse.compat.FuseDirEnt;

/**
 *
 * @author Michael Auracher
 */
public final class OperationGetDir extends AbstractOperation {

    private static final long serialVersionUID = 4254142205189650228L;
    private String path;

    static {
        minAuthorizationType = AuthorizationType.READ_ONLY;
    }

    OperationGetDir(String path) {
    	this.path = path;
    }
    
    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root, path);
        if (!dir.exists()) {
            savedObject = new FuseException("No Such Entry").initErrno(FuseException.ENOENT);
            return;
        }
        if (!dir.isDirectory()) {
            savedObject = new FuseException("Not a Directory").initErrno(FuseException.ENOTDIR);
            return;
        }
        String[] childs = dir.list();
        FuseDirEnt[] dirEntries = new FuseDirEnt[childs.length];
        for (int i = 0; i < childs.length; ++i) {
            String child = childs[i];
            File childDir = new File(dir, child);
            FuseDirEnt dirEntry = new FuseDirEnt();
            dirEntry.name = child;
//          dirEntry.inode = child.hashCode();
            if (childDir.isDirectory()) {
                dirEntry.mode = FuseFtype.TYPE_DIR;
            } else {
                dirEntry.mode = FuseFtype.TYPE_FILE;
            }
            dirEntries[i] = dirEntry;
        }
        savedObject = dirEntries;
    }

    @Override
    public String toString() {
        return "Operation GET DIR on " + path + " with result " + savedObject;
    }
}
