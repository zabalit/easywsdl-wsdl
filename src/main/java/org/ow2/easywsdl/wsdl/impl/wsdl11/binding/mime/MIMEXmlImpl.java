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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11.binding.mime;

import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TMimeXml;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class MIMEXmlImpl implements org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEXml {

    private final TMimeXml mimeXml;

    public MIMEXmlImpl(final TMimeXml mimeXml) {
        this.mimeXml = mimeXml;
    }

    public String getPart() {
        return this.mimeXml.getPart();
    }

    public boolean isRequired() {
        return this.mimeXml.isRequired();
    }
}