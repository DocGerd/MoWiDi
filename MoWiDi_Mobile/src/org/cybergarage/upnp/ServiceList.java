/******************************************************************
 *
 *	CyberUPnP for Java
 *
 *	Copyright (C) Satoshi Konno 2002
 *
 *	File: ServiceList.java
 *
 *	Revision;
 *
 *	12/04/02
 *		- first revision.
 *	06/18/03
 *		- Added caching a ArrayIndexOfBound exception.
 *
 ******************************************************************/
package org.cybergarage.upnp;

import java.util.Vector;

public class ServiceList extends Vector<Service> {
    ////////////////////////////////////////////////
    //	Constants
    ////////////////////////////////////////////////

	private static final long serialVersionUID = -1110546520797823529L;
	public final static String ELEM_NAME = "serviceList";

    ////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////
    public ServiceList() {
    }

    ////////////////////////////////////////////////
    //	Methods
    ////////////////////////////////////////////////
    public Service getService(int n) {
        Service obj = null;
        try {
            obj = get(n);
        } catch (Exception e) {
        }
        return obj;
    }
}
