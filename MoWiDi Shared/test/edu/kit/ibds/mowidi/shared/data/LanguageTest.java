/*
 * @(#)LanguageTest.java
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
 * 2010-08-03   completed PK
 */
package edu.kit.ibds.mowidi.shared.data;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public final class LanguageTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Language");
    }

    /**
     * Test of getLanguageCode method, of class Language.
     */
    @Test
    public void testGetLanguageCode() {
        System.out.println("getLanguageCode");
        final Language instance = Language.ENGLISH;
        final String expResult = "en";
        final String result = instance.getLanguageCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Language.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        final Language instance = Language.FRENCH;
        final String expResult = "Francais";
        final String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test valueOf.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        final Language ger = Language.valueOf("GERMAN");
        assertSame(ger, Language.GERMAN);
    }
}
