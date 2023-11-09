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

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;

/**
 * This interface represents an input message, and contains the name of the input and the message itself.
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract interface AbsItfParam extends WSDLElement {

    /**
     * Set the name of this input.
     * 
     * @param name
     *            the desired name
     * @throws WSDLException
     */
    void setName(String name) throws WSDLException;

    /**
     * Get the name of this input.
     * 
     * @return the input message name
     */
    String getName();

    /**
     * Set the name of this input message.
     * 
     * @param name
     *            the desired name
     */
    void setMessageName(QName name);

    /**
     * Get the name of this input message.
     * 
     * @return the input message name
     */
    QName getMessageName();

    void setElement(Element element) throws WSDLException;

    Element getElement();

    List<Part> getParts();

    Part getPart(String name);

}
