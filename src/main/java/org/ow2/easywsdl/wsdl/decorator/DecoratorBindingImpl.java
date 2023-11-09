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
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding.BindingConstants;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding.StyleConstant;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class DecoratorBindingImpl<I extends AbsItfInterfaceType, BO extends AbsItfBindingOperation> 
extends Decorator<AbsItfBinding<I, BO>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorBindingImpl(final AbsItfBinding<I, BO> binding) throws WSDLException {
        this.internalObject = binding;
    }

    public void addBindingOperation(final BO arg0) {
        this.internalObject.addBindingOperation(arg0);
    }

    public BO getBindingOperation(final String arg0) {
        return this.internalObject.getBindingOperation(arg0);
    }

    public List<BO> getBindingOperations() {
        return this.internalObject.getBindingOperations();
    }

    public String getHttpContentEncodingDefault() {
        return this.internalObject.getHttpContentEncodingDefault();
    }

    public String getHttpDefaultMethod() {
        return this.internalObject.getHttpDefaultMethod();
    }

    public String getHttpQueryParameterSeparatorDefault() {
        return this.internalObject.getHttpQueryParameterSeparatorDefault();
    }

    public I getInterface() {
        return this.internalObject.getInterface();
    }

    public QName getQName() {
        return this.internalObject.getQName();
    }

    public StyleConstant getStyle() {
        return this.internalObject.getStyle();
    }

    public String getTransportProtocol() {
        return this.internalObject.getTransportProtocol();
    }

    public BindingConstants getTypeOfBinding() {
        return this.internalObject.getTypeOfBinding();
    }

    public String getVersion() {
        return this.internalObject.getVersion();
    }

    public boolean isHttpCookies() {
        return this.internalObject.isHttpCookies();
    }

    public BO removeBindingOperation(final String arg0) {
        return this.internalObject.removeBindingOperation(arg0);
    }

    public void setInterface(final I arg0) {
        this.internalObject.setInterface(arg0);
    }

    public void setQName(final QName arg0) {
        this.internalObject.setQName(arg0);
    }

    public void setTransportProtocol(String transportProtocol) {
        this.internalObject.setTransportProtocol(null);
    }

	public BO createBindingOperation() {
		return this.internalObject.createBindingOperation();
	}
}
