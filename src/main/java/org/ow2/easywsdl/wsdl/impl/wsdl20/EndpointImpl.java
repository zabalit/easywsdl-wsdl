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

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractEndpointImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.EndpointType;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class EndpointImpl extends AbstractEndpointImpl<EndpointType, Service, Binding> implements
        org.ow2.easywsdl.wsdl.api.Endpoint {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;



    public EndpointImpl(final EndpointType endpoint, final Service service) {
        super(endpoint,  (AbstractWSDLElementImpl)service);
        this.service = service;

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(
                this.model.getDocumentation(), this);
    }

    public Binding getBinding() {
        final QName bindingName = this.model.getBinding();
        return (Binding) ((ServiceImpl)this.service).getDescription().getBinding(bindingName);
    }

    public String getName() {
        return this.model.getName();
    }

    public String getAddress() {
        return this.model.getAddress();
    }

    public void setBinding(final Binding binding) {
        this.model.setBinding(binding.getQName());
    }

    public void setName(final String name) {
        this.model.setName(name);
    }

    public void setAddress(final String address) {
        this.model.setAddress(address);
    }

    public String getHttpAuthenticationRealm() {
        return this.model
                .getOtherAttributes()
                .get(
                        new QName(
                                org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
                                        .value().toString(), "authenticationScheme"));
    }

    public String getHttpAuthenticationScheme() {
        return this.model
                .getOtherAttributes()
                .get(
                        new QName(
                                org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
                                        .value().toString(), "authenticationRealm"));
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


}
