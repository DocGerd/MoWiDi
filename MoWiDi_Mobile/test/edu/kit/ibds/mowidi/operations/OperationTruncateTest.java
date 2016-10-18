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
import java.io.FileOutputStream;
import java.io.IOException;

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import android.os.Environment;
import android.test.AndroidTestCase;

public class OperationTruncateTest extends AndroidTestCase {

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationTruncate("", 0);
        assertEquals(AuthorizationType.READ_AND_WRITE, test.minAuthorizationType);
    }

    public void testDoOperationBigger() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "biggerFile");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(new byte[]{'T', 'e', 's', 't'});
            stream.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        long bevoreSize = file.length();
        AbstractOperation test = new OperationTruncate("biggerFile", bevoreSize + 50);
        test.doOperation();
        assertEquals(bevoreSize + 50, file.length());
    }

    public void testDoOperationSmaller() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "smallerFile");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(new byte[]{'T', 'e', 's', 't'});
            stream.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        long bevoreSize = file.length();
        AbstractOperation test = new OperationTruncate("smallerFile", bevoreSize - 3);
        test.doOperation();
        assertEquals(bevoreSize - 3, file.length());
    }

    public void testDoOperationCreate() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "createFile");
        if (file.exists()) {
            file.delete();
        }
        AbstractOperation test = new OperationTruncate("createFile", 50);
        test.doOperation();
        assertEquals(50, file.length());
    }
}
