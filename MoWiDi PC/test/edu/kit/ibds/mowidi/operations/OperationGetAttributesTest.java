/*
 * @(#)OperationGetAttributesTest.java
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

/**
 *
 * @author Patrick Kuhn
 */
public class OperationGetAttributesTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--OperationGetAttribute");
    }

    /**
     * Test of doOperation method, of class OperationGetAttributes.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testDoOperation() {
        System.out.println("doOperation");
        OperationGetAttributes instance = new OperationGetAttributes("C:\\a.txt");
        instance.doOperation();
    }
}
