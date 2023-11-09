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
 
package org.ow2.easywsdl.wsdl.impl.wsdl20;

import java.util.List;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingParamImpl;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.BindingOperationMessageType;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class BindingInputImpl extends AbstractBindingParamImpl<BindingOperationMessageType>
        implements org.ow2.easywsdl.wsdl.api.BindingInput {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(BindingInputImpl.class.getName());


    @SuppressWarnings("unchecked")
    public BindingInputImpl(final BindingOperationMessageType param,
            final BindingOperationImpl bindingOperationImpl) {
        super(param, bindingOperationImpl);
        this.bindingOperation = bindingOperationImpl;

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(
                this.model.getDocumentation(), this);

        // get the binding protocol
        this.bindingProtocol = AbstractBindingParamImpl.extractBindingProtocol((List) this.model
                .getAny(), this);
    }

    public String getName() {
        return this.model.getMessageLabel();
    }

    public void setName(final String name) {
        this.model.setMessageLabel(name);
    }

    public String getHttpContentEncoding() {
        return this.model
                .getOtherAttributes()
                .get(
                        new QName(
                                org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
                                        .value().toString(), "contentEncoding"));
    }

    @Override
    public List<Element> getOtherElements() throws XmlException {
        final List<Element> res = super.getOtherElements();

        for (final Object item : this.model.getAny()) {
            if (item instanceof Element) {
                res.add((Element) item);
            }
        }

        return res;
    }

	public void setSOAP11Binding4Wsdl11(SOAP11Binding4Wsdl11 soap11binding) {
		LOG.warning("This binding does not exist in wsdl 2.0");
	}

	public void setSOAP12Binding4Wsdl20(SOAPBinding4Wsdl20 soap12binding) {
        throw new UnsupportedOperationException();
	}

	public SOAP11Binding4Wsdl11 createSOAP11Binding4Wsdl11() {
		LOG.warning("This binding does not exist in wsdl 2.0");
		return null;
	}

	public SOAPBinding4Wsdl20 createSOAP12Binding4Wsdl20() {
        throw new UnsupportedOperationException();
	}
}
