/*
 * @(#)ConnectionPending.java
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
 */
package edu.kit.ibds.mowidi.mobile.network;

import android.util.Log;
import edu.kit.ibds.mowidi.mobile.data.PCDevice;
import java.net.Socket;
import java.security.cert.X509Certificate;
import edu.kit.ibds.mowidi.shared.connection.AbstractConnection;
import edu.kit.ibds.mowidi.operations.AbstractOperation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.UUID;

/**
 * This class represents a pending connection. Although this connection is to
 * perform interaction with SSL, the partner is not yet authenticated, which is done here.
 * @author Patrick Kuhn
 */
public final class ConnectionPending extends AbstractConnection implements Runnable {

    /** The partner of this connection. */
    private final PCDevice partner;
    /** The input-stream of this connection. */
    private ObjectInputStream istream;
    /** The output stream of this connection. */
    private ObjectOutputStream ostream;
    /** The Socket of this connection. */
    private final Socket socket;
    /** The MobileDevice instance to release the connection properly. */
    private final MobileDevice mobileDevice;
    /** If the connection was already released. */
    private boolean released;

    /**
     * Create a new pending connection.
     * @param socket the socket on which <tt>MobileDevice</tt> accepted.
     * @param mobileDevice the mobile device to connect to
     */
    public ConnectionPending(final Socket socket, final MobileDevice mobileDevice) {
        PCDevice device = null;
        this.mobileDevice = mobileDevice;
        this.socket = socket;
        try {
            istream = new ObjectInputStream(socket.getInputStream());
            AbstractOperation op = (AbstractOperation) istream.readObject();
            device = new PCDevice((UUID) op.getReturnValue());
            ostream = new ObjectOutputStream(socket.getOutputStream());
            ostream.writeObject(op);
            ostream.flush();
        } catch (StreamCorruptedException ex) {
            release();
            Log.e(ConnectionPending.class.getName(), ex.getMessage(), ex);
        } catch (IOException ex) {
            release();
            Log.e(ConnectionPending.class.getName(), ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            Log.e(ConnectionPending.class.getName(), ex.getMessage(), ex);
        } finally {
            if (ostream == null || istream == null) {
                throw new RuntimeException("Stream is null");
            }
        }
        device.setAvailable(true);
        this.partner = device;
    }

    /**
     * Get the Socket herein used.
     * @return the input stream
     */
    public ObjectInputStream getInstream() {
        return istream;
    }

    /**
     * Get the output stream.
     * @return the output stream
     */
    public ObjectOutputStream getOutStream() {
        return ostream;
    }

    @Override
    public void run() {
        while (!released) {
            //Thread.yield();
        }
    }

    /**
     * Get the PC Partner used in this connection.
     * @return the partner
     */
    public PCDevice getPartner() {
        return partner;
    }

    @Override
    public X509Certificate getFarCertificate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void release() {
        //TODO: Inform the model that the status has changed???
        partner.setConnected(false);
        partner.setAvailable(false);
        try {
            socket.close();
        } catch (IOException ex) {
            // don't really care if fucked up...
            Log.e(ConnectionPending.class.getName(), ex.getMessage(), ex);
        }
        mobileDevice.removeConnection(this);
        this.released = true;
    }
}
