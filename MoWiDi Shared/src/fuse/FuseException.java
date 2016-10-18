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

public final class FuseException extends Exception implements Errno, Serializable {

	private static final long serialVersionUID = -5788314010453129351L;
	private int errno;

    public FuseException() {
        super();
    }

    public FuseException(final Throwable cause) {
        super(cause);
    }

    public FuseException(final String message) {
        super(message);
    }

    public FuseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FuseException initErrno(final int errno) {
        this.errno = errno;
        return this;
    }

    public int getErrno() {
        return errno;
    }
}
