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

import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractServiceImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPort;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TService;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class ServiceImpl extends AbstractServiceImpl<TService, InterfaceType, Endpoint> implements Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ServiceImpl.class.getName());

	public ServiceImpl(final TService service, final DescriptionImpl desc) {
		super(service, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(this.model.getDocumentation(), this);

		// get the endpoint
		for (final TPort port : this.model.getPort()) {
			final Endpoint e = new org.ow2.easywsdl.wsdl.impl.wsdl11.EndpointImpl(port, this);
			this.endpoints.add(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addEndpoint(final Endpoint endpoint) {
		super.addEndpoint(endpoint);
		this.model.getPort().add((TPort) ((AbstractWSDLElementImpl) endpoint).getModel());
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
		InterfaceType res = null;
		InterfaceType item = null;
		for (final Endpoint ep : this.getEndpoints()) {
			item = ep.getBinding().getInterface();
			if (item != null) {
			    if (res == null) {
			        res = item;
			    } else if (res != item) {
			        throw new WSDLException(
			                "WSDL 1.1: The endpoints of this service do not implement the same interface");
			    }
                // else 'res' is the same instance as 'item', so the current
                // endpoint implements the same interface as the previous
                // ones.
			}
		}
		return res;
	}

	public Endpoint createEndpoint() {
		return new EndpointImpl(new TPort(), this);
	}

	public void setInterface(InterfaceType itf) {
		LOG.warning("Operation setInterface not supported");
	}

	public static TService replaceDOMElementByTService(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		TService res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("service")))) {
				JAXBElement<TService> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, TService.class);

				// change element by jaxb element
				((TDefinitions) ((AbstractWSDLElementImpl) parent).getModel()).getAny().remove(childToReplace);
				((TDefinitions) ((AbstractWSDLElementImpl) parent).getModel()).getAnyTopLevelOptionalElement().add(jaxbElement.getValue());

				// get element
				res = jaxbElement.getValue();
			}
		} catch (JAXBException e) {
			throw new WSDLException(e);
		}
		return res;
	}
}
