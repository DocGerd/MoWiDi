/*
 * @(#)Asn1UtcTest.java
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

import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrick Kuhn
 */
public class Asn1UtcTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1UTC");
    }

    @Test
    public void testVariousUTCTimeRepresentations() {
        final Date date = new Date(1234567890000L);
        Asn1ObjTest.test(new Asn1Utc(date), 23, 13, 0x30, 0x39, 0x30, 0x32, 0x31, 0x33,
                0x32, 0x33, 0x33, 0x31, 0x33, 0x30, 64 + 26);
    }
}
