/*
 * @(#)OperationTruncate.java
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

public class OperationTruncate extends AbstractOperation {

    private static final long serialVersionUID = -7168264192023098783L;
    private String path;
    private long size;

    static {
        minAuthorizationType = AuthorizationType.READ_AND_WRITE;
    }
    
    OperationTruncate(String path, long size) {
    	this.path = path;
    	this.size = size;
    }

    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                savedObject = new FuseException("IO Error", e).initErrno(FuseException.EIO);
                return;
            }
        }
        try {
            RandomAccessFile rFile = new RandomAccessFile(file, "rw");
            rFile.setLength(size);
        } catch (FileNotFoundException e) {
            //Not expected to happen since we create the file bevorehand if it does not exist.
            savedObject = new FuseException("Not expected error", e).initErrno(FuseException.ENOENT);
        } catch (IOException e) {
            savedObject = new FuseException("IO Error", e).initErrno(FuseException.EIO);
        }
        savedObject = true;
    }

    @Override
    public String toString() {
        return "Operation TRUNCATE on " + path + " with result " + savedObject;
    }
}
