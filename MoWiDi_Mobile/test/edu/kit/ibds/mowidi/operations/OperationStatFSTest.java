/*
 * @(#)Operation
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
import fuse.FuseStatfs;
import android.os.Environment;
import android.os.StatFs;
import android.test.AndroidTestCase;


/**
 *
 * @author Michael Auracher
 */
public final class OperationStatFSTest extends AndroidTestCase {
	
	@SuppressWarnings("static-access")
	public void testAuthorizationType() {
		AbstractOperation test = new OperationStatFS();
		assertEquals(AuthorizationType.READ_ONLY, test.minAuthorizationType);
	}
	
	public void testDoOperation() {
		File root = Environment.getExternalStorageDirectory();
		AbstractOperation test = new OperationStatFS();
		test.doOperation();
		
		StatFs stats = new StatFs(root.getAbsolutePath());
		FuseStatfs result = (FuseStatfs)test.savedObject;
		assertEquals(stats.getBlockCount(), result.blocks);
		assertEquals(stats.getAvailableBlocks(), result.blocksAvail);
		assertEquals(stats.getBlockSize(), result.blockSize);
		assertEquals(stats.getFreeBlocks(), result.blocksFree);
		//TODO: Test not complete
	}


}
