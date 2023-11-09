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
 
package org.ow2.easywsdl.wsdl.api.abstractItf;

import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.http.HTTPBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.http.HTTPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20;

/**
 * This interface represents an input message, and contains the name of the input and the message itself.
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract interface AbsItfBindingParam extends WSDLElement {

    /**
     * Set the name of this input.
     *
     * @param name
     *            the desired name
     */
    void setName(String name);

    /**
     * Get the name of this input.
     *
     * @return the input message name
     */
    String getName();

    SOAP11Binding4Wsdl11 createSOAP11Binding4Wsdl11();

    SOAP11Binding4Wsdl11 getSOAP11Binding4Wsdl11();

    void setSOAP11Binding4Wsdl11(SOAP11Binding4Wsdl11 soap11binding);

    SOAP12Binding4Wsdl11 getSOAP12Binding4Wsdl11();

    HTTPBinding4Wsdl11 getHTTPBinding4Wsdl11();

    MIMEBinding4Wsdl11 getMIMEBinding4Wsdl11();

    SOAPBinding4Wsdl20 createSOAP12Binding4Wsdl20();

    SOAPBinding4Wsdl20 getSOAP12Binding4Wsdl20();

    void setSOAP12Binding4Wsdl20(SOAPBinding4Wsdl20 soap12binding);

    HTTPBinding4Wsdl20 getHTTPBinding4Wsdl20();

    String getHttpContentEncoding();
}
