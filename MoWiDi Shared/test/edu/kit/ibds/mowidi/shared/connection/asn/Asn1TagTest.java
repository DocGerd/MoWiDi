/*
 * @(#)Asn1TagTest.java
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
 */
package edu.kit.ibds.mowidi.shared.connection.asn;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class Asn1TagTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1Tag");
    }

    /**
     * Test of getValueLength method, of class Asn1Tag.
     */
    @Test
    public void testGetValueLength() {
        System.out.println("getValueLength");
        final Asn1Tag instance = new Asn1Tag(Asn1Type.Null, new byte[]{});
        final int expResult = 0;
        final int result = instance.getValueLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of fillEncodedValue method, of class Asn1Tag.
     */
    @Test
    public void testFillEncodedValue() {
        System.out.println("fillEncodedValue");
        final byte[] enc = new byte[]{0x12, 0x54, 0x22, 0x13};
        final int off = 0;
        final Asn1Tag instance = new Asn1Tag(Asn1Type.Null, new byte[4]);
        final int expResult = 4;
        final int result = instance.fillEncodedValue(enc, off);
        assertEquals(expResult, result);
    }
}
