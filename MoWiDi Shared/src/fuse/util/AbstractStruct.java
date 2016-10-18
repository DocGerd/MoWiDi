/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse.util;

import java.io.Serializable;

public abstract class AbstractStruct implements Cloneable, Serializable {

    private static final long serialVersionUID = 5432196833248618955L;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getName());

        return sb.append("[ ").append(appendAttributes(sb, false) ? ", " : "").append("hashCode=").
                append(hashCode()).append(" ]").toString();
    }

    protected boolean appendAttributes(final StringBuilder buff, final boolean isPrefixed) {
        return isPrefixed;
    }
}
