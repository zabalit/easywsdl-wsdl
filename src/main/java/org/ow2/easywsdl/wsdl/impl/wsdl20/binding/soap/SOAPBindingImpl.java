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
 
package org.ow2.easywsdl.wsdl.impl.wsdl20.binding.soap;

import java.util.ArrayList;
import java.util.List;

import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPHeader;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPModule;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Header;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Module;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAPBindingImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20 {

    private final List<SOAPHeader> soapHeaders = new ArrayList<SOAPHeader>();

    private final List<SOAPModule> soapModules = new ArrayList<SOAPModule>();

    public SOAPBindingImpl(final List<Header> headers, final List<Module> modules, AbstractWSDLElementImpl parent) {
        for (final Header header : headers) {
            this.soapHeaders
                    .add(new org.ow2.easywsdl.wsdl.impl.wsdl20.binding.soap.SOAPHeaderImpl(
                            header, parent));
        }
        for (final Module module : modules) {
            this.soapModules
                    .add(new org.ow2.easywsdl.wsdl.impl.wsdl20.binding.soap.SOAPModuleImpl(
                            module, parent));
        }
    }

    public List<SOAPHeader> getHeaders() {
        return this.soapHeaders;
    }

    public List<SOAPModule> getModules() {
        return this.soapModules;
    }

}
