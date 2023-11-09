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
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBinding;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperation;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TStyleChoice;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class BindingImpl extends AbstractBindingImpl<TBinding, InterfaceType, BindingOperation> implements org.ow2.easywsdl.wsdl.api.Binding {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObjectFactory soap11BindingFactory = new ObjectFactory();

	public BindingImpl(final TBinding binding, final DescriptionImpl desc) {
		super(binding, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(this.model.getDocumentation(), this);

		// get the interface
		final QName itfName = this.model.getType();
		this.itf = (InterfaceType) this.desc.getInterface(itfName);

		// get the binding operations
		for (final TBindingOperation tbo : this.model.getOperation()) {
			final BindingOperation bo = new org.ow2.easywsdl.wsdl.impl.wsdl11.BindingOperationImpl(tbo, this);
			this.bindingOperations.add(bo);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addBindingOperation(final BindingOperation bindingOperation) {
		super.addBindingOperation(bindingOperation);
		this.model.getOperation().add((TBindingOperation) ((AbstractWSDLElementImpl) bindingOperation).getModel());
	}

	public QName getQName() {
		return new QName(this.desc.getTargetNamespace(), this.model.getName());
	}

	public BindingOperation removeBindingOperation(final String name) {
        throw new UnsupportedOperationException();
	}

	public void setInterface(final InterfaceType interfaceType) {
		this.model.setType(interfaceType.getQName());
		this.itf = interfaceType;
	}

	public void setQName(final QName name) {
		this.model.setName(name.getLocalPart());
	}

	@SuppressWarnings("unchecked")
	public String getTransportProtocol() {
		String protocol = null;
		for (final Object element : this.model.getAny()) {
			if (element instanceof JAXBElement) {
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) {
					protocol = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) ((JAXBElement) element).getValue()).getTransport();
					break;
				}
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) {
					protocol = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) ((JAXBElement) element).getValue()).getTransport();
					break;
				}
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.BindingType) {
					protocol = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.BindingType) ((JAXBElement) element).getValue()).getVerb();
					break;
				}
			}
		}
		return protocol;
	}

	public void setTransportProtocol(String transportProtocol) {
		boolean find = false;
		for (final Object element : this.model.getAny()) {
			if (element instanceof JAXBElement) {
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) {
					((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) ((JAXBElement) element).getValue()).setTransport(transportProtocol);
					find = true;
					break;
				}
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) {
					((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) ((JAXBElement) element).getValue()).setTransport(transportProtocol);
					find = true;
					break;
				}
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.BindingType) {
					((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.BindingType) ((JAXBElement) element).getValue()).setVerb(transportProtocol);
					find = true;
					break;
				}
			}
		}

		if (!find) {
			// create soap 1.1 transport in document mode by default
			org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding binding = new org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding();
			binding.setTransport(transportProtocol);
			binding.setStyle(TStyleChoice.DOCUMENT);

			// add to model
			this.model.getAny().add(this.soap11BindingFactory.createBinding(binding));
		}
	}

	@SuppressWarnings("unchecked")
	public StyleConstant getStyle() {
		StyleConstant style = null;
		for (final Object element : this.model.getAny()) {
			if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) {
				if (((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) ((JAXBElement) element).getValue()).getStyle() != null) {
					style = StyleConstant.valueOf(((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) ((JAXBElement) element).getValue()).getStyle().value().toUpperCase());
				}
				break;
			}
			if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) {
				if (((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) ((JAXBElement) element).getValue()).getStyle() != null) {
					style = StyleConstant.valueOf(((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) ((JAXBElement) element).getValue()).getStyle().value().toUpperCase());
				}
				break;
			}
		}
		return style;
	}

	@SuppressWarnings("unchecked")
	public BindingConstants getTypeOfBinding() {
		BindingConstants res = null;
		for (final Object element : this.model.getAny()) {
			if (element instanceof JAXBElement) {
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBinding) {
					res = BindingConstants.SOAP11_BINDING4WSDL11;
					break;
				}
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBinding) {
					res = BindingConstants.SOAP12_BINDING4WSDL11;
					break;
				}
				if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.BindingType) {
					res = BindingConstants.HTTP11_BINDING4WSDL11;
					break;
				}
			}
		}
		return res;
	}

	public String getVersion() {
		String res = null;
		if (this.getTypeOfBinding().equals(BindingConstants.SOAP11_BINDING4WSDL11)) {
			res = "1.1";
		} else if (this.getTypeOfBinding().equals(BindingConstants.SOAP12_BINDING4WSDL11)) {
			res = "1.2";
		} else if (this.getTypeOfBinding().equals(BindingConstants.HTTP11_BINDING4WSDL11)) {
			res = "1.1";
		}
		return res;
	}

	public String getHttpContentEncodingDefault() {
		return null;
	}

	public String getHttpDefaultMethod() {
		return null;
	}

	public String getHttpQueryParameterSeparatorDefault() {
		return null;
	}

	public boolean isHttpCookies() {
		return false;
	}

	public BindingOperation createBindingOperation() {
		return new BindingOperationImpl(new TBindingOperation(), this);
	}

	public static TBinding replaceDOMElementByTBinding(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		TBinding res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("binding")))) {
				JAXBElement<TBinding> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, TBinding.class);

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
