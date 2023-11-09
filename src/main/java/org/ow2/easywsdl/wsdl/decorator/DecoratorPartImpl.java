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
 
package org.ow2.easywsdl.wsdl.decorator;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLException;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class DecoratorPartImpl  extends Decorator<Part> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorPartImpl(final Part part) throws WSDLException {
        this.internalObject = part;
    }

    public Element getElement() {
        return this.internalObject.getElement();
    }

    public QName getPartQName() {
        return this.internalObject.getPartQName();
    }

    public Type getType() {
        return this.internalObject.getType();
    }

    public void setElement(final Element arg0) {
        this.internalObject.setElement(arg0);
    }

    public void setPartQName(final QName arg0) {
        this.internalObject.setPartQName(arg0);
    }

    public void setType(final Type arg0) {
        this.internalObject.setType(arg0);
    }
}
