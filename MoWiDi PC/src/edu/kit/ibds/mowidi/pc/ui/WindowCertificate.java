/*
 * @(#)WindowCertificate.java
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
 * 2010-06-11   repaired PK
 * 2010-07-13   look and feel improofed CS
 */
package edu.kit.ibds.mowidi.pc.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Shows a window for new authorisation request.
 * @author Christopher Schuetze
 */
final class WindowCertificate extends JFrame {

    /** JFrame default serial. */
    private static final long serialVersionUID = 7462986245858786452L;
    /** The string of the certificate. */
    private final String certificate;

    /**
     * Create a new <tt>WindowCertificate</tt>.
     * @param target name of mobile
     * @param cert the certificate representation to be shown
     * @param res
     */
    public WindowCertificate(final String target, final String cert, final ResourceBundle res) {
        super(res.getString("TRY_CONNECT") + " " + target);
        this.certificate = cert;
        this.setResizable(false);
        final Dimension frameSize = new Dimension(350, 100);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int top = (screenSize.height - frameSize.height) / 2;
        final int left = (screenSize.width - frameSize.width) / 2;
        this.setSize(frameSize);
        setLocation(left, top);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final JPanel pan = new JPanel();
        JLabel lab = new JLabel(certificate);
        lab.setFont(new Font("Arial",Font.BOLD,30));
        pan.add(lab);
        lab = new JLabel(res.getString("VALIDATE_TEXT"));
        pan.add(lab);
        this.setContentPane(pan);
        this.setVisible(true);
    }
}
