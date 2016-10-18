/*
 * @(#)OperationRead.java
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.os.Environment;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;

import fuse.FuseException;

/**
 *
 * @author Michael Auracher
 */
public final class OperationRead extends AbstractOperation {

    private static final long serialVersionUID = -7872535549381513790L;
    private String path;
    private long offset;
    private byte[] bytes;

    static {
        minAuthorizationType = AuthorizationType.READ_ONLY;
    }

    OperationRead(String path, long offset, byte[] bytes) {
    	this.path = path;
    	this.offset = offset;
    	this.bytes = bytes;
    }
    
    @Override
    public void doOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, path);
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        if (!file.exists()) {
            savedObject = new FuseException("No such file").initErrno(FuseException.ENOENT);
            return;
        }
        if (file.isFile()) {
            try {
                BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file), buf.capacity());
                inStream.skip(offset);
                byte[] buffer = new byte[buf.capacity()];
                int bread;
                bread = inStream.read(buffer);
                if (bread > 0) {
                    buf.put(buffer, 0, bread);
                }
                inStream.close();
            } catch (FileNotFoundException e) {
            	//not supposed to happen
                savedObject = new FuseException("No such file", e).initErrno(FuseException.ENOENT);
                return;
            } catch (IOException e) {
                savedObject = new FuseException("IO Error", e).initErrno(FuseException.EIO);
                return;
            }
            savedObject = buf.array();
            return;
        }
        savedObject = new FuseException("Not a File").initErrno(FuseException.EISDIR);
    }

    @Override
    public String toString() {
        return "Operation READ on " + path + " with result " + savedObject;
    }
}
