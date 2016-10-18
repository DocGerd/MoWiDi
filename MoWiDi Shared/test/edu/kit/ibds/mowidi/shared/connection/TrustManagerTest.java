/*
 * @(#)TrustManagerTest.java
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
package edu.kit.ibds.mowidi.shared.connection;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class TrustManagerTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--TrustManager");
    }

    /**
     * Test of cert2Strings method, of class TrustManager.
     * @throws Exception
     */
    @Test
    public void testCert2Strings() throws Exception {
        System.out.println("cert2Strings");
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkClientTrusted method, of class TrustManager.
     * @throws Exception
     */
    @Test
    public void testCheckClientTrusted() throws Exception {
        System.out.println("checkClientTrusted");
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkServerTrusted method, of class TrustManager.
     */
    @Test
    public void testCheckServerTrusted() throws Exception {
        System.out.println("checkServerTrusted");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAcceptedIssuers method, of class TrustManager.
     */
    @Test
    public void testGetAcceptedIssuers() {
        System.out.println("getAcceptedIssuers");
        fail("The test case is a prototype.");
    }
}
