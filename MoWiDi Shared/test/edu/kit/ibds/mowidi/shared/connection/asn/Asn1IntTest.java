/*
 * @(#)Asn1IntTest.java
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
 * 2010-08-12   completed PK
 */
package edu.kit.ibds.mowidi.shared.connection.asn;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrick Kuhn
 */
public class Asn1IntTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1Int");
    }

    private void test(int val, int... icmp) {
        Asn1ObjTest.test(new Asn1Int(val), icmp);
    }

    private void testUs(int val, int... icmp) {
        Asn1ObjTest.test(new Asn1Int(val, true), icmp);
    }

    private void test(byte[] intnum, int... icmp) {
        Asn1ObjTest.test(new Asn1Int(intnum), icmp);
    }

    private void testLong(int size, int... icmp) {
        byte[] test = new byte[size];
        byte[] cmp = new byte[size + icmp.length];
        int i;

        for (i = 0; i < icmp.length; i++) {
            cmp[i] = (byte) icmp[i];
        }
        cmp[icmp.length] = test[0] = (byte) 1;

        Asn1ObjTest.test(new Asn1Int(test), cmp);
    }

    @Test
    public void testVariousIntegerRepresentations() {
        test(0, 2, 1, 0);
        test(-1, 2, 1, 0xff);
        test(2, 2, 1, 2);
        test(256, 2, 2, 1, 0);
        test(-256, 2, 2, -1, 0);
        test(-512, 2, 2, -2, 0);
        test(65535, 2, 3, 0, -1, -1);
        test(-65535, 2, 3, -1, 0, 1);
        test(2147483647, 2, 4, 127, -1, -1, -1);
        test(-2147483648, 2, 4, -128, 0, 0, 0);

        testUs(511, 2, 2, 1, -1);
        testUs(-511, 2, 5, 0, -1, -1, -2, 1);

        testLong(127, 2, 127);
        testLong(128, 2, -127, 128);
        testLong(255, 2, -127, 255);
        testLong(256, 2, -126, 1, 0);
        testLong(423, 2, -126, 1, 167);
    }
}
