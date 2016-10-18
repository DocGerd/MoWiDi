/*
 * @(#)TrayAppTest.java
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

import edu.kit.ibds.mowidi.pc.controller.Information;
import edu.kit.ibds.mowidi.pc.controller.MainController;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import java.util.UUID;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrick Kuhn
 */
public class TrayAppTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--TrayApp");
    }

    /**
     * Test of update method, of class TrayApp.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        final UUID uid1 = UUID.randomUUID();
        final MobileDevice[] mds = new MobileDevice[]{
            new MobileDevice(uid1),
            new MobileDevice(uid1),
            new MobileDevice(uid1),
            new MobileDevice(UUID.randomUUID()),
            new MobileDevice(UUID.randomUUID())
        };
        mds[1].setAvailable(true);
        mds[1].setConnected(true);
        mds[2].setAvailable(false);
        mds[3].setAvailable(true);
        final Information i = new Information(mds);
        final TrayApp instance = new TrayApp(new MainController());
        instance.update(i);
    }
}
