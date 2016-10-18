/*
 * @(#)Language.java
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
 * 2010-06-10   assumed completed PK
 */
package edu.kit.ibds.mowidi.shared.data;

/**
 * An enum which represents available languages for internationalisation.
 * @author Patrick Kuhn
 */
public enum Language {

    /** English language. */
    ENGLISH("English", "en"),
    /** German language. */
    GERMAN("Deutsch", "de"),
    /** Spanish language. */
    SPANISH("Espanol", "es"),
    /** Italian language. */
    ITALIAN("Italiano", "it"),
    /** French language. */
    FRENCH("Francais", "fr"),
    /** Choose language automatically. */
    AUTO("Automatic", "AUTO");
    /** String representation of the language. */
    private final String caption;
    /** The local code of language, e.g. "de". */
    private final String localCode;

    /**
     * Create a language type.
     * @param s name of language
     * @param code the language code
     */
    private Language(final String s, final String code) {
        this.caption = s;
        this.localCode = code;
    }

    /**
     * Get the language code.
     * @return the language code, e.g. "en", "de" etc.
     */
    public String getLanguageCode() {
        return localCode;
    }

    @Override
    public String toString() {
        return caption;
    }
}
