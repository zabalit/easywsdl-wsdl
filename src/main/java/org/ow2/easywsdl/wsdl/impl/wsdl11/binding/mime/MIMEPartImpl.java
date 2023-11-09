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

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingOperationImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingParamImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingParam;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TPart;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class MIMEPartImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEPart {

    private final TPart part;

    private final AbsItfBindingParam param;

    private final BindingProtocol bindingProtocol;

    /**
     * 
     * @param part
     * @param param
     */
    @SuppressWarnings("unchecked")
    public MIMEPartImpl(final TPart part, final AbsItfBindingParam param) {
        this.part = part;
        this.param = param;

        // get the binding protocol
        this.bindingProtocol = AbstractBindingParamImpl.extractBindingProtocol((List) this.part
                .getAny(), (AbstractBindingParamImpl) this.param);
    }

    @SuppressWarnings("unchecked")
    public QName getPartQName() {
        QName res = null;
        if (this.part.getName() != null) {
            res = new QName(((AbstractBindingOperationImpl) ((AbstractBindingParamImpl) this.param)
                    .getBindingOperation()).getQName().getNamespaceURI(), this.part.getName());
        }
        return res;
    }

    public void setPartQName(final QName name) {
        throw new UnsupportedOperationException();
    }

    public BindingProtocol getBindingProtocol() {
        return this.bindingProtocol;
    }

}
