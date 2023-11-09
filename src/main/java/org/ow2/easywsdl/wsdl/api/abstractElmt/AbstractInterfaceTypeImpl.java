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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfFault;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOutput;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractInterfaceTypeImpl<E, Op extends AbsItfOperation<? extends AbsItfInput, ? extends AbsItfOutput, ? extends AbsItfFault>>
        extends AbstractWSDLElementImpl<E> implements AbsItfInterfaceType<Op> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * the desc
     */
    @SuppressWarnings("unchecked")
    protected AbsItfDescription desc;

    /**
     * list of operations
     */
    protected List<Op> operations = new ArrayList<Op>();

    
    public AbstractInterfaceTypeImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }
    
    public void addOperation(final Op op) {
        if (this.operations == null) {
            this.operations = new ArrayList<Op>();
        }
        this.operations.add(op);
    }

    public Op getOperation(final QName name) {
        Op res = null;
        for (final Op op : this.operations) {
            if (op.getQName().equals(name)) {
                res = op;
                break;
            }
        }
        return res;
    }

    public List<Op> getOperations() {
        if (this.operations == null) {
            this.operations = new ArrayList<Op>();
        }
        return this.operations;
    }

    /**
     * @return the desc
     */
    @SuppressWarnings("unchecked")
    public AbsItfDescription getDescription() {
        return this.desc;
    }

}
