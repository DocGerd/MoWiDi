/*
 * @(#)IModel.java
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
 */
package edu.kit.ibds.mowidi.mobile.data;

import java.util.List;
import java.util.UUID;

/**
 * Interface of the model to the controller.
 * @author Patrick Kuhn
 */
interface IModel {

    /**
     * Get settings of this model.
     * @return the settings
     */
    Settings getSettings();

    /**
     * Set the settings of this mobile.
     * @param sm the settings
     * @return Returns if the Settings could be set successfuly
     */
    boolean setSettings(Settings sm);

    /**
     * Add a device to the list containing the devices.
     * @param pc the pc to add
     */
    void addPC(PCDevice pc);

    /**
     * Get a PCDevice by unique ID.
     * @param uID unique ID
     * @return the PCDevice
     */
    PCDevice getPC(UUID uID);

    /**
     * Remove a device from list by unique ID.
     * @param uID the unique ID.
     */
    void removePC(UUID uID);

    /**
     * Add all devices from collection to the device list.
     * @param pcs the devices to add.
     * @return <tt>true</tt>
     */
    boolean addAllPCs(List<PCDevice> pcs);

    /**
     * Get all devices currently in device list.
     * @return the PCs
     */
    List<PCDevice> getAllPC();
}
