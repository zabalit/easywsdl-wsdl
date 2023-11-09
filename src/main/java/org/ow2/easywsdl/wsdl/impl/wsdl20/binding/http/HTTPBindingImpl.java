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
 
package org.ow2.easywsdl.wsdl.impl.wsdl20.binding.http;

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.http.HTTPHeader;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.http.Header;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class HTTPBindingImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl20.http.HTTPBinding4Wsdl20 {

    private final List<HTTPHeader> httpHeaders = new ArrayList<HTTPHeader>();

    public HTTPBindingImpl(final List<Header> headers, AbstractWSDLElementImpl parent) {
        for (final Header header : headers) {
            this.httpHeaders
                    .add(new org.ow2.easywsdl.wsdl.impl.wsdl20.binding.http.HTTPHeaderImpl(
                            header, parent));
        }
    }

    public List<HTTPHeader> getHeaders() {
        return this.httpHeaders;
    }

}
