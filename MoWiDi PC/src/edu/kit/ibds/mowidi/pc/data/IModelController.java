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
 * 2010-06-16   completed CS
 */
package edu.kit.ibds.mowidi.pc.data;

import java.util.List;
import java.util.UUID;

/**
 * This class is the interface for the Model and Controller.
 * @author Christopher Schuetze
 */
public interface IModelController {

    /**
     * Saves the settings of an SettingsPC object.
     * @param s a SettingsPC object
     * @return returns if the change of the Settings was successful
     */
    boolean setSettings(Settings s);

    /**
     * Returns the SettingsPC object which contains local configuration informations.
     * @return a SettingsPC object with settings
     */
    Settings getSettings();

    /**
     * Adds a mobile object.
     * @param d a mobile device
     * @return true if added, false if not
     */
    boolean addDevice(MobileDevice d);

    /**
     * Returns a device which is selected by its uID.
     * @param uID the id of the device
     * @return a mobile object
     */
    MobileDevice getDevice(UUID uID);

    /**
     * Removes a device.
     * @param uID the unique id of the device
     * @return the device
     */
    MobileDevice removeDevice(UUID uID);

    /**
     * Adds an array of mobile objects.
     * @param md Array of mobile devices
     * @return mobile array
     */
    boolean addAllDevices(List<MobileDevice> md);

    /**
     * Returns a mobile array of available mobile phones.
     * @return mobile array
     */
    List<MobileDevice> getAllDevices();

    /**
     * Function to load everything.
     */
    void loadAll();

    /**
     * Function to save Everything.
     */
    void saveAll();
}
