package fuse;

import fuse.util.AbstractStruct;

/**
 * Java counterpart of struct fuse_context FUSE C API.
 * Every instance is filled-in with current Thread's active FUSE context which is
 * only relevant for the duration of a filesystem operation
 */
public final class FuseContext extends AbstractStruct {

    private static final long serialVersionUID = 8439999620278587029L;
    public int uid;
    public int gid;
    public int pid;

    private FuseContext() {
        super();
    }

    public static FuseContext get() {
        FuseContext fuseContext = new FuseContext();
        fuseContext.fillInFuseContext();
        return fuseContext;
    }

    @Override
    protected boolean appendAttributes(final StringBuilder buff, final boolean isPrefixed) {
        buff.append(super.appendAttributes(buff, isPrefixed) ? ", " : " ");

        buff.append("uid=").append(uid).append(", gid=").append(gid).append(", pid=").append(pid);

        return true;
    }

    private native void fillInFuseContext();
}
