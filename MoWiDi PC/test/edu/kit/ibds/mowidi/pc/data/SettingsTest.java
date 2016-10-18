/*
 * @(#)SettingsTest.java
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
package edu.kit.ibds.mowidi.pc.data;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tester for <tt>Settings</tt>.
 * @author Patrick Kuhn
 */
public final class SettingsTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Settings");
    }

    /**
     * Test of getNumberTrayView method, of class Settings.
     */
    @Test
    public void testGetNumberTrayView() {
        System.out.println("getNumberTrayView");
        final Settings instance = new Settings();
        final int expResult = 5;
        final int result = instance.getNumberTrayView();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNumberTrayView method, of class Settings.
     */
    @Test
    public void testSetNumberTrayView() {
        System.out.println("setNumberTrayView");
        final int numberTrayView = 2;
        final Settings instance = new Settings();
        instance.setNumberTrayView(numberTrayView);
        assertEquals(instance.getNumberTrayView(), numberTrayView);
    }

    /**
     * Test of setNumberTrayView method, of class Settings.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNumberTrayView2() {
        System.out.println("setNumberTrayView w/ exception");
        final int numberTrayView = -1;
        final Settings instance = new Settings();
        instance.setNumberTrayView(numberTrayView);
    }

    /**
     * Test of getStandardMountPoint method, of class Settings.
     */
    @Test
    public void testGetStandardMountPoint() {
        System.out.println("getStandardMountPoint");
        final Settings instance = new Settings();
        final String expResult = System.getProperty("user.home") + "/MoWiDi";
        final String result = instance.getStandardMountPoint();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStandardMountPoint method, of class Settings.
     */
    @Test
    public void testSetStandardMountPoint() {
        System.out.println("setStandardMountPoint");
        final String standardMountPoint = System.getProperty("user.home") + "/NotMoWiDi";
        final Settings instance = new Settings();
        instance.setStandardMountPoint(standardMountPoint);
        assertEquals(instance.getStandardMountPoint(), standardMountPoint);
    }
}
