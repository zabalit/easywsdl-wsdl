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
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.MultipartRelatedType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TPart;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class MIMEMultipartRelatedImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEMultipartRelated {

    private final MultipartRelatedType multipart;

    private AbsItfBindingParam param = null;

    private final List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEPart> parts = new ArrayList<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEPart>();

    public MIMEMultipartRelatedImpl(final MultipartRelatedType multipart,
            final AbsItfBindingParam param) {
        this.param = param;
        this.multipart = multipart;

        for (final TPart part : this.multipart.getPart()) {
            this.parts
                    .add(new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.mime.MIMEPartImpl(
                            part, param));
        }
    }

    public List<org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEPart> getParts() {
        return this.parts;
    }

    /**
     * @return the param
     */
    public AbsItfBindingParam getParam() {
        return this.param;
    }

}
