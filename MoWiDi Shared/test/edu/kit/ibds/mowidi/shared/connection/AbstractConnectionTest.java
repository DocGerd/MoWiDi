/*
 * @(#)AbstractConnectioTest.java
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
 * Tester for {@link AbstractConnection}.
 * @author Patrick Kuhn
 */
public class AbstractConnectionTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--AbstractConnection");
    }

    @Test
    public void testCreateCertificate() {
        System.out.println("createCertificate");
        fail("Test not implemented yet.");
    }

    @Test
    public void testGetFarCertificate() {
        System.out.println("getFarCertificate");
        fail("Test not implemented yet.");
    }

    @Test
    public void testGetOwnCertificate() {
        System.out.println("getOwnCertificate");
        fail("Test not implemented yet.");
    }

// <editor-fold defaultstate="collapsed" desc="AbstractConnectionImpl">
    private static class AbstractConnectionImpl extends AbstractConnection {

        @Override
        public void release() {
            // do nothing
        }
    }
    // </editor-fold>
}
