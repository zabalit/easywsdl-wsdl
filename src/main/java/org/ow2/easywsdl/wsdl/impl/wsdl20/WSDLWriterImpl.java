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

import java.io.StringWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ow2.easywsdl.schema.api.extensions.NamespaceMapperImpl;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.util.CustomPrefixMapper;
import org.ow2.easywsdl.wsdl.util.Util;
import org.w3c.dom.Document;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLWriterImpl implements org.ow2.easywsdl.wsdl.api.WSDLWriter {

	private String[] customPrefixes = null;

    private final DocumentBuilderFactory builder;

	/*
	 * Private object initializations
	 */
	public WSDLWriterImpl() throws WSDLException {
		builder = DocumentBuilderFactory.newInstance();
		builder.setNamespaceAware(true);

	}


	/**
	 * Build the XML nodes from the WSDL descriptor in Java classes form.
	 * @param schemaLocation 
	 */
	@SuppressWarnings("unchecked")
	public Document convertWSDL20Description2DOMElement(final DescriptionType wsdlDescriptor, String schemaLocation) throws WSDLException {
		Document doc = null;
		try {
			doc = builder.newDocumentBuilder().newDocument();

			// TODO : Check if it is a Thread safe method
			final JAXBElement element = new JAXBElement(new QName(Constants.WSDL_20_NAMESPACE, Constants.WSDL20_ROOT_TAG), wsdlDescriptor.getClass(), wsdlDescriptor);

            Marshaller marshaller = WSDLJAXBContext.getJaxbContext().createMarshaller();
			NamespacePrefixMapper mapper = null;
			if(this.customPrefixes != null) {
				mapper = new CustomPrefixMapper(customPrefixes);
			} else {
				mapper = new CustomPrefixMapper();
			}
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
			if (schemaLocation != null) {
				marshaller.setProperty("jaxb.schemaLocation", schemaLocation);
			}
			marshaller.marshal(element, doc);

		} catch (final JAXBException ex) {
			throw new WSDLException("Failed to build XML binding from WSDL descriptor Java classes", ex);
		} catch (final ParserConfigurationException ex) {
			throw new WSDLException("Failed to build XML binding from WSDL descriptor Java classes", ex);

		}
		return doc;
	}

	/**
	 * Build the XML String from the WSDL descriptor in Java classes form.
	 * @param namespaceMapperImpl 
	 * @param schemaLocation 
	 * 
	 * @param wsdlDescriptor
	 *            The WSDL Descriptor root class
	 * @return The String to fill with the WSDL descriptor XML
	 * @throws WSDLException
	 *             The exception raised during the marshaller creation or the exception raised during the build of the XML string.
	 */
	@SuppressWarnings("unchecked")
	public String convertWSDL20Description2String(final DescriptionType wsdlDescriptor, NamespaceMapperImpl namespaceMapperImpl, String schemaLocation) throws WSDLException {

		try {
			final StringWriter stringWriter = new StringWriter();
			// TODO : Check if it is a Thread safe method

			final JAXBElement element = new JAXBElement(new QName(Constants.WSDL_20_NAMESPACE, Constants.WSDL20_ROOT_TAG), wsdlDescriptor.getClass(), wsdlDescriptor);

            Marshaller marshaller = WSDLJAXBContext.getJaxbContext().createMarshaller();

			NamespacePrefixMapper mapper = null;
			if(this.customPrefixes != null) {
				mapper = new CustomPrefixMapper(customPrefixes);
			} else {
				mapper = namespaceMapperImpl;
			}
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
			if (schemaLocation != null) {
				marshaller.setProperty("jaxb.schemaLocation", schemaLocation);
			}
			marshaller.marshal(element, stringWriter);

			return stringWriter.toString();
		} catch (final JAXBException e) {
			throw new WSDLException("Failed to build XML binding from Agreement descriptor Java classes", e);
		}
	}


	public Document getDocument(final Description wsdlDef) throws WSDLException {
		Document doc = null;
		if ((wsdlDef != null) && (wsdlDef instanceof org.ow2.easywsdl.wsdl.impl.wsdl20.DescriptionImpl)) {
			try {
				String schemaLocation = Util.convertSchemaLocationIntoString(wsdlDef);
				doc = this.convertWSDL20Description2DOMElement(((org.ow2.easywsdl.wsdl.impl.wsdl20.DescriptionImpl) wsdlDef).getModel(), schemaLocation);
				if (wsdlDef.getDocumentBaseURI() != null) {
					doc.setDocumentURI(wsdlDef.getDocumentBaseURI().toString());
				} else {
					doc.setDocumentURI(".");
				}
			} catch (final WSDLException e) {
				throw new WSDLException("Can not write wsdl description", e);
			} 
		}
		return doc;
	}

	public boolean getFeature(final String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
	}

	public void setFeature(final String name, final boolean value) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
	}

	public String writeWSDL(final Description wsdlDef) throws WSDLException {
		String res = null;
		if ((wsdlDef != null) && (wsdlDef instanceof org.ow2.easywsdl.wsdl.impl.wsdl20.DescriptionImpl)) {
			try {

				String schemaLocation = Util.convertSchemaLocationIntoString(wsdlDef);
				res = this.convertWSDL20Description2String(((org.ow2.easywsdl.wsdl.impl.wsdl20.DescriptionImpl) wsdlDef).getModel(), wsdlDef.getNamespaces(), schemaLocation);
			} catch (final WSDLException e) {
				throw new WSDLException("Can not write wsdl description", e);
			} 
		}
		return res;
	}

	/*
	 * Method used to set predefined namespace prefixes.
	 */
	public void useCustomNamespacesPrefixes(String[] customPrefixes) throws WSDLException {
		this.customPrefixes = customPrefixes.clone();
	}

	/*
	 * Method used to set normalized namespace prefixes.
	 */
	public void useNormalizedNamespacesPrefixes() throws WSDLException {
		this.customPrefixes = null;
	}

}
