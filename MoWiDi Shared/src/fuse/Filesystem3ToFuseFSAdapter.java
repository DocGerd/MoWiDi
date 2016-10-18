/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse;

import org.apache.commons.logging.Log;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.BufferOverflowException;
import java.nio.charset.*;
import java.util.Date;

/**
 * This is an adapter that implements fuse.FuseFS byte level API and delegates
 * to the fuse.Filesystem3 String level API. You specify the encoding to be used
 * for file names and paths.
 */
public class Filesystem3ToFuseFSAdapter implements FuseFS {

    private static final long serialVersionUID = 3366365479430305165L;
    private Filesystem3 fs3;
    private XattrSupport xattrSupport;
    private transient Charset cs;
    private transient final Log log;

    public Filesystem3ToFuseFSAdapter(final Filesystem3 fs3, final Log log) {
        this(fs3, System.getProperty("file.encoding", "UTF-8"), log);
    }

    public Filesystem3ToFuseFSAdapter(final Filesystem3 fs3, final String encoding, final Log log) {
        this(fs3, Charset.forName(encoding), log);
    }

    public Filesystem3ToFuseFSAdapter(final Filesystem3 fs3, final Charset cs, final Log log) {
        this.fs3 = fs3;

        // XattrSupport is optional
        if (fs3 instanceof XattrSupport) {
            xattrSupport = (XattrSupport) fs3;
        }

        this.cs = cs;
        this.log = log;
    }

