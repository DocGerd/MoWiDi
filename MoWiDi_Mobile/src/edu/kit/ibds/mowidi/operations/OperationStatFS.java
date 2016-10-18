/*
 * @(#)Operation
 *
 * This file is part of MoWiDi.
 *
 * MoWiDi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoWiDi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MoWiDi. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2010 by PSE23-Team:
 *
 * Patrick Kuhn, Michael Auracher,
 * André Wengert, Kim Spieß, Christopher Schütze
 */
package edu.kit.ibds.mowidi.operations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Environment;
import android.os.StatFs;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseStatfs;

/**
 *
 * @author Michael Auracher
 */
public final class OperationStatFS extends AbstractOperation {

    private static final long serialVersionUID = -4090549829384661996L;

    static {
        minAuthorizationType = AuthorizationType.READ_ONLY;
    }

    @Override
    public void doOperation() {
        FuseStatfs statfs = new FuseStatfs();
        File root = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(root.getAbsolutePath());
        List<File> fileList = getFileListing(root);
        statfs.blockSize = stat.getBlockSize();
        statfs.blocksFree = stat.getFreeBlocks();
        statfs.blocks = stat.getBlockCount();
        statfs.blocksAvail = stat.getAvailableBlocks();
        int files = 0;
        int dirs = 0;
        for (File file : fileList) {
            if (file.isDirectory()) {
                dirs++;
            } else {
                files++;
            }
        }
        statfs.files = files + dirs;
        statfs.filesFree = (1 << 28) - statfs.files;
        statfs.namelen = 255;
        savedObject = statfs;
    }

    /**
     * Recursive helper function to get a List with every Directory and
     * File on the SD-Card. It lists the roots directory and moves down one
     * recursion every time it gets to a directory. Afterwards it appends the
     * deeper file list to the one already existing.
     * @param root the root
     * @return the file listing
     */
    private List<File> getFileListing(final File root) {
        List<File> result = new ArrayList<File>();
        File[] subFiles = root.listFiles();
        if (subFiles != null) {
            List<File> filesDirs = Arrays.asList(subFiles);
            for (File file : filesDirs) {
                result.add(file);
                if (file.isDirectory()) {
                    List<File> subList = getFileListing(file);
                    result.addAll(subList);
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Operation STATFS with result " + savedObject;
    }
}
