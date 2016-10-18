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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public final class OperationReadTest extends AndroidTestCase {

    private byte[] testBuf = new byte[]{'H', 'A', 'L', 'L', 'O', ' ', 'W', 'E', 'l', 't', '!'};

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationRead("", 0, new byte[0]);
        assertEquals(AuthorizationType.READ_ONLY, test.minAuthorizationType);
    }

    public void testDoOperationNoFile() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "noReadFile");
        if (file.exists()) {
            file.delete();
        }
        AbstractOperation test = new OperationRead("noReadFile", 0, new byte[1]);
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperationIsDir() {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root, "readDir");
        if (!dir.exists()) {
            dir.mkdir();
        }
        AbstractOperation test = new OperationRead("readDir", 0, new byte[1]);
        test.doOperation();
        assertEquals(FuseException.EISDIR, ((FuseException) test.savedObject).getErrno());
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "readFile");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(testBuf);
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        //Operation no offset not till end
        AbstractOperation test = new OperationRead("readFile", 0, new byte[9]);
        test.doOperation();
        byte[] result = (byte[]) test.savedObject;
        assertEquals(9, result.length);
        for (int i = 0; i < 9; i++) {
            assertEquals(testBuf[i], result[i]);
        }


        //Operation with offset not till end
        test = new OperationRead("readFile", 5, new byte[4]);
        test.doOperation();
        result = (byte[]) test.savedObject;
        assertEquals(4, result.length);
        for (int i = 5; i < 9; i++) {
            assertEquals(testBuf[i], result[i - 5]);
        }

        //Operation beyond end
        test = new OperationRead("readFile", 7, new byte[5]);
        test.doOperation();
        result = (byte[]) test.savedObject;
        Log.i("Tester", new String(result));
        for (int i = 7; i < 11; i++) {
            assertEquals(testBuf[i], result[i - 7]);
        }
        assertEquals(0, result[4]);
    }
}
