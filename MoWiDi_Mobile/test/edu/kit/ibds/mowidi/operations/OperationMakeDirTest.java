/*
 * @(#)OperationMakeDir.java
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

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import android.os.Environment;
import android.test.AndroidTestCase;


/**
 *
 * @author Michael Auracher
 */
public final class OperationMakeDirTest extends AndroidTestCase {
	
	@SuppressWarnings("static-access")
	public void testAuthorizationType() {
		AbstractOperation test = new OperationMakeDir("");
		assertEquals(AuthorizationType.READ_AND_WRITE, test.minAuthorizationType);
	}
	
	public void testDoOperation() {
		File root = Environment.getExternalStorageDirectory();
		File dir = new File(root, "makeDir");
		if (dir.exists()) {
			dir.delete();
		}
		AbstractOperation test = new OperationMakeDir("makeDir");
		test.doOperation();
		assertTrue(dir.exists());
	}

}
