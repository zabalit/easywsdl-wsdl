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

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfEndpoint;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfService;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class DecoratorEndpointImpl<S extends AbsItfService, B extends AbsItfBinding> 
extends Decorator<AbsItfEndpoint<S, B>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorEndpointImpl(final AbsItfEndpoint<S, B> endpoint) throws WSDLException {
        this.internalObject = endpoint;
    }

    public String getAddress() {
        return this.internalObject.getAddress();
    }

    public B getBinding() {
        return this.internalObject.getBinding();
    }

    public String getHttpAuthenticationRealm() {
        return this.internalObject.getHttpAuthenticationRealm();
    }

    public String getHttpAuthenticationScheme() {
        return this.internalObject.getHttpAuthenticationScheme();
    }

    public String getName() {
        return this.internalObject.getName();
    }

    public void setAddress(final String arg0) {
        this.internalObject.setAddress(arg0);
    }

    public void setBinding(final B arg0) {
        this.internalObject.setBinding(arg0);
    }

    public void setName(final String arg0) {
        this.internalObject.setName(arg0);
    }

	public S getService() {
		return this.internalObject.getService();
	}
}
