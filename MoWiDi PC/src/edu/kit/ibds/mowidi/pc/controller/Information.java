/*
 * @(#)Information.java
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

import java.util.ArrayList;
import java.util.List;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Settings;
import java.util.Arrays;

/**
 * This class encapsulates all the information needed by the GUI
 * to be informed about any changes in the Model.
 */
public final class Information {

    /** List of MobileDevice Phones that have been modified and need to be updated in the view. */
    private final List<MobileDevice> modified;
    /** Settings Object containing the newest information about any changed Settings. */
    private final Settings settings;
    /** ErrorType containing if a specific error has occurred so the GUI can print error Messages. */
    private final ErrorType error;

    /**
     * Constructor to generate a complete Information Object with every
     * information present. (i.e after starting the programme to inform the
     * GUI about the initial values).
     * @param modified List of MobileDevice Phones with new information
     * @param settings Settings Object containing the newest Settings
     * @param error ErrorType which occurred.
     */
    public Information(final List<MobileDevice> modified, final Settings settings, final ErrorType error) {
        this.modified = modified;
        this.settings = settings;
        this.error = error;
    }

    /**
     * Another constructor which is used to create an <tt>Information</tt> object only
     * containing modified MobileDevice Phones but no other information.
     * @param modified List of changed mobile Phones
     */
    public Information(final MobileDevice[] modified) {
        final ArrayList<MobileDevice> arrl = new ArrayList<MobileDevice>(Arrays.asList(modified));
        this.modified = arrl;
        this.settings = null;
        this.error = ErrorType.NULL;
    }

    /**
     * Constructor which is used to create an Information Object only containing
     * the new Settings but no other Information.
     * @param settings The newest Settings
     */
    public Information(final Settings settings) {
        this.modified = new ArrayList<MobileDevice>();
        this.settings = settings;
        this.error = ErrorType.NULL;
    }

    /**
     * Constructor which can be used if an Error occurred and no other Information
     * has changed.
     * @param error ErrorType which has occurred.
     */
    public Information(final ErrorType error) {
        this.modified = new ArrayList<MobileDevice>();
        this.settings = null;
        this.error = error;
    }

    /**
     * Get the error.
     * @return the type of error
     */
    public ErrorType getError() {
        return error;
    }

    /**
     * Get the modified device.
     * @return the modified <tt>MobileDevice</tt>
     */
    public List<MobileDevice> getModified() {
        return modified;
    }

    /**
     * Get the new settings.
     * @return the new settings
     */
    public Settings getSettings() {
        return settings;
    }
}
