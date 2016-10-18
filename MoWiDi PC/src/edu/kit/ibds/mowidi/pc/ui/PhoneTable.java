/*
 * @(#)PhoneTable.java
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
package edu.kit.ibds.mowidi.pc.ui;

import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import java.util.UUID;

/**
 * This is the representation of the mobiles in table in the connection window.
 *
 * @author Kim Spiess
 */
class PhoneTable extends DefaultTableModel {

    /** serialVersionUID. */
    private static final long serialVersionUID = 6584841485252460668L;
    /** Index of uID column. */
    private static final int COLUMN_INDEX_UID = 3;
    /** Image which shall be displayed if the device is connected. */
    private final ImageIcon conImg;
    /** Image which shall be displayed if the device is not connected but permanent. */
    private final ImageIcon perImg;
    /** Image which shall be displayed if the device is available. */
    private final ImageIcon availableImg;

//    private ResourceBundle lang_resource;
//    private Language activeLanguage;
    /**
     * Create a new <tt>PhoneTable</tt> object.
     * @param langResource the resource bundle for language
     */
    public PhoneTable(final ResourceBundle langResource) {
        super();
//      this.lang_resource = lang_resource;
//      this.activeLanguage = activeLanguage;
        this.conImg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("trayicon.gif"));
        this.perImg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("trayicon.gif"));
        this.availableImg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("trayicon.gif"));
        addColumn(langResource.getString("STATE"));
        addColumn(langResource.getString("NAME"));
        addColumn(langResource.getString("MOUNTPOINT"));
        addColumn(langResource.getString("UID"));
    }

    /**
     * Seek rowIndex of mobile. Delete row if "old" mobile found.
     * @param mobile the mobile
     * @return rowIndex the row in which the mobile is found
     */
    private int seekAndDestroy(final MobileDevice mobile) {
        final UUID target = mobile.getuID();
        int row = 0;
        boolean found = false;
        while (row < this.getRowCount() && !found) {
            //FIXME: does not work i'm pretty sure. just fixed it so there is no error.
            final UUID current = (UUID) this.getValueAt(row, COLUMN_INDEX_UID);
            if (current.equals(target)) {
                found = true;
                this.removeRow(row);
            } else {
                row++;
            }
        }
        return row;
    }

    /**
     * Add new mobile / update outdated mobile.
     * @param mobile the mobile to add
     */
    public void add(final MobileDevice mobile) {
        final int rowIndex = seekAndDestroy(mobile);
        ImageIcon img = null;
        //TODO: Images auch als solche rendern und nicht als Text anzeigen!
        if (mobile.isConnected()) {
            img = conImg;
        } else if (mobile.isAvailable()) {
            img = availableImg;
        } else if (mobile.isPermanent()) {
            img = perImg;
        }
        final Object[] newRow = {img, mobile.getGivenName(), mobile.getMountPoint(),
            mobile.getuID()};
        this.insertRow(rowIndex, newRow);
    }

    /**
     * Remove an unavailable mobile.
     * @param mobile mobile to be removed
     */
    public void remove(final MobileDevice mobile) {
        seekAndDestroy(mobile);
    }

    @Override
    public boolean isCellEditable(final int row, final int column) {
        boolean result = false;
        if (column == 1 || column == 2) {
            result = true;
        }
        return result;
    }
}
