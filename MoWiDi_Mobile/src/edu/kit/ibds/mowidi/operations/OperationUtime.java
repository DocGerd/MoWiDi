/*
 * @(#)OperationUtime.java
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
 *
 * 2010-07-09   implemented PK
 */
package edu.kit.ibds.mowidi.operations;

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import java.io.File;

/**
 * Operation which changes access and modified time of a file.
 * @author Patrick Kuhn
 */
public final class OperationUtime extends AbstractOperation {

    /** Serial Version UID. */
    private static final long serialVersionUID = 8881667335259659776L;
    /** The path. */
    private String path;
    /** Last access time. */
    @SuppressWarnings("unused")
    private int aTime;
    /** Modified time. */
    private int mTime;

    static {
        minAuthorizationType = AuthorizationType.READ_AND_WRITE;
    }

    OperationUtime(String path, int mTime) {
        this.path = path;
        this.mTime = mTime;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        if (!file.exists()) {
            savedObject = new FuseException("Does not exist").initErrno(FuseException.ENOENT);
            return;
        }
        savedObject = file.setLastModified(((long) mTime) * 1000);
    }

    @Override
    public String toString() {
        return "Operation UTIME on " + path + " with result " + savedObject;
    }
}
