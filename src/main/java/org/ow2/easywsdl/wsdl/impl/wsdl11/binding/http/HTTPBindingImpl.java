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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11.binding.http;

import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlEncoded;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlReplacement;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class HTTPBindingImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.http.HTTPBinding4Wsdl11 {

    private UrlEncoded encoded = null;

    private UrlReplacement replacement = null;

    public HTTPBindingImpl(final UrlEncoded encoded, final UrlReplacement replacement) {
        this.encoded = encoded;
        this.replacement = replacement;
    }

    public boolean isUrlEncoded() {
        return this.encoded != null;
    }

    public boolean isUrlReplacement() {
        return this.replacement != null;
    }
}
