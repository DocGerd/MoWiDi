/*
 * @(#)FuseTest.java
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

import edu.kit.ibds.mowidi.pc.network.ConnectionPC;
import fuse.FuseStatfs;
import fuse.compat.FuseDirEnt;
import fuse.compat.FuseStat;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class FuseTest {

    private final boolean testable;

    public FuseTest() {
        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            testable = false;
        } else {
            testable = true;
        }
    }

    public void checkTestable() {
        if (!testable) {
            fail("Test not available on Windows!");
        }
    }

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Fuse");
    }

    /**
     * Test of getattr method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetattr() throws Exception {
        System.out.println("getattr");
        checkTestable();
        final String path = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        final FuseStat expResult = null;
        final FuseStat result = instance.getattr(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of readlink method, of class Fuse.
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReadlink() throws Exception {
        System.out.println("readlink");
        checkTestable();
        final String path = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        final String expResult = "";
        final String result = instance.readlink(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of getdir method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetdir() throws Exception {
        System.out.println("getdir");
        checkTestable();
        final String path = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        final FuseDirEnt[] expResult = null;
        final FuseDirEnt[] result = instance.getdir(path);
        assertTrue(expResult == result);
    }

    /**
     * Test of mknod method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMknod() throws Exception {
        System.out.println("mknod");
        checkTestable();
        final String path = "";
        final int mode = 0;
        final int rdev = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.mknod(path, mode, rdev);
    }

    /**
     * Test of mkdir method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMkdir() throws Exception {
        System.out.println("mkdir");
        checkTestable();
        final String path = "";
        final int mode = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.mkdir(path, mode);
    }

    /**
     * Test of unlink method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnlink() throws Exception {
        System.out.println("unlink");
        checkTestable();
        final String path = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.unlink(path);
    }

    /**
     * Test of rmdir method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRmdir() throws Exception {
        System.out.println("rmdir");
        checkTestable();
        final String path = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.rmdir(path);
    }

    /**
     * Test of symlink method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSymlink() throws Exception {
        System.out.println("symlink");
        checkTestable();
        final String from = "";
        final String to = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.symlink(from, to);
    }

    /**
     * Test of rename method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRename() throws Exception {
        System.out.println("rename");
        checkTestable();
        final String from = "";
        final String to = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.rename(from, to);
    }

    /**
     * Test of link method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLink() throws Exception {
        System.out.println("link");
        checkTestable();
        final String from = "";
        final String to = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.link(from, to);
    }

    /**
     * Test of chmod method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChmod() throws Exception {
        System.out.println("chmod");
        checkTestable();
        final String path = "";
        final int mode = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.chmod(path, mode);
    }

    /**
     * Test of chown method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChown() throws Exception {
        System.out.println("chown");
        checkTestable();
        final String path = "";
        final int uid = 0;
        final int gid = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.chown(path, uid, gid);
    }

    /**
     * Test of truncate method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTruncate() throws Exception {
        System.out.println("truncate");
        checkTestable();
        final String path = "";
        final long size = 0L;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.truncate(path, size);
    }

    /**
     * Test of utime method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUtime() throws Exception {
        System.out.println("utime");
        checkTestable();
        final String path = "";
        final int atime = 0;
        final int mtime = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.utime(path, atime, mtime);
    }

    /**
     * Test of statfs method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStatfs() throws Exception {
        System.out.println("statfs");
        checkTestable();
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        final FuseStatfs expResult = null;
        final FuseStatfs result = instance.statfs();
        assertEquals(expResult, result);
    }

    /**
     * Test of open method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOpen() throws Exception {
        System.out.println("open");
        checkTestable();
        final String path = "";
        final int flags = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.open(path, flags);
    }

    /**
     * Test of read method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRead() throws Exception {
        System.out.println("read");
        checkTestable();
        final String path = "";
        final ByteBuffer buf = ByteBuffer.allocate(8);
        final long offset = 0L;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.read(path, buf, offset);
    }

    /**
     * Test of write method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrite() throws Exception {
        System.out.println("write");
        checkTestable();
        final String path = "";
        final ByteBuffer buf = ByteBuffer.allocate(8);
        final long offset = 0L;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.write(path, buf, offset);
    }

    /**
     * Test of release method, of class Fuse.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRelease() throws Exception {
        System.out.println("release");
        checkTestable();
        final String path = "";
        final int flags = 0;
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.release(path, flags);
    }

    /**
     * Test of disconnect method, of class Fuse.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDisconnect() {
        System.out.println("disconnect");
        checkTestable();
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.disconnect();
    }

    /**
     * Test of mount method, of class Fuse.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMount() {
        System.out.println("mount");
        checkTestable();
        final String mntPoint = "";
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.mount(mntPoint);
    }

    /**
     * Test of unmount method, of class Fuse.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnmount() {
        System.out.println("unmount");
        checkTestable();
        final ConnectionPC cp = new ConnectionPC(null, UUID.randomUUID());
        final Fuse instance = new Fuse(cp);
        instance.unmount();
    }
}
