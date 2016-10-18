/*
 * @(#)ConnectionPC.java
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
 * 2010-07-04   Most of the functions implemented rudimentarily
 * 2010-07-04   removed PORT as already included in AbstractConnection PK
 * 2010-07-06   stuff MA
 * 2010-07-06   refactor, removed Runnable PK
 * 2010-08-12   threads PK
 */
package edu.kit.ibds.mowidi.pc.network;

import edu.kit.ibds.mowidi.operations.*;
import edu.kit.ibds.mowidi.shared.connection.AbstractConnection;
import fuse.FuseException;
import fuse.FuseStatfs;
import fuse.compat.FuseDirEnt;
import fuse.compat.FuseStat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a connection on the PC's side.<br/>
 * It is used by the controller to kill the connection as well as FUSE will
 * use one object to execute commands on the mobile.
 * @author Patrick Kuhn
 * @author Michael Auracher
 */
public final class ConnectionPC extends AbstractConnection implements Runnable {

    /** The output stream. */
    private final ObjectOutputStream ostream;
    /** The input stream. */
    private final ObjectInputStream istream;
    /** The socket. */
    private final Socket sock;
    private volatile boolean released = false;
    /** A map containing all incoming <tt>AbstractOperation</tt>s. */
    private final Map<UUID, AbstractOperation> transactions = new ConcurrentHashMap<UUID, AbstractOperation>();

