/*
 * @(#)Notifier.java
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

import java.util.LinkedList;
import java.util.List;

/**
 * This class is used inside the Controller to perform an update of the UI.
 * One can generate new Messages using Methods of this class.
 * @author Michael Auracher
 */
public final class Notifier {

    /** list with observers. */
    private final List<ObservingView> list;

    /**
     * Constructor to generate a new Notifier object which can be used to send
     * updated Information to the GUIs.
     */
    public Notifier() {
        list = new LinkedList<ObservingView>();
    }

    /**
     * Function which registers a new ObservingView in the Notifier so when
     * the function notifyObservers is called the registered ObservingView
     * gets an Update as well.
     * @param o The ObservingView which shall be registered
     */
    public void addObservingView(final ObservingView o) {
        if (!list.contains(o)) {
            list.add(o);
        }
    }

    /**
     * Function which informs every registered ObserverView using the Information
     * Object passed to this function.
     * @param i Object containing all the necessary Information
     */
    public void notifyObservers(final Information i) {
        for (ObservingView observer : list) {
            observer.update(i);
        }
    }

    /**
     * Function which returns the amount of registered View elements.
     * @return Value of registered View elements.
     */
    public int getRegisteredCount() {
        return list.size();
    }
}
