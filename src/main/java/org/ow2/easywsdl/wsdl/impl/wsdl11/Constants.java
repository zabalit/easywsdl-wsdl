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

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class Constants {

    /**
     * WSDL 1.1 Namespace
     */
    public static final String WSDL_11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";

    /**
     * WSDL 1.1 package
     */
    public static final String WSDL11_PACKAGE = Constants.class.getPackage().getName();

    /**
     * WSDL 1.1 schema name
     */
    public static final String XSD_WSDL_11 = "org/ow2/easywsdl/wsdl/wsdl11/wsdl11.xsd";

    /**
     * WSDL 1.1 root tag
     */
    public static final String WSDL11_ROOT_TAG = "definitions";

    /**
     * Errors
     */
    public static final String NOT_SUPPORTED = "the WSDL 1.1 model does not support this operation";

    /**
     * Transport soap/http
     */
    public static final String HTTP_SCHEMAS_XMLSOAP_ORG_SOAP_HTTP = "http://schemas.xmlsoap.org/soap/http";

}
