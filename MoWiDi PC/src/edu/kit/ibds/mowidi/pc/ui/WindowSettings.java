/*
 * @(#)WindowSettings.java
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.kit.ibds.mowidi.pc.controller.ErrorType;
import edu.kit.ibds.mowidi.pc.controller.IController;
import edu.kit.ibds.mowidi.pc.controller.Information;
import edu.kit.ibds.mowidi.pc.controller.ObservingView;
import edu.kit.ibds.mowidi.pc.data.Settings;
import edu.kit.ibds.mowidi.shared.data.Language;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Shows a window with the local settings of MOWIDI.
 * @author Christopher Schuetze
 * @author Michal Auracher
 */
final class WindowSettings extends JFrame implements ObservingView {

    /** JFrame default serial. */
    private static final long serialVersionUID = 1L;
    /** ComboBox to select the possible languages. */
    private final JComboBox language;
    /** Spinner to select the amount of shown entries in the TrayMenu. */
    private final JSpinner availableCount;
    /** TextField to select the default mount point for new devices. */
    private final JTextField standardMountPoint;
    /** IController object. */
    private final IController controller;
    /** Button to accept the changes made. */
    private final JButton accept;
    /** Button to cancel the changes made. */
    private final JButton cancel;
    /** Resource bundle for i18n. */
    private ResourceBundle langResource;
    /** Language currently active in this Window. */
    private Language activeLanguage;

    /**
     * Constructor for a new WindowSettings object.
     * @param control
     **/
    public WindowSettings(final IController control) {
        super();
        this.langResource = ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH);
        this.setTitle(langResource.getString("SETTINGS"));
        final Dimension frameSize = new Dimension(400, 300);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int top = (screenSize.height - frameSize.height) / 2;
        final int left = (screenSize.width - frameSize.width) / 2;
        setLocation(left, top);
        controller = control;
        language = new JComboBox(Language.values());
        availableCount = new JSpinner();
        availableCount.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent arg0) {
                if ((Integer) availableCount.getValue() < 0) {
                    availableCount.setValue(0);
                }
            }
        });
        standardMountPoint = new JTextField();
        accept = new JButton();
        cancel = new JButton();
        this.makeActions();
        this.setTexts();
        this.makeLayout();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void update(final Information i) {
        final Settings sets = i.getSettings();
        if (sets != null) {
            language.setSelectedItem(sets.getLanguage());
            availableCount.setValue(sets.getNumberTrayView());
            standardMountPoint.setText(sets.getStandardMountPoint());
            if (sets.getLanguage() != this.activeLanguage) {
                this.activeLanguage = sets.getLanguage();
                langResource = ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", new Locale(sets.getLanguage().getLanguageCode()));
                this.setTexts();
                this.makeLayout();
            }
        }
        if (i.getError() == ErrorType.MOUNTPOINT_NOT_AVAILABLE) {
            JOptionPane.showMessageDialog(this, langResource.getString("ERROR_MOUNT_NOT_AVAILABLE"),
                    langResource.getString("ERROR_MOUNT_NOT_AVAILABLE"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setTexts() {
        this.setTitle(langResource.getString("SETTINGS"));
        this.accept.setText(langResource.getString("ACCEPT"));
        this.cancel.setText(langResource.getString("CANCEL"));
    }

    private void makeLayout() {
        final GridLayout gl = new GridLayout(4, 2);
        final JPanel pane = new JPanel();
        pane.setLayout(gl);
        pane.add(new JLabel(langResource.getString("LANGUAGE") + ": "));
        pane.add(language);
        pane.add(new JLabel(langResource.getString("NUM_PHONES") + ": "));
        pane.add(availableCount);
        pane.add(new JLabel(langResource.getString("STD_MOUNTPOINT") + ": "));
        pane.add(standardMountPoint);
        pane.add(accept);
        pane.add(cancel);
        this.setContentPane(pane);
        this.pack();
    }

    private void makeActions() {
        accept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                final Settings settings = new Settings();
                settings.setStandardMountPoint(standardMountPoint.getText());
                settings.setNumberTrayView((Integer) availableCount.getValue());
                settings.setLanguage((Language) language.getSelectedItem());
                controller.setSettings(settings);
                setVisible(false);
            }
        });

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                setVisible(false);
            }
        });
    }
}
