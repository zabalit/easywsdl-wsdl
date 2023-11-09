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

import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.ContentType;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class MIMEContentImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEContent {

    private final ContentType content;

    public MIMEContentImpl(final ContentType content) {
        this.content = content;
    }

    public String getPart() {
        return this.content.getPart();
    }

    public String getType() {
        return this.content.getType();
    }
}
