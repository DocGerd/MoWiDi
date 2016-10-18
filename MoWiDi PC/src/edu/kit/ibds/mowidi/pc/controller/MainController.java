/*
 * @(#)MainController.java
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

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import edu.kit.ibds.mowidi.pc.data.IModelController;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import edu.kit.ibds.mowidi.pc.data.Model;
import edu.kit.ibds.mowidi.pc.data.Settings;
import edu.kit.ibds.mowidi.pc.network.ConnectionPC;
import edu.kit.ibds.mowidi.pc.network.LocalDevice;
import edu.kit.ibds.mowidi.pc.ui.TrayApp;

/**
 * This class is the control centre of the Programme. It is used by the View
 * through the Interface and performs the changes on the Model. It is also the
 * entering Point for the programme.
 *
 * @author Michael Auracher
 */
public final class MainController implements IController {

    /** The notifier. */
    private final Notifier notifier;
    /** The model. */
    private final IModelController modelController;
    /** The FUSE Threads. */
    private final List<FuseRunner> fuseThreads;
    /** The UPNP and network stuff object. */
    private final LocalDevice net;

    /**
     * Constructor for a new MainController Object.
     */
    public MainController() {
        this.notifier = new Notifier();
        this.fuseThreads = new LinkedList<FuseRunner>();
        final Model m = new Model();
        this.modelController = m;
        modelController.loadAll();
        this.net = new LocalDevice(m, notifier);
        final MainController control = this;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                control.notifier.addObservingView(new TrayApp(control));
                final Information startInfo = new Information(modelController.getAllDevices(),
                        modelController.getSettings(), ErrorType.NULL);
                control.notifier.notifyObservers(startInfo);
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void closeConnection(final MobileDevice m) {
        FuseRunner fr = null;
        boolean found = false;
        for (final Iterator<FuseRunner> it = fuseThreads.iterator(); it.hasNext() && !found;) {
            final FuseRunner temp = it.next();
            if (temp.getUID().equals(m.getuID())) {
                fr = temp;
                found = true;
            }
        }
        if (found) {
            fr.unmount();
            fuseThreads.remove(fr);
            m.setConnected(false);
            modelController.addDevice(m);
            final MobileDevice[] md = {m};
            final Information info = new Information(md);
            notifier.notifyObservers(info);

        } else {
            return;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void closeProgram() {
        //Unmount all mounted Filesystems and delete created directories.
        for (FuseRunner runner : fuseThreads) {
            runner.unmount();
            try {
                runner.join();
            } catch (InterruptedException e) {
                //Not expected to happen
                throw new RuntimeException(e);
            }
        }
        modelController.saveAll();
        net.stop();
        final Window[] windows = Window.getWindows();
        for (Window win : windows) {
            win.dispose();
        }
        final SystemTray tray = SystemTray.getSystemTray();
        final TrayIcon[] icons = tray.getTrayIcons();
        for (TrayIcon ico : icons) {
            tray.remove(ico);
        }
        // FIXME: replace exit(0) by something more fancy
        System.exit(0);
    }

    /** {@inheritDoc} */
    @Override
    public void manualSearch() {
        // XXX: check if correct
        net.search();
    }

    /** {@inheritDoc} */
    @Override
    public void setProperties(final MobileDevice m) {
        modelController.addDevice(m);
        final MobileDevice[] mb = {m};
        final Information info = new Information(mb);
        notifier.notifyObservers(info);
    }

    /** {@inheritDoc} */
    @Override
    public void setSettings(final Settings s) {
        Settings sets = s;
        if (!modelController.setSettings(sets)) {
            notifier.notifyObservers(new Information(ErrorType.MOUNTPOINT_NOT_AVAILABLE));
            sets = modelController.getSettings();
        }
        notifier.notifyObservers(new Information(sets));
    }

    /** {@inheritDoc} */
    @Override
    public void startConnection(final MobileDevice m) {
        final ConnectionPC cp = net.connectTo(m);
        final Fuse f = new Fuse(cp);
        final FuseRunner fr = new FuseRunner(f, m.getuID(), m.getMountPoint() + "/" + m.getGivenName());
        fuseThreads.add(fr);
        fr.start();
        m.setConnected(true);
        modelController.addDevice(m);
        final MobileDevice[] md = {m};
        final Information info = new Information(md);
        notifier.notifyObservers(info);
    }

    /**
     * Entering Point of the programme. It starts the Construction of the
     * classes and sets everything ready for the use by the user.
     * @param args no command line arguments supported yet
     */
    public static void main(final String[] args) {
        new MainController();
    }
}
