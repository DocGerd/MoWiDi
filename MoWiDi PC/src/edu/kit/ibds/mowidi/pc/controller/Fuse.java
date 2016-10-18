/*
 * @(#)Fuse.java
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
 * 2010-06-30   added <tt>available</tt> PK
 */
package edu.kit.ibds.mowidi.pc.controller;

import fuse.FuseException;
import fuse.FuseStatfs;
import fuse.compat.Filesystem1;
import fuse.compat.FuseDirEnt;
import fuse.compat.FuseStat;
import java.nio.ByteBuffer;
import edu.kit.ibds.mowidi.pc.network.ConnectionPC;
import fuse.FuseMount;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the "interface" for the OS.
 * @author Patrick Kuhn
 */
class Fuse extends Observable implements Filesystem1 {

    private static final long serialVersionUID = -1027711372035399148L;
    /** The connection with SSL on which all methods are called afterwards. */
    private final ConnectionPC connection;
    /** The mount-point. */
    private transient String mntPoint;

    /**
     * Create a new FUSE object.
     * @param c the connection for RMI stuff
     */
    public Fuse(final ConnectionPC c) {
        super();
        this.connection = c;
    }

    @Override
    public FuseStat getattr(final String path) throws FuseException {
        try {
            return connection.getAttributes(path);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public String readlink(final String path) throws FuseException {
        throw new FuseException("Readlink is not supported").initErrno(FuseException.ENOSYS);
    }

    @Override
    public FuseDirEnt[] getdir(final String path) throws FuseException {
        try {
            return connection.getDirectory(path);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void mknod(final String path, final int mode, final int rdev) throws FuseException {
        try {
            connection.makeNode(path, mode, rdev);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void mkdir(final String path, final int mode) throws FuseException {
        try {
            connection.makeDirectory(path, mode);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void unlink(final String path) throws FuseException {
        try {
            connection.unlink(path);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void rmdir(final String path) throws FuseException {
        try {
            connection.removeDirectory(path);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void symlink(final String from, final String to) throws FuseException {
        throw new FuseException("Symlinks are not Supported").initErrno(FuseException.ENOSYS);
    }

    @Override
    public void rename(final String from, final String to) throws FuseException {
        try {
            connection.rename(from, to);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void link(final String from, final String to) throws FuseException {
        throw new FuseException("Links are not Supported").initErrno(FuseException.ENOSYS);
    }

    @Override
    public void chmod(final String path, final int mode) throws FuseException {
        try {
            connection.changeMode(path, mode);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void chown(final String path, final int uid, final int gid) throws FuseException {
        try {
            connection.changeOwner(path, uid, gid);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void truncate(final String path, final long size) throws FuseException {
        try {
            connection.truncate(path, size);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void utime(final String path, final int atime, final int mtime) throws FuseException {
        try {
            connection.utime(path, atime, mtime);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public FuseStatfs statfs() throws FuseException {
        try {
            return connection.statFS();
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void open(final String path, final int flags) throws FuseException {
        try {
            connection.open(path, flags);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void read(final String path, final ByteBuffer buf, final long offset) throws FuseException {
        try {
            connection.read(path, buf, offset);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void write(final String path, final ByteBuffer buf, final long offset) throws FuseException {
        try {
            connection.write(path, buf, offset);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    @Override
    public void release(final String path, final int flags) throws FuseException {
        try {
            connection.close(path, flags);
        } catch (FuseException ex) {
            if (ex.getErrno() == FuseException.EHOSTDOWN) {
                notifyObservers(Boolean.TRUE);
            }
            throw ex;
        }
    }

    /**
     * Disconnect this Fuse instance from the partner. This method only
     * releases the connection and does not unmount the file-system!
     */
    public void disconnect() {
        connection.release();
    }

    /**
     * Mount this file system.
     * @param mntPoint the mount point
     */
    public void mount(final String mntPoint) {
        this.mntPoint = mntPoint;
        final Thread t = new Thread(new MountRunner(this, mntPoint));
        t.start();
    }

    /**
     * Unmount from previous set mount point.
     */
    public void unmount() {
        try {
            Runtime.getRuntime().exec("fusermount -u " + mntPoint);
            disconnect();
            final File dir = new File(mntPoint);
            final boolean result = dir.delete();
            if (!result) {
                Logger.getLogger(Fuse.class.getName()).log(Level.FINE, "Deleting obsolete Mountpoint directory failed.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Fuse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static final class MountRunner implements Runnable {

        private final String[] arguments;
        private final String mntPoint;
        private final Fuse runner;

        public MountRunner(final Fuse fr, final String mountPoint) {
            this.mntPoint = mountPoint;
            arguments = new String[]{mntPoint, "-f"};
            //arguments = new String[]{mntPoint, "-s", "-f"};
            runner = fr;
        }

        @Override
        public void run() {
            try {
                final File dir = new File(mntPoint);
                final boolean result = dir.mkdirs();
                if (!result) {
                    Logger.getLogger(MountRunner.class.getName()).log(Level.FINE, "Creating Mountpoint directory failed.");
                }
                FuseMount.mount(arguments, runner);
            } catch (Exception ex) {
                throw new IOError(ex);
            }
        }
    }
}
