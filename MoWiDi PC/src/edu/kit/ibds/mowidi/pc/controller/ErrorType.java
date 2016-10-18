/*
 * @(#)ErrorType.java
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

/**
 * This Enum represents certain errors which may occur in the programme.
 * It is used by sending it with an Information Object to the GUIs.
 */
public enum ErrorType {

    /** If no Error occurred. */
    NULL("", ""),
    /** If the connection was lost unexpectedly. */
    CONNECTION_LOST("Connection lost", ""),
    /** If the mount-point the user wanted is not available on this system
     *  (i.e the user has no right to mount there).
     */
    MOUNTPOINT_NOT_AVAILABLE("Mount Error", ""),
    /** If there was a Problem with the Certificate. */
    CERTIFICATE_ERROR("Certificate Error", "");
    /** Caption of error message. */
    private String caption;
    /** Text of error message. */
    private String text;

    /**
     * Create an <tt>ErrorType</tt>.
     * @param nCaption caption of error message
     * @param nText text of error message
     */
    private ErrorType(final String nCaption, final String nText) {
        this.caption = nCaption;
        this.text = nText;
    }

    /**
     * Function returning the caption of the ErrorType.
     * @return caption of the ErrorType
     */
    public String getCaption() {
        return caption;
    }

    /**
     *  Function returning the text which contains information about the error.
     *  @return text containing the information about the error
     */
    public String getText() {
        return text;
    }
}
