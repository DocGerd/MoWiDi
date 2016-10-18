/*
 * @(#)SettingsPC.java
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
 * 2010-06-13   completed CS
 * 2010-06-29   constructor for default vals added PK
 * 2010-07-02   improved Standard Mountpoint, added Copy constructor
 * 2010-07-06   added STANDARD_NUMBER bla PK
 */
package edu.kit.ibds.mowidi.pc.data;

import edu.kit.ibds.mowidi.shared.data.AbstractSettings;

/**
 * Extends the shared settings and contains the local computer settings.
 * @author Christopher Schuetze
 */
public final class Settings extends AbstractSettings {

    /** serialVersionUID. */
    private static final long serialVersionUID = 178040479956676433L;
    /** Standard number of mobiles in tray view. */
    private static final int STANDARD_NUMBER_TRAY_VIEW = 5;
    /** Path of the default mount point. **/
    private String standardMountPoint;
    /** Amount of mobile phones in tray view. **/
    private int numberTrayView;

    /**
     * Create a settings object.
     */
    public Settings() {
        super();
        standardMountPoint = System.getProperty("user.home") + "/MoWiDi";
        numberTrayView = STANDARD_NUMBER_TRAY_VIEW;
    }


    /**
     * Get number of items to be shown in tray view.
     * @return amount
     */
    public int getNumberTrayView() {
        return numberTrayView;
    }

    /**
     * Set number of items in tray view.
     * @param numberTrayView amount
     */
    public void setNumberTrayView(final int numberTrayView) {
        if (numberTrayView < 0) {
            throw new IllegalArgumentException();
        }
        this.numberTrayView = numberTrayView;
    }

    /**
     * Get standard mount-point.
     * @return the standard mount-point
     */
    public String getStandardMountPoint() {
        return standardMountPoint;
    }

    /**
     * Set standard mount-point.
     * @param standardMountPoint the standard mount-point
     */
    public void setStandardMountPoint(final String standardMountPoint) {
        // TODO: check path
        this.standardMountPoint = standardMountPoint;
    }
}
