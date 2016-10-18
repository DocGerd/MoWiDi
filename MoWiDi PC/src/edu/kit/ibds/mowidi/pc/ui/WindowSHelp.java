/*
 * @(#)WindowSHelp.java
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
 * 2010-07-13   first revision CS
 *              should be enough for first version CS
 */
package edu.kit.ibds.mowidi.pc.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * Shows a new Window with the short help.
 * @author Christopher Schuetze
 * @author Patrick Kuhn
 */
public class WindowSHelp extends JFrame {

    /** JFrame default serial. **/
    private static final long serialVersionUID = -1738067493876131583L;
    private final JTextArea ta;

    /**
     * Constructor for new Help Window.
     * @param res the resource bundle
     */
    public WindowSHelp(final ResourceBundle res) {
        super();

        ta = new JTextArea(4, 10);
        ta.setText(res.getString("SHELP_TEXT"));
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setWrapStyleWord(true);
        ta.setBackground(Color.WHITE);
        this.add(ta);

        final Dimension frameSize = new Dimension(400, 300);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int top = (screenSize.height - frameSize.height) / 2;
        final int left = (screenSize.width - frameSize.width) / 2;
        setSize(frameSize);
        setLocation(left, top);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(res.getString("SHELP_TITLE"));
        this.setResizable(false);
        this.setSize(400, 400);
    }
}
