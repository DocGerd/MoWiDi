/*
 * @(#)LocalDevice.java
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
 * 2010-06-08   first revision, PK
 * 2010-06-12   added DeviceChangeListener PK
 * 2010-06-19   apparently finished PK
 * 2010-06-19   concurrency changed PK
 * 2010-06-25   Listener finished, apparently class completed PK
 * 2010-06-25   getAvailableDevices marked deprecated PK
 * 2010-07-02   Added notifier to supply the UI with new Devices
 */
package edu.kit.ibds.mowidi.pc.network;

import edu.kit.ibds.mowidi.pc.controller.Information;
import edu.kit.ibds.mowidi.pc.controller.Notifier;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Model;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.DeviceChangeListener;
import org.cybergarage.upnp.xml.DeviceData;
import org.cybergarage.xml.Node;

/**
 * Here all the UPnP stuff is done as well as creation of a connection to a
 * mobile.
 * @author Patrick Kuhn
 */
public final class LocalDevice extends ControlPoint implements DeviceChangeListener {

    /** Map for all available devices. This Map is refreshed instantly and is thread safe. */
    private final transient Model model;
    /** The notifier. */
    private final Notifier notifier;

    /**
     * Create a new LocalDevice.
     * @param m the model, must not be <tt>null</tt>
     * @param notif the notifier, must not be <tt>null</tt>
     */
    public LocalDevice(final Model m, final Notifier notif) {
        super();
        if (m == null || notif == null) {
            throw new IllegalArgumentException();
        }
        notifier = notif;
        model = m;
        addDeviceChangeListener((DeviceChangeListener) this);
        start(); // start UPnP
    }

    /**
     * Get all devices found and available by UPnP.
     * @return all mobiles currently available and permanent ones
     * @deprecated should not be used and is to be removed in final stage,
     * as this list is just a reference to the model!
     */
    @Deprecated
    public Map<UUID, MobileDevice> getAvailableDeviceList() {
        return model.getDevices();
    }

    /**
     * Connect to a device.
     * @param d the device to connect to, must not be <tt>null</tt>
     * @return the connection object on PC side
     */
    public ConnectionPC connectTo(final MobileDevice d) {
        if (d == null) {
            throw new IllegalArgumentException();
        }
        return new ConnectionPC(d.getAddress(), model.getSettings().getUID());
    }

    /**
     * Add a UPnP device to the map. If the device does provide the right service
     * it is parsed and added to the map.
     * @param dev the newly discovered device
     */
    @Override
    public void deviceAdded(final Device dev) {
        // works! yehaaa
        final Node node = dev.getDeviceNode();
        final Node manufacturer = node.getNode("manufacturer");
        if (manufacturer.getValue().equals("PSE23")) {
            final UUID uid = UUID.fromString(dev.getUDN());
            final String name = dev.getUPC();
            final MobileDevice md = new MobileDevice(uid);
            md.setAvailable(true);
            md.setGivenName(name);
            md.setFirstTimeAdded(new Date());
            md.setMountPoint(model.getSettings().getStandardMountPoint());
            final DeviceData dd = (DeviceData) node.getUserData();
            final InetAddress ia = dd.getSSDPPacket().getRemoteInetAddress();
            md.setAddress(ia);
            // model shall decide what to do
            model.addDevice(md);
            final MobileDevice[] mds = new MobileDevice[]{md};
            notifier.notifyObservers(new Information(mds));
        }
    }

    /**
     * Is called by <tt>ControlPoint</tt> to remove a device which is not available anymore.
     * If there is no such device in the map nothing happens.
     * @param dev the device to be removed
     */
    @Override
    public void deviceRemoved(final Device dev) {
        final Node node = dev.getDeviceNode();
        final Node manufacturer = node.getNode("manufacturer");
        if (manufacturer.getValue().equals("PSE23")) {
            final UUID uid = UUID.fromString(dev.getUDN());
            final MobileDevice md = model.getDevice(uid);
            if (md != null) {
                md.setAvailable(false);
                if (!md.isPermanent()) {
                    model.removeDevice(uid);
                }
                final MobileDevice[] mds = new MobileDevice[]{md};
                notifier.notifyObservers(new Information(mds));
            }
        }
    }
}
