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
 
package org.ow2.easywsdl.wsdl.api;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface WSDLReader_Internal {

	//Description readWSDL(final URI wsdlURI, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports) throws WSDLException;
	
	//Description readWSDL(final Document doc, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports) throws WSDLException;

	//Description readWSDL(final URI wsdlURI, WSDLVersionConstants version, InputSource source) throws WSDLException;

	//Description readWSDL(final URI wsdlURI, WSDLVersionConstants version, InputSource source, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports) throws WSDLException;
	
	/**
     * Read an external WSDL URI according to the current base URI of the
     * reader.
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
    /*Description readExternalPart(final URI schemaURI, final Map<URI, AbsItfDescription> descriptionImports,
            final Map<URI, AbsItfSchema> schemaImports, final boolean deleteImports)
            throws WSDLException, MalformedURLException, URISyntaxException;

    protected abstract Map<URI, AbsItfSchema> getImportList();*/
}
