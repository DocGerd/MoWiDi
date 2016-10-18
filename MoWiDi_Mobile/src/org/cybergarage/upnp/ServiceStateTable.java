/******************************************************************
 *
 *	CyberUPnP for Java
 *
 *	Copyright (C) Satoshi Konno 2002
 *
 *	File: ServiceStateTable.java
 *
 *	Revision:
 *
 *	12/06/02
 *		- first revision.
 *
 ******************************************************************/
package org.cybergarage.upnp;

import java.util.Vector;

public class ServiceStateTable extends Vector<StateVariable> {
    ////////////////////////////////////////////////
    //	Constants
    ////////////////////////////////////////////////

	private static final long serialVersionUID = -3105067481712717201L;
	public final static String ELEM_NAME = "serviceStateTable";

    ////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////
    public ServiceStateTable() {
    }

    ////////////////////////////////////////////////
    //	Methods
    ////////////////////////////////////////////////
    public StateVariable getStateVariable(int n) {
        return get(n);
    }
}
