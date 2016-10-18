/*
 * @(#)Asn1EncTest.java
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
public class Asn1EncTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1Enc");
    }

    /**
     * Test of getValueLength method, of class Asn1Enc.
     */
    @Test
    public void testGetValueLength() {
        System.out.println("getValueLength");
        final Asn1Enc instance = new Asn1Enc(null);
        int expResult = 0;
        int result = instance.getValueLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncodedLength method, of class Asn1Enc.
     */
    @Test
    public void testGetEncodedLength() {
        System.out.println("getEncodedLength");
        final byte[] enc = {0x19, 0x12};
        Asn1Enc instance = new Asn1Enc(enc);
        int expResult = 2;
        int result = instance.getEncodedLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of fillEncodedValue method, of class Asn1Enc.
     */
    @Test
    public void testFillEncodedValue() {
        System.out.println("fillEncodedValue");
        Asn1Enc instance = new Asn1Enc(null);
        int expResult = 0;
        int result = instance.fillEncodedValue(null, 0);
        assertEquals(expResult, result);
    }

    /**
     * Test of fillEncoded method, of class Asn1Enc.
     */
    @Test
    public void testFillEncoded() {
        System.out.println("fillEncoded");
        final byte[] enc = {0x11, 0x11, 0x11};
        Asn1Enc instance = new Asn1Enc(enc);
        final int off = 0;
        int expResult = 4;
        byte[] result = new byte[3];
        int res = instance.fillEncoded(result, off);
        assertEquals(res, 3);
    }
}
