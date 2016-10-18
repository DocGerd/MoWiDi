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
 * 2010-06-20   added abstract methods, still nothing done here PK
 * 2010-06-21   load and save, must be tested!! PK
 * 2010-06-21   context problem solved PK
 * 2010-06-23   todo and some part of adders PK
 * 2010-07-03   setSettings implemented
 */
package edu.kit.ibds.mowidi.mobile.data;

import edu.kit.ibds.mowidi.shared.data.AbstractModel;
import android.content.Context;
import android.util.Log;
import edu.kit.ibds.mowidi.mobile.MainView;
import java.io.EOFException;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Performs operations for the user interface.
 * @author Christopher Schuetze
 * @author Patrick Kuhn
 */
public final class Model extends AbstractModel<PCDevice, Settings> implements IModel {

    /**
     * Create new Model.
     */
    public Model() {
        super(new Settings());
    }

    @Override
    public boolean addAllPCs(final List<PCDevice> pcs) {
        for (PCDevice pc : pcs) {
            addPC(pc);
        }
        return true;
    }

    @Override
    public void addPC(final PCDevice pc) {
        addDevice(pc);
    }

    @Override
    public List<PCDevice> getAllPC() {
        return new LinkedList<PCDevice>(getDevices().values());
    }

    @Override
    public PCDevice getPC(final UUID uID) {
        return getDevice(uID);
    }

    @Override
    public void removePC(final UUID uID) {
        removeDevice(uID);
    }

    @Override
    public void saveSettings() {
        saveObject(settings, SETTINGS_FILE_NAME);
    }

    @Override
    public void saveDevices() {
        saveObject(deviceList, DEVICES_FILE_NAME);
    }

    /**
     * Save an object to file.
     * @param o the object
     * @param dest the destination file
     */
    private void saveObject(final Object o, final String dest) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = MainView.getContext().openFileOutput(dest, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Log.e(Model.class.getName(), ex.getMessage(), ex);
            }
        }
    }

    /**
     * Load an <tt>Object</tt> from a file.
     * @param dest file name
     * @return <tt>null</tt> on fail or the <tt>Object</tt>
     */
    private Object loadObject(final String dest) {
        Object o = null;
        FileInputStream fin = null;
        ObjectInputStream oin = null;
        try {
            fin = MainView.getContext().openFileInput(dest);
            oin = new ObjectInputStream(fin);
            o = oin.readObject();
        } catch (ClassNotFoundException ex) {
            // do nothing but bad
            Log.e(Model.class.getName(), ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            // do nothing
            Log.i(Model.class.getName(), "File not found:" + dest + ", default values will be loaded!", ex);
        } catch (EOFException ex) {
            Log.i(Model.class.getName(), "File fucked up:" + dest + ", default values will be loaded!", ex);
        } catch (IOException ex) {
            // XXX: in newer version replace with IOError
            throw new RuntimeException(ex);
        } finally {
            try {
                if (oin != null) {
                    oin.close();
                }
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ex) {
                Log.e(Model.class.getName(), ex.getMessage(), ex);
            }
        }
        return o;
    }

    @Override
    public void loadSettings() {
        final Object obj = loadObject(SETTINGS_FILE_NAME);
        // should work
        if (obj instanceof Settings) {
            settings = (Settings) obj;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadDevices() {
        final Object obj = loadObject(DEVICES_FILE_NAME);
        // should work
        if (obj != null && obj.getClass().equals(new ConcurrentHashMap<UUID, PCDevice>().getClass())) {
            try {
                deviceList = (ConcurrentHashMap<UUID, PCDevice>) obj;
            } catch (ClassCastException ex) {
                // not expected to ever happen
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public boolean setSettings(final Settings settings) {
        // TODO
        Settings sets = this.getSettings();
        if (settings.getGivenName() != null && !sets.getGivenName().equals(settings.getGivenName())) {
            sets.setGivenName(settings.getGivenName());
        }
        if (settings.getLanguage() != null && sets.getLanguage() != settings.getLanguage()) {
            sets.setLanguage(settings.getLanguage());
        }
        return true;
    }

    @Override
    public boolean addDevice(final PCDevice dev) {
        final PCDevice cont = deviceList.get(dev.getuID());
        if (cont == null) {
            deviceList.put(dev.getuID(), cont);
        } else {
            cont.setAuthType(dev.getAuthType());
            cont.setAvailable(dev.isAvailable());
            cont.setCertificate(dev.getCertificate());
            cont.setConnected(dev.isConnected());
            cont.setGivenName(dev.getGivenName());
            cont.setPermanent(dev.isPermanent());
        }
        return true;
    }
}
