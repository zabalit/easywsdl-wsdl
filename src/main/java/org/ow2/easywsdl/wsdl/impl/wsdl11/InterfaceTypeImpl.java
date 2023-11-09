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

import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractInterfaceTypeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TOperation;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPortType;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class InterfaceTypeImpl extends AbstractInterfaceTypeImpl<TPortType, Operation> implements org.ow2.easywsdl.wsdl.api.InterfaceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterfaceTypeImpl(final TPortType itf, final DescriptionImpl desc) {
		super(itf, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(this.model.getDocumentation(), this);

		// get the operation
		for (final TOperation top : this.model.getOperation()) {
			final Operation op = new org.ow2.easywsdl.wsdl.impl.wsdl11.OperationImpl(top, this);
			this.operations.add(op);
		}

	}

	public QName getQName() {
		return new QName(this.getDescription().getTargetNamespace(), this.model.getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addOperation(final Operation op) {
		super.addOperation(op);
		this.model.getOperation().add((TOperation) ((AbstractWSDLElementImpl) op).getModel());
	}

	public Operation removeOperation(final String name) {
        throw new UnsupportedOperationException();
	}

	public void setQName(final QName name) {
		this.model.setName(name.getLocalPart());
	}

	public Operation createOperation() {
		return new OperationImpl(new TOperation(), this);
	}

	public static TPortType replaceDOMElementByTPortType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		TPortType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("portType")))) {
				JAXBElement<TPortType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, TPortType.class);

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
