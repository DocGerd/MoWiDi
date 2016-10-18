/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse.compat;

//import fuse.*;
import fuse.FilesystemConstants;
import fuse.FuseException;
import fuse.FuseStatfs;
import java.nio.ByteBuffer;

/**
 * This is an old compatibility API (renamed from fuse.Filesystem).
 * Use fuse.Filesystem instead for new applications
 */
public interface Filesystem1 extends FilesystemConstants {

    FuseStat getattr(String path) throws FuseException;

    String readlink(String path) throws FuseException;

    FuseDirEnt[] getdir(String path) throws FuseException;

    void mknod(String path, int mode, int rdev) throws FuseException;

    void mkdir(String path, int mode) throws FuseException;

    void unlink(String path) throws FuseException;

    void rmdir(String path) throws FuseException;

    void symlink(String from, String to) throws FuseException;

    void rename(String from, String to) throws FuseException;

    void link(String from, String to) throws FuseException;

    void chmod(String path, int mode) throws FuseException;

    void chown(String path, int uid, int gid) throws FuseException;

    void truncate(String path, long size) throws FuseException;

    void utime(String path, int atime, int mtime) throws FuseException;

    FuseStatfs statfs() throws FuseException;

    void open(String path, int flags) throws FuseException;

    void read(String path, ByteBuffer buf, long offset) throws FuseException;

    void write(String path, ByteBuffer buf, long offset) throws FuseException;

    void release(String path, int flags) throws FuseException;
}
