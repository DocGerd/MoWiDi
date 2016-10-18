/*
 * @(#)PhoneTableTest.java
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

import java.util.ResourceBundle;
import java.util.UUID;
import edu.kit.ibds.mowidi.pc.data.MobileDevice;
import java.util.Locale;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class PhoneTableTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--PhoneTable");
    }

    /**
     * Test of add method, of class PhoneTable.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        final MobileDevice mobile = new MobileDevice(UUID.randomUUID());
        final PhoneTable instance = new PhoneTable(ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH));
        instance.add(mobile);
    }

    /**
     * Test of remove method, of class PhoneTable.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        final MobileDevice mobile = new MobileDevice(UUID.randomUUID());
        final PhoneTable instance = new PhoneTable(ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH));
        instance.add(mobile);
        instance.remove(mobile);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isCellEditable method, of class PhoneTable.
     */
    @Test
    public void testIsCellEditable() {
        System.out.println("isCellEditable");
        final int row = 0;
        final int column = 0;
        final PhoneTable instance = new PhoneTable(ResourceBundle.getBundle("edu/kit/ibds/mowidi/pc/ui/res", Locale.ENGLISH));
        final boolean expResult = false;
        final boolean result = instance.isCellEditable(row, column);
        assertEquals(expResult, result);
    }
}
