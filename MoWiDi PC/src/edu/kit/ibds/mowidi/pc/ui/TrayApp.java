/*
 * @(#)TrayApp.java
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
 * 2010-06-28   Needed Controller in WindowSettings so i delivered it on construction.
 */
package edu.kit.ibds.mowidi.pc.ui;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import edu.kit.ibds.mowidi.pc.controller.IController;
import edu.kit.ibds.mowidi.pc.controller.Information;
import edu.kit.ibds.mowidi.pc.controller.ObservingView;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import java.util.UUID;

/**
 * This is the tray icon. It manages the complete UI.
 *
 * @author Kim Spiess
 * @author Michael Auracher
 * @author Patrick Kuhn
 */
public final class TrayApp extends TrayIcon implements ObservingView {

    private static final long serialVersionUID = 896230193521248026L;
    /** The controller. */
    private final IController controller;
    /** The connections window. */
    private final WindowConnection winCon;
    /** The settings window. */
    private final WindowSettings winSet;
    /** Certification Window displayed on connection establishment. */
    //private WindowCertificate wincert;
    /** Resource for i18n purposes. */
    private ResourceBundle langResource;
    /** The menu tray. */
    private final MenuTray mt;

    /**
     * This constructs a new TrayApp object with an image and a reference to an
     * IController.
     * @param controller <tt>IController</tt> to be used
     */
    public TrayApp(final IController controller) {
        super(Toolkit.getDefaultToolkit().getImage("trayicon.gif"));
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // no error should happen here at all
            throw new RuntimeException(ex);
        }
        this.langResource = ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH);
        this.setToolTip(langResource.getString("TRAY_TOOL"));
        this.setImageAutoSize(true);
        this.controller = controller;
        this.winSet = new WindowSettings(controller);
        this.winCon = new WindowConnection(winSet);
        //wincert = null;
        mt = new MenuTray(langResource, 5, controller);
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
                    mt.setLocation(e.getX(), e.getY() + 5);
                    mt.setInvoker(mt);
                    mt.setVisible(true);
                }
            }
        });
        if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();
            try {
                tray.add((TrayIcon) this);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Tray not supported");
        }
    }

    private void setTexts() {
        this.setToolTip(langResource.getString("TRAY_TOOL"));
        this.mt.setTexts(langResource);
    }

    @Override
    public void update(final Information i) {
        winSet.update(i);
        winCon.update(i);
        if (i.getModified().size() > 0) {
            final Iterator<MobileDevice> it = i.getModified().iterator();
            while (it.hasNext()) {
                final MobileDevice md = it.next();
//                if (md.isConnected() && wincert != null) {
//                    wincert.dispose();
//                    wincert = null;
//                }
                this.mt.addPhone(md);
            }
        }
        if (i.getSettings() != null) {
            this.mt.setShownPhones(i.getSettings().getNumberTrayView());
            // FIXME: activeLanguage is never set!
            langResource =
                    ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res",
                    new Locale(i.getSettings().getLanguage().getLanguageCode()));
            this.setTexts();
        }
    }

    private class CloseActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            controller.closeProgram();
        }
    }

    private class SettingsActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            winSet.setVisible(true);
        }
    }

    private class ConnectionActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            winCon.setVisible(true);
        }
    }

    private final class MenuTray extends JPopupMenu {

        private static final long serialVersionUID = 1L;
        /** Map saving the JMenuItems and the corresponding UID from the phone. */
        private final Map<UUID, JMenuItem> phonesList;
        /** count of Phones which shall be shown in the Tray Icon. */
        private int shownPhones;
        /** Reference to the Controller to perform the Actions. */
        private final IController control;
// <editor-fold defaultstate="collapsed" desc="The Menu Items which need to be translated.">
        private final JMenuItem available;
        private final JMenu morePhones;
        private final JMenuItem connection;
        private final JMenuItem settings;
        private final JMenuItem close;
// </editor-fold>

        /**
         * Constructor for a new Menu for the Tray Icon.
         *
         * @param res A ResourceBundle needed for the proper language.
         * @param shownPhones number of Phones which shall be initially shown.
         * @param control Reference to the Controller so the Actions can perform their task.
         */
        public MenuTray(final ResourceBundle res, final int shownPhones, final IController control) {
            super();

            this.control = control;
            this.shownPhones = shownPhones;
            this.phonesList = new TreeMap<UUID, JMenuItem>();

            available = new JMenuItem();
            this.add(available);

            morePhones = new JMenu();
            morePhones.setVisible(false);
            this.add(morePhones);

            this.addSeparator();

            connection = new JMenuItem();
            connection.addActionListener(new ConnectionActionListener());
            this.add(connection);

            settings = new JMenuItem();
            settings.addActionListener(new SettingsActionListener());
            this.add(settings);

            this.addSeparator();

            close = new JMenuItem();
            close.addActionListener(new CloseActionListener());
            this.add(close);

            setTexts(res);
        }

        public void setTexts(final ResourceBundle res) {
            available.setText(res.getString("AVAILABLE_PHONES") + ":");
            available.setEnabled(false);
            morePhones.setText(res.getString("MORE_PHONES"));
            close.setText(res.getString("CLOSE"));
            settings.setText(res.getString("SETTINGS"));
            connection.setText(res.getString("CONNECTION_MANAGER"));
        }

        /**
         * Function to set the amount of phones which shall be displayed in the Tray.
         * @param newNumber the count of phones which shall be shown
         */
        public void setShownPhones(final int newNumber) {
            shownPhones = newNumber;
            balancePhones();
        }

        private void balancePhones() {
            final int start = 1;
            final int end = this.getComponentIndex(morePhones) - 1;
            if (end - start >= shownPhones) {
                int count = end - start - shownPhones;
                final int pos = end - count;
                for (; count >= 0; count--) {
                    final Component comp = this.getComponent(pos);
                    this.morePhones.add(comp);
                    this.remove(comp);
                }
            } else if (this.morePhones.getMenuComponentCount() > 0) {
                for (int count = shownPhones - end - start; count >= 0; count--) {
                    if (this.morePhones.getMenuComponentCount() > 0) {
                        final Component comp = this.morePhones.getMenuComponent(0);
                        this.add(comp, this.getComponentIndex(morePhones));
                        this.morePhones.remove(comp);
                    }
                }
            }
            if (this.morePhones.getMenuComponentCount() == 0) {
                this.morePhones.setVisible(false);
            } else {
                this.morePhones.setVisible(true);
            }
            this.validate();
        }

        /**
         * Function to add or remove a Phone from the Tray list. If the Phone
         * is no longer available it will be remove otherwise nothing will happen.
         * If a new Phone is available it will be added as long as the amount of
         * Phones which shall be shown is not already reached.
         * @param md the mobile phone you want to add or remove from the List
         */
        public void addPhone(final MobileDevice md) {
            //Bring the phone to which we are connected to the first Spot.
            if (md.isAvailable() && md.isConnected() && phonesList.containsKey(md.getuID())) {
                final JMenuItem phone = phonesList.get(md.getuID());
                this.remove(phone);
                this.morePhones.remove(phone);
                this.add(phone, 1);
                this.validate();
                //If the phone is no longer available but we have a button -> remove it
            } else if (!md.isAvailable() && phonesList.containsKey(md.getuID())) {
                final JMenuItem phone = phonesList.get(md.getuID());
                this.remove(phone);
                this.morePhones.remove(phone);
                phonesList.remove(md.getuID());
                //If the Phone is available but not in our list -> add it
            } else if (md.isAvailable() && !phonesList.containsKey(md.getuID())) {
                final JMenuItem phone = new JMenuItem("     -" + md.getGivenName());
                phone.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent ae) {
                        if (md.isConnected()) {
                            control.closeConnection(md);
                        } else {
                            //TODO: WindowCertificate with real certificate
                            final WindowCertificate wincert = new WindowCertificate(md.getGivenName(), "ABCD-EFGH-11-88-0", langResource);
                            wincert.addWindowListener(new WindowAdapter() {

                                @Override
                                public void windowClosing(final WindowEvent we) {
                                    control.closeConnection(md);
                                }
                            });
                            control.startConnection(md);

                            // Hide window if mobile is connected
                            // TODO thread or sth. l.t.
                            if (md.isConnected()) {
                                wincert.setVisible(false);
                            }
                        }
                    }
                });
                phonesList.put(md.getuID(), phone);
                if (phonesList.size() > shownPhones) {
                    this.morePhones.add(phone);
                } else {
                    this.add(phone, this.getComponentIndex(morePhones));
                }
            }
            balancePhones();
        }
    }
}
