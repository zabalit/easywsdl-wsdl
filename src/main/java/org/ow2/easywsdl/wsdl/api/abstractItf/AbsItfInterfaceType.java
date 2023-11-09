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

/**
 * This interface represents a port type. It contains information about
 * operations associated with this port type.
 * 
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfInterfaceType<O extends AbsItfOperation> extends WSDLElement {
    /**
     * Set the name of this port type.
     * 
     * @param name
     *            the desired name
     */
    void setQName(QName name);

    /**
     * Get the name of this port type.
     * 
     * @return the port type name
     */
    QName getQName();
    
    /**
     * create an operation to this port type.
     * 
     */
    O createOperation();

    /**
     * Add an operation to this port type.
     * 
     * @param operation
     *            the operation to be added
     */
    void addOperation(O operation);

    /**
     * Get the specified operation. Note that operation names can be overloaded
     * within a PortType.
     * 
     * @param name
     *            the name of the desired operation.
     * @return the corresponding operation, or null if there wasn't any matching
     *         operation
     * 
     * @throws IllegalArgumentException
     *             if duplicate operations are found.
     */
    O getOperation(QName name);

    /**
     * Get all the operations defined here.
     */
    List<O> getOperations();

    /**
     * Remove the specified operation. Note that operation names can be
     * overloaded within a PortType.
     * 
     * @param name
     *            the name of the desired operation.
     * 
     * @throws IllegalArgumentException
     *             if duplicate operations are found.
     * 
     * @see #getOperation(QName)
     */
    O removeOperation(String name);

}
