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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 *
 * @author Michael Auracher
 */
public final class OperationWriteTest extends AndroidTestCase {

    private static final byte[] writeBuf = new byte[]{'H', 'a', 'l', 'l', '0'};

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationUUID();
        assertEquals(AuthorizationType.READ_AND_WRITE, test.minAuthorizationType);
    }

    public void testDoOperationNotExists() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "notExists");
        if (file.exists()) {
            file.delete();
        }
        AbstractOperation test = new OperationWrite("notExists", 0, new byte[1]);
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperationNoFile() {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root, "noFile");
        if (dir.exists() && dir.isFile()) {
            dir.delete();
        }
        dir.mkdir();
        AbstractOperation test = new OperationWrite("noFile", 0, new byte[1]);
        test.doOperation();
        assertEquals(FuseException.EISDIR, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "writeFile");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }
        AbstractOperation test = new OperationWrite("writeFile", 4, writeBuf);
        test.doOperation();
        byte[] result = new byte[writeBuf.length + 4];
        try {
            FileInputStream stream = new FileInputStream(file);
            stream.read(result);
            stream.close();
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        Log.i("Tester", new String(result));
        for (int i = 0; i < 4; i++) {
            assertEquals(0, result[i]);
        }
        for (int i = 4; i < result.length; i++) {
            assertEquals(result[i], writeBuf[i - 4]);
        }
    }
}
