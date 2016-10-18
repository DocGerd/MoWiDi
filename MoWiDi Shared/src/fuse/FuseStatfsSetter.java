package fuse;

import java.io.Serializable;

/**
 * User: peter
 * Date: Nov 3, 2005
 * Time: 3:17:09 PM
 */
public interface FuseStatfsSetter extends Serializable {

    void set(int blockSize, int blocks, int blocksFree, int blocksAvail, int files, int filesFree, int namelen);
}
