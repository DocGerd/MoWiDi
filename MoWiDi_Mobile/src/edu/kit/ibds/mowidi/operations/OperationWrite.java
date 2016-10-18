/*
 * @(#)OperationWrite.java
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;

/**
 *
 * @author Michael Auracher
 */
public final class OperationWrite extends AbstractOperation {

    private static final long serialVersionUID = -8110275744211802166L;
    private String path;
    private long offset;
    private byte[] buffer;

    static {
        minAuthorizationType = AuthorizationType.READ_AND_WRITE;
    }

    OperationWrite(String path, long offset, byte[] buffer) {
        this.path = path;
        this.offset = offset;
        this.buffer = buffer;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath(), path);
        if (!file.exists()) {
            savedObject = new FuseException("No such entry").initErrno(FuseException.ENOENT);
            return;
        }
        if (!file.isFile()) {
            savedObject = new FuseException("Not a File").initErrno(FuseException.EISDIR);
            return;
        }
        try {
            RandomAccessFile rFile = new RandomAccessFile(file, "rw");
            rFile.seek(offset);
            rFile.write(buffer);
            rFile.close();
        } catch (FileNotFoundException e) {
            //not expected to happen since RandomAccessFile would create one
            savedObject = new FuseException("No such entry", e).initErrno(FuseException.ENOENT);
            return;
        } catch (IOException e) {
            savedObject = new FuseException("IO Error", e).initErrno(FuseException.EIO);
            return;
        }
        savedObject = true;
    }

    @Override
    public String toString() {
        return "Operation WRITE on " + path + " with result " + savedObject;
    }
}
