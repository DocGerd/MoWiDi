/*
 * @(#)OperationUtime.java
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
 *
 * 2010-07-09   implemented PK
 */
package edu.kit.ibds.mowidi.operations;

import java.io.File;
import java.io.IOException;

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import android.os.Environment;
import android.test.AndroidTestCase;

/**
 * Operation which changes access and modified time of a file.
 * @author Patrick Kuhn
 */
public final class OperationUtimeTest extends AndroidTestCase {

    @SuppressWarnings("static-access")
    public void testAuthorizationType() {
        AbstractOperation test = new OperationUtime("", 0);
        assertEquals(AuthorizationType.READ_AND_WRITE, test.minAuthorizationType);
    }

    public void testDoOperation() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "utimeFile");
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        int lastTime = (int) (file.lastModified() / 1000);
        AbstractOperation test = new OperationUtime("utimeFile", 100);
        test.doOperation();
        int nowTime = (int) (file.lastModified() / 1000);
        assertFalse(lastTime == nowTime);
        assertEquals(nowTime, 100);
    }

    public void testDoOperationNotExists() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "notExists");
        if (file.exists()) {
            file.delete();
        }
        AbstractOperation test = new OperationUtime("notExists", 0);
        test.doOperation();
        assertEquals(FuseException.ENOENT, ((FuseException) test.savedObject).getErrno());
    }
}
