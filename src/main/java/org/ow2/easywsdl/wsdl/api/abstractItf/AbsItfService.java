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

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;

/**
 * This interface represents a service, which groups related endpoints to provide some functionality.
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfService<I extends AbsItfInterfaceType, E extends AbsItfEndpoint> extends
        WSDLElement {
    /**
     * Set the name of this service.
     * 
     * @param name
     *            the desired name
     */
    void setQName(QName name);

    /**
     * Get the name of this service.
     * 
     * @return the service name
     */
    QName getQName();
    
    /**
     * create the specified endpoint.
	 *
     * @return the corresponding endpoint, or null if there wasn't any matching
     *         endpoint
     */
    E createEndpoint();

    /**
     * Add a endpoint to this service.
     * 
     * @param endpoint
     *            the endpoint to be added
     */
    void addEndpoint(E endpoint);

    /**
     * Get the specified endpoint.
     * 
     * @param name
     *            the name of the desired endpoint.
     * @return the corresponding endpoint, or null if there wasn't any matching
     *         endpoint
     */
    E getEndpoint(String name);

    /**
     * Remove the specified endpoint.
     * 
     * @param name
     *            the name of the endpoint to be removed.
     * @return the endpoint which was removed.
     */
    E removeEndpoint(String name);

    /**
     * Get all the endpoints defined here.
     */
    List<E> getEndpoints();

    /**
     * <p>
     * Get interface.
     * </p>
     * <p>
     * In case of WSDL 2.0, the returned interface is defined by the XPath
     * expression '/description/service@interface'.
     * </p>
     * <p>
     * In case of WSDL 1.1, according to <a
     * href="http://www.w3.org/TR/wsdl#_services">WSDL 1.1 specifications</a>,
     * if all service endpoints share the same port type, the returned interface
     * is defined by the endpoints port type, otherwise an exception
     * {@link WSDLException} is thrown.
     * </p>
     */
    I getInterface() throws WSDLException;
    
    
    void setInterface(I itf);
}
