/*
 * @(#)ErrorTypeTest.java
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
package edu.kit.ibds.mowidi.pc.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class ErrorTypeTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--ErrorType");
    }

    /**
     * Test of valueOf method, of class ErrorType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        final String name = "CONNECTION_LOST";
        final ErrorType expResult = ErrorType.CONNECTION_LOST;
        final ErrorType result = ErrorType.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCaption method, of class ErrorType.
     */
    @Test
    public void testGetCaption() {
        System.out.println("getCaption");
        final ErrorType instance = ErrorType.CERTIFICATE_ERROR;
        final String expResult = "Certificate Error";
        final String result = instance.getCaption();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText method, of class ErrorType.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        final ErrorType instance = ErrorType.NULL;
        final String expResult = "";
        final String result = instance.getText();
        assertEquals(expResult, result);
    }
}
