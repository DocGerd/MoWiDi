/*
 * @(#)MainControllerTest.java
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
 * 2010-08-06   created PK
 */
package edu.kit.ibds.mowidi.pc.controller;

import java.util.UUID;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Settings;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrick Kuhn
 */
public class MainControllerTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--MainController");
    }

    /**
     * Test of closeConnection method, of class MainController.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        final MobileDevice m = new MobileDevice(UUID.randomUUID());
        final MainController instance = new MainController();
        instance.closeConnection(m);
    }

    /**
     * Test of manualSearch method, of class MainController.
     */
    @Test
    public void testManualSearch() {
        System.out.println("manualSearch");
        final MainController instance = new MainController();
        instance.manualSearch();
    }

    /**
     * Test of setProperties method, of class MainController.
     */
    @Test
    public void testSetProperties() {
        System.out.println("setProperties");
        final MobileDevice m = new MobileDevice(UUID.randomUUID());
        final MainController instance = new MainController();
        instance.setProperties(m);
    }

    /**
     * Test of setSettings method, of class MainController.
     */
    @Test
    public void testSetSettings() {
        System.out.println("setSettings");
        final Settings s = new Settings();
        final MainController instance = new MainController();
        instance.setSettings(s);
    }

    /**
     * Test of startConnection method, of class MainController.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStartConnection() {
        System.out.println("startConnection");
        final UUID uuid = UUID.randomUUID();
        final MobileDevice m = new MobileDevice(uuid);
        final MainController instance = new MainController();
        instance.startConnection(m);
    }
}
