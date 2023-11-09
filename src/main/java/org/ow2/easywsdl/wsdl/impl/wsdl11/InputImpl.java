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

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractDescriptionImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractInterfaceTypeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractOperationImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractParamImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TParam;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPart;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class InputImpl extends AbstractParamImpl<TParam> implements
org.ow2.easywsdl.wsdl.api.Input {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TMessage correspondingMessage = null;

	private List<Part> parts = new ArrayList<Part>();

	@SuppressWarnings("unchecked")
	public InputImpl(final TParam param, final OperationImpl operationImpl) {
		super(param, operationImpl);
		this.operation = operationImpl;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(
				this.model.getDocumentation(), this);

		findCorrespondingMessage();
	}

	private void findCorrespondingMessage() {
		// get corresponding messages
		final DescriptionImpl desc = (org.ow2.easywsdl.wsdl.impl.wsdl11.DescriptionImpl) ((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) this.operation)
				.getInterface()).getDescription();
		for (final MessageImpl msg : desc.getMessages()) {
			if ((this.getMessageName() != null)&&(msg.getQName().getLocalPart().equals(this.getMessageName().getLocalPart()))) {
				this.correspondingMessage = (TMessage) ((AbstractWSDLElementImpl)msg).getModel();
			}
		}

		// get parts
		if(this.correspondingMessage != null) {
			this.parts = new ArrayList<Part>();
			for (final TPart part : this.correspondingMessage.getPart()) {
				this.parts.add(new org.ow2.easywsdl.wsdl.impl.wsdl11.PartImpl(part, this));
			}
		}
	}

	public QName getMessageName() {
		return this.model.getMessage();
	}

	public void setMessageName(final QName name) {
		// TODO: Delete unused message

		this.model.setMessage(name);

		// create corresponding message
		final DescriptionImpl desc = (org.ow2.easywsdl.wsdl.impl.wsdl11.DescriptionImpl) ((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) this.operation)
				.getInterface()).getDescription();
		if(this.correspondingMessage == null) {
			this.findCorrespondingMessage();
		}
		if(this.correspondingMessage == null) {
			this.correspondingMessage = new TMessage();
			this.correspondingMessage.setName(name.getLocalPart());
			MessageImpl mess = new MessageImpl(this.correspondingMessage, desc);
			desc.getMessages().add(mess);
			((TDefinitions)desc.getModel()).getAnyTopLevelOptionalElement().add(this.correspondingMessage);
		}
		
	}

	@Override
	public Element getElement() {
		Element res = null;
		if(this.correspondingMessage == null) {
			this.findCorrespondingMessage();
		}
		if (this.parts.size() == 1) {
			res = this.parts.get(0).getElement();
		}
		return res;
	}

	public List<Part> getParts() {
		if(this.correspondingMessage == null) {
			this.findCorrespondingMessage();
		}
		return this.parts;
	}

	public void setElement(final Element element) throws WSDLException {
		if(this.correspondingMessage == null) {
			this.findCorrespondingMessage();
		}
		if(this.correspondingMessage == null) {
			throw new WSDLException("No message is associated to this input");
		}

		this.elementName = element.getQName();
		if(this.correspondingMessage.getPart().isEmpty()) {
			// create part
			TPart part = new TPart();
			part.setName(element.getQName().getLocalPart());
			part.setElement(element.getQName());

			// add part to list of parts
			this.parts.add(new PartImpl(part, this));

			// add part in model
			this.correspondingMessage.getPart().add(part);

		} else {
			// set part
			TPart part = this.correspondingMessage.getPart().get(0);
			part.setName(element.getQName().getLocalPart());
			part.setElement(element.getQName());
		}
	}

	public String getName() {
		return this.model.getName();
	}

	public void setName(final String name) {
		this.model.setName(name);
	}

	public Part getPart(final String name) {
		Part res = null;
		if(this.correspondingMessage == null) {
			this.findCorrespondingMessage();
		}
		if (this.parts != null) {
			for (final Part p : this.parts) {
				if (p.getPartQName().getLocalPart().equals(name)) {
					res = p;
					break;
				}
			}
		}
		return res;
	}

}
