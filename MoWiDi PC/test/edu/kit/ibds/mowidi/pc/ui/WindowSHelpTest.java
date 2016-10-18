/*
 * @(#)WindowSHelpTest.java
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
 * 2010-08-13   completed PK
 */
package edu.kit.ibds.mowidi.pc.ui;

import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrick Kuhn
 */
public class WindowSHelpTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--WindowSHelp");
    }

    @Test
    public void testSomeMethod() {
        System.out.println("WindowSHelp");
        new WindowSHelp(ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH));
    }
}
