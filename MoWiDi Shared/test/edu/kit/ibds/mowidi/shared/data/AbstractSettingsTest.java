/*
 * @(#)AbstractSettingsTest.java
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
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tester for <tt>AbstractSettingsTest</tt>.
 * @author Patrick Kuhn
 */
public class AbstractSettingsTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--AbstractSettings");
    }

    /**
     * Test the constructor.
     */
    @Test
    public void testAbstractSettings() {
        System.out.println("AbstractSettings");
        Locale.setDefault(Locale.CHINESE);
        final AbstractSettings instance = new AbstractSettingsImpl();
        assertTrue(instance.getLanguage().equals(Language.ENGLISH));
    }

    /**
     * Test of getUID method, of class AbstractSettings.
     */
    @Test
    public void testGetUID() {
        System.out.println("getUID");
        final AbstractSettings instance = new AbstractSettingsImpl();
        instance.getUID();
    }

    /**
     * Test of getLanguage method, of class AbstractSettings.
     */
    @Test
    public void testGetLanguage() {
        System.out.println("getLanguage");
        final AbstractSettings instance = new AbstractSettingsImpl();
        Language expResult = Language.ITALIAN;
        instance.setLanguage(expResult);
        Language result = instance.getLanguage();
        assertEquals(expResult, result);

        expResult = Language.FRENCH;
        instance.setLanguage(expResult);
        result = instance.getLanguage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLanguage method, of class AbstractSettings.
     */
    @Test
    public void testSetLanguage() {
        System.out.println("setLanguage");
        testGetLanguage();
    }

    /**
     * Test of setLanguage method, of class AbstractSettings.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetLanguage2() {
        System.out.println("setLanguage w/ exception");
        final AbstractSettings instance = new AbstractSettingsImpl();
        instance.setLanguage(null);
    }

    /**
     * Test of getGivenName method, of class AbstractSettings.
     */
    @Test
    public void testGetGivenName() {
        System.out.println("getGivenName");
        final AbstractSettings instance = new AbstractSettingsImpl();
        final String expResult = "Horst";
        instance.setGivenName(expResult);
        final String result = instance.getGivenName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGivenName method, of class AbstractSettings.
     */
    @Test
    public void testSetGivenName() {
        System.out.println("setGivenName");
        testGetGivenName();
    }

    /**
     * Test of setGivenName method, of class AbstractSettings.
     */
    @Test
    public void testSetGivenName2() {
        System.out.println("setGivenName");
        final AbstractSettings instance = new AbstractSettingsImpl();
        instance.setGivenName(null);
        assertTrue(instance.getUID().toString().equals(instance.getGivenName()));
    }

// <editor-fold defaultstate="collapsed" desc="Implementations of abstract class">
    private static final class AbstractSettingsImpl extends AbstractSettings {

        private static final long serialVersionUID = -4819430728215491225L;
    }
// </editor-fold>
}
