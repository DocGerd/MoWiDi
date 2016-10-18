/*
 * @(#)MobileDevice.java
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
 * 2010-06-19   XML stuff PK
 * 2010-06-20   big blocker PK
 * 2010-06-21   UPNP works now, description file must be worked around PK
 * 2010-07-13   Few Typos lol...
 */
package edu.kit.ibds.mowidi.mobile.network;

import android.content.res.Resources;
import android.util.Log;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.PCDevice;
import edu.kit.ibds.mowidi.shared.connection.AbstractConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.LinkedList;
import java.util.UUID;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.InvalidDescriptionException;

/**
 * This class mainly handles UPnP and accepts connections. It represents the interface
 * to the controller as well.
 * @author Patrick Kuhn
 */
public final class MobileDevice extends Device implements Runnable {

    /** The PORT. */
    private static final int PORT = AbstractConnection.PORT;
    /** Serial Version UID. */
    private static final long serialVersionUID = 317000245416341872L;
    /** List of pending connections. */
    private final List<ConnectionPending> connectionsPending;
    /** The RMI connection. */
    private ConnectionOnMobile connection;
    /** The Server Socket on which new connections are incoming. */
    private ServerSocket sock;
    /** Indicates whether this thread shall be running or not. */
    private boolean running;
    private final List<PCDevice> allConnectedPCs;

    /**
     * Create a new MobileDevice object.<br/>
     * UDN, UPC and friendlyName must be set independently!
     * @param resource the projects resource reference
     * @param friendlyName the friendly name
     * @param uID the UPC and UDN number
     */
    public MobileDevice(final Resources resource, final String friendlyName, final UUID uID) {
        super();
        running = true;
        connection = null;
        connectionsPending = new LinkedList<ConnectionPending>();
        allConnectedPCs = new LinkedList<PCDevice>();
        try {
            loadDescription(resource.openRawResource(R.raw.device_desc));
        } catch (InvalidDescriptionException ex) {
            throw new RuntimeException(ex);
        }
        try {
            sock = new ServerSocket(PORT);
            sock.setReuseAddress(true);
        } catch (IOException ex) {
            Log.e(MobileDevice.class.getName(), ex.getMessage(), ex);
        }
        setWirelessMode(true);
        setValues(uID.toString(), friendlyName);
        start();
    }

    /**
     * Set the UDN and UPC.
     * @param value the new UDN and UPC
     * @param name friendly name
     */
    public void setValues(final String value, final String name) {
        super.stop();
        setUDN(value);
        setUPC(name);
        setFriendlyName(name);
        start();
    }

    /**
     * Overridden function to stop the whole MobileDevice. Only call this if
     * you want the thread of this MobileDevice to terminate.
     * @return <tt>true</tt>
     */
    @Override
    public boolean stop() {
        running = false;
        try {
            sock.close();
        } catch (IOException ex) {
            Log.e(MobileDevice.class.getName(), ex.getMessage(), ex);
        }
        return super.stop();
    }

    /**
     * Get all PCs wanting to connect to this mobile and the already connected one.
     * @return the array of PCs trying to connect
     */
    public List<PCDevice> getConnectees() {
        List<PCDevice> list = new LinkedList<PCDevice>();
        for (ConnectionPending cp : connectionsPending) {
            list.add(cp.getPartner());
        }
        // DEBUG
//        return DEBUG_LIST;
//        return list;
        return allConnectedPCs;

    }

    /**
     * Get PC which is connected if there is any.
     * @return The connected PC. <tt>null</tt> if nobody is connected
     */
    public PCDevice getConnectedPC() {
        if (connection == null) {
            return null;
        }
        return connection.getPartner();
    }

    /**
     * Releases the <tt>ConnectionOnMobile</tt>.
     * If no connection exists, nothing is done.
     */
    public void release() {
        if (connection != null) {
            allConnectedPCs.remove(connection.getPartner());
            connection.release();
            connection = null;
        }
    }

    /**
     * Function to remove a specific pending connection from the list. e.g It
     * is used if the connection has been broken
     * @param cp the pending connection which shall be removed
     */
    public void removeConnection(final ConnectionPending cp) {
        allConnectedPCs.remove(cp.getPartner());
        connectionsPending.remove(cp);
    }

    /**
     * Function to be called if we want to trust this PCDevice and
     * want to establish the communication to it.
     * @param toDevice the PCDevice we trust and want to communicate with
     * @return Returns the upgraded Connection if the PCDevice was already waiting and no
     * other Device is currently connected null otherwise
     */
    public ConnectionOnMobile upgradeConnection(final PCDevice toDevice) {
        if (connection != null || toDevice == null) {
            return null;
        }
        for (ConnectionPending cp : connectionsPending) {
            if (cp.getPartner() == toDevice) {
                connection = new ConnectionOnMobile(cp);
                return connection;
            }
        }
        return null;
    }

    // TODO: Test and Keymanager
    @Override
    public void run() {
        while (running) {
            try {
                ConnectionPending cp = new ConnectionPending(sock.accept(), this);
                allConnectedPCs.add(cp.getPartner());
                Thread access = new Thread(cp);
                access.start();
                connectionsPending.add(cp);
//                Thread worker = new Thread(this.upgradeConnection(cp.getPartner()));
//                worker.start();
            } catch (IOException ex) {
                Log.e(MobileDevice.class.getName(), ex.getMessage(), ex);
            }
        }
        try {
            sock.close();
        } catch (IOException ex) {
            //Dont really care if fucked up...
            Log.e(MobileDevice.class.getName(), ex.getMessage(), ex);
        }
    }
}
