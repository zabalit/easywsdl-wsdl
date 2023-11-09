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

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfParam;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class DecoratorParamImpl<P extends AbsItfParam> extends Decorator<P> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    public DecoratorParamImpl(final AbsItfParam param) throws WSDLException {
        this.internalObject = (P)param;
    }

    public Element getElement() {
        return this.internalObject.getElement();
    }

    public QName getMessageName() {
        return this.internalObject.getMessageName();
    }

    public String getName() {
        return this.internalObject.getName();
    }

    public Part getPart(final String arg0) {
        return this.internalObject.getPart(arg0);
    }

    public List<Part> getParts() {
        return this.internalObject.getParts();
    }

    public void setElement(final Element arg0) throws WSDLException {
        this.internalObject.setElement(arg0);
    }

    public void setMessageName(final QName arg0) {
        this.internalObject.setMessageName(arg0);
    }

    public void setName(final String arg0) throws WSDLException {
        this.internalObject.setName(arg0);
    }
}
