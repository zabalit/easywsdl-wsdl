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
 
package org.ow2.easywsdl.wsdl.decorator;

import java.net.URI;

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfImport;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class DecoratorImportImpl<D extends AbsItfDescription>  extends Decorator<AbsItfImport<D>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorImportImpl(final AbsItfImport<D> impt) throws WSDLException {
        this.internalObject = impt;
    }

    public String getNamespaceURI() {
        return this.internalObject.getNamespaceURI();
    }

    public void setNamespaceURI(final String arg0) {
        this.internalObject.setNamespaceURI(arg0);
    }

    public D getDescription() {
        return this.internalObject.getDescription();
    }

    public URI getLocationURI() {
        return this.internalObject.getLocationURI();
    }

    public void setDescription(final D arg0) {
        this.internalObject.setDescription(arg0);
    }

    public void setLocationURI(final URI arg0) {
        this.internalObject.setLocationURI(arg0);
    }
}
