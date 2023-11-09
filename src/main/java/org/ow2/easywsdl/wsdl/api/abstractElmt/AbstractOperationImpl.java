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

import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfFault;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInput;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOutput;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractOperationImpl<E, In extends AbsItfInput, Out extends AbsItfOutput, F extends AbsItfFault>
        extends AbstractWSDLElementImpl<E> implements AbsItfOperation<In, Out, F> {



    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * the parent interface
     */
    @SuppressWarnings("unchecked")
    protected AbsItfInterfaceType itf;

    /**
     * the input
     */
    protected In input;

    /**
     * the output
     */
    protected Out output;

    /**
     * the faults
     */
    protected List<F> faults = new ArrayList<F>();

    public AbstractOperationImpl(E model, AbstractWSDLElementImpl parent) {
        super(model, parent);
    }
    
    /**
     * @return the interface
     */
    @SuppressWarnings("unchecked")
    public AbsItfInterfaceType getInterface() {
        return this.itf;
    }

    /**
     * @return the input
     */
    public In getInput() {
        return this.input;
    }

    /**
     * @param input
     *            the input to set
     */
    public void setInput(final In input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public Out getOutput() {
        return this.output;
    }

    /**
     * @param output
     *            the output to set
     */
    public void setOutput(final Out output) {
        this.output = output;
    }

    /**
     * @return the fault
     */
    public List<F> getFaults() {
        if (this.faults == null) {
            this.faults = new ArrayList<F>();
        }
        return this.faults;
    }

    /**
     * @param fault
     *            the fault to set
     */
    public void setFaults(final List<F> fault) {
        this.faults = fault;
    }

    public F getFault(final String name) {
        F res = null;
        for (final F f : this.faults) {
            if (f.getMessageName().getLocalPart().equals(name)) {
                res = f;
                break;
            }
        }
        return res;
    }

    public String getSignature() {
        String res = null;
        
        // get the output
        String returnS = "void";
        if (this.getOutput() != null) {
            if (this.getOutput().getElement() != null) {
                if (this.getOutput().getElement().getType().getQName() != null) {
                    returnS = this.getOutput().getElement().getType().getQName().toString();
                } else {
                    returnS = this.getOutput().getElement().getQName().toString();
                }
            } else if (this.getOutput().getParts() != null) {
                if (this.getOutput().getParts().size() == 1) {
                    if (this.getOutput().getParts().get(0).getElement() != null) {
                        if (this.getOutput().getParts().get(0).getElement().getType().getQName() != null) {
                            returnS = this.getOutput().getParts().get(0).getElement().getType()
                                    .getQName().toString();
                        } else {
                            returnS = this.getOutput().getParts().get(0).getElement().getQName()
                                    .toString();
                        }
                    } else if (this.getOutput().getParts().get(0).getType() != null) {
                        returnS = this.getOutput().getParts().get(0).getType().getQName()
                                .toString();
                    }
                } else {
                    returnS = "{";
                    final StringBuilder buf = new StringBuilder();

                    for (final Part part : this.getOutput().getParts()) {
                        if (part.getElement() != null) {
                            if (part.getElement().getType().getQName() != null) {
                                buf.append(part.getElement().getType().getQName().toString() + " ;");
                            } else {
                            	buf.append(part.getType().getQName().toString() + " ;");
                            }
                        } else if (part.getType() != null) {
                        	buf.append(part.getType().getQName().toString() + " ;");
                        }
                    }
                    returnS = buf.toString() + "}";
                    returnS = returnS.replace(";}", "}");
                }
            }
        }

        // get the operation name
        final String operation = this.getQName().toString();

        // get the input parameters
        String params = "(";
        if (this.getInput() != null) {
            if (this.getInput().getElement() != null) {
                if (this.getInput().getElement().getType().getQName() != null) {
                    params = params + this.getInput().getElement().getType().getQName().toString()
                            + " " + this.getInput().getElement().getQName().toString();
                } else {
                    params = params + this.getInput().getElement().getQName().toString();
                }
            } else if (this.getInput().getParts() != null) {
                for (final Part part : this.getInput().getParts()) {
                    if (part.getElement() != null) {
                        if (part.getElement().getType().getQName() != null) {
                            params = params + part.getElement().getType().getQName().toString()
                                    + " " + part.getElement().getQName().toString() + " ,";
                        } else {
                            params = params + part.getElement().getQName().toString() + " ,";
                        }
                    } else if (part.getType() != null) {
                        params = params + part.getType().getQName().toString() + " "
                                + part.getPartQName() + " ,";
                    }
                }
            }
        }
        params = params + ")";
        params = params.replace(",)", ")");

        // create signature
        res = returnS + " " + operation + params;
        return res;
    }
}
