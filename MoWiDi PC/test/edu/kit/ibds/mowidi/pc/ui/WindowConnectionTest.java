/*
 * @(#)WindowConnectionTest.java
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

import edu.kit.ibds.mowidi.pc.controller.IController;
import edu.kit.ibds.mowidi.pc.controller.Information;
import edu.kit.ibds.mowidi.pc.controller.MainController;
import edu.kit.ibds.mowidi.pc.data.Settings;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrick Kuhn
 */
public class WindowConnectionTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--WindowConnection");
    }

    /**
     * Test of update method, of class WindowConnection.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        final Settings s = new Settings();
        final Information i = new Information(s);
        final IController c = new MainController();
        final WindowSettings ws = new WindowSettings(c);
        final WindowConnection instance = new WindowConnection(ws);
        instance.update(i);
    }
}
