/*
 * @(#)FuseFtypeConstants.java
 */
package fuse;

import java.io.Serializable;

/**
 * FuseFtypeConstants.
 * User: peter
 * Date: Nov 11, 2005
 * Time: 10:14:14 AM
 */
public interface FuseFtypeConstants extends Serializable {
    // file type 'mode' bits

    /** Bit-mask for the file type bit-fields. */
    int TYPE_MASK = 0170000;
    /** Socket. */
    int TYPE_SOCKET = 0140000;
    /** Symbolic link. */
    int TYPE_SYMLINK = 0120000;
    /** Regular file. */
    int TYPE_FILE = 0100000;
    /** Block device. */
    int TYPE_BLOCKDEV = 0060000;
    /** Directory. */
    int TYPE_DIR = 0040000;
    /** Character device. */
    int TYPE_CHARDEV = 0020000;
    /** FIFO. */
    int TYPE_FIFO = 0010000;
}
