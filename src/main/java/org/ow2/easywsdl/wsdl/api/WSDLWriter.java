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


import org.w3c.dom.Document;


/**
 * This interface describes a collection of methods that allow a WSDL model to
 * be written to a writer in an XML format that follows the WSDL schema.
 *
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface WSDLWriter {
    /**
     * Sets the specified feature to the specified value.
     * <p>
     * There are no minimum features that must be supported.
     * <p>
     * All feature names must be fully-qualified, Java package style. All names
     * starting with javax.wsdl. are reserved for features defined by the JWSDL
     * specification. It is recommended that implementation- specific features
     * be fully-qualified to match the package name of that implementation. For
     * example: com.abc.featureName
     *
     * @param name
     *            the name of the feature to be set.
     * @param value
     *            the value to set the feature to.
     * @throws IllegalArgumentException
     *             if the feature name is not recognized.
     * @see #getFeature(String)
     */
    void setFeature(String name, boolean value) throws IllegalArgumentException;

    /**
     * Gets the value of the specified feature.
     *
     * @param name
     *            the name of the feature to get the value of.
     * @return the value of the feature.
     * @throws IllegalArgumentException
     *             if the feature name is not recognized.
     * @see #setFeature(String, boolean)
     */
    boolean getFeature(String name) throws IllegalArgumentException;

    /**
     * Return a document generated from the specified WSDL model.
     */
    Document getDocument(Description wsdlDef) throws WSDLException;

    /**
     * Write the specified WSDL definition .
     *
     * @param wsdlDef
     *            the WSDL definition to be written.
     */
    String writeWSDL(Description wsdlDef) throws WSDLException;

}
