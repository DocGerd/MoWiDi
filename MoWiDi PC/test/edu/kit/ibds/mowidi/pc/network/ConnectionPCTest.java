/*
 * @(#)ConnectionPCTest.java
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
package edu.kit.ibds.mowidi.pc.network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;
import fuse.FuseStatfs;
import fuse.compat.FuseDirEnt;
import fuse.compat.FuseStat;
import java.nio.ByteBuffer;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class ConnectionPCTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--ConnectionPC");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectionPC() throws UnknownHostException, IOException {
        System.out.println("ConnectionPC");
        new ConnectionPC(null, UUID.randomUUID());
    }

    /**
     * Test of release method, of class ConnectionPC.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRelease() {
        System.out.println("release");
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.release();
    }

    /**
     * Test of doNothing method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDoNothing() throws Exception {
        System.out.println("doNothing");
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.doNothing();
    }

    /**
     * Test of getAttributes method, of class ConnectionPC.
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAttributes() throws Exception {
        System.out.println("getAttributes");
        final String path = "";
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        final FuseStat result = instance.getAttributes(path);
        assertNull(result);
    }

    /**
     * Test of open method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOpen() throws Exception {
        System.out.println("open");
        final String path = "";
        final int flags = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.open(path, flags);
    }

    /**
     * Test of statFS method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStatFS() throws Exception {
        System.out.println("statFS");
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        final FuseStatfs result = instance.statFS();
        assertNull(result);
    }

    /**
     * Test of read method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRead() throws Exception {
        System.out.println("read");
        final String path = "";
        final ByteBuffer buf = ByteBuffer.allocate(8);
        final long offset = 0L;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        final ByteBuffer result = instance.read(path, buf, offset);
        assertNull(result);
    }

    /**
     * Test of write method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrite() throws Exception {
        System.out.println("write");
        final String path = "";
        final ByteBuffer buf = ByteBuffer.allocate(8);
        final long offset = 0L;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.write(path, buf, offset);
    }

    /**
     * Test of makeDirectory method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMakeDirectory() throws Exception {
        System.out.println("makeDirectory");
        final String path = "";
        final int mode = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.makeDirectory(path, mode);
    }

    /**
     * Test of removeDirectory method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDirectory() throws Exception {
        System.out.println("removeDirectory");
        final String path = "";
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.removeDirectory(path);
    }

    /**
     * Test of unlink method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnlink() throws Exception {
        System.out.println("unlink");
        final String path = "";
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.unlink(path);
    }

    /**
     * Test of makeNode method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMakeNode() throws Exception {
        System.out.println("makeNode");
        final String path = "";
        final int mode = 0;
        final int rdev = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.makeNode(path, mode, rdev);
    }

    /**
     * Test of getDirectory method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetDirectory() throws Exception {
        System.out.println("getDirectory");
        final String path = "";
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        final FuseDirEnt[] result = instance.getDirectory(path);
        assertNull(result);
    }

    /**
     * Test of rename method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRename() throws Exception {
        System.out.println("rename");
        final String from = "";
        final String to = "";
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.rename(from, to);
    }

    /**
     * Test of changeMode method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeMode() throws Exception {
        System.out.println("changeMode");
        final String path = "";
        final int mode = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.changeMode(path, mode);
    }

    /**
     * Test of changeOwner method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeOwner() throws Exception {
        System.out.println("changeOwner");
        final String path = "";
        final int uID = 0;
        final int gID = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.changeOwner(path, uID, gID);
    }

    /**
     * Test of truncate method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTruncate() throws Exception {
        System.out.println("truncate");
        final String path = "";
        final long size = 0L;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.truncate(path, size);
    }

    /**
     * Test of utime method, of class ConnectionPC.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUtime() throws Exception {
        System.out.println("utime");
        final String path = "";
        final int aTime = 0;
        final int mTime = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.utime(path, aTime, mTime);
    }

    /**
     * Test of close method, of class ConnectionPC.
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testClose() throws Exception {
        System.out.println("close");
        final String path = "";
        final int flags = 0;
        final ConnectionPC instance = new ConnectionPC(null, UUID.randomUUID());
        instance.close(path, flags);
    }
}
