/*
 * @(#)Asn1StrTest.java
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
package edu.kit.ibds.mowidi.shared.connection.asn;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class Asn1StrTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1Str");
    }

    private static void test(String str, int... icmp) {
        Asn1ObjTest.test(new Asn1Str(str), icmp);
    }

    @Test
    public void testVariousStringRepresentations() {
        test("", 19, 0);
        test("ABC", 19, 3, 65, 66, 67);
    }

    @Test
    public void testSetupVal() {
        System.out.println("setupVal w/ exception");
        final Asn1Str instance = new Asn1Str("@");
        assertNotNull(instance);
    }

    /**
     * Test of getValueLength method, of class Asn1Str.
     */
    @Test
    public void testGetValueLength() {
        System.out.println("getValueLength");
        final Asn1Str instance = new Asn1Str("test");
        final int expResult = 4;
        final int result = instance.getValueLength();
        assertEquals(expResult, result);
    }
}
