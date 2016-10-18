/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import fuse.compat.Filesystem1;
import fuse.compat.Filesystem1ToFilesystem2Adapter;
import fuse.compat.Filesystem2;
import fuse.compat.Filesystem2ToFilesystem3Adapter;

public final class FuseMount {

    private static final Log LOG = LogFactory.getLog(FuseMount.class);

    static {
        System.loadLibrary("javafs");
    }

    private FuseMount() {
        // no instances
    }

    //
    // compatibility APIs
    public static void mount(final String[] args, final Filesystem1 filesystem1) throws Exception {
        mount(args, new Filesystem2ToFilesystem3Adapter(new Filesystem1ToFilesystem2Adapter(filesystem1)), LogFactory.getLog(filesystem1.getClass()));
    }

    public static void mount(final String[] args, final Filesystem2 filesystem2) throws Exception {
        mount(args, new Filesystem2ToFilesystem3Adapter(filesystem2), LogFactory.getLog(filesystem2.getClass()));
    }

    //
    // prefered String level API
    public static void mount(final String[] args, final Filesystem3 filesystem3, final Log log) throws Exception {
        mount(args, new Filesystem3ToFuseFSAdapter(filesystem3, log));
    }

    //
    // byte level API
    public static void mount(final String[] args, final FuseFS fuseFS) throws Exception {
        ThreadGroup threadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), "FUSE Threads");
        threadGroup.setDaemon(true);

        LOG.info("Mounting filesystem");

        mount(args, fuseFS, threadGroup);

        LOG.info("Filesystem is unmounted");

        if (LOG.isDebugEnabled()) {
            int n = threadGroup.activeCount();
            LOG.debug("ThreadGroup(\"" + threadGroup.getName() + "\").activeCount() = " + n);

            Thread[] threads = new Thread[n];
            threadGroup.enumerate(threads);
            for (int i = 0; i < threads.length; i++) {
                LOG.debug("thread[" + i + "] = " + threads[i] + ", isDaemon = " + threads[i].isDaemon());
            }
        }
    }

    private static native void mount(String[] args, FuseFS fuseFS, ThreadGroup threadGroup) throws Exception;
}
