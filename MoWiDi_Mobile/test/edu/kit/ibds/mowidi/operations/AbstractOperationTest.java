/*
 * @(#)AbstractOperation.java
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

import android.test.AndroidTestCase;

import edu.kit.ibds.mowidi.operations.AbstractOperation;
import fuse.FuseException;

/**
 * Abstract Superclass for Operations over the Network.
 *
 * This class is a superclass which needs to be implemented on both sides.
 * The Server needs to provide a valid doOperation Function and the Client
 * uses the getReturnValue function to determine the result of the Operation
 *
 * @author Michael Auracher
 */
public class AbstractOperationTest extends AndroidTestCase {

    /**
     * Test of getReturnValue method, of class AbstractOperation.
     */
    public void testGetReturnValue() {
        AbstractOperation instance = new AbstractOperationImpl();
        Object expResult = "success";
        instance.doOperation();
        Object result = instance.getReturnValue();
        assertTrue(expResult.equals(result));
    }
    

    public void testSetAccessError() {
    	AbstractOperation instance = new AbstractOperationImpl();
    	int expResult = FuseException.EACCES;
    	instance.setAccessError();
    	assertEquals(expResult, ((FuseException)instance.savedObject).getErrno());
    }

    
    private class AbstractOperationImpl extends AbstractOperation {

		private static final long serialVersionUID = 263015289987352896L;

		@Override
        public void doOperation() {
            savedObject = "success";
        }
    }
}
