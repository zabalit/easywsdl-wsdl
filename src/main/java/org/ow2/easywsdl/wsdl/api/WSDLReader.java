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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * This interface describes a collection of methods that enable conversion of a
 * WSDL document (in XML, following the WSDL schema described in the WSDL
 * specification) into a WSDL model.
 * 
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface WSDLReader {

	/**
	 * Constants for the Message Exchange Patterns.
	 * 
	 */
	public enum FeatureConstants {
		VERBOSE("org.ow2.easywsdl.schema.test.verbose"), IMPORT_DOCUMENTS(
				"org.ow2.easywsdl.schema.test.importDocuments");

		private final String value;

		/**
		 * Creates a new instance of {@link FeatureConstants}
		 * 
		 * @param value
		 */
		private FeatureConstants(final String value) {
			this.value = value;
		}

		/**
		 * 
		 * @return
		 */
		public String value() {
			return this.value;
		}

		/**
		 * Please use this equals method instead of using :<code>
         * value().equals(value)
         * </code> which is
		 * almost 10 times slower...
		 * 
		 * @param val
		 * @return
		 */
		public boolean equals(final String val) {
			return this.value().equals(val);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.value;
		}
	}

	/**
	 * Sets the specified feature to the specified value.
	 * <p>
	 * The minimum features that must be supported are:
	 * </p>
	 * <table border=1>
	 * <caption>Minimal features to support</caption>
	 * <tr>
	 * <th>Name</th>
	 * <th>DescriptionImpl</th>
	 * <th>Default Value</th>
	 * </tr>
	 * <tr>
	 * <td><center>org.ow2.easywsdl.schema.test.verbose</center></td>
	 * <td>If set to true, status messages will be displayed.</td>
	 * <td><center>type: boolean - default value: false</center></td>
	 * </tr>
	 * <tr>
	 * <td><center>org.ow2.easywsdl.schema.test.importDocuments</center></td>
	 * <td>If set to true, imported WSDL documents will be retrieved and
	 * processed.</td>
	 * <td><center>type: boolean - default value: true</center></td>
	 * </tr>
	 * <tr>
	 * <td>
	 * <center>org.ow2.easywsdl.schema.test.pathDirectoryOfImportLocations
	 * </center></td>
	 * <td>If the location is set, imported WSDL documents will be retrieved at
	 * this location (Set the importDocuments Features at true).</td>
	 * <td><center>type: String</center></td>
	 * </tr>
	 * </table>
	 * <p>
	 * All feature names must be fully-qualified, Java package style. All names
	 * starting with om.ebmwebsourcing. are reserved for features defined by the
	 * specification. It is recommended that implementation- specific features
	 * be fully-qualified to match the package name of that implementation. For
	 * example: com.abc.featureName
	 * 
	 * @param name
	 *            the name of the feature to be set.
	 * @param value
	 *            the value to set the feature to.
	 * @throws WSDLException
	 *             TODO
	 * @see #getFeature(FeatureConstants)
	 */
	void setFeature(FeatureConstants name, Object value) throws WSDLException;

	/**
	 * Gets the value of the specified feature.
	 * 
	 * @param name
	 *            the name of the feature to get the value of.
	 * @return the value of feature
	 * @throws IllegalArgumentException
	 *             if the feature name is not recognized.
	 * @see #setFeature(FeatureConstants, Object)
	 */
	Object getFeature(FeatureConstants name);

	/**
	 * Get all features.
	 * 
	 * @return the features
	 * @see #setFeature(FeatureConstants, Object)
	 */
	Map<FeatureConstants, Object> getFeatures();

	/**
     * Set all features.
     */
    void setFeatures(final Map<FeatureConstants, Object> features);
	
    /**
     * <p>
     * Read the WSDL definition available at the location identified by the
     * specified URL, and bind it into a {@link Description} object.
     * </p>
     * <p>
     * <b>Note</b>: all relative URIs are resolved according to the specified
     * URL.
     * </p>
     * 
     * @param wsdlURL
     *            an URL pointing to a WSDL definition.
     * @return the {@link Description} definition.
     * @throws WSDLException
     *             An error occurs during the parsing or the binding of the
     *             WSDL definition
     * @throws URISyntaxException
     *             If the URL is not formatted strictly according to to RFC2396
     *             and cannot be converted to a URI.
     * @throws IOException
     *             An I/O error occurs openning the URL stream.
     */
	Description read(final URL wsdlURL) throws WSDLException, IOException, URISyntaxException;

    /**
     * <p>
     * Read the WSDL definition available at the specified DOM {@link Document},
     * and bind it into a {@link Description} object.
     * </p>
     * <p>
     * <b>Note</b>: To be able to resolve relative URIs, the {@link Document}
     * base URI must be set.
     * </p>
     * 
     * @param document
     *            a DOM {@link Document} pointing to a WSDL definition.
     * @return the {@link Description} definition.
     * @throws WSDLException
     *             An error occurs during the parsing or the binding of the WSDL
     *             definition
     * @throws URISyntaxException
     *             the DOM {@link Document} base URI is not formatted strictly
     *             according to to RFC2396 and cannot be converted to a URI.
     */
    Description read(final Document document) throws WSDLException, URISyntaxException;

    /**
     * <p>
     * Read the WSDL definition available at the specified {@link InputSource},
     * and bind it into a {@link Description} object.
     * </p>
     * <p>
     * <b>Note</b>: To be able to resolve relative URIs, the {@link InputSource}
     * system identifier must be set.
     * </p>
     * 
     * @param inputSource
     *            an {@link InputSource} pointing to a WSDL definition.
     * @return the {@link Description} definition.
     * @throws WSDLException
     *             An error occurs during the parsing or the binding of the WSDL
     *             definition.
     * @throws MalformedURLException
     *             The {@link InputSource} system identifier is not a
     *             well-formed URL.
     * @throws URISyntaxException
     *             the {@link InputSource} system identifier is not formatted
     *             strictly according to to RFC2396 and cannot be converted to a
     *             URI.
     */
    Description read(final InputSource inputSource) throws WSDLException, MalformedURLException, URISyntaxException;

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
    Description read(final InputSource source, final Map<URI, AbsItfDescription> descriptionImports, final Map<URI, AbsItfSchema> schemaImports) throws WSDLException, MalformedURLException, URISyntaxException;


}
