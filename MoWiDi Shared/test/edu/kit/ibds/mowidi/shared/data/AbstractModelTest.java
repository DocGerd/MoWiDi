/*
 * @(#)AbstractModelTest.java
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
 * 2010-08-03   first rev. PK
 */
package edu.kit.ibds.mowidi.shared.data;

import org.junit.BeforeClass;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import java.util.UUID;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public final class AbstractModelTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--AbstractModel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        System.out.println("AbstractModel");
        new AbstractModelImpl2();
    }

    /**
     * Test of saveAll method, of class AbstractModel.
     */
    @Test
    public void testSaveAll() {
        System.out.println("saveAll");
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        instance.saveAll();
    }

    /**
     * Test of loadAll method, of class AbstractModel.
     */
    @Test
    public void testLoadAll() {
        System.out.println("loadAll");
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        instance.loadAll();
    }

    /**
     * Test of cleanUpDevices method, of class AbstractModel.
     */
    @Test
    public void testCleanUpDevices() {
        System.out.println("cleanUpDevices");
        final AbstractDeviceImpl dev1 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev2 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev3 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev4 = new AbstractDeviceImpl();
        dev1.setAvailable(true);
        dev1.setPermanent(false);
        dev2.setAvailable(false);
        dev2.setPermanent(true);

        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        instance.addDevice(dev1);
        instance.addDevice(dev2);
        instance.addDevice(dev3);
        instance.addDevice(dev4);
        instance.cleanUpDevices();

        assertTrue(instance.getDevices().size() == 2);

        final Iterator<AbstractDeviceImpl> iter = instance.getDevices().values().iterator();
        while (iter.hasNext()) {
            final AbstractDeviceImpl d = iter.next();
            if (d == dev3 || d == dev4) {
                fail();
            }
        }
    }

    /**
     * Test of getSettings method, of class AbstractModel.
     */
    @Test
    public void testGetSettings() {
        System.out.println("getSettings");
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        final AbstractSettings result = instance.getSettings();
        assertTrue(result instanceof AbstractSettingsImpl);
    }

    /**
     * Test of getDevices method, of class AbstractModel.
     */
    @Test
    public void testGetDevices() {
        System.out.println("getDevices");
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        final AbstractDeviceImpl dev1 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev2 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev3 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev4 = new AbstractDeviceImpl();
        instance.addDevice(dev1);
        instance.addDevice(dev2);
        instance.addDevice(dev3);
        instance.addDevice(dev4);

        final Map<UUID, AbstractDeviceImpl> expResult = new HashMap<UUID, AbstractDeviceImpl>();
        expResult.put(dev1.getuID(), dev1);
        expResult.put(dev2.getuID(), dev2);
        expResult.put(dev3.getuID(), dev3);
        expResult.put(dev4.getuID(), dev4);

        final Map<UUID, AbstractDeviceImpl> result = instance.getDevices();
        assertSame(expResult.size(), result.size());

        final Iterator<AbstractDeviceImpl> iter = expResult.values().iterator();
        boolean r = true;
        while (iter.hasNext()) {
            final AbstractDeviceImpl d = iter.next();
            r &= result.containsValue(d);
        }
        assertTrue(r);
    }

    /**
     * Test of getDevice method, of class AbstractModel.
     */
    @Test
    public void testGetDevice() {
        System.out.println("getDevice");
        final AbstractDeviceImpl dev1 = new AbstractDeviceImpl();
        final UUID uid = dev1.getuID();
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        instance.addDevice(dev1);
        final AbstractDevice result = instance.getDevice(uid);
        assertEquals(dev1, result);
    }

    /**
     * Test of addAllDevices method, of class AbstractModel.
     */
    @Test
    public void testAddAllDevices() {
        System.out.println("addAllDevices");
        final AbstractDeviceImpl dev1 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev2 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev3 = new AbstractDeviceImpl();
        final AbstractDeviceImpl dev4 = new AbstractDeviceImpl();
        final List<AbstractDeviceImpl> devices = new LinkedList<AbstractDeviceImpl>();
        devices.add(dev1);
        devices.add(dev2);
        devices.add(dev3);
        devices.add(dev4);
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        final boolean expResult = true;
        final boolean result = instance.addAllDevices(devices);
        assertEquals(expResult, result);
    }

    /**
     * Test of addAllDevices method, of class AbstractModel.
     * Exception test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddAllDevices2() {
        System.out.println("addAllDevices w/ exception");
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        instance.addAllDevices(null);
    }

    /**
     * Test of removeDevice method, of class AbstractModel.
     */
    @Test
    public void testRemoveDevice() {
        System.out.println("removeDevice");
        final AbstractDeviceImpl dev1 = new AbstractDeviceImpl();
        final UUID uid = dev1.getuID();
        final AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> instance = new AbstractModelImpl();
        instance.addDevice(dev1);
        final AbstractDeviceImpl result = instance.removeDevice(uid);
        assertEquals(dev1, result);
    }

// <editor-fold defaultstate="collapsed" desc="Implementations of abstract classes">
    private static final class AbstractModelImpl extends AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> {

        public AbstractModelImpl() {
            super(new AbstractSettingsImpl());
        }

        @Override
        public void saveSettings() {
        }

        @Override
        public void saveDevices() {
        }

        @Override
        public void loadSettings() {
        }

        @Override
        public void loadDevices() {
        }

        @Override
        public boolean setSettings(AbstractSettingsImpl settings) {
            return false;
        }

        @Override
        public boolean addDevice(AbstractDeviceImpl dev) {
            deviceList.put(dev.getuID(), dev);
            return true;
        }
    }

    private static final class AbstractModelImpl2 extends AbstractModel<AbstractDeviceImpl, AbstractSettingsImpl> {

        public AbstractModelImpl2() {
            super(null);
        }

        @Override
        public void saveSettings() {
        }

        @Override
        public void saveDevices() {
        }

        @Override
        public void loadSettings() {
        }

        @Override
        public void loadDevices() {
        }

        @Override
        public boolean setSettings(AbstractSettingsImpl settings) {
            return false;
        }

        @Override
        public boolean addDevice(AbstractDeviceImpl dev) {
            return false;
        }
    }

    private static final class AbstractSettingsImpl extends AbstractSettings {

        private static final long serialVersionUID = 9145014234637851022L;
    }

    private static final class AbstractDeviceImpl extends AbstractDevice {

        private static final long serialVersionUID = 2465915773891877577L;

        public AbstractDeviceImpl() {
            super(UUID.randomUUID());
        }
    }
// </editor-fold>
}
