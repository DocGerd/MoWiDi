/*
 * @(#)Settings.java
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
 * 2010-06-18   finished PK
 * 2010-06-28   added setter for language CS
 * 2010-06-29   name changed to AbstractSettings PK
 * 2010-07-04   UUID added PK
 */
package edu.kit.ibds.mowidi.shared.data;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

/**
 * Settings which can be set both in PC or mobile.
 * @author Patrick Kuhn
 */
public abstract class AbstractSettings implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 2433832014938839988L;
    /** Name of this device. */
    protected String givenName = "";
    /** The language. */
    private Language language = null;
    /** The UID. */
    private final UUID uUID;

    /**
     * Create a new settings object.
     */
    public AbstractSettings() {
        final Locale l = Locale.getDefault();
        // auto set to PC respectively mobile language
        // if language not available switch to english
        for (Language lang : Language.values()) {
            if (l.getLanguage().equals(lang.getLanguageCode())) {
                language = lang;
                break;
            }
        }
        if (language == null) {
            language = Language.ENGLISH;
        }

        uUID = UUID.randomUUID();
    }

    /**
     * Get the UID of this device.
     * @return UID
     */
    public final UUID getUID() {
        return uUID;
    }

    /**
     * Returns the current language.
     * @return Language Object
     */
    public final Language getLanguage() {
        return language;
    }

    /**
     * Sets a new Language.
     * @param language the language, must not be <tt>null</tt>
     */
    public final void setLanguage(final Language language) {
        if (language == null) {
            throw new IllegalArgumentException();
        }
        this.language = language;
    }

    /**
     * Get given name of this device.
     * @return the given name
     */
    public final String getGivenName() {
        String result;
        if (givenName == null || givenName.equals("")) {
            result = uUID.toString();
        } else {
            result = givenName;
        }
        return result;
    }

    /**
     * Set given name of this device.
     * @param nName new name, if <tt>null</tt>, name will be set to ""
     */
    public final void setGivenName(final String nName) {
        if (nName == null) {
            this.givenName = "";
        } else {
            this.givenName = nName.trim();
        }
    }
}
