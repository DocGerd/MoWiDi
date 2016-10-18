/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse;


import fuse.util.AbstractStruct;

public class FuseFtype extends AbstractStruct implements FuseFtypeConstants {

    private static final long serialVersionUID = -4274494140241271720L;
    public int mode;

    @Override
    protected boolean appendAttributes(final StringBuilder buff, final boolean isPrefixed) {
        buff.append(super.appendAttributes(buff, isPrefixed) ? ", " : " ").
                append("mode=").append(Integer.toOctalString(mode)).append("(OCT)");

        return true;
    }
}
