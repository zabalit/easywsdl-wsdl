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
 
package org.ow2.easywsdl.wsdl.api.abstractElmt;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfParam;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractParamImpl<E> extends AbstractWSDLElementImpl<E> implements
        AbsItfParam {


    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * the parent operation
     */
    @SuppressWarnings("unchecked")
    protected AbsItfOperation operation;

    /**
     * the element name
     */
    protected QName elementName;
    
    public AbstractParamImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }

    /**
     * @return the interface
     */
    @SuppressWarnings("unchecked")
    public AbsItfOperation getOperation() {
        return this.operation;
    }

    @SuppressWarnings("unchecked")
    public Element getElement() {
        Element res = null;
        final Types types = (Types) ((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) this.operation)
                .getInterface()).getDescription().getTypes();
        if (this.elementName != null) {
        	List<Schema> schemas = types.getSchemas();
        	Iterator it = schemas.iterator();
        	while(it.hasNext()){
        		AbsItfSchema temp = (AbsItfSchema)it.next();
        		res = (Element) temp.getElement(this.elementName);
        		if(res != null){
        			break;
        		}
        	}
        }
        return res;
    }

}
