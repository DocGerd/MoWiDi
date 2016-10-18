/*
 * @(#)ModelTest.java
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
 * 2010-08-12   completed PK
 */
package edu.kit.ibds.mowidi.pc.data;

import edu.kit.ibds.mowidi.shared.data.Language;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class ModelTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Model");

        deleteSaveFiles();
    }

    @AfterClass
    public static void after() {
        deleteSaveFiles();
    }

//    private static void createFakeFile(final Object o, final String dest) {
//        FileOutputStream fos = null;
//        ObjectOutputStream oos = null;
//        try {
//            fos = new FileOutputStream(dest);
//            oos = new ObjectOutputStream(fos);
//            oos.writeObject(o);
//        } catch (IOException ex) {
//            Logger.getLogger(ModelTest.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (oos != null) {
//                    oos.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(ModelTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    private static void deleteSaveFiles() {
        deleteFile("settings.data");
        deleteFile("devices.data");
    }

    private static void deleteFile(final String dest) {
        final File file = new File(dest);
        if (!file.delete()) {
            Logger.getLogger(ModelTest.class.getName()).log(Level.FINE, "File could not be deleted!");
        }
    }

    /**
     * Test of loadSettings method, of class Model.
     * No file exists.
     */
    @Test
    public void testLoadSettings() {
        System.out.println("loadSettings no file");
        final Model instance = new Model();
        instance.loadSettings();
    }

    /**
     * Test of loadDevices method, of class Model.
     */
    @Test
    public void testLoadDevices() {
        System.out.println("loadDevices no file");
        final Model instance = new Model();
        instance.loadDevices();
    }

    /**
     * Test of saveSettings method, of class Model.
     */
    @Test
    public void testSaveSettings() {
        System.out.println("saveSettings");
        final Model instance = new Model();
        instance.saveSettings();
    }

    /**
     * Test of saveDevices method, of class Model.
     */
    @Test
    public void testSaveDevices() {
        System.out.println("saveDevices");
        final Model instance = new Model();
        instance.saveDevices();
    }

    /**
     * Test of saveDevices method, of class Model.
     */
    @Test
    public void testSaveDevices2() {
        System.out.println("saveDevices");
        final Model instance = new Model();

        final MobileDevice md1 = new MobileDevice(UUID.randomUUID());
        final MobileDevice md2 = new MobileDevice(UUID.randomUUID());
        final MobileDevice md3 = new MobileDevice(UUID.randomUUID());
        final MobileDevice md4 = new MobileDevice(UUID.randomUUID());
        md1.setPermanent(true);
        md3.setPermanent(true);

        instance.addDevice(md1);
        instance.addDevice(md2);
        instance.addDevice(md3);
        instance.addDevice(md4);

        instance.saveDevices();
    }

    /**
     * Test of loadSettings method, of class Model.
     * No file exists.
     */
    @Test
    public void testLoadSettings2() {
        System.out.println("loadSettings");
        final Model instance = new Model();
        instance.loadSettings();
    }

    /**
     * Test of loadDevices method, of class Model.
     */
    @Test
    public void testLoadDevices2() {
        System.out.println("loadDevices");
        final Model instance = new Model();
        instance.loadDevices();
    }

    /**
     * Test of getAllDevices method, of class Model.
     */
    @Test
    public void testGetAllDevices() {
        System.out.println("getAllDevices");
        deleteSaveFiles();
        final Model instance = new Model();
        final MobileDevice md = new MobileDevice(UUID.randomUUID());
        instance.addDevice(md);
        final List<MobileDevice> result = instance.getAllDevices();
        assertSame("size == 1", result.size(), 1);
        assertSame("ass", result.get(0), md);
    }

    /**
     * Test of setSettings method, of class Model.
     */
    @Test
    public void testSetSettings() {
        System.out.println("setSettings");
        final Settings settings = new Settings();
        settings.setStandardMountPoint("C:\\");
        settings.setGivenName("Horst");
        settings.setNumberTrayView(2);
        settings.setLanguage(Language.FRENCH);
        final Model instance = new Model();
        final boolean expResult = true;
        final boolean result = instance.setSettings(settings);
        assertEquals("set settings failed", expResult, result);
    }

    /**
     * Test of addDevice method, of class Model.
     */
    @Test
    public void testAddDevice() {
        System.out.println("addDevice");
        final UUID uid = UUID.randomUUID();
        final MobileDevice dev = new MobileDevice(uid);
        final Model instance = new Model();
        final boolean expResult = true;
        final boolean result = instance.addDevice(dev);
        assertEquals("adding failed", expResult, result);
    }

    /**
     * Test of addDevice with the same device already in map.
     */
    @Test
    public void testAddDevice2() {
        System.out.println("addDevice w/ already containing MD");
        final UUID uid = UUID.randomUUID();
        final MobileDevice dev1 = new MobileDevice(uid);
        final MobileDevice dev2 = new MobileDevice(uid);
        dev1.setGivenName("Horst");
        dev2.setGivenName("Tom");
        final Model instance = new Model();
        final boolean expResult = true;
        boolean result = instance.addDevice(dev1);
        result &= instance.addDevice(dev2);
        assertEquals("adding failed", expResult, result);
        assertEquals("overwrite failed", "Tom", dev1.getGivenName());
    }
}
