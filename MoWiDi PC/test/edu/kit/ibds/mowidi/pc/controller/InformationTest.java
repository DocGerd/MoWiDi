/*
 * @(#)InformationTest.java
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
 * 2010-08-04   completed PK
 */
package edu.kit.ibds.mowidi.pc.controller;

import java.util.LinkedList;
import java.util.UUID;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Settings;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class InformationTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Information");
    }

    @Test
    public void testInformation() {
        System.out.println("Information");
        final List<MobileDevice> list = new LinkedList<MobileDevice>();
        list.add(new MobileDevice(UUID.randomUUID()));
        final Settings settings = new Settings();
        final ErrorType et = ErrorType.CONNECTION_LOST;
        new Information(list, settings, et);
    }

    /**
     * Test of getError method, of class Information.
     */
    @Test
    public void testGetError() {
        System.out.println("getError");
        final Information instance = new Information(ErrorType.CERTIFICATE_ERROR);
        final ErrorType expResult = ErrorType.CERTIFICATE_ERROR;
        final ErrorType result = instance.getError();
        assertEquals(expResult, result);
    }

    /**
     * Test of getModified method, of class Information.
     */
    @Test
    public void testGetModified() {
        System.out.println("getModified");
        final MobileDevice expResult = new MobileDevice(UUID.randomUUID());
        final MobileDevice[] mds = new MobileDevice[]{expResult};
        final Information instance = new Information(mds);
        final List<MobileDevice> result = instance.getModified();
        assertSame(result.size(), 1);
        assertSame(result.get(0), expResult);
    }

    /**
     * Test of getSettings method, of class Information.
     */
    @Test
    public void testGetSettings() {
        System.out.println("getSettings");
        final Settings expResult = new Settings();
        final Information instance = new Information(expResult);
        final Settings result = instance.getSettings();
        assertEquals(expResult, result);
    }
}
