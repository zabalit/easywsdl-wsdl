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
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingFault;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingInput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOutput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding.StyleConstant;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol.SOAPMEPConstants;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class DecoratorBindingOperationImpl<O extends AbsItfOperation, BIn extends AbsItfBindingInput, BOut extends AbsItfBindingOutput, BF extends AbsItfBindingFault> 
extends Decorator<AbsItfBindingOperation<O, BIn, BOut, BF>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorBindingOperationImpl(final AbsItfBindingOperation<O, BIn, BOut, BF> bindingOperation)
            throws WSDLException {
        this.internalObject = bindingOperation;
    }

    public void addFault(final BF arg0) {
        this.internalObject.addFault(arg0);
    }

    public BF getFault(final String arg0) {
        return this.internalObject.getFault(arg0);
    }

    public List<BF> getFaults() {
        return this.internalObject.getFaults();
    }

    public String getHttpContentEncodingDefault() {
        return this.internalObject.getHttpContentEncodingDefault();
    }

    public String getHttpFaultSerialization() {
        return this.internalObject.getHttpFaultSerialization();
    }

    public String getHttpInputSerialization() {
        return this.internalObject.getHttpInputSerialization();
    }

    public String getHttpLocation() {
        return this.internalObject.getHttpLocation();
    }

    public String getHttpMethod() {
        return this.internalObject.getHttpMethod();
    }

    public String getHttpOutputSerialization() {
        return this.internalObject.getHttpOutputSerialization();
    }

    public String getHttpQueryParameterSeparator() {
        return this.internalObject.getHttpQueryParameterSeparator();
    }

    public BIn getInput() {
        return this.internalObject.getInput();
    }

    public SOAPMEPConstants getMEP() {
        return this.internalObject.getMEP();
    }

    public O getOperation() {
        return this.internalObject.getOperation();
    }

    public BOut getOutput() {
        return this.internalObject.getOutput();
    }

    public QName getQName() {
        return this.internalObject.getQName();
    }

    public String getSoapAction() {
        return this.internalObject.getSoapAction();
    }

    public StyleConstant getStyle() {
        return this.internalObject.getStyle();
    }

    public boolean isHttpIgnoreUncited() {
        return this.internalObject.isHttpIgnoreUncited();
    }

    public BF removeFault(final String arg0) {
        return this.internalObject.removeFault(arg0);
    }

    public void setInput(final BIn arg0) {
        this.internalObject.setInput(arg0);
    }

    public void setMEP(final SOAPMEPConstants arg0) {
        this.internalObject.setMEP(arg0);
    }

    public void setOutput(final BOut arg0) {
        this.internalObject.setOutput(arg0);
    }

    public void setQName(final QName arg0) {
        this.internalObject.setQName(arg0);
    }

 	public BF createFault() {
		return this.internalObject.createFault();
	}

	public BIn createInput() {
		return this.internalObject.createInput();
	}

	public BOut createOutput() {
		return this.internalObject.createOutput();
	}

	public void setSoapAction(String action) {
		this.internalObject.setSoapAction(action);
	}

}
