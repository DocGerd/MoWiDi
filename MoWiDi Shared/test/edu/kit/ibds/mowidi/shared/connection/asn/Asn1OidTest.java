/*
 * @(#)Asn1OidTest.java
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
import static org.junit.Assert.*;

/**
 * Everything covered by other tests.
 * @author Patrick Kuhn
 */
public class Asn1OidTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Asn1Oid");
    }

    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        Oid oid = Oid.valueOf("CN");
        assertTrue(oid == Oid.CN);
    }
}
