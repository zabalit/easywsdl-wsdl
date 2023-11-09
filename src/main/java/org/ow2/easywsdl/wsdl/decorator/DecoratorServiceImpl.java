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

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfEndpoint;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfService;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class DecoratorServiceImpl<I extends AbsItfInterfaceType, E extends AbsItfEndpoint>  extends Decorator<AbsItfService<I, E>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorServiceImpl(final AbsItfService<I, E> service) throws WSDLException {
        this.internalObject = service;
    }

    public void addEndpoint(final E arg0) {
        this.internalObject.addEndpoint(arg0);
    }

    public E getEndpoint(final String arg0) {
        return this.internalObject.getEndpoint(arg0);
    }

    public List<E> getEndpoints() {
        return this.internalObject.getEndpoints();
    }

    public I getInterface() throws WSDLException {
        return this.internalObject.getInterface();
    }

    public QName getQName() {
        return this.internalObject.getQName();
    }

    public E removeEndpoint(final String arg0) {
        return this.internalObject.removeEndpoint(arg0);
    }

    public void setQName(final QName arg0) {
        this.internalObject.setQName(arg0);
    }

	public E createEndpoint() {
		return this.internalObject.createEndpoint();
	}

	public void setInterface(I itf) {
		this.internalObject.setInterface(itf);
	}

}
