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

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Documentation;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractSchemaElementImpl;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;

/**
 * @author Nicolas Boissel-Dallier - EBM WebSourcing
 */
public abstract class Decorator<IO extends WSDLElement> extends AbstractWSDLElementImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected IO internalObject;
	
    public Documentation createDocumentation() {
        return this.internalObject.createDocumentation();
    }

    public Documentation getDocumentation() {
        return this.internalObject.getDocumentation();
    }

    public Map<QName, String> getOtherAttributes() throws XmlException {
        return this.internalObject.getOtherAttributes();
    }

    public List<org.w3c.dom.Element> getOtherElements() throws XmlException {
        return this.internalObject.getOtherElements();
    }

    public void setDocumentation(final Documentation doc) {
        this.internalObject.setDocumentation(doc);
    }

	public Object getModel() {
		return ((AbstractWSDLElementImpl)this.internalObject).getModel();
	}

	public AbstractSchemaElementImpl getParent() {
		return ((AbstractWSDLElementImpl)this.internalObject).getParent();
	}

	public WSDLElement getInternalObject() {
		return this.internalObject;
	}
	
    public boolean equals(Object o){
    	return this.internalObject.equals(o);
    }
    
    public int hashCode(){
    	return this.internalObject.hashCode();
    }
	
}
