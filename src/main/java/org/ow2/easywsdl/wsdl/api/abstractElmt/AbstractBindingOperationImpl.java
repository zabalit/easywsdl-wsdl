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

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingFault;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingInput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOutput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBindingOperationImpl<E, O extends AbsItfOperation, BIn extends AbsItfBindingInput, BOut extends AbsItfBindingOutput, BF extends AbsItfBindingFault>
        extends AbstractWSDLElementImpl<E> implements AbsItfBindingOperation<O, BIn, BOut, BF> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * the parent binding
     */
    protected AbsItfBinding binding;

    /**
     * the input
     */
    protected BIn input;

    /**
     * the output
     */
    protected BOut output;

    /**
     * the faults
     */
    protected List<BF> faults = new ArrayList<BF>();
    
    public AbstractBindingOperationImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }

    /**
     * @return the binding
     */
    public AbsItfBinding getBinding() {
        return this.binding;
    }

    public O getOperation() {
    	AbsItfInterfaceType interfaceType = this.binding.getInterface();
    	QName name = new QName(interfaceType.getQName().getNamespaceURI(), this.getQName().getLocalPart());
        return (O) this.binding.getInterface().getOperation(name);
    }

    /**
     * @return the input
     */
    public BIn getInput() {
        return this.input;
    }

    /**
     * @param input
     *            the input to set
     */
    public void setInput(final BIn input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public BOut getOutput() {
        return this.output;
    }

    /**
     * @param output
     *            the output to set
     */
    public void setOutput(final BOut output) {
        this.output = output;
    }

    /**
     * @return the fault
     */
    public List<BF> getFaults() {
        if (this.faults == null) {
            this.faults = new ArrayList<BF>();
        }
        return this.faults;
    }

    /**
     * @param fault
     *            the fault to set
     */
    public void setFaults(final List<BF> fault) {
        this.faults = fault;
    }

    public BF getFault(final String name) {
        BF res = null;
        for (final BF f : this.faults) {
            if (f.getName().equals(name)) {
                res = f;
                break;
            }
        }
        return res;
    }

}
