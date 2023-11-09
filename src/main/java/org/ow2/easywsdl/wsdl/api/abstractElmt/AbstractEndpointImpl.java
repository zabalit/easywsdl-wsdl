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
 
package org.ow2.easywsdl.wsdl.api.abstractElmt;

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfEndpoint;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfService;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractEndpointImpl<E, S extends AbsItfService, B extends AbsItfBinding> extends
        AbstractWSDLElementImpl<E> implements AbsItfEndpoint<S, B> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * the parent serviceImpl
     */
    protected S service;
    
    public AbstractEndpointImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }

	public S getService() {
		return this.service;
	}
}
