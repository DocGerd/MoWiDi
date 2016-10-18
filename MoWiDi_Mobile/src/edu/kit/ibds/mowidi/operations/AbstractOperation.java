/*
 * @(#)AbstractOperation.java
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
 *
 */
package edu.kit.ibds.mowidi.operations;

import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import fuse.FuseException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Abstract Superclass for Operations over the Network.
 *
 * This class is a superclass which needs to be implemented on both sides.
 * The Server needs to provide a valid doOperation Function and the Client
 * uses the getReturnValue function to determine the result of the Operation
 *
 * @author Michael Auracher
 */
public abstract class AbstractOperation implements Serializable {

    /** Serial Version UID. */
    private static final long serialVersionUID = 2722580826900753539L;
    /** The Object which is loaded with the information to return. */
    protected Object savedObject;
    protected final UUID transactionNumber = UUID.randomUUID();
    /** Authorisation type of this operation. */
    protected static transient AuthorizationType minAuthorizationType;

    /**
     * Get the minimum required <tt>AuthorizationType</tt>.
     * @return the authorisation type
     */
    public final AuthorizationType getAuthorizationType() {
        return minAuthorizationType;
    }

    /**
     * Set the result to an access denied <tt>FuseException</tt>.
     */
    public final void setAccessError() {
        savedObject = new FuseException("Access denied").initErrno(FuseException.EACCES);
    }

    /**
     * Function to determine the result of an operation.
     * @return Object which is specific to the implementation of the Subclass
     */
    public final Object getReturnValue() {
        return savedObject;
    }

    /**
     * Function which needs to be implemented by every subclass.
     * This function should perform the operation associated with the
     * specific subclass. The derived Object or Value needs to be saved
     * in savedObject so that the client can get a return value.
     */
    public abstract void doOperation();
}