    //
    // FuseFS implementation
    @Override
    public int getattr(final ByteBuffer path, final FuseGetattrSetter getattrSetter) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("getattr: path=" + pathStr);
        }

        try {
            return handleErrno(fs3.getattr(pathStr, getattrSetter), getattrSetter);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int readlink(final ByteBuffer path, final ByteBuffer link) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("readlink: path=" + pathStr);
        }

        CharBuffer linkCb = CharBuffer.allocate(link.capacity());

        try {
            int errno = fs3.readlink(pathStr, linkCb);

            if (errno == 0) {
                linkCb.flip();

                CharsetEncoder enc = cs.newEncoder().onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);

                CoderResult result = enc.encode(linkCb, link, true);
                if (result.isOverflow()) {
                    throw new FuseException("Buffer owerflow while encoding result").initErrno(Errno.ENAMETOOLONG);
                }
            }

            return handleErrno(errno, linkCb.rewind());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int getdir(final ByteBuffer path, final FuseFSDirFiller dirFiller) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("getdir: path=" + pathStr);
        }

        try {
            dirFiller.setCharset(cs);
            return handleErrno(fs3.getdir(pathStr, dirFiller), dirFiller);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int mknod(final ByteBuffer path, final int mode, final int rdev) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("mknod: path=" + pathStr + ", mode=" + Integer.toOctalString(mode) + "(OCT), rdev=" + rdev);
        }

        try {
            return handleErrno(fs3.mknod(pathStr, mode, rdev));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int mkdir(final ByteBuffer path, final int mode) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("mkdir: path=" + pathStr + ", mode=" + Integer.toOctalString(mode) + "(OCT)");
        }

        try {
            return handleErrno(fs3.mkdir(pathStr, mode));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int unlink(final ByteBuffer path) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("unlink: path=" + pathStr);
        }

        try {
            return handleErrno(fs3.unlink(pathStr));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int rmdir(final ByteBuffer path) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("rmdir: path=" + pathStr);
        }

        try {
            return handleErrno(fs3.rmdir(pathStr));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int symlink(final ByteBuffer from, final ByteBuffer to) {
        String fromStr = cs.decode(from).toString();
        String toStr = cs.decode(to).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("symlink: from=" + fromStr + " to=" + toStr);
        }

        try {
            return handleErrno(fs3.symlink(fromStr, toStr));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int rename(final ByteBuffer from, final ByteBuffer to) {
        String fromStr = cs.decode(from).toString();
        String toStr = cs.decode(to).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("rename: from=" + fromStr + " to=" + toStr);
        }

        try {
            return handleErrno(fs3.rename(fromStr, toStr));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int link(final ByteBuffer from, final ByteBuffer to) {
        String fromStr = cs.decode(from).toString();
        String toStr = cs.decode(to).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("link: from=" + fromStr + " to=" + toStr);
        }

        try {
            return handleErrno(fs3.link(fromStr, toStr));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int chmod(final ByteBuffer path, final int mode) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("chmod: path=" + pathStr + ", mode=" + Integer.toOctalString(mode) + "(OCT)");
        }

        try {
            return handleErrno(fs3.chmod(pathStr, mode));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int chown(final ByteBuffer path, final int uid, final int gid) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("chown: path=" + pathStr + ", uid=" + uid + ", gid=" + gid);
        }

        try {
            return handleErrno(fs3.chown(pathStr, uid, gid));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int truncate(final ByteBuffer path, final long size) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("truncate: path=" + pathStr + ", size=" + size);
        }

        try {
            return handleErrno(fs3.truncate(pathStr, size));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int utime(final ByteBuffer path, final int atime, final int mtime) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("utime: path=" + pathStr + ", atime=" + atime + " (" + new Date((long) atime * 1000L) + "), mtime=" + mtime + " (" + new Date((long) mtime * 1000L) + ")");
        }

        try {
            return handleErrno(fs3.utime(pathStr, atime, mtime));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int statfs(final FuseStatfsSetter statfsSetter) {
        if (log != null && log.isDebugEnabled()) {
            log.debug("statfs");
        }

        try {
            return handleErrno(fs3.statfs(statfsSetter), statfsSetter);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int open(final ByteBuffer path, final int flags, final FuseOpenSetter openSetter) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("open: path=" + pathStr + ", flags=" + flags);
        }

        try {
            return handleErrno(fs3.open(pathStr, flags, openSetter), openSetter);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int read(final ByteBuffer path, final Object fh, final ByteBuffer buf, final long offset) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("read: path=" + pathStr + ", fh=" + fh + ", offset=" + offset);
        }

        try {
            return handleErrno(fs3.read(pathStr, fh, buf, offset), buf);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int write(final ByteBuffer path, final Object fh, final boolean isWritepage, final ByteBuffer buf, final long offset) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("write: path=" + pathStr + ", fh=" + fh + ", isWritepage=" + isWritepage + ", offset=" + offset);
        }

        try {
            return handleErrno(fs3.write(pathStr, fh, isWritepage, buf, offset), buf);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int flush(final ByteBuffer path, final Object fh) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("flush: path=" + pathStr + ", fh=" + fh);
        }

        try {
            return handleErrno(fs3.flush(pathStr, fh));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int release(final ByteBuffer path, final Object fh, final int flags) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("release: path=" + pathStr + ", fh=" + fh + ", flags=" + flags);
        }

        try {
            return handleErrno(fs3.release(pathStr, fh, flags));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int fsync(final ByteBuffer path, final Object fh, final boolean isDatasync) {
        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("fsync: path=" + pathStr + ", fh=" + fh + ", isDatasync=" + isDatasync);
        }

        try {
            return handleErrno(fs3.fsync(cs.decode(path).toString(), fh, isDatasync));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    //
    // extended attribute support is optional
    @Override
    public int getxattrsize(final ByteBuffer path, final ByteBuffer name, final FuseSizeSetter sizeSetter) {
        if (xattrSupport == null) {
            return handleErrno(Errno.ENOTSUPP);
        }

        String pathStr = cs.decode(path).toString();
        String nameStr = cs.decode(name).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("getxattrsize: path=" + pathStr + ", name=" + nameStr);
        }

        try {
            return handleErrno(xattrSupport.getxattrsize(pathStr, nameStr, sizeSetter), sizeSetter);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int getxattr(final ByteBuffer path, final ByteBuffer name, final ByteBuffer value) {
        if (xattrSupport == null) {
            return handleErrno(Errno.ENOTSUPP);
        }

        String pathStr = cs.decode(path).toString();
        String nameStr = cs.decode(name).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("getxattr: path=" + pathStr + ", name=" + nameStr);
        }

        try {
            return handleErrno(xattrSupport.getxattr(pathStr, nameStr, value), value);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    //
    // private implementation of XattrLister that estimates the byte size of the attribute names list
    // using Charset of the enclosing Filesystem3ToFuseFSAdapter class
    private class XattrSizeLister implements XattrLister {

        CharsetEncoder enc = cs.newEncoder();
        int size = 0;

        public void add(final String xattrName) {
            size += (int) ((float) xattrName.length() * enc.averageBytesPerChar()) + 1;
        }
    }

    //
    // estimate the byte size of attribute names list...
    @Override
    public int listxattrsize(final ByteBuffer path, final FuseSizeSetter sizeSetter) {
        if (xattrSupport == null) {
            return handleErrno(Errno.ENOTSUPP);
        }

        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("listxattrsize: path=" + pathStr);
        }

        int errno;
        XattrSizeLister lister = new XattrSizeLister();

        try {
            errno = xattrSupport.listxattr(pathStr, lister);
        } catch (Exception e) {
            return handleException(e);
        }

        sizeSetter.setSize(lister.size);

        return handleErrno(errno, sizeSetter);
    }

    //
    // private implementation of XattrLister that encodes list of attribute names into given ByteBuffer
    // using Charset of the enclosing Filesystem3ToFuseFSAdapter class
    private class XattrValueLister implements XattrLister {

        CharsetEncoder enc = cs.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        ByteBuffer list;
        BufferOverflowException boe;

        XattrValueLister(final ByteBuffer list) {
            this.list = list;
        }

        @Override
        public void add(final String xattrName) {
            if (boe == null) // don't need to bother any more if there was an exception already
            {
                try {
                    enc.encode(CharBuffer.wrap(xattrName), list, true);
                    list.put((byte) 0); // each attribute name is terminated by byte 0
                } catch (BufferOverflowException e) {
                    boe = e;
                }
            }
        }

        //
        // for debugging
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("[");
            boolean first = true;

            for (int i = 0; i < list.position(); i++) {
                int offset = i;
                int length = 0;
                while (offset + length < list.position() && list.get(offset + length) != 0) {
                    length++;
                }

                byte[] nameBytes = new byte[length];
                for (int j = 0; j < length; j++) {
                    nameBytes[j] = list.get(offset + j);
                }

                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }

                sb.append('"').append(cs.decode(ByteBuffer.wrap(nameBytes))).append('"');

                i = offset + length;
            }

            sb.append("]");

            return sb.toString();
        }
    }

    //
    // list attributes into given ByteBuffer...
    @Override
    public int listxattr(final ByteBuffer path, final ByteBuffer list) {
        if (xattrSupport == null) {
            return handleErrno(Errno.ENOTSUPP);
        }

        String pathStr = cs.decode(path).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("listxattr: path=" + pathStr);
        }

        int errno;
        XattrValueLister lister = new XattrValueLister(list);

        try {
            errno = xattrSupport.listxattr(pathStr, lister);
        } catch (Exception e) {
            return handleException(e);
        }

        // was there a BufferOverflowException?
        if (lister.boe != null) {
            return handleException(lister.boe);
        }

        return handleErrno(errno, lister);
    }

    @Override
    public int setxattr(final ByteBuffer path, final ByteBuffer name, final ByteBuffer value, final int flags) {
        if (xattrSupport == null) {
            return handleErrno(Errno.ENOTSUPP);
        }

        String pathStr = cs.decode(path).toString();
        String nameStr = cs.decode(name).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("setxattr: path=" + pathStr + ", name=" + nameStr + ", value=" + value + ", flags=" + flags);
        }

        try {
            return handleErrno(xattrSupport.setxattr(pathStr, nameStr, value, flags));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    public int removexattr(final ByteBuffer path, final ByteBuffer name) {
        if (xattrSupport == null) {
            return handleErrno(Errno.ENOTSUPP);
        }

        String pathStr = cs.decode(path).toString();
        String nameStr = cs.decode(name).toString();

        if (log != null && log.isDebugEnabled()) {
            log.debug("removexattr: path= " + pathStr + ", name=" + nameStr);
        }

        try {
            return handleErrno(xattrSupport.removexattr(pathStr, nameStr));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    //
    // private
    private int handleErrno(final int errno) {
        if (log != null && log.isDebugEnabled()) {
            log.debug((errno == 0) ? "  returning with success" : "  returning errno: " + errno);
        }

        return errno;
    }

    private int handleErrno(final int errno, final Object v1) {
        if (errno != 0) {
            return handleErrno(errno);
        }

        if (log != null && log.isDebugEnabled()) {
            log.debug("  returning: " + v1);
        }

        return errno;

    }

//   private int handleErrno(int errno, Object v1, Object v2)
//   {
//      if (errno != 0)
//         return handleErrno(errno);
//
//      if (log != null && log.isDebugEnabled())
//         log.debug("  returning: " + v1 + ", " + v2);
//
//      return errno;
//
//   }
    private int handleException(final Exception e) {
        int errno;

        if (e instanceof FuseException) {
            errno = handleErrno(((FuseException) e).getErrno());
            if (log != null && log.isDebugEnabled()) {
                log.debug(e);
            }
        } else if (e instanceof BufferOverflowException) {
            errno = handleErrno(Errno.ERANGE);
            if (log != null && log.isDebugEnabled()) {
                log.debug(e);
            }
        } else {
            errno = handleErrno(Errno.EFAULT);
            if (log != null) {
                log.error(e);
            }
        }

        return errno;
    }
}
