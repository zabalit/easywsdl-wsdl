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
 
package org.ow2.easywsdl.wsdl.api.abstractItf;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding.StyleConstant;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol.SOAPMEPConstants;

/**
 * This interface represents a WSDL operation binding. 
 * <p>
 * It holds information about would be specified in the operation 
 * element contained within a binding element.
 * </p>
 * 
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfBindingOperation<O extends AbsItfOperation, BIn extends AbsItfBindingInput, BOut extends AbsItfBindingOutput, BF extends AbsItfBindingFault>
        extends WSDLElement {

    /**
     * Set the name of this operation binding.
     * 
     * @param name
     *            the desired name
     */
    void setQName(QName name);

    /**
     * Get the name of this operation binding.
     * 
     * @return the operation binding name
     */
    QName getQName();

    /**
     * Get the operation that this operation binding binds.
     * 
     * @return the operation that this operation binding binds
     */
    O getOperation();
    
    /**
     * create input
     */
    BIn createInput();

    /**
     * Set the input message specification for this operation.
     * 
     * @param input
     *            the new input message
     */
    void setInput(BIn input);

    /**
     * Get the input message specification for this operation.
     * 
     * @return the input message
     */
    BIn getInput();
    
    /**
     * create output
     */
    BOut createOutput();

    /**
     * Set the output message specification for this operation.
     * 
     * @param output
     *            the new output message
     */
    void setOutput(BOut output);

    /**
     * Get the output message specification for this operation.
     * 
     * @return the output message specification for the operation
     */
    BOut getOutput();
    
    /**
     * create fault
     */
    BF createFault();

    /**
     * Add a fault binding.
     * 
     * @param absItfBindingFault
     *            the new fault binding
     */
    void addFault(BF absItfBindingFault);

    /**
     * Remove a fault binding.
     * 
     * @param name
     *            the name of the fault binding to be removed
     * @return the BindingFaultImpl which was removed
     */
    BF removeFault(String name);

    /**
     * Get the specified fault binding.
     * 
     * @param name
     *            the name of the desired fault binding.
     * @return the corresponding fault binding, or null if there wasn't any
     *         matching fault binding
     */
    BF getFault(String name);

    /**
     * Get all the fault bindings associated with this operation binding.
     * 
     * @return names of fault bindings
     */
    List<BF> getFaults();

    /**
     * get the mep
     */
    SOAPMEPConstants getMEP();

    /**
     * set the mep
     * 
     * @param mep
     *            the mep
     */
    void setMEP(SOAPMEPConstants mep);

    /**
     * get style
     */
    StyleConstant getStyle();

    /**
     * get the soap action
     * 
     * @return
     */
    String getSoapAction();
    
    /**
     * set soap action
     */
    void setSoapAction(String action);

    /*
     * http attribute for WSDL 2.0
     */

    /**
     * get the http location
     * 
     * @return
     */
    String getHttpLocation();

    String getHttpMethod();

    String getHttpInputSerialization();

    String getHttpOutputSerialization();

    String getHttpFaultSerialization();

    String getHttpQueryParameterSeparator();

    String getHttpContentEncodingDefault();

    boolean isHttpIgnoreUncited();

}
