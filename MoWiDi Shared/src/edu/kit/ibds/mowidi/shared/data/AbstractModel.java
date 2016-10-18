/*
 * @(#)AbstractModel.java
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
 * 2010-06-08   First revision PK
 * 2010-06-11   Second revision, completed PK
 * 2010-06-13   Removed some final modifiers CS
 * 2010-06-20   Added some final modifieres LOL
 *                  made some abstract as stuff will work differently on mobile PK
 */
package edu.kit.ibds.mowidi.shared.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An abstract model provides methods to save and load settings, as well
 * as a list containing devices, which must be checked in child classes for
 * parent type, i.e. PC oder Mobile and SettingsPC and SettingsMobile.<br/>
 * Save and load operations can ONLY save one object per file, so settings is stored
 * as one file <tt>settings.data</tt>. The permanent devices are saved as a
 * <tt>HashMap</tt> object in file <tt>devices.data</tt>.
 * @param <D> a device, either <tt>DevicePC</tt> or <tt>DeviceMobile</tt>
 * @param <S> a settings child, either <tt>SettingsPC</tt> or <tt>SettingsMobile</tt>
 * @author Patrick Kuhn
 */
public abstract class AbstractModel<D extends AbstractDevice, S extends AbstractSettings> {

    /** List containing all devices. */
    protected Map<UUID, D> deviceList = null;
    /** The settings. */
    protected S settings = null;
    /** Standard file name for settings. */
    protected static final String SETTINGS_FILE_NAME = "settings.data";
    /** Standard file name for devices. */
    protected static final String DEVICES_FILE_NAME = "devices.data";

    /**
     * Create an <tt>AbstractModel</tt>.
     * @param set the settings object, needed as java is crappy, if <tt>null</tt>
     * and load unsuccessful, exception will be thrown
     */
    public AbstractModel(final S set) {
        loadDevices();
        loadSettings();
        if (deviceList == null) {
            deviceList = new ConcurrentHashMap<UUID, D>();
        }
        if (settings == null) {
            if (set == null) {
                throw new IllegalArgumentException();
            }
            settings = set;
        }
    }

    /**
     * Save all settings and permanent devices.
     */
    public final void saveAll() {
        saveSettings();
        saveDevices();
    }

    /**
     * Load devices and settings.
     */
    public final void loadAll() {
        loadSettings();
        loadDevices();
    }

    /**
     * Saves the settings.
     */
    public abstract void saveSettings();

    /**
     * Save all permanent devices to file.
     */
    public abstract void saveDevices();

    /**
     * Loads the settings.
     */
    public abstract void loadSettings();

    /**
     * Load all devices from file.
     */
    public abstract void loadDevices();

    /**
     * Set the settings of this model.
     * @param settings the settings
     * @return <tt>true</tt> if successful
     */
    public abstract boolean setSettings(final S settings);

    /**
     * Add a device.
     * @param dev the device, must not be <tt>null</tt>
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public abstract boolean addDevice(D dev);

    /**
     * Removes {@link Devices} which are not permanent and not available.<br/>
     * (NOT available) AND (NOT permanent)
     */
    public final void cleanUpDevices() {
        final Iterator<AbstractDevice> iter = new LinkedList<AbstractDevice>(deviceList.values()).iterator();
        while (iter.hasNext()) {
            final AbstractDevice dev = iter.next();
            if (!dev.isAvailable() && !dev.isPermanent()) {
                deviceList.remove(dev.getuID());
            }
        }
    }

    /**
     * Get the settings of this model.
     * @return the settings object.
     */
    public final S getSettings() {
        return settings;
    }

    /**
     * Get the devices of this model.
     * @return the devices
     */
    public final Map<UUID, D> getDevices() {
        return deviceList;
    }

    /**
     * Get device with given unique ID.
     * @param uid unique ID
     * @return the device or <tt>null</tt> if not in the map
     */
    public final D getDevice(final UUID uid) {
        return deviceList.get(uid);
    }

    /**
     * Add devices from a list.<br/>
     * Should probably be overridden.
     * @param devices the devices, must not be <tt>null</tt>
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public final boolean addAllDevices(final List<D> devices) {
        if (devices == null) {
            throw new IllegalArgumentException();
        }
        for (D d : devices) {
            addDevice(d);
        }
        return true;
    }

    /**
     * Remove a device.
     * @param uid unique ID of device
     * @return the removed device or <tt>null</tt> if no such device
     */
    public final D removeDevice(final UUID uid) {
        return deviceList.remove(uid);
    }
}
