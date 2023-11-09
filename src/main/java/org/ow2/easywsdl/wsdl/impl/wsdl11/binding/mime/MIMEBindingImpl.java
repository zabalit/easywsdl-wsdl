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

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingParam;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.ContentType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.MultipartRelatedType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TMimeXml;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class MIMEBindingImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEBinding4Wsdl11 {

    private final List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEContent> contents = new ArrayList<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEContent>();

    private final List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEMultipartRelated> multiparts = new ArrayList<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEMultipartRelated>();

    private final List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEXml> mimeXmls = new ArrayList<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEXml>();

    private AbsItfBindingParam param = null;

    public MIMEBindingImpl(final List<ContentType> contents, final List<TMimeXml> mimeXmls,
            final List<MultipartRelatedType> multiparts, final AbsItfBindingParam param) {
        this.param = param;
        if (contents != null) {
            for (final ContentType content : contents) {
                this.contents.add(new MIMEContentImpl(content));
            }
        }
        if (mimeXmls != null) {
            for (final TMimeXml content : mimeXmls) {
                this.mimeXmls.add(new MIMEXmlImpl(content));
            }
        }
        if (multiparts != null) {
            for (final MultipartRelatedType mpart : multiparts) {
                this.multiparts.add(new MIMEMultipartRelatedImpl(mpart, this.param));
            }
        }
    }

    public List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEContent> getContents() {
        return this.contents;
    }

    public List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEMultipartRelated> getMultipartRelateds() {
        return this.multiparts;
    }

    public List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEXml> getMimeXml() {
        return this.mimeXmls;
    }

}
