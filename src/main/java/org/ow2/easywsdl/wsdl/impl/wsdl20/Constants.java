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

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class Constants {

    /**
     * WSDL 2.0 Namespace
     */
    public static final String WSDL_20_NAMESPACE = "http://www.w3.org/ns/wsdl";

    /**
     * WSDL 2.0 package
     */
    public static final String WSDL20_PACKAGE = Constants.class.getPackage().getName();

    /**
     * WSDL 2.0 schema name
     */
    public static final String XSD_WSDL_20 = "org/ow2/easywsdl/wsdl/wsdl20/wsdl20.xsd";

    /**
     * WSDL 2.0 root tag
     */
    public static final String WSDL20_ROOT_TAG = "description";

    /**
     * http method default attribute in http binding
     */
    public static final String HTTP_METHOD_DEFAULT = "methodDefault";

    /**
     * http method attribute in http binding
     */
    public static final String HTTP_METHOD = "method";

    /**
     * soap protocol attribute in soap binding
     */
    public static final String SOAP_PROTOCOL = "protocol";

    /**
     * soap mep attribute in soap binding
     */
    public static final String MEP_ATTRIBUTE = "mep";

    /**
     * Errors
     */
    public static final String NOT_SUPPORTED = "the WSDL 2.0 model does not support this operation";

    /**
     * transport soap/http
     */
    public static final String HTTP_WWW_W3_ORG_2003_05_SOAP_BINDINGS_HTTP = "http://www.w3.org/2003/05/soap/bindings/HTTP/";

}
