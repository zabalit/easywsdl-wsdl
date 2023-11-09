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

import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Module;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAPModuleImpl extends AbstractWSDLElementImpl<Module> implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPModule {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public SOAPModuleImpl(final Module m, AbstractWSDLElementImpl parent) {
        super(m, parent);

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(
                this.model.getDocumentation(), this);

    }

    public String getRef() {
        return this.model.getRef();
    }

    public boolean isRequired() {
        return this.model.isRequired();
    }
}
