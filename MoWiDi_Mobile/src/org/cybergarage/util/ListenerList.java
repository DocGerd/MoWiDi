/******************************************************************
 *
 *	CyberUtil for Java
 *
 *	Copyright (C) Satoshi Konno 2002
 *
 *	File: ListenerList.java
 *
 *	Revision;
 *
 *	12/30/02
 *		- first revision.
 *
 ******************************************************************/
package org.cybergarage.util;

import java.util.Vector;

public class ListenerList extends Vector<Object> {

	private static final long serialVersionUID = -8590599174566365989L;

	@Override
    public boolean add(Object obj) {
        if (0 <= indexOf(obj)) {
            return false;
        }
        return super.add(obj);
    }
}
