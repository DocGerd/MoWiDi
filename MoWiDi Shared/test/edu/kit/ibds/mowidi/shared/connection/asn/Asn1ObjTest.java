/*
 * @(#)Asn1ObjTest.java
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

import org.junit.Test;
import java.util.Formatter;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class Asn1ObjTest {

    private static final Formatter form = new Formatter(System.out);

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Ans1Obj");
    }

    @Test
    public void test() {
        assertTrue(true);
    }

    private static boolean byteArrayEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        if (a.length > 0) {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void outputBytes(String label, byte[] data) {
        System.out.print(label);
        for (int i = 0; i < data.length; i++) {
            form.format(" %02x", data[i]);
        }
        System.out.println();
    }

    private static byte[] intToByte(int[] iarr) {
        byte[] res = new byte[iarr.length];

        for (int i = 0; i < iarr.length; i++) {
            res[i] = (byte) iarr[i];
        }

        return res;
    }

    public static void test(Asn1Obj test, byte[] cmp) {
        byte[] res;

        res = test.encodeDER();
        outputBytes("expected:", cmp);
        outputBytes("got:     ", res);
        assertSame(cmp.length, res.length);
        assertTrue(byteArrayEqual(cmp, res));
    }

    public static void test(Asn1Obj test, int... icmp) {
        byte[] cmp;

        cmp = intToByte(icmp);
        test(test, cmp);
    }
}
