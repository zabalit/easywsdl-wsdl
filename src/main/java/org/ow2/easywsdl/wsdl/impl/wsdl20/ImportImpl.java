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
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLImportException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractImportImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ImportType;
import org.w3c.dom.Element;

import com.ebmwebsourcing.easycommons.uri.URIHelper;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class ImportImpl extends AbstractImportImpl<ImportType, Description> implements org.ow2.easywsdl.wsdl.api.Import {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ImportImpl(final ImportType impt, final Description parent, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports, final URI baseURI, WSDLReaderImpl reader) throws WSDLException, WSDLImportException {
		super(impt, parent, descriptionImports, schemaImports, baseURI, reader);

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(this.model.getDocumentation(), this);
	}

	public String getNamespaceURI() {
		return this.model.getNamespace();
	}

	public void setNamespaceURI(final String namespaceURI) {
		this.model.setNamespace(namespaceURI);
	}

	/**
     * {@inheritDoc}
     */
    public URI getLocationURI() {
        if (this.model.getLocation() != null) {
            return URIHelper.filePathToUri(this.model.getLocation());
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setLocationURI(final URI locationURI) {
		this.model.setLocation(locationURI.toString());
	}

	@Override
	public List<Element> getOtherElements() throws XmlException {
		final List<Element> res = super.getOtherElements();

		for (final Object item : this.model.getAny()) {
			if (item instanceof Element) {
				res.add((Element) item);
			}
		}

		return res;
	}

	public static ImportType replaceDOMElementByImportType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		ImportType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("import")) && (childToReplace.getNamespaceURI().equals(Constants.WSDL_20_NAMESPACE)))) {
				JAXBElement<ImportType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, ImportType.class);

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
