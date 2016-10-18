/*
 * @(#)OperationGetAttributes.java
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
import android.os.StatFs;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;

import fuse.FuseException;
import fuse.FuseFtype;
import fuse.compat.FuseStat;

/**
 *
 * @author Michael Auracher
 */
public final class OperationGetAttributes extends AbstractOperation {

    private static final long serialVersionUID = 1714557022337276102L;
    private String path;

    OperationGetAttributes(String path) {
        this.path = path;
    }

    static {
        minAuthorizationType = AuthorizationType.READ_ONLY;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        StatFs statData = new StatFs(root.getAbsolutePath());
        int blockSize = statData.getBlockSize();
        File file = new File(root, path);
        if (!file.exists()) {
            savedObject = new FuseException("Does not exist").initErrno(FuseException.ENOENT);
            return;
        }
        FuseStat stat = new FuseStat();
        if (file.isDirectory()) {
            stat.mode = FuseFtype.TYPE_DIR | 0755;
        } else {
            stat.mode = FuseFtype.TYPE_FILE | 0755;
        }
        stat.nlink = 1;
        stat.uid = 0;
        stat.gid = 0;
        long length = file.length();
        stat.size = length;
        stat.blocks = (int) ((stat.size + blockSize - 1) / blockSize);
        // java does not support aTime and cTime
        int lastModified = (int) (file.lastModified() / 1000);
        stat.atime = lastModified;
        stat.ctime = lastModified;
        stat.mtime = lastModified;
        this.savedObject = stat;
    }

    @Override
    public String toString() {
        return "Operation GET ATTR on " + path + " with result " + savedObject;
    }
}
