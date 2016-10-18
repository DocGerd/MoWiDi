/*
 * @(#)WindowConnection.java
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
 * 2010-07-02   Internationalised
 * 2010-07-06   remove of mobiles fixed PK
 */
package edu.kit.ibds.mowidi.pc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import edu.kit.ibds.mowidi.pc.controller.Information;
import edu.kit.ibds.mowidi.pc.controller.ObservingView;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.shared.data.Language;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * This is the connection manager window.
 *
 * @author Christopher Schuetze
 * @author Kim Spiess
 */
final class WindowConnection extends JFrame implements ObservingView, TableModelListener {

    /** serialVersionUID. */
    private static final long serialVersionUID = -2972272480606022803L;
    /** The controller. */
    private final WindowConnection winCon = this;
    /** The settings window. */
    private final WindowSettings winSet;
    /** The table for the phones. */
    private final JTable table;
    /** The resource bundle. */
    private ResourceBundle langResource;
    /** The active language. */
    private Language activeLanguage;

    /**
     * constructs new WindowConnection object with a reference to the
     * controller and the settings window.
     *
     * @param controller controller to use
     * @param winSet settings window
     */
    public WindowConnection(final WindowSettings winSet) {
        super();

        // set language
        this.langResource = ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH);
        activeLanguage = Language.ENGLISH;

        this.setTitle(langResource.getString("CONNECTION_MANAGER"));
        final Dimension frameSize = new Dimension(600, 500);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int top = (screenSize.height - frameSize.height) / 2;
        final int left = (screenSize.width - frameSize.width) / 2;
        setSize(frameSize);
        setLocation(left, top);

        this.winSet = winSet;
        this.setJMenuBar(new Menu());

        // set phone table
        this.table = new JTable(new PhoneTable(langResource));
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                if (table.getSelectedRow() != -1) {
                    ((Menu) getJMenuBar()).setMenuActive(true);
                } else {
                    ((Menu) getJMenuBar()).setMenuActive(false);
                }
            }
        });
        this.table.getModel().addTableModelListener((TableModelListener) this);
        this.add(new JScrollPane(table));
        this.pack();
    }

    @Override
    public void update(final Information i) {
        if (i.getModified() != null) {
            final Iterator<MobileDevice> iter = i.getModified().iterator();
            while (iter.hasNext()) {
                final MobileDevice device = iter.next();
                if (!device.isAvailable() && !device.isPermanent()) {
                    ((PhoneTable) table.getModel()).remove(device);

                } else {
                    ((PhoneTable) table.getModel()).add(device);

                }
            }
        }
        if (i.getSettings() != null) {
            // TODO update settings
            if (i.getSettings().getLanguage() != this.activeLanguage) {
                activeLanguage = i.getSettings().getLanguage();
                langResource = ResourceBundle.getBundle(
                        "edu/kit/ibds/mowidi/pc/ui/res", new Locale(
                        activeLanguage.getLanguageCode()));
                updateLanguage();
            }
        }
        if (i.getError() != null) {
            // TODO: handle error
        }
        this.validate();
        // this.pack();
    }

    private void updateLanguage() {
        this.setTitle(langResource.getString("CONNECTION_MANAGER"));
        this.setJMenuBar(new Menu());
        this.table.getColumnModel().getColumn(0).setHeaderValue(langResource.getString("STATE"));
        this.table.getColumnModel().getColumn(1).setHeaderValue(langResource.getString("NAME"));
        this.table.getColumnModel().getColumn(2).setHeaderValue(langResource.getString("MOUNTPOINT"));
        this.table.getColumnModel().getColumn(3).setHeaderValue(langResource.getString("UID"));
    }

    @Override
    public void tableChanged(final TableModelEvent e) {
        try {
            final int row = e.getFirstRow();
            final int column = e.getColumn();
            final PhoneTable model = (PhoneTable) e.getSource();
            final Object data = model.getValueAt(row, column);

            switch (column) {
                case 1:
                    // TODO Check data Input
                    System.out.println("Bearbeite Name: " + data);
                    break;

                case 2:
                    // TODO Check data Input
                    System.out.println("Bearbeite Mountpoint: " + data);
                    break;

                default:
                    assert false : "Unknown column number.";
                    Logger.getLogger(WindowConnection.class.getName()).log(Level.WARNING,
                            "Unknown column number.");
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Do nothing
            Logger.getLogger(WindowConnection.class.getName()).log(Level.FINE,
                    "Bad index", ex);
        }
    }

    private class CloseActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            winCon.setVisible(false);
        }
    }

    private class NameActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            // TODO Switch into name and wait for input
        }
    }

    private class MountpointActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            // TODO Switch into Mountpoint and wait for input
        }
    }

    private class SettingsActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            winSet.setVisible(true);
        }
    }
    
    private class SHelpActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            final WindowSHelp winSHelp = new WindowSHelp(langResource);
            winSHelp.setVisible(true);
        }
    }
    
    private class AboutActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent arg0) {
            new WindowAbout(langResource).setVisible(true);
        }
    }

    private class Menu extends JMenuBar {

        /** Serial Version UID. */
        private static final long serialVersionUID = 1L;
        private final JMenuItem name;
        private final JMenuItem mnt;
        private final JMenu file;
        private final JMenuItem close;
        private final JMenu edit;
        private final JMenuItem settings;
        private final JMenu help;
        private final JMenuItem shelp;
        private final JMenuItem about;

        /**
         * Function to manipulate the state of the MenuItems which are related
         * to a selected Entry in the Table.
         * @param active if they shall be active or inactive
         */
        public void setMenuActive(final boolean active) {
            this.name.setEnabled(active);
            this.mnt.setEnabled(active);
        }

        /**
         * Generates a new Menu for the WindowConnection.
         */
        public Menu() {
            super();

            file = new JMenu(langResource.getString("FILE"));
            close = new JMenuItem(langResource.getString("CLOSE"));
            close.addActionListener(new CloseActionListener());
            file.add(close);
            this.add(file);

            edit = new JMenu(langResource.getString("EDIT"));
            mnt = new JMenuItem(langResource.getString("CHANGE_MOUNTPOINT"));
            mnt.addActionListener(new MountpointActionListener());
            mnt.setEnabled(false);
            name = new JMenuItem(langResource.getString("CHANGE_NAME"));
            name.setEnabled(false);
            name.addActionListener(new NameActionListener());
            settings = new JMenuItem(langResource.getString("SETTINGS"));
            settings.addActionListener(new SettingsActionListener());
            edit.add(mnt);
            edit.add(name);
            edit.addSeparator();
            edit.add(settings);
            this.add(edit);
            
            help = new JMenu(langResource.getString("HELP"));
            shelp = new JMenuItem(langResource.getString("SHORT_HELP"));
            //shelp.addActionListener(new SHelpActionListener());
            about = new JMenuItem(langResource.getString("ABOUT"));
            about.addActionListener(new AboutActionListener());
            //help.add(shelp);
            //help.addSeparator();
            help.add(about);
            this.add(help);
            
        }
    }
}
