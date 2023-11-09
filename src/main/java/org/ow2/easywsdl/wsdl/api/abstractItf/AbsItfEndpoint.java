/**
 * Copyright (c) 2008-2012 EBM WebSourcing, 2012-2018 Linagora
 * 
 * This program/library is free software: you can redistribute it and/or modify
 * it under the terms of the New BSD License (3-clause license).
 *
 * This program/library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the New BSD License (3-clause license)
 * for more details.
 *
 * You should have received a copy of the New BSD License (3-clause license)
 * along with this program/library; If not, see http://directory.fsf.org/wiki/License:BSD_3Clause/
 * for the New BSD License (3-clause license).
 */
 
package org.ow2.easywsdl.wsdl.api.abstractItf;

import org.ow2.easywsdl.wsdl.api.WSDLElement;

/**
 * This interface represents a port, an endpoint for the functionality described by a particular port type.
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfEndpoint<S extends AbsItfService, B extends AbsItfBinding> extends WSDLElement {
    /**
     * Set the name of this port.
     * 
     * @param name
     *            the desired name
     */
    void setName(String name);

    /**
     * Get the name of this port.
     * 
     * @return the port name
     */
    String getName();

    /**
     * Set the address of this port.
     * 
     * @param address
     *            the desired name
     */
    void setAddress(String address);

    /**
     * Get the address of this port.
     * 
     * @return the port address
     */
    String getAddress();

    /**
     * Set the binding this port should refer to.
     * 
     * @param binding
     *            the desired binding
     */
    void setBinding(B binding);

    /**
     * Get the binding this port refers to.
     * 
     * @return the binding associated with this port
     */
    B getBinding();
    
    S getService();

    /*
     * http binding attribute
     */
    String getHttpAuthenticationScheme();

    String getHttpAuthenticationRealm();
}
