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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingParamImpl;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11BindingImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperationMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class BindingOutputImpl extends AbstractBindingParamImpl<TBindingOperationMessage> implements
        org.ow2.easywsdl.wsdl.api.BindingOutput {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(BindingOutputImpl.class.getName());


    @SuppressWarnings("unchecked")
    public BindingOutputImpl(final TBindingOperationMessage param,
            final BindingOperationImpl bindingOperationImpl) {
        super(param, bindingOperationImpl);
        this.bindingOperation = bindingOperationImpl;

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(
                this.model.getDocumentation(), this);

        // get the binding protocol
        this.bindingProtocol = AbstractBindingParamImpl.extractBindingProtocol((List) this.model
                .getAny(), this);
    }

    public String getName() {
        return this.model.getName();
    }

    public void setName(final String name) {
        this.model.setName(name);
    }

    public String getHttpContentEncoding() {
        return null;
    }

	public void setSOAP11Binding4Wsdl11(SOAP11Binding4Wsdl11 soap11binding) {
		this.bindingProtocol = soap11binding;
		AbstractBindingParamImpl.setBindingProtocol(soap11binding, (List) this.model
                .getAny(), this);
	}

	public void setSOAP12Binding4Wsdl20(SOAPBinding4Wsdl20 soap12binding) {
		LOG.warning("this binding does not exist in wsdl 1.1");
	}

	public SOAP11Binding4Wsdl11 createSOAP11Binding4Wsdl11() {
		return new SOAP11BindingImpl(new ArrayList<THeader>(), new TBody(), null, this);
	}

	public SOAPBinding4Wsdl20 createSOAP12Binding4Wsdl20() {
		LOG.warning("this binding does not exist in wsdl 1.1");
		return null;
	}
}
