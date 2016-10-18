/*
 * @(#)IController.java
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
package edu.kit.ibds.mowidi.pc.controller;

import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Settings;

/**
 * This is the interface for the Controller used by the (possibly many) GUIs.
 * It needs to be implemented in at least one class inside the Controller so
 * the GUIs can get their needed information.
 *
 */
public interface IController {

    /**
     * This function can be used to set the properties of a defined MobileDevice
     * Device to new values.
     * @param m a MobileDevice Device containing the new values for the properties.
     * The uID needs to be corresponding to one saved in the Model.
     */
    void setProperties(MobileDevice m);

    /**
     * This function can be used to update the settings for the programme.
     * The <tt>SettingsPC</tt> object needs to contain the new information.
     * @param s SettingsPC object with new settings that shall be saved
     */
    void setSettings(Settings s);

    /**
     * This function allows you to start a new connection to a defined MobileDevice
     * Device. In the process a new file-system is created and mounted to the Mobiles
     * mount-point.
     * @param m the MobileDevice one wants to start a connection to
     */
    void startConnection(MobileDevice m);

    /**
     * This function allows you to terminate a consisting connection to a MobileDevice
     * Device. In the Process the corresponding file-system is unmounted as well.
     * @param m MobileDevice device with whom the connection shall be terminated
     */
    void closeConnection(MobileDevice m);

    /**
     * This function starts a new Search for mobile Devices immediately.
     * It is totally unrelated to the automatic search.
     */
    void manualSearch();

    /**
     * This function sets everything ready so the programme can be terminated.
     * After everything is set up the programme gets terminated.
     */
    void closeProgram();
}
