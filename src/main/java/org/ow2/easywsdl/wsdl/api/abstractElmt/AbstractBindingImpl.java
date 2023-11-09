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

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBindingImpl<E, I extends AbsItfInterfaceType, BO extends AbsItfBindingOperation>
        extends AbstractWSDLElementImpl<E> implements AbsItfBinding<I, BO> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * the desc
     */
    protected AbsItfDescription desc;

    /**
     * the associated interface
     */
    protected I itf;

    /**
     * the list of binding operations
     */
    protected List<BO> bindingOperations = new ArrayList<BO>();
    
    public AbstractBindingImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }

    /**
     * @return the desc
     */
    public AbsItfDescription getDescription() {
        return this.desc;
    }

    public I getInterface() {
        return this.itf;
    }

    public List<BO> getBindingOperations() {
        if (this.bindingOperations == null) {
            this.bindingOperations = new ArrayList<BO>();
        }
        return this.bindingOperations;
    }

    public void addBindingOperation(final BO bo) {
        if (this.bindingOperations == null) {
            this.bindingOperations = new ArrayList<BO>();
        }
        this.bindingOperations.add(bo);
    }

    public BO getBindingOperation(final String name) {
        BO res = null;
        for (final BO bo : this.bindingOperations) {
            if (bo.getQName().getLocalPart().equals(name)) {
                res = bo;
                break;
            }
        }
        return res;
    }
}
