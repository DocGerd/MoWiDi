/*
 * @(#)ConnectionOnMobile.java
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
 * 2010-06-21   release implemented, attributes added PK
 * 2010-08-14   harrrr PK
 * 2010-08-18   comments PK
 */
package edu.kit.ibds.mowidi.mobile.network;

import android.util.Log;
import edu.kit.ibds.mowidi.mobile.data.PCDevice;
import edu.kit.ibds.mowidi.operations.AbstractOperation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.EOFException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class starts a thread which receives all operations and performs this
 * operations multi-threaded and sends them back.
 * @author Patrick Kuhn
 */
public final class ConnectionOnMobile implements Runnable {

    /** The partner in this connection. */
    private final PCDevice partner;
    /** The output stream to write objects to. */
    private final ObjectOutputStream ostream;
    /** The input stream to read objects from. */
    private final ObjectInputStream istream;
    /** The pendingConnection this object belongs to. */
    private final ConnectionPending pendingConnection;
    /** The executor for the thread which performs the operations. */
    private final ExecutorService pool;
    /** Flag for state of connection. */
    private volatile boolean released;

    /**
     * Create a new connection with RMI on a established socket.
     * @param cp the pending connection to be upgraded
     */
    public ConnectionOnMobile(final ConnectionPending cp) {
        this.partner = cp.getPartner();
        this.partner.setConnected(true);
        this.ostream = cp.getOutStream();
        this.istream = cp.getInstream();
        this.pendingConnection = cp;
        this.released = false;
        this.pool = Executors.newCachedThreadPool();
        new Thread((Runnable) this).start();
    }

    /**
     * Release the connection.
     */
    public void release() {
        released = true;
        // the connection itself will be released after thread is finished
    }

    /**
     * Get the partner of this connection.
     * @return the partner
     */
    public PCDevice getPartner() {
        return partner;
    }

    @Override
    public void run() {
        while (!released) {
            try {
                final AbstractOperation operation = (AbstractOperation) istream.readObject();
                pool.execute(new WorkerThread(operation));
            } catch (EOFException ex) {
                // bad -> quit connection
                release();
                Log.i(ConnectionOnMobile.class.getName(), "Connection terminated by other side", ex);
            } catch (OptionalDataException ex) {
                // don't care
                Log.d(ConnectionOnMobile.class.getName(), ex.getMessage(), ex);
            } catch (ClassNotFoundException ex) {
                // don't care
                Log.d(ConnectionOnMobile.class.getName(), ex.getMessage(), ex);
            } catch (IOException ex) {
                // bad -> quit connection
                release();
                Log.e(ConnectionOnMobile.class.getName(), ex.getMessage(), ex);
            }
        }
        pool.shutdown();
        pendingConnection.release();
    }

    /**
     * The thread which does the operations.
     */
    private class WorkerThread implements Runnable {

        /** The operation to perform. */
        private final AbstractOperation ao;

        /**
         * Create a <tt>WorkerThread</tt> object.
         * @param ao the operation to perform
         */
        public WorkerThread(final AbstractOperation ao) {
            this.ao = ao;
        }

        @Override
        public void run() {
            if (partner.getAuthType().compareTo(ao.getAuthorizationType()) >= 0) {
                ao.doOperation();
                Log.i(ConnectionOnMobile.class.getName(), "Just performed operation" + ao.toString());
            } else {
                // send access denied exception
                ao.setAccessError();
            }
            synchronized (ostream) {
                try {
                    ostream.writeObject(ao);
                    ostream.flush();
                } catch (IOException ex) {
                    Log.e(ConnectionOnMobile.class.getName(), ex.getMessage(), ex);
                }
            }
        }
    }
}
