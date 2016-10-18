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
import java.io.IOException;

import android.os.Environment;
import android.test.AndroidTestCase;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;

import fuse.FuseException;
import fuse.FuseFtype;
import fuse.compat.FuseStat;

/**
 *
 * @author Michael Auracher
 */
public final class OperationGetAttributesTest extends AndroidTestCase {

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationGetAttributes("");
        assertEquals(AuthorizationType.READ_ONLY, test.minAuthorizationType);
    }

    public void testDoOperationFileNotExists() {
        File root = Environment.getExternalStorageDirectory();
        File path = new File(root, "lalalalal");
        if (path.exists()) {
            path.delete();
        }
        AbstractOperation test = new OperationGetAttributes("lalalalal");
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "blubb");
        File dir = new File(root, "dir");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }
        if (dir.exists() && dir.isFile()) {
            dir.delete();
        }
        dir.mkdir();
        //For a File
        AbstractOperation test = new OperationGetAttributes("blubb");
        test.doOperation();
        long size = file.length();
        int mode = FuseFtype.TYPE_FILE | 0755;

        FuseStat result = (FuseStat) test.savedObject;
        assertEquals(size, result.size);
        assertEquals(mode, result.mode);

        //For a Dir
        test = new OperationGetAttributes("dir");
        test.doOperation();
        size = dir.length();
        mode = FuseFtype.TYPE_DIR | 0755;

        result = (FuseStat) test.savedObject;
        assertEquals(size, result.size);
        assertEquals(mode, result.mode);
    }
}
