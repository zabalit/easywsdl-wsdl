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
 
package org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11;

import java.util.List;

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface SOAP11Binding4Wsdl11 extends SOAPBinding4Wsdl11 {

    List<SOAP11Header> getHeaders();

    SOAP11Body createBody();

    SOAP11Body getBody();

    void setBody(SOAP11Body body) throws WSDLException;

    SOAP11Fault createFault();

    SOAP11Fault getFault();

    void setFault(SOAP11Fault fault) throws WSDLException;
}
