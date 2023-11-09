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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractServiceImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.EndpointType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ObjectFactory;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ServiceType;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class ServiceImpl extends AbstractServiceImpl<ServiceType, InterfaceType, Endpoint> implements Service {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ObjectFactory factory = new ObjectFactory();

	@SuppressWarnings("unchecked")
	public ServiceImpl(final ServiceType service, final DescriptionImpl desc) {
		super(service, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(this.model.getDocumentation(), this);

		for (final Object element : this.model.getEndpointOrAny()) {
			if (element instanceof JAXBElement) {
				final Object part = ((JAXBElement) element).getValue();

				// get the endpoint
				if (part instanceof EndpointType) {
					final Endpoint e = new org.ow2.easywsdl.wsdl.impl.wsdl20.EndpointImpl((EndpointType) part, this);
					this.endpoints.add(e);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addEndpoint(final Endpoint endpoint) {
		JAXBElement<EndpointType> ep = factory.createEndpoint((EndpointType) ((AbstractWSDLElementImpl) endpoint).getModel());

		super.addEndpoint(endpoint);
		this.model.getEndpointOrAny().add(ep);
	}

	public QName getQName() {
		QName serviceName = null;
		serviceName = new QName(this.desc.getTargetNamespace(), this.model.getName());
		return serviceName;
	}

	public Endpoint removeEndpoint(final String name) {
        throw new UnsupportedOperationException();
	}

	public void setQName(final QName name) {
		this.model.setName(name.getLocalPart());
	}

	/**
     * {@inheritDoc}
     */
    public InterfaceType getInterface() throws WSDLException {
		return (InterfaceType) this.desc.getInterface(this.model.getInterface());
	}

	@Override
	public List<Element> getOtherElements() throws XmlException {
		final List<Element> res = super.getOtherElements();

		for (final Object item : this.model.getEndpointOrAny()) {
			if (item instanceof Element) {
				res.add((Element) item);
			}
		}

		return res;
	}

	public Endpoint createEndpoint() {
		return new EndpointImpl(new EndpointType(), this);
	}

	public void setInterface(InterfaceType itf) {
		this.model.setInterface(itf.getQName());
	}

	public static ServiceType replaceDOMElementByServiceType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		ServiceType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("service")) && (childToReplace.getNamespaceURI().equals(Constants.WSDL_20_NAMESPACE)))) {
				JAXBElement<ServiceType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, ServiceType.class);

				// change element by jaxb element
				((DescriptionType) ((AbstractWSDLElementImpl) parent).getModel()).getImportOrIncludeOrTypes().remove(childToReplace);
				((DescriptionType) ((AbstractWSDLElementImpl) parent).getModel()).getImportOrIncludeOrTypes().add(jaxbElement.getValue());

				// get element
				res = jaxbElement.getValue();
			}
		} catch (JAXBException e) {
			throw new WSDLException(e);
		}
		return res;
	}
}
