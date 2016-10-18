/*
 * @(#)FuseRunnerTest.java
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
package edu.kit.ibds.mowidi.pc.controller;

import java.net.UnknownHostException;
import java.util.UUID;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.kit.ibds.mowidi.pc.network.ConnectionPC;

/**
 *
 * @author Patrick Kuhn
 */
public class FuseRunnerTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--FuseRunner");
    }

    /**
     * Test of run method, of class FuseRunner.
     * @throws UnknownHostException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRun() throws UnknownHostException {
        System.out.println("run");
        final UUID uuid = UUID.randomUUID();
        final ConnectionPC cp = new ConnectionPC(null, uuid);
        final Fuse fuse = new Fuse(cp);
        final FuseRunner instance = new FuseRunner(fuse, uuid, "C:\\A\\");
        instance.start();
    }

    /**
     * Test of getUID method, of class FuseRunner.
     */
    @Test
    public void testGetUID() {
        System.out.println("getUID");
        final UUID expResult = UUID.randomUUID();
        final FuseRunner instance = new FuseRunner(new Fuse(null), expResult, "C:\\A\\");
        final UUID result = instance.getUID();
        assertEquals(expResult, result);
    }

    /**
     * Test of unmount method, of class FuseRunner.
     */
    @Test
    public void testUnmount() {
        System.out.println("unmount");
        final FuseRunner instance = new FuseRunner(new Fuse(null), UUID.randomUUID(), "C:\\A\\");
        instance.unmount();
    }

    /**
     * Test of update method, of class FuseRunner.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        final FuseRunner instance = new FuseRunner(new Fuse(null), UUID.randomUUID(), "C:\\A\\");
        instance.update(null, null);
    }
}
