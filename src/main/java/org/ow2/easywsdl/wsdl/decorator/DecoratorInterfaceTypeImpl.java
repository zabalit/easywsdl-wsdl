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
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class DecoratorInterfaceTypeImpl<O extends AbsItfOperation>  extends Decorator<AbsItfInterfaceType<O>>  {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorInterfaceTypeImpl(final AbsItfInterfaceType<O> itf) throws WSDLException {
        this.internalObject = itf;
    }

    public void addOperation(final O arg0) {
        this.internalObject.addOperation(arg0);
    }

    public O getOperation(final QName arg0) {
        return this.internalObject.getOperation(arg0);
    }

    public List<O> getOperations() {
        return this.internalObject.getOperations();
    }

    public QName getQName() {
        return this.internalObject.getQName();
    }

    public O removeOperation(final String arg0) {
        return this.internalObject.removeOperation(arg0);
    }

    public void setQName(final QName arg0) {
        this.internalObject.setQName(arg0);
    }

	public O createOperation() {
		return this.internalObject.createOperation();
	}
}
