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

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfFault;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOutput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation.MEPPatternConstants;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class DecoratorOperationImpl<In extends AbsItfInput, Out extends AbsItfOutput, F extends AbsItfFault> 
extends Decorator<AbsItfOperation<In, Out, F>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorOperationImpl(final AbsItfOperation<In, Out, F> operation) throws WSDLException {
        this.internalObject = operation;
    }

    public void addFault(final F arg0) {
        this.internalObject.addFault(arg0);
    }

    public F getFault(final String arg0) {
        return this.internalObject.getFault(arg0);
    }

    public F getFaultByElementName(final QName arg0) {
        return this.internalObject.getFaultByElementName(arg0);
    }

    public List<F> getFaults() {
        return this.internalObject.getFaults();
    }

    public In getInput() {
        return this.internalObject.getInput();
    }

    public Out getOutput() {
        return this.internalObject.getOutput();
    }

    public List<String> getParameterOrdering() {
        return this.internalObject.getParameterOrdering();
    }

    public MEPPatternConstants getPattern() {
        return this.internalObject.getPattern();
    }

    public QName getQName() {
        return this.internalObject.getQName();
    }

    public String getSignature() {
        return this.internalObject.getSignature();
    }

    public F removeFault(final String arg0) {
        return this.internalObject.removeFault(arg0);
    }

    public F removeFaultByElementName(final QName arg0) {
        return this.internalObject.getFaultByElementName(arg0);
    }

    public void setInput(final In arg0) {
        this.internalObject.setInput(arg0);
    }

    public void setOutput(final Out arg0) {
        this.internalObject.setOutput(arg0);
    }

    public void setParameterOrdering(final List<String> arg0) throws WSDLException {
        this.internalObject.setParameterOrdering(arg0);
    }

    public void setPattern(final MEPPatternConstants arg0) throws WSDLException {
        this.internalObject.setPattern(arg0);
    }

    public void setQName(final QName arg0) {
        this.internalObject.setQName(arg0);
    }

	public F createFault() {
		return this.internalObject.createFault();
	}

	public In createInput() {
		return this.internalObject.createInput();
	}

	public Out createOutput() {
		return this.internalObject.createOutput();
	}

}
