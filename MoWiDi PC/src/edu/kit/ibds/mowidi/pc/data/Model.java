/*
 * @(#)Model.java
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
 * 2010-06-13   first revision CS
 * 2010-06-13   second revision PK
 * 2010-06-16   third revision CS
 * 2010-06-29   addDevice corrected PK
 * 2010-06-02   first implementation of setSettings.. not good yet!
 * 2010-07-13   saveDevices improved to only save devices which are permanent.
 */
package edu.kit.ibds.mowidi.pc.data;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import edu.kit.ibds.mowidi.shared.data.AbstractModel;
import java.io.EOFException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Performs operations for the user interface.
 * @author Christopher Schuetze
 * @author Patrick Kuhn
 */
public final class Model extends AbstractModel<MobileDevice, Settings> implements IModelController {

    /**
     * Creates a new Model.
     */
    public Model() {
        super(new Settings());
    }

    @Override
    public synchronized void loadSettings() {
        final Object obj = loadObject(SETTINGS_FILE_NAME);
        // should work
        if (obj instanceof Settings) {
            settings = (Settings) obj;
        } else {
            Logger.getLogger(Model.class.getName()).
                    log(Level.INFO, "Settings file corrupted", new Object[]{});
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized void loadDevices() {
        final Object obj = loadObject(DEVICES_FILE_NAME);
        // XXX: extremely anti-yeah
        if (obj instanceof ConcurrentHashMap<?, ?>) {
            deviceList = (ConcurrentHashMap<UUID, MobileDevice>) obj;
        } else {
            Logger.getLogger(Model.class.getName()).log(Level.INFO, "Device file corrupted", new Object[]{null});
        }
    }

    @Override
    public synchronized void saveSettings() {
        saveObject(settings, SETTINGS_FILE_NAME);
    }

    @Override
    public synchronized void saveDevices() {
        final ConcurrentHashMap<UUID, MobileDevice> toSave = new ConcurrentHashMap<UUID, MobileDevice>();
        for (Map.Entry<UUID, MobileDevice> entry : deviceList.entrySet()) {
            if (entry.getValue().isPermanent()) {
                toSave.put(entry.getKey(), entry.getValue());
            }
        }
        saveObject(toSave, DEVICES_FILE_NAME);
    }

    /**
     * Save an object to file.
     * @param o the object
     * @param dest the destination file
     */
    private static void saveObject(final Object o, final String dest) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(dest);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (IOException ex) {
            throw new IOError(ex);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Load an <tt>Object</tt> from a file.
     * @param dest file name
     * @return <tt>null</tt> on fail or the <tt>Object</tt>
     */
    private static Object loadObject(final String dest) {
        Object o = null;
        FileInputStream fin = null;
        ObjectInputStream oin = null;
        try {
            fin = new FileInputStream(dest);
            oin = new ObjectInputStream(fin);
            o = oin.readObject();
        } catch (ClassNotFoundException ex) {
            // do nothing but bad
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            // just do nothing
            Logger.getLogger(Model.class.getName()).log(Level.INFO,
                    "File not found:" + dest + ", default values will be loaded!", ex);
        } catch (EOFException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.INFO,
                    "File fucked up:" + dest + ", default values will be loaded!", ex);
        } catch (IOException ex) {
            throw new IOError(ex);
        } finally {
            try {
                if (oin != null) {
                    oin.close();
                }
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return o;
    }

    @Override
    public List<MobileDevice> getAllDevices() {
        return Collections.synchronizedList(new LinkedList<MobileDevice>(getDevices().values()));
    }

    @Override
    public boolean setSettings(final Settings settings) {
        // FIXME: improve!!
        boolean result = true;
        final Settings sets = this.getSettings();
        if (result && settings.getStandardMountPoint() != null && !sets.getStandardMountPoint().equals(settings.getStandardMountPoint())) {
            final File f = new File(settings.getStandardMountPoint());
            if (f.exists() || f.mkdir()) {
                sets.setStandardMountPoint(settings.getStandardMountPoint());
            } else {
                result = false;
            }
        }
        if (result && settings.getGivenName() != null && !settings.getGivenName().equals(sets.getGivenName())) {
            sets.setGivenName(settings.getGivenName());
        }
        if (result && sets.getNumberTrayView() != settings.getNumberTrayView()) {
            sets.setNumberTrayView(settings.getNumberTrayView());
        }
        if (result && settings.getLanguage() != null && sets.getLanguage() != settings.getLanguage()) {
            sets.setLanguage(settings.getLanguage());
        }
        return result;
    }

    @Override
    public boolean addDevice(final MobileDevice dev) {
        final MobileDevice md = deviceList.get(dev.getuID());
        if (md == null) {
            deviceList.put(dev.getuID(), dev);
        } else {
            md.setAddress(dev.getAddress());
            md.setAvailable(true);
            md.setCertificate(dev.getCertificate());
            md.setConnected(dev.isConnected());
            md.setGivenName(dev.getGivenName());
            md.setMountPoint(dev.getMountPoint());
            md.setPermanent(dev.isPermanent());
        }
        return true;
    }
}
