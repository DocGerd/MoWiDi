/******************************************************************
 *
 *	CyberUPnP for Java
 *
 *	Copyright (C) Satoshi Konno 2002
 *
 *	File: SubscriberList.java
 *
 *	Revision;
 *
 *	01/31/03
 *		- first revision.
 *	06/18/03
 *		- Fixed to catch ArrayIndexOutOfBounds.
 *
 ******************************************************************/
package org.cybergarage.upnp.event;

import java.util.*;

public class SubscriberList extends Vector<Subscriber> {
    ////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////

	private static final long serialVersionUID = 6913886766145134842L;

	public SubscriberList() {
    }

    ////////////////////////////////////////////////
    //	Methods
    ////////////////////////////////////////////////
    public Subscriber getSubscriber(int n) {
        Subscriber obj = null;
        try {
            obj = get(n);
        } catch (Exception e) {
        }
        return obj;
    }
}
