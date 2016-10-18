/*
 * @(#)OperationRemDir.java
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

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import android.os.Environment;
import android.test.AndroidTestCase;

/**
 *
 * @author Michael Auracher
 */
public final class OperationRemDirTest extends AndroidTestCase {

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationRemDir("");
        assertEquals(AuthorizationType.READ_AND_WRITE, test.minAuthorizationType);
    }

    public void testDoOperationNoDir() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "noDir");
        if (path.exists() && path.isDirectory()) {
            path.delete();
        }
        if (!path.exists()) {
            try {
                path.createNewFile();
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }
        AbstractOperation test = new OperationRemDir("noDir");
        test.doOperation();
        assertEquals(FuseException.ENOTDIR, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperationNotExist() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "notExist");
        if (path.exists()) {
            path.delete();
        }
        AbstractOperation test = new OperationRemDir("notExist");
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperationNotEmpty() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "notEmpty");
        path.mkdir();
        File subdir = new File(path, "subdir");
        subdir.mkdir();
        AbstractOperation test = new OperationRemDir("notEmpty");
        test.doOperation();
        assertEquals(FuseException.ENOTEMPTY, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "remDir");
        if (path.exists() && path.isFile()) {
            path.delete();
        }
        if (!path.exists()) {
            path.mkdir();
        }
        AbstractOperation test = new OperationRemDir("remDir");
        test.doOperation();
        assertFalse(path.exists());
    }
}
