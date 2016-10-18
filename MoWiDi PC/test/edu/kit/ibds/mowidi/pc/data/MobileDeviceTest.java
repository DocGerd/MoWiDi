/*
 * @(#)MobileDeviceTest.java
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
package edu.kit.ibds.mowidi.pc.data;

import java.net.UnknownHostException;
import java.util.UUID;
import java.net.InetAddress;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class MobileDeviceTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--MobileDevice");
    }

    /**
     * Test of getAddress method, of class MobileDevice.
     * @throws UnknownHostException 
     */
    @Test
    public void testGetAddress() throws UnknownHostException {
        System.out.println("getAddress");
        final MobileDevice instance = new MobileDevice(UUID.randomUUID());
        final InetAddress expResult = InetAddress.getByName("localhost");
        instance.setAddress(expResult);
        final InetAddress result = instance.getAddress();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMountPoint method, of class MobileDevice.
     */
    @Test
    public void testGetMountPoint() {
        System.out.println("getMountPoint");
        final MobileDevice instance = new MobileDevice(UUID.randomUUID());
        final String expResult = "C:/MoWiDi";
        instance.setMountPoint(expResult);

        final String result = instance.getMountPoint();
        assertEquals(expResult, result);
    }
}
