package fuse;

import fuse.util.AbstractStruct;

/**
 * User: peter
 * Date: Nov 16, 2005
 * Time: 10:04:26 AM
 */
public class FuseSize extends AbstractStruct implements FuseSizeSetter {

    private static final long serialVersionUID = -2539142540224233143L;
    public int size;

    //
    // FuseSizeSetter implementation
    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    protected boolean appendAttributes(StringBuilder buff, boolean isPrefixed) {
        buff.append(super.appendAttributes(buff, isPrefixed) ? ", " : " ");

        buff.append("size=").append(size);

        return true;
    }
}
