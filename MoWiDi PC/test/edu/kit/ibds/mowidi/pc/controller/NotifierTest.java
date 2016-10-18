/*
 * @(#)NotifierTest.java
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

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Kuhn
 */
public class NotifierTest {

    @BeforeClass
    public static void before() {
        System.out.println("---------------");
        System.out.println("--Notifier");
    }

    private static final class ObservingViewImpl implements ObservingView {

        @Override
        public void update(final Information i) {
            assert true;
        }
    }

    /**
     * Test of addObservingView method, of class Notifier.
     */
    @Test
    public void testAddObservingView() {
        System.out.println("addObservingView");
        final int expResult = 1;
        final ObservingView o = new ObservingViewImpl();
        final Notifier instance = new Notifier();
        instance.addObservingView(o);
        assertEquals(expResult, instance.getRegisteredCount());
    }

    /**
     * Test of notifyObservers method, of class Notifier.
     */
    @Test
    public void testNotifyObservers() {
        System.out.println("notifyObservers");
        final Information i = null;
        final Notifier instance = new Notifier();
        final ObservingView o = new ObservingViewImpl();
        instance.addObservingView(o);
        instance.notifyObservers(i);
    }

    /**
     * Test of getRegisteredCount method, of class Notifier.
     */
    @Test
    public void testGetRegisteredCount() {
        System.out.println("getRegisteredCount");
        final Notifier instance = new Notifier();
        final int expResult = 0;
        final int result = instance.getRegisteredCount();
        assertEquals(expResult, result);
    }
}
