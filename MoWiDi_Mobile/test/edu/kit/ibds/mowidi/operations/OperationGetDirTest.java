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
import java.io.IOException;

import android.os.Environment;
import android.test.AndroidTestCase;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import fuse.FuseFtype;
import fuse.compat.FuseDirEnt;

/**
 *
 * @author Michael Auracher
 */
public final class OperationGetDirTest extends AndroidTestCase {

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationGetDir("");
        assertEquals(AuthorizationType.READ_ONLY, test.minAuthorizationType);
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
        AbstractOperation test = new OperationGetDir("noDir");
        test.doOperation();
        assertEquals(FuseException.ENOTDIR, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperationNotExists() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "lalalala");
        if (path.exists()) {
            path.delete();
        }
        AbstractOperation test = new OperationGetDir("lalalala");
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "getDir");
        path.mkdir();
        File subDir = new File(path, "subDir");
        subDir.mkdir();
        File subFile = new File(path, "subFile");
        try {
            subFile.createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        AbstractOperation test = new OperationGetDir("getDir");
        test.doOperation();
        FuseDirEnt[] result = (FuseDirEnt[]) test.savedObject;
        String[] childs = path.list();
        assertTrue(childs[0].equals(result[0].name));
        assertTrue(childs[1].equals(result[1].name));
        assertEquals(FuseFtype.TYPE_DIR, result[0].mode);
        assertEquals(FuseFtype.TYPE_FILE, result[1].mode);
    }

    public void testDoOperationEmptyDir() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "emptyDir");
        path.mkdir();
        AbstractOperation test = new OperationGetDir("emptyDir");
        test.doOperation();
        assertEquals(0, ((FuseDirEnt[]) test.savedObject).length);
    }
}
