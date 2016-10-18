/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: PropertyList.java
*
*	Revision;
*
*	09/08/03
*		- first revision.
*
******************************************************************/

package org.cybergarage.upnp.event;

import java.util.*;

public class PropertyList extends Vector<Property>
{
	////////////////////////////////////////////////
	//	Constants
	////////////////////////////////////////////////
	private static final long serialVersionUID = -8883971027098180140L;
	public final static String ELEM_NAME = "PropertyList";

	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public PropertyList() 
	{
	}
	
	////////////////////////////////////////////////
	//	Methods
	////////////////////////////////////////////////
	
	public Property getProperty(int n)
	{
		return get(n);
	}
}

