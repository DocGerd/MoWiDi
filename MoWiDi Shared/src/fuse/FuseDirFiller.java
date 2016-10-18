/*
 * @(#)FuseDirFiller.java
 */
package fuse;

import java.io.Serializable;

/**
 * FuseDirFiller.
 * User: peter
 * Date: Nov 3, 2005
 * Time: 2:54:06 PM
 */
public interface FuseDirFiller extends Serializable {

    /**
     * Add to directory.
     * @param name name
     * @param inode iNode
     * @param mode mode
     */
    void add(String name, long inode, int mode);
}
