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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractInterfaceTypeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractOperationImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractParamImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractPartImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPart;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class PartImpl extends AbstractPartImpl<TPart> implements
        org.ow2.easywsdl.wsdl.api.Part {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;


    /**
     * 
     * @param part
     * @param parent
     */
    public PartImpl(final TPart part, final WSDLElement parent) {
        super(part, (AbstractWSDLElementImpl) parent);

        this.elementName = this.model.getElement();
        this.typeName = this.model.getType();
    }

    @SuppressWarnings("unchecked")
    public QName getPartQName() {
    	String tns = null;
    	if(parent instanceof AbstractParamImpl) {
    		tns = ((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) ((AbstractParamImpl) this.parent)
                    .getOperation()).getInterface()).getDescription().getTargetNamespace();
    	}
    	if(parent instanceof MessageImpl) {
    		tns = ((MessageImpl)parent).getDescription().getTargetNamespace();
    	}
    	
    	
        return new QName(
                tns,
                this.model.getName());
    }

    public void setPartQName(final QName name) {
        this.model.setName(name.getLocalPart());
    }

    public void setElement(final org.ow2.easywsdl.schema.api.Element element) {
        throw new UnsupportedOperationException();
    }

    public void setType(final Type type) {
        throw new UnsupportedOperationException();
    }

}
