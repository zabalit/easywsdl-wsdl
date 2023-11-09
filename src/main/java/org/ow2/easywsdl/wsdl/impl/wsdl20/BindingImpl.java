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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.BindingOperationType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.BindingType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ObjectFactory;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class BindingImpl extends AbstractBindingImpl<BindingType, InterfaceType, BindingOperation> 
implements org.ow2.easywsdl.wsdl.api.Binding {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(BindingImpl.class.getName());

	private ObjectFactory factory = new ObjectFactory();

	@SuppressWarnings("unchecked")
	public BindingImpl(final BindingType binding, final DescriptionImpl desc) {
		super(binding, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(this.model.getDocumentation(), this);

		// get the interface
		final QName itfName = this.model.getInterface();
		this.itf = (InterfaceType) this.desc.getInterface(itfName);

		// get the binding operation
		for (final Object element : this.model.getOperationOrFaultOrAny()) {

			if (element instanceof JAXBElement) {
				final Object part = ((JAXBElement) element).getValue();

				// get binding operation
				if (part instanceof BindingOperationType) {
					final BindingOperation bo = new org.ow2.easywsdl.wsdl.impl.wsdl20.BindingOperationImpl((BindingOperationType) part, this);
					this.bindingOperations.add(bo);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addBindingOperation(final BindingOperation bindingOperation) {
		JAXBElement<BindingOperationType> bo = factory.createBindingTypeOperation((BindingOperationType) ((AbstractWSDLElementImpl) bindingOperation).getModel());
		super.addBindingOperation(bindingOperation);
		this.model.getOperationOrFaultOrAny().add(bo);
	}

	public QName getQName() {
		return new QName(this.desc.getTargetNamespace(), this.model.getName());
	}

	public BindingOperation removeBindingOperation(final String name) {
        throw new UnsupportedOperationException();
	}

	public void setInterface(final InterfaceType interfaceType) {
		this.model.setInterface(interfaceType.getQName());
		this.itf = interfaceType;
	}

	public void setQName(final QName name) {
		this.model.setName(name.getLocalPart());
	}

	public String getTransportProtocol() {
		String protocol = null;
		for (final Entry<QName, String> attribute : this.model.getOtherAttributes().entrySet()) {
			if ((attribute.getKey().getLocalPart().equals(Constants.SOAP_PROTOCOL)) && (attribute.getKey().getNamespaceURI().equals(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20.value().toString()))) {
				protocol = attribute.getValue();
				break;
			} else if (((attribute.getKey().getLocalPart().equals(Constants.HTTP_METHOD)) || (attribute.getKey().getLocalPart().equals(Constants.HTTP_METHOD_DEFAULT))) && ((attribute.getKey().getNamespaceURI().equals(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString())))) {
				protocol = attribute.getValue();
				break;
			}
		}
		return protocol;
	}

	public void setTransportProtocol(String transportProtocol) {
		if (transportProtocol.contains("soap")) {
			this.model.setType(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20.value().toString());
			this.model.getOtherAttributes().put(new QName(this.model.getType(), Constants.SOAP_PROTOCOL), transportProtocol);
		} else if (transportProtocol.contains("http")) {
			this.model.setType(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString());
			this.model.getOtherAttributes().put(new QName(this.model.getType(), Constants.HTTP_METHOD_DEFAULT), transportProtocol);
		} else {
			this.LOG.severe("unrecognized transport protocol");
		}
	}

	public StyleConstant getStyle() {
		return StyleConstant.DOCUMENT;
	}

	public BindingConstants getTypeOfBinding() {
		BindingConstants res = null;
		try {
			if (this.model.getType() != null) {
				res = BindingConstants.valueOf(new URI(this.model.getType()));
			}
		} catch (final URISyntaxException e) {
			BindingImpl.LOG.warning("The binding type is unknown");
		}
		return res;
	}

	public String getVersion() {
		String res = null;
		res = this.model.getOtherAttributes().get(new QName(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString(), "version"));
		if (res == null) {
			res = this.model.getOtherAttributes().get(new QName(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20.value().toString(), "version"));
		}
		return res;
	}

	public String getHttpContentEncodingDefault() {
		return this.model.getOtherAttributes().get(new QName(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString(), "contentEncodingDefault"));
	}

	public String getHttpDefaultMethod() {
		return this.model.getOtherAttributes().get(new QName(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString(), "methodDefault"));
	}

	public String getHttpQueryParameterSeparatorDefault() {
		return this.model.getOtherAttributes().get(new QName(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString(), "queryParameterSeparatorDefault"));
	}

	public boolean isHttpCookies() {
		return Boolean.valueOf(this.model.getOtherAttributes().get(new QName(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20.value().toString(), "cookies")));
	}

	@Override
	public List<Element> getOtherElements() throws XmlException {
		final List<Element> res = super.getOtherElements();

		for (final Object item : this.model.getOperationOrFaultOrAny()) {
			if (item instanceof Element) {
				res.add((Element) item);
			}
		}

		return res;
	}

	public BindingOperation createBindingOperation() {
		return new BindingOperationImpl(new BindingOperationType(), this);
	}

	public static BindingType replaceDOMElementByBindingType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		BindingType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("binding")) && (childToReplace.getNamespaceURI().equals(Constants.WSDL_20_NAMESPACE)))) {
				JAXBElement<BindingType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, BindingType.class);

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
