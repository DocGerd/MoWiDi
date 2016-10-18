/*
 * @(#)FuseRunner.java
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
 *
 * 2010-07-09   thread safety PK
 */
package edu.kit.ibds.mowidi.pc.controller;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread which will run nearly independently from the rest of the programme.
 * It is used to encapsulate the FUSE logic in an extra Thread.
 *
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
class FuseRunner extends Thread implements Observer {

    /** The FUSE instance. */
    private final Fuse fuse;
    /** uID of corresponding mobile phone. */
    private final UUID uID;
    /** Specifies the Mount point for the file-system. */
    private final String mntPoint;
    /**  States if the file-system shall be unmounted. */
    private boolean requestUnmount;
    /** A lock for the thread. */
    private final Object lock;

    /**
     * Constructor creating a new FuseRunner Thread. It needs a Fuse file-system
     * and a uID.
     * @param f the file-system which shall be executed in this thread.
     * @param uid the uID so one can identify the corresponding Mobile Phone
     * @param mntPoint the mount-point
     */
    public FuseRunner(final Fuse f, final UUID uid, final String mntPoint) {
        super();
        this.fuse = f;
        this.fuse.addObserver((Observer) this);
        this.uID = uid;
        this.mntPoint = mntPoint;
        requestUnmount = false;
        lock = new Object();
    }

    @Override
    public void run() {
        fuse.mount(mntPoint);

        // busy wait
        // XXX: check correctness
        synchronized (lock) {
            while (!requestUnmount) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FuseRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        fuse.unmount();
    }

    /**
     * Function which returns the uID for which this FuseRunner is working.
     * It can be used to identify the corresponding MobilePhone
     * @return the uID
     */
    public UUID getUID() {
        return uID;
    }

    /**
     * Function which can be used to unmount the file-system associated with
     * this FuseRunner. In the process the connection will be terminated
     * and this <tt>FuseRunner</tt> will terminate.
     */
    public void unmount() {
        synchronized (lock) {
            requestUnmount = true;
            lock.notifyAll();
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {
        unmount();
        // TODO: change mobile state to unavailable
    }
}
