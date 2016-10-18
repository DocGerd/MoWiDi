/*
 * @(#)AbstractDeviceTest.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class AbstractDeviceTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--AbstractDevice");
    }

    /**
     * Test of getuID method, of class AbstractDevice.
     */
    @Test
    public void testGetuID() {
        System.out.println("getuID");
        final UUID expResult = UUID.randomUUID();
        final AbstractDevice instance = new AbstractDeviceImpl(expResult);
        final UUID result = instance.getuID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCertificate method, of class AbstractDevice.
     */
    @Test
    public void testGetCertificate() {
        System.out.println("getCertificate");
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCertificate method, of class AbstractDevice.
     */
    @Test
    public void testSetCertificate() {
        System.out.println("setCertificate");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstTimeAdded method, of class AbstractDevice.
     */
    @Test
    public void testGetFirstTimeAdded() {
        System.out.println("getFirstTimeAdded");
        final AbstractDevice instance = new AbstractDeviceImpl(null);
        final Date expResult = Calendar.getInstance().getTime();
        instance.setFirstTimeAdded(expResult);
        final Date result = instance.getFirstTimeAdded();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFirstTimeAdded method, of class AbstractDevice.
     */
    @Test
    public void testSetFirstTimeAdded() {
        System.out.println("setFirstTimeAdded");
        testGetFirstTimeAdded();
    }

    /**
     * Test of getGivenName method, of class AbstractDevice.
     */
    @Test
    public void testGetGivenName() {
        System.out.println("getGivenName");
        final AbstractDevice instance = new AbstractDeviceImpl(null);
        final String expResult = "Horst";
        instance.setGivenName(expResult);
        final String result = instance.getGivenName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGivenName method, of class AbstractDevice.
     */
    @Test
    public void testSetGivenName() {
        System.out.println("setGivenName");
        testGetGivenName();
    }

    /**
     * Test of isAvailable method, of class AbstractDevice.
     */
    @Test
    public void testIsAvailable() {
        System.out.println("isAvailable");
        final AbstractDevice instance = new AbstractDeviceImpl(null);
        final boolean expResult = true;
        instance.setAvailable(expResult);
        final boolean result = instance.isAvailable();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAvailable method, of class AbstractDevice.
     */
    @Test
    public void testSetAvailable() {
        System.out.println("setAvailable");
        testIsAvailable();
    }

    /**
     * Test of isConnected method, of class AbstractDevice.
     */
    @Test
    public void testIsConnected() {
        System.out.println("isConnected");
        AbstractDevice instance = new AbstractDeviceImpl(null);
        boolean expResult = true;
        instance.setConnected(expResult);
        boolean result = instance.isConnected();
        assertEquals(expResult, result);

        expResult = false;
        instance.setConnected(expResult);
        result = instance.isConnected();
        assertEquals(expResult, result);
    }

    /**
     * Test of setConnected method, of class AbstractDevice.
     */
    @Test
    public void testSetConnected() {
        System.out.println("setConnected");
        testIsConnected();
    }

    /**
     * Test of isPermanent method, of class AbstractDevice.
     */
    @Test
    public void testIsPermanent() {
        System.out.println("isPermanent");
        AbstractDevice instance = new AbstractDeviceImpl(null);
        boolean expResult = true;
        instance.setPermanent(expResult);
        boolean result = instance.isPermanent();
        assertEquals(expResult, result);

        expResult = false;
        instance.setPermanent(expResult);
        result = instance.isPermanent();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPermanent method, of class AbstractDevice.
     */
    @Test
    public void testSetPermanent() {
        System.out.println("setPermanent");
        testIsPermanent();
    }

// <editor-fold defaultstate="collapsed" desc="comment">
    private static final class AbstractDeviceImpl extends AbstractDevice {

        private static final long serialVersionUID = -8463150763715575656L;

        public AbstractDeviceImpl(final UUID uid) {
            super(uid);
        }
    }
// </editor-fold>
}
