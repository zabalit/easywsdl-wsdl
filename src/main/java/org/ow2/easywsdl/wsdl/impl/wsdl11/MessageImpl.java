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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPart;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class MessageImpl extends AbstractWSDLElementImpl<TMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Description description;

	private List<Part> parts = new ArrayList<Part>();

	public MessageImpl(TMessage msg, Description parent) {
		super(msg, (AbstractWSDLElementImpl) parent);
		this.description = parent;

		if(this.model.getPart() != null) {
			for (TPart part : this.model.getPart()) {
				parts.add(new PartImpl(part, this));
			}
		}
	}

	public List<Part> getParts() {
		return this.parts;
	}

	public Part getPart(QName partName) {
		Part res = null;
		for (Part part : this.parts) {
			if ((part.getPartQName().getLocalPart().equals(partName.getLocalPart())) && (part.getPartQName().getNamespaceURI().equals(partName.getNamespaceURI()))) {
				res = part;
				break;
			}
		}
		return res;
	}

	public QName getQName() {
		return new QName(this.description.getTargetNamespace(), this.model.getName());
	}

	public Description getDescription() {
		return description;
	}

	public static TMessage replaceDOMElementByTMessage(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		TMessage res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("message")))) {
				JAXBElement<TMessage> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, TMessage.class);

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
