/*
 * @(#)MobileProperties.java
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

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.kit.ibds.mowidi.pc.controller.IController;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;

/**
 * This is a properties window of a specific mobile.
 *
 * @author Kim Spiess
 * @author Michael Auracher
 */
class MobileProperties extends JFrame {

    /** serialVersionUID. */
    private static final long serialVersionUID = -2298235959889261033L;
    /** The mobile to change properties from. */
    private final MobileDevice m;
    /** The controller. */
    private final IController c;
    /** Text field to change name. */
    private final JTextField name;
    /** Text field to change mount-point. */
    private final JTextField mountPoint;
    /** Button to accept. */
    private final JButton accept;
    /** Button to cancel. */
    private final JButton cancel;
    /** Resource for i18n. */
    private final ResourceBundle langResource;

    /**
     * constructs a new properties window to a mobile with a reference to the
     * controller.
     *
     * @param m mobile
     * @param c controller
     * @param res the language bundle
     */
    public MobileProperties(final MobileDevice m, final IController c, final ResourceBundle res) {
        super(res.getString("MOBILE_PROPERTIES"));
        this.langResource = res;
        this.m = m;
        this.c = c;
        this.name = new JTextField(m.getGivenName());
        this.mountPoint = new JTextField(m.getMountPoint());
        this.accept = new JButton(langResource.getString("ACCEPT"));
        this.cancel = new JButton(langResource.getString("CANCEL"));
        this.makeLayout();
        this.makeActions();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void makeLayout() {
        final GridLayout gl = new GridLayout(3, 2);
        this.setLayout(gl);
        final Container content = this.getContentPane();
        content.add(new JLabel(langResource.getString("NAME") + ": "));
        content.add(name);
        content.add(new JLabel(langResource.getString("MOUNTPOINT") + ": "));
        content.add(mountPoint);
        content.add(accept);
        content.add(cancel);
    }

    private void makeActions() {
        accept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                m.setGivenName(name.getText());
                m.setMountPoint(mountPoint.getText());
                c.setProperties(m);
            }
        });

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
            }
        });
    }
}
