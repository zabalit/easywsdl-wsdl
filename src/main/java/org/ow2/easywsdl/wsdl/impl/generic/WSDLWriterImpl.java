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
 
package org.ow2.easywsdl.wsdl.impl.generic;

import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.w3c.dom.Document;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLWriterImpl implements org.ow2.easywsdl.wsdl.api.WSDLWriter {

	
	private final org.ow2.easywsdl.wsdl.impl.wsdl11.WSDLWriterImpl wsdl11writer;
	
	private final org.ow2.easywsdl.wsdl.impl.wsdl20.WSDLWriterImpl wsdl20writer;
	

	public WSDLWriterImpl() throws WSDLException {
		wsdl11writer = new org.ow2.easywsdl.wsdl.impl.wsdl11.WSDLWriterImpl();
		wsdl20writer = new org.ow2.easywsdl.wsdl.impl.wsdl20.WSDLWriterImpl();
	}

	public Document getDocument(final Description wsdlDef) throws WSDLException {
		Document doc = null;
		if(wsdlDef != null) {
			Description source = wsdlDef;
			final org.ow2.easywsdl.wsdl.api.WSDLWriter writer = this
			.getConcreteWriter(source);

			doc = writer.getDocument(source);
		}
		return doc;
	}

	@SuppressWarnings("unchecked")
	private org.ow2.easywsdl.wsdl.api.WSDLWriter getConcreteWriter(
			final Description wsdlDef) throws WSDLException {
		org.ow2.easywsdl.wsdl.api.WSDLWriter writer = null;
		if ((wsdlDef != null)
				&& (((AbstractWSDLElementImpl) wsdlDef).getModel() instanceof TDefinitions)) {
			writer = this.wsdl11writer;
		} else if ((wsdlDef != null)
				&& (((AbstractWSDLElementImpl) wsdlDef).getModel() instanceof DescriptionType)) {
			writer = this.wsdl20writer;
		} 

	    else {
			throw new WSDLException("Unknown model");
		}
		return writer;
	}

	public boolean getFeature(final String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
	}

	public void setFeature(final String name, final boolean value) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
	}

	public String writeWSDL(final Description wsdlDef) throws WSDLException {
		String res = null;
		if(wsdlDef != null) {
			Description source = wsdlDef;
			final org.ow2.easywsdl.wsdl.api.WSDLWriter writer = this
			.getConcreteWriter(source);

			res = writer.writeWSDL(source);
		}
		return res;
	}

}
