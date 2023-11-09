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

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractEndpointImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractServiceImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPort;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class EndpointImpl extends AbstractEndpointImpl<TPort, Service, Binding> implements
        org.ow2.easywsdl.wsdl.api.Endpoint {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;



    private ObjectFactory soapFactory = new ObjectFactory();

    public EndpointImpl(final TPort endpoint, final Service service) {
        super(endpoint, (AbstractWSDLElementImpl)service);
        this.service = service;

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(
                this.model.getDocumentation(), this);

    }

    @SuppressWarnings("unchecked")
    public Binding getBinding() {
        final QName bindingName = this.model.getBinding();
        return (Binding) ((AbstractServiceImpl) this.service).getDescription().getBinding(
                bindingName);
    }

    public String getName() {
        return this.model.getName();
    }

    @SuppressWarnings("unchecked")
    public String getAddress() {
        String location = null;
        for (final Object element : this.model.getAny()) {
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TAddress) {
                location = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TAddress) ((JAXBElement) element)
                        .getValue()).getLocation();
                break;
            }
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TAddress) {
                location = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TAddress) ((JAXBElement) element)
                        .getValue()).getLocation();
                break;
            }
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.AddressType) {
                location = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.AddressType) ((JAXBElement) element)
                        .getValue()).getLocation();
                break;
            }
        }
        return location;
    }

    public void setBinding(final Binding binding) {
        this.model.setBinding(binding.getQName());
    }

    public void setName(final String name) {
        this.model.setName(name);
    }

    @SuppressWarnings("unchecked")
    public void setAddress(final String address) {
    	boolean find = false;
        for (final Object element : this.model.getAny()) {
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TAddress) {
                ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TAddress) ((JAXBElement) element)
                        .getValue()).setLocation(address);
                find = true;
                break;
            }
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TAddress) {
                ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TAddress) ((JAXBElement) element)
                        .getValue()).setLocation(address);
                find = true;
                break;
            }
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.AddressType) {
                ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.AddressType) ((JAXBElement) element)
                        .getValue()).setLocation(address);
                find = true;
                break;
            }
        }

        if(!find) {
        	// create default address
        	org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TAddress soapAddress = new org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TAddress();
        	soapAddress.setLocation(address);
        	this.model.getAny().add(soapFactory.createAddress(soapAddress));
        }
    }

    public String getHttpAuthenticationRealm() {
        return null;
    }

    public String getHttpAuthenticationScheme() {
        return null;
    }

}