    /**
     * Create a new connection to a mobile.
     * @param address the address to connect to, must not be <tt>null</tt>
     * @param myUUID UID of this
     */
    public ConnectionPC(final InetAddress address, final UUID myUUID) {
        super();
        if (address == null) {
            throw new IllegalArgumentException();
        }
        try {
            Logger.getLogger(ConnectionPC.class.getName()).log(Level.INFO, address.toString(), address);
            sock = new Socket(address, PORT);
            ostream = new ObjectOutputStream(sock.getOutputStream());
            ostream.writeObject(new OperationUUID(myUUID));
            ostream.flush();
            istream = new ObjectInputStream(sock.getInputStream());

            new Thread((Runnable) this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The main Operation in this class. It serialises the Object to the Host
     * waits for a response and returns this response as an Object. If there
     * was a FuseException triggered at the host this exception will be thrown
     * back to Fuse so it can handle it.
     * @param operation the operation to be queried
     * @throws FuseException if something FUSEy happens...
     * @return the result of the operation
     */
    private Object queryOperation(final AbstractOperation operation) throws FuseException {
        Logger.getLogger(ConnectionPC.class.getName()).log(Level.FINE,
                "Operation queried:\n" + operation.toString(), operation);
        try {
            synchronized (ostream) {
                ostream.writeObject(operation);
                ostream.flush();
            }

            boolean success = false;
            while (!success && !released) {
                success = transactions.containsKey(operation.getUUID());
            }
            
            final Object result = transactions.remove(operation.getUUID()).getReturnValue();
            if (result instanceof FuseException) {
                throw (FuseException) result;
            }

            return result;
        } catch (IOException ex) {
            throw new FuseException("Connection broken", ex).initErrno(FuseException.EHOSTDOWN);
        }
    }

    @Override
    public void run() {
        while (!released) {
            try {
                final AbstractOperation ao = (AbstractOperation) istream.readObject();
                transactions.put(ao.getUUID(), ao);
            } catch (SocketException ex) {
                // is caused by closed socket just ignore
                Logger.getLogger(ConnectionPC.class.getName()).log(Level.INFO, "Socket closed", ex);
                release();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionPC.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionPC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void release() {
        released = true;
        try {
            if (istream != null) {
                istream.close();
            }
            if (ostream != null) {
                ostream.close();
            }
            if (sock != null) {
                sock.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionPC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Just do nothing.
     * {@link OperationDoNothing}
     * @throws FuseException on fail
     */
    public void doNothing() throws FuseException {
        queryOperation(new OperationDoNothing());
    }

    /**
     * Get the attributes of a file.
     * {@link OperationGetAttributes}
     * @param path path to file
     * @return the attributes
     * @throws FuseException on fail
     */
    public FuseStat getAttributes(final String path) throws FuseException {
        return (FuseStat) queryOperation(new OperationGetAttributes(path));
    }

    /**
     * Open a file.
     * {@link OperationOpen}
     * @param path path to file
     * @param flags file flags
     * @throws FuseException on fail
     */
    public void open(final String path, final int flags) throws FuseException {
        queryOperation(new OperationOpen(path, flags));
    }

    /**
     * Do statFS.
     * {@link OperationStatFS}
     * @return stats of file system
     * @throws FuseException on fail
     */
    public FuseStatfs statFS() throws FuseException {
        return (FuseStatfs) queryOperation(new OperationStatFS());
    }

    /**
     * Read a file.
     * {@link OperationRead}
     * @param path path to file
     * @param buf the buffer to write to
     * @param offset the offset
     * @return read data
     * @throws FuseException on fail
     */
    public ByteBuffer read(final String path, final ByteBuffer buf, final long offset) throws FuseException {
        final byte[] buffer = new byte[buf.capacity()];
        final Object result = queryOperation(new OperationRead(path, buffer, offset));
        return buf.put((byte[]) result);
    }

    /**
     * Write to a file.
     * {@link OperationWrite}
     * @param path path to file
     * @param buf the buffer with data to write
     * @param offset offset in file
     * @throws FuseException on fail
     */
    public void write(final String path, final ByteBuffer buf, final long offset) throws FuseException {
        final byte[] buffer = new byte[buf.capacity()];
        buf.get(buffer);
        queryOperation(new OperationWrite(path, buffer, offset));
    }

    /**
     * Make a directory.
     * {@link OperationMakeDir}
     * @param path path to file
     * @param mode mode of the new directory
     * @throws FuseException on fail
     */
    public void makeDirectory(final String path, final int mode) throws FuseException {
        queryOperation(new OperationMakeDir(path, mode));
    }

    /**
     * Remove a directory.
     * {@link OperationRemDir}
     * @param path path to file
     * @throws FuseException on fail
     */
    public void removeDirectory(final String path) throws FuseException {
        queryOperation(new OperationRemDir(path));
    }

    /**
     * Remove a file.
     * {@link OperationUnlink}
     * @param path path to file
     * @throws FuseException on fail
     */
    public void unlink(final String path) throws FuseException {
        queryOperation(new OperationUnlink(path));
    }

    /**
     * Make node.
     * {@link OperationMakeNode}
     * @param path path to file
     * @param mode mode of new file
     * @param rdev root device
     * @throws FuseException on fail
     */
    public void makeNode(final String path, final int mode, final int rdev) throws FuseException {
        queryOperation(new OperationMakeNode(path, mode, rdev));
    }

    /**
     * Get the directory.
     * {@link OperationGetDir}
     * @param path path to file
     * @return the directory
     * @throws FuseException on fail
     */
    public FuseDirEnt[] getDirectory(final String path) throws FuseException {
        return (FuseDirEnt[]) queryOperation(new OperationGetDir(path));
    }

    /**
     * Rename a file.
     * {@link OperationRename}
     * @param from path to file
     * @param to new path to file
     * @throws FuseException on fail
     */
    public void rename(final String from, final String to) throws FuseException {
        queryOperation(new OperationRename(from, to));
    }

    /**
     * Change mode of a file.
     * {@link OperationChangeMode}
     * @param path path to file
     * @param mode new mode
     * @throws FuseException on fail
     */
    public void changeMode(final String path, final int mode) throws FuseException {
        queryOperation(new OperationChangeMode(path, mode));
    }

    /**
     * Change owner of a file.
     * {@link OperationChangeOwner}
     * @param path path to file
     * @param uID user ID
     * @param gID group ID
     * @throws FuseException on fail
     */
    public void changeOwner(final String path, final int uID, final int gID) throws FuseException {
        queryOperation(new OperationChangeOwner(path, uID, gID));
    }

    /**
     * Truncate a file.
     * {@link OperationTruncate}
     * @param path path to file
     * @param size new size
     * @throws FuseException on fail
     */
    public void truncate(final String path, final long size) throws FuseException {
        queryOperation(new OperationTruncate(path, size));
    }

    /**
     * Change utime.
     * {@link OperationUtime}
     * @param path path to file
     * @param aTime new access time
     * @param mTime new modified time
     * @throws FuseException on fail
     */
    public void utime(final String path, final int aTime, final int mTime) throws FuseException {
        queryOperation(new OperationUtime(path, aTime, mTime));
    }

    /**
     * Close file.
     * {@link OperationClose}
     * @param path path to file
     * @param flags flags
     * @throws FuseException on fail
     */
    public void close(final String path, final int flags) throws FuseException {
        queryOperation(new OperationClose(path, flags));
    }
}
