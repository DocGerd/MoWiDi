/*
 * @(#)MessageBox.java
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
 * 2010-06-23   created PK
 */
package edu.kit.ibds.mowidi.mobile.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * This class provides a simple message box just to show a title, message and
 * a OK button. Nothing happens on OK.
 * @author Patrick Kuhn
 */
public final class MessageBox {

    /** The <tt>AlertDialog</tt> for this message box. */
    private AlertDialog ad;

    /**
     * Create a new message box.
     * @param context the context on which this box is to be shown
     * @param title the title
     * @param message the message
     */
    public MessageBox(final Context context, final String title, final String message) {
        ad = new AlertDialog.Builder(context).create();
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setButton("OK", new OnClickListener());
    }

    /**
     * Show this box.
     */
    public void show() {
        ad.show();
    }

    /** Listener for the message box. */
    private class OnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(final DialogInterface arg0, final int arg1) {
            return; // do nothing
        }
    }
}
