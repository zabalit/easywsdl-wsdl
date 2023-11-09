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

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfEndpoint;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfService;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractServiceImpl<E, I extends AbsItfInterfaceType<? extends AbsItfOperation>, Ep extends AbsItfEndpoint>
		extends AbstractWSDLElementImpl<E> implements AbsItfService<I, Ep> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the desc
	 */
	protected AbsItfDescription desc;

	/**
	 * list of endpoints
	 */
	protected List<Ep> endpoints = new ArrayList<Ep>();

	public AbstractServiceImpl(E model, AbstractWSDLElementImpl parent) {
		super(model, parent);
	}

	public void addEndpoint(final Ep endpoint) {
		this.endpoints.add(endpoint);
	}

	public Ep getEndpoint(final String name) {
		Ep res = null;
		for (final Ep ep : this.endpoints) {
			if (ep.getName() != null && ep.getName().equals(name)) {
				res = ep;
				break;
			}
		}
		return res;
	}

	public List<Ep> getEndpoints() {
		return this.endpoints;
	}

	/**
	 * @return the desc
	 */
	public AbsItfDescription getDescription() {
		return this.desc;
	}

}
