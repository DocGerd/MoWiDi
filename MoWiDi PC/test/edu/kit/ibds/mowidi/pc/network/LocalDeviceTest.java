/*
 * @(#)LocalDeviceTest.java
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
package edu.kit.ibds.mowidi.pc.network;

import java.io.File;
import java.net.UnknownHostException;
import java.util.UUID;
import edu.kit.ibds.mowidi.pc.controller.Notifier;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Model;
import java.net.InetAddress;
import java.util.Map;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.InvalidDescriptionException;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class LocalDeviceTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--LocalDevice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocalDevice() {
        System.out.println("LocalDevice");
        new LocalDevice(null, new Notifier());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocalDevice2() {
        System.out.println("LocalDevice");
        new LocalDevice(new Model(), null);
    }

    /**
     * Test of getAvailableDeviceList method, of class LocalDevice.
     * @deprecated
     */
    @Test
    @Deprecated
    @SuppressWarnings("deprecation")
    public void testGetAvailableDeviceList() {
        System.out.println("getAvailableDeviceList");
        final LocalDevice instance = new LocalDevice(new Model(), new Notifier());
        final Map<UUID, MobileDevice> result = instance.getAvailableDeviceList();
        assertTrue("List is not empty!", result.isEmpty());
    }

    /**
     * Test of connectTo method, of class LocalDevice.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConnectTo() {
        System.out.println("connectTo");
        final LocalDevice instance = new LocalDevice(new Model(), new Notifier());
        instance.connectTo((MobileDevice) null);
    }

    /**
     * Test of deviceAdded method, of class LocalDevice.
     * @throws InvalidDescriptionException
     * @throws UnknownHostException
     */
    @Test
    public void testDeviceAdded() throws InvalidDescriptionException, UnknownHostException {
        System.out.println("deviceAdded");
        final Device dev = new Device(new File("device_desc.xml"));
        final UUID uid = UUID.randomUUID();
        dev.setUDN(uid.toString());
        dev.setUPC(uid.toString());
        final SSDPPacket pack = new SSDPPacket(new byte[0], 0);
        pack.setLocalAddress(InetAddress.getLocalHost().getHostAddress());
        dev.setSSDPPacket(pack);
        final Model model = new Model();
        final LocalDevice instance = new LocalDevice(model, new Notifier());
        instance.deviceAdded(dev);
        assertNotNull("Device was not found!", model.getDevice(uid));
    }

    /**
     * Test of deviceRemoved method, of class LocalDevice.
     * @throws InvalidDescriptionException
     * @throws UnknownHostException
     */
    @Test
    public void testDeviceRemoved() throws InvalidDescriptionException, UnknownHostException {
        System.out.println("deviceRemoved");
        final Device dev = new Device(new File("device_desc.xml"));
        final UUID uid = UUID.randomUUID();
        dev.setUDN(uid.toString());
        dev.setUPC(uid.toString());
        final SSDPPacket pack = new SSDPPacket(new byte[0], 0);
        pack.setLocalAddress(InetAddress.getLocalHost().getHostAddress());
        dev.setSSDPPacket(pack);
        final Model model = new Model();
        final LocalDevice instance = new LocalDevice(model, new Notifier());
        instance.deviceAdded(dev);

        instance.deviceRemoved(dev);
        assertNull("Device was found although it should not have been found!", model.getDevice(uid));
    }
}
