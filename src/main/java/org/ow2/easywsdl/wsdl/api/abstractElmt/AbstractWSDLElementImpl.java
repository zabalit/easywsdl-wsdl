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
 
package org.ow2.easywsdl.wsdl.api.abstractElmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Documentation;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractXMLElementImpl;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DocumentedType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ExtensibleDocumentedType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDocumented;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TExtensibleAttributesDocumented;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TExtensibleDocumented;
import org.w3c.dom.Element;

/**
 * Abstract super class for all WSDL Elements, providing some basic common functionality.
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractWSDLElementImpl<E> extends AbstractXMLElementImpl<E> implements WSDLElement {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(AbstractWSDLElementImpl.class.getName());

    public AbstractWSDLElementImpl() {
    	super();
    }

    public AbstractWSDLElementImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }

    /**
     * Set the documentation for this document.
     *
     * @param doc
     *            the documentation element
     */
    @SuppressWarnings("unchecked")
    public void setDocumentation(final Documentation doc) {
        this.documentation = doc;

        if (this.model instanceof TDocumented) {
            ((TDocumented) this.model)
                    .setDocumentation(((org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl) this.documentation)
                            .getModel());
        } else if (this.model instanceof List) {
            ((List<DocumentedType>) this.model)
                    .add((DocumentedType) ((org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl) this.documentation)
                            .getModel());
        }
    }

    /**
     * Get the documentation.
     *
     * @return the documentation element
     */
    public Documentation getDocumentation() {
        return this.documentation;
    }

    /**
     * Create the documentation element.
     *
     * @return the documentation element
     */
    public Documentation createDocumentation() {
        Documentation doc = null;
        if (this.getClass().getPackage().getName().equals(
                org.ow2.easywsdl.wsdl.impl.wsdl11.Constants.WSDL11_PACKAGE)) {
            doc = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl();
        } else if (this.getClass().getPackage().getName().equals(
                org.ow2.easywsdl.wsdl.impl.wsdl20.Constants.WSDL20_PACKAGE)) {
            doc = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl();
        } else {
            LOG.info("Impossible to create a documentation on this element");
        }
        return doc;
    }

    /**
     * Get all the extensibility elements defined here.
     *
     * @throws WSDLException
     */
    public List<Element> getOtherElements() throws XmlException {
        final List<Element> res = new ArrayList<Element>();

		if (this.model instanceof TExtensibleDocumented) {
			List<Object> any = null;
			any = ((TExtensibleDocumented) this.model).getAny();
			for (final Object anyItem : any) {
				if (anyItem instanceof Element) {
					Element xmlElmt = (Element)anyItem;
					res.add(xmlElmt);
				}
			}
		}
        return res;
    }
    
    public void addOtherElements(Element elmt) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the map containing all the attributes defined on this element. The
     * keys are the qnames of the attributes.
     *
     * @return a map containing all the attributes defined on this element
     * @throws WSDLException
     *
     */
    public Map<QName, String> getOtherAttributes() throws XmlException {
        Map<QName, String> res = new HashMap<QName, String>();

        if (this.model instanceof TExtensibleAttributesDocumented) {
            res = ((TExtensibleAttributesDocumented) this.model).getOtherAttributes();
        } else if (this.model instanceof ExtensibleDocumentedType) {
            res = ((ExtensibleDocumentedType) this.model).getOtherAttributes();
        }
        return res;
    }

    @Override
    public String toString() {
    	String res = null;
    	if(this.model != null) {
    		res = this.model.toString();
    	}
        return res;
    }

    public E getModel() {
        return this.model;
    }
}
