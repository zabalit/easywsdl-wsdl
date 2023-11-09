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
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractInterfaceTypeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.InterfaceOperationType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ObjectFactory;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class InterfaceTypeImpl extends AbstractInterfaceTypeImpl<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.InterfaceType, Operation> implements InterfaceType {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ObjectFactory factory = new ObjectFactory();

	@SuppressWarnings("unchecked")
	public InterfaceTypeImpl(final org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.InterfaceType itf, final DescriptionImpl desc) {
		super(itf, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(this.model.getDocumentation(), this);

		// get the operations
		for (final Object element : this.model.getOperationOrFaultOrAny()) {

			if (element instanceof JAXBElement) {
				final Object part = ((JAXBElement) element).getValue();

				// get binding operation
				if (part instanceof InterfaceOperationType) {
					final Operation op = new org.ow2.easywsdl.wsdl.impl.wsdl20.OperationImpl((InterfaceOperationType) part, this);
					this.operations.add(op);
				}
			}
		}

	}

	public QName getQName() {
		return new QName(this.getDescription().getTargetNamespace(), this.model.getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addOperation(final Operation op) {
		JAXBElement<InterfaceOperationType> jabxOp = factory.createInterfaceTypeOperation((InterfaceOperationType) ((AbstractWSDLElementImpl) op).getModel());
		super.addOperation(op);
		this.model.getOperationOrFaultOrAny().add(jabxOp);
	}

	public Operation removeOperation(final String name) {
        throw new UnsupportedOperationException();
	}

	public void setQName(final QName name) {
		this.model.setName(name.getLocalPart());
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

	public Operation createOperation() {
		return new OperationImpl(new InterfaceOperationType(), this);
	}

	public static InterfaceType replaceDOMElementByInterfaceType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		InterfaceType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("interface")) && (childToReplace.getNamespaceURI().equals(Constants.WSDL_20_NAMESPACE)))) {
				JAXBElement<InterfaceType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, InterfaceType.class);

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
