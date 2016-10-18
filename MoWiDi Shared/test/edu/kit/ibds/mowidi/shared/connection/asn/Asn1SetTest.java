/*
 * @(#)Asn1SetTest.java
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

/**
 *
 * @author Patrick Kuhn
 */
public class Asn1SetTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1Set");
    }

    @Test
    public void testVariousSetRepresentations() {
        Asn1Set set;
        set = new Asn1Set(
                new Asn1Str("testing"),
                new Asn1Oid(Oid.CN));
        Asn1ObjTest.test(set, 0x31, 14, 6, 3, 85, 4, 3, 19, 7, 116, 101, 115, 116, 105, 110, 103);
    }
}
