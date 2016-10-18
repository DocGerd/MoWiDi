/*
 * @(#)OperationUnlink.java
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
 * @author Patrick Kuhn
 */
public final class OperationUnlinkTest extends AndroidTestCase {

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationUnlink("");
        assertEquals(AuthorizationType.READ_AND_WRITE, test.minAuthorizationType);
    }

    public void testDoOperationNoFile() {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root, "noFile");
        if (dir.exists() && dir.isFile()) {
            dir.delete();
        }
        dir.mkdir();
        AbstractOperation test = new OperationUnlink("noFile");
        test.doOperation();
        assertEquals(FuseException.EISDIR, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperationNotExists() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "notExists");
        if (file.exists()) {
            file.delete();
        }
        AbstractOperation test = new OperationUnlink("notExists");
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "deleteFile");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }
        AbstractOperation test = new OperationUnlink("deleteFile");
        test.doOperation();
        assertFalse(file.exists());
    }
}
