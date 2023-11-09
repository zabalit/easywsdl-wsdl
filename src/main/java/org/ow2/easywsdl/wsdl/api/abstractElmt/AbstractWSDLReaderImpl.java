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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.ow2.easywsdl.schema.api.SchemaException;
import org.ow2.easywsdl.schema.api.SchemaReader;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.schema.impl.SchemaReaderImpl;
import org.ow2.easywsdl.schema.util.URILocationResolver;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.xml.sax.InputSource;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractWSDLReaderImpl implements WSDLReader {

	protected static final Logger LOG = Logger.getLogger(AbstractWSDLReaderImpl.class.getName());

	private Map<FeatureConstants, Object> features = new HashMap<FeatureConstants, Object>();

	private SchemaReader schemaReader = null; 

	private final URILocationResolver uriLocationResolver = new URILocationResolver();

	private URI documentBaseURI;
    
    public AbstractWSDLReaderImpl() throws WSDLException {
		try {
			this.schemaReader = new SchemaReaderImpl();
			this.features.put(FeatureConstants.VERBOSE, false);
			this.features.put(FeatureConstants.IMPORT_DOCUMENTS, true);
		} catch (SchemaException e) {
			throw new WSDLException(e);
		}
	}
	
	public SchemaReader getSchemaReader() {
		return schemaReader;
	}
	
	public void setDocumentBaseURI(final URI documentBaseURI) {
	    this.documentBaseURI = documentBaseURI;
	    if (this.schemaReader != null) {
	        this.schemaReader.setDocumentBaseURI(documentBaseURI);
	    }
	}

	public final void setFeature(final FeatureConstants name, final Object value)
	throws WSDLException {
		this.features.put(name, value);
		AbstractWSDLReaderImpl.LOG.finest("set proterty: " + name + " - value = " + value);
	}

	public final Object getFeature(final FeatureConstants name) {
		return this.features.get(name);
	}

	public final Map<FeatureConstants, Object> getFeatures() {
		return this.features;
	}

	public final void setFeatures(final Map<FeatureConstants, Object> features) {
		this.features = features;
	}

	/**
	 * Read an WSDL part provided by an {@link InputSource}, description
     * imports/includes and schema imports/includes provided by
     * <code>descriptionImports</code> and <code>schemaImports</code> are not
     * read.
     * 
     * @throws WSDLException
     * @throws MalformedURLException
     *             The {@link InputSource} systemId is a malformed URL.
     * @throws URISyntaxException
     *             The {@link InputSource} systemId is an URL not formatted
     *             strictly according to to RFC2396 and cannot be converted to a
     *             URI.
	 */
	public abstract Description read(final InputSource source, final Map<URI, AbsItfDescription> descriptionImports, final Map<URI, AbsItfSchema> schemaImports) throws WSDLException, MalformedURLException, URISyntaxException;

    /**
     * Read an external WSDL URI according to a base URI.
     * 
     * @throws WSDLException
     * @throws MalformedURLException
     *             The URL based on the external WSDL URI and the current base
     *             URI is a malformed URL.
     * @throws URISyntaxException
     *             The URL based on the external WSDL URI and the current base
     *             URI is an URL not formatted strictly according to to RFC2396
     *             and cannot be converted to a URI.
     */
    protected Description readExternalPart(final URI externalURI, final URI documentBaseURI, final Map<URI, AbsItfDescription> descriptionImports,
            final Map<URI, AbsItfSchema> schemaImports)
            throws WSDLException, MalformedURLException, URISyntaxException {
        
        InputSource inputSource = null;
		try {
			inputSource = new InputSource(this.uriLocationResolver.resolve(documentBaseURI, externalURI).openStream());
			inputSource.setSystemId(this.uriLocationResolver.resolve(documentBaseURI, externalURI).toString());
		} catch (IOException e) {
			throw new WSDLException(e);
		}
        return this.read(inputSource, descriptionImports, schemaImports);
    }

}
