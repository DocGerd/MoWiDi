/*
 * @(#)AbstractOperationTest.java
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
 * 2010-08-04   completed PK
 */
package edu.kit.ibds.mowidi.operations;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class AbstractOperationTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--AbstractOperation");
    }

    /**
     * Test of getReturnValue method, of class AbstractOperation.
     */
    @Test
    public void testGetReturnValue() {
        System.out.println("getReturnValue");
        AbstractOperation instance = new AbstractOperationImpl();
        Object expResult = "success";
        instance.doOperation();
        Object result = instance.getReturnValue();
        assertTrue(expResult.equals(result));
    }

    private class AbstractOperationImpl extends AbstractOperation {

        private static final long serialVersionUID = -8497538005209367523L;

        @Override
        public void doOperation() {
            savedObject = "success";
        }
    }
}
