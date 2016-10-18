/*
 * @(#)WindowAbout.java
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
 * 2010-06-04   comments
 * 2010-06-10   first revision, PM
 * 2010-06-10   correction:
 *                  - several build errors
 *                  - for internationalisation Strings must be attributes
 *                  - constructor does NOT show the window!
 *                  PK
 * 2010-06-10   singleton removed, internationalized PK
 * 2010-07-03   slightly improved
 * 2010-07-13   refactorised PK
 */
package edu.kit.ibds.mowidi.pc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * Shows a window with the credentials of MOWIDI.
 * @author Patrick Kuhn
 */
final class WindowAbout extends JDialog {

    /** JFrame default serial. **/
    private static final long serialVersionUID = -1738067493845431583L;
    /** The authors. */
    private static final String AUTHORS = "Michael Auracher<br/>"
            + "Patrick Kuhn<br/>Christopher Sch&uuml;tze<br/>"
            + "Kim Spieß<br/>André Wengert";
    /** Version. */
    private static final String VERSION = "0.9.0.873 beta";
    /** Standard width of window. */
    private static final int WINDOW_WIDTH = 400;
    /** Standard height of window. */
    private static final int WINDOW_HEIGHT = 300;
// <editor-fold defaultstate="collapsed" desc="Labels and stuff">
    /** Label for logo. */
    private final JLabel jLabelLogo;
    /** Label for header. */
    private final JLabel jLabelMoWiDi;
    /** Label for the version. */
    private final JLabel jLabelVersion;
    /** Label for the authors. YEAH: */
    private final JLabel jLabelAuthors;
    /** The close button. */
    private final JButton close;
    /** The Box for the layout. */
    private final Box box = new Box(BoxLayout.Y_AXIS);
// </editor-fold>

    /**
     * Constructor for a new WindowAbout object. It always gets newly Constructed
     * @param res the resource bundle
     */
    public WindowAbout(final ResourceBundle res) {
        super();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        final Dimension frameSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int top = (screenSize.height - frameSize.height) / 2;
        final int left = (screenSize.width - frameSize.width) / 2;

        setSize(frameSize);
        setLocation(left, top);

        setTitle(res.getString("ABOUT_TITLE"));
        setAlwaysOnTop(true);
        setResizable(false);

        jLabelLogo = new JLabel();
        jLabelLogo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("logo.png")));
        jLabelMoWiDi = new JLabel("<html><center><b><h2>MOWIDI</h2></b></center></html>");
        jLabelVersion = new JLabel(res.getString("ABOUT_VERSION") + VERSION);
        jLabelAuthors = new JLabel("<html><b><p></p>" + res.getString("ABOUT_AUTHOR")
                + "</b>" + AUTHORS + "</html>");

        box.add(jLabelLogo);
        box.add(jLabelMoWiDi);
        box.add(jLabelVersion);
        box.add(jLabelAuthors);

        close = new JButton(res.getString("CLOSE"));
        close.setName("CLOSE");
        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent event) {
                dispose();
            }
        });
        box.add(close);
        add(box);
        pack();
    }
}
