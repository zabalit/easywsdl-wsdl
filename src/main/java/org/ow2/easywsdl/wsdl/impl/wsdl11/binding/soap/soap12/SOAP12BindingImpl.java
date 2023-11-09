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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12;

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Body;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Fault;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Header;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBody;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeader;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP12BindingImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Binding4Wsdl11 {

    SOAP12Body soap12body = null;

    List<SOAP12Header> soap12headers = new ArrayList<SOAP12Header>();

    SOAP12Fault soap12fault = null;

    public SOAP12BindingImpl(final List<THeader> headers, final TBody body, final TFault fault) {
        if (headers != null) {
            for (final THeader h : headers) {
                this.soap12headers
                        .add(new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12.SOAP12HeaderImpl(
                                h));
            }
        }
        if (body != null) {
            this.soap12body = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12.SOAP12BodyImpl(
                    body);
        }
        if (fault != null) {
            this.soap12fault = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12.SOAP12FaultImpl(
                    fault);
        }
    }

    public List<SOAP12Header> getHeaders() {
        return this.soap12headers;
    }

    public SOAP12Body getBody() {
        return this.soap12body;
    }

    public SOAP12Fault getFault() {
        return this.soap12fault;
    }

}
