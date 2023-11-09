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
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractIncludeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.IncludeType;
import org.w3c.dom.Element;

import com.ebmwebsourcing.easycommons.uri.URIHelper;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class IncludeImpl extends AbstractIncludeImpl<IncludeType, Description> implements org.ow2.easywsdl.wsdl.api.Include {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public IncludeImpl(final IncludeType incl, final Description parent, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports, final URI baseURI, WSDLReaderImpl reader) throws WSDLException, WSDLImportException {
		super(incl, parent, descriptionImports, schemaImports, baseURI, reader);

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(this.model.getDocumentation(), this);
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

	public static IncludeType replaceDOMElementByIncludeType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		IncludeType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("include")) && (childToReplace.getNamespaceURI().equals(Constants.WSDL_20_NAMESPACE)))) {
				JAXBElement<IncludeType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, IncludeType.class);

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
