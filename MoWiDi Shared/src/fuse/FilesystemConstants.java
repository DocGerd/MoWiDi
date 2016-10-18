/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse;

import java.io.Serializable;

/**
 * Filesystem constants common to all filesystem interfaces.
 */
public interface FilesystemConstants extends Serializable {

    /** Read only. */
    int O_RDONLY = 00;
    /** Write only. */
    int O_WRONLY = 01;
    /** Read and write. */
    int O_RDWR = 02;
}
