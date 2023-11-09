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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol.SOAPMEPConstants;

/**
 * This interface represents a port type binding and describes the protocol
 * required for using operations in a port type.
 * 
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfBinding<I extends AbsItfInterfaceType, BO extends AbsItfBindingOperation>
        extends WSDLElement {

    /**
     * SOAP Version Constants for the Message Exchange Patterns.
     * 
     */
    public enum BindingConstants {
        SOAP11_BINDING4WSDL11("http://schemas.xmlsoap.org/wsdl/soap/"), SOAP12_BINDING4WSDL11(
                "http://schemas.xmlsoap.org/wsdl/soap12/"), HTTP11_BINDING4WSDL11(
                "http://schemas.xmlsoap.org/wsdl/http/"), MIME_BINDING4WSDL11(
                "http://schemas.xmlsoap.org/wsdl/mime/"), SOAP_BINDING4WSDL20(
                "http://www.w3.org/ns/wsdl/soap"), HTTP_BINDING4WSDL20(
                "http://www.w3.org/ns/wsdl/http"), RPC_BINDING4WSDL20(
                "http://www.w3.org/ns/wsdl/rpc");

        /**
         * 
         */
        private final String nameSpace;

        private final URI soapURI;

        /**
         * Creates a new instance of {@link SOAPMEPConstants}
         * 
         * @param nameSpace
         */
        private BindingConstants(final String nameSpace) {
            this.nameSpace = nameSpace;
            try {
                this.soapURI = new URI(nameSpace);
            } catch (final URISyntaxException e) {
                throw new Error("Unexpected Error in URI namespace syntax", e); 
            }
        }

        /**
         * 
         * @param pattern
         * @return
         */
        public static BindingConstants valueOf(final URI pattern) {
            BindingConstants result = null;
            if (pattern != null) {
                for (final BindingConstants soap : BindingConstants.values()) {
                    if (soap.nameSpace.equals(pattern.toString())) {
                        result = soap;
                    }
                }
            }
            return result;
        }

        /**
         * 
         * @return
         */
        public URI value() {
            return this.soapURI;
        }

        /**
         * Please use this equals method instead of using :<code>
         * value().equals(mep)
         * </code> which is
         * almost 10 times slower...
         * 
         * @param mep
         * @return
         */
        public boolean equals(final URI mep) {
            return this.toString().equals(mep.toString());
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.nameSpace;
        }
    }

    /**
     * Constants for the style of Message Exchange.
     * 
     */
    public enum StyleConstant {
        DOCUMENT("document"), RPC("rpc");

        private final String value;

        /**
         * Creates a new instance of {@link StyleConstant}
         * 
         * @param value
         */
        private StyleConstant(final String value) {
            this.value = value;
        }

        /**
         * 
         * @return
         */
        public String value() {
            return this.value;
        }

        /**
         * Please use this equals method instead of using :<code>
         * value().equals(value)
         * </code> which is
         * almost 10 times slower...
         * 
         * @param v
         * @return
         */
        public boolean equals(final String v) {
            return this.toString().equals(v);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     * Set the name of this binding.
     * 
     * @param name
     *            the desired name
     */
    void setQName(QName name);

    /**
     * Get the name of this binding.
     * 
     * @return the binding name
     */
    QName getQName();

    /**
     * Set the port type this is a binding for.
     * 
     * @param interfaceType
     *            the port type associated with this binding
     */
    void setInterface(I interfaceType);

    /**
     * Get the port type this is a binding for.
     * 
     * @return the associated port type
     */
    I getInterface();
    
    /**
     * create binding operation
     * @return
     */
    BO createBindingOperation();

    /**
     * Add an operation binding to binding.
     * 
     * @param bindingOperation
     *            the operation binding to be added
     */
    void addBindingOperation(BO bindingOperation);

    /**
     * Get the specified operation binding. Note that operation names can be
     * overloaded within a PortType.
     * <p>
     * 
     * @throws IllegalArgumentException
     *             if duplicate operations are found.
     */
    BO getBindingOperation(String name);

    /**
     * Get all the operation bindings defined here.
     */
    List<BO> getBindingOperations();

    /**
     * Remove the specified operation binding. Note that operation names can be
     * overloaded within a PortType.
     * 
     * Usage of the input and output message name parameters is as described for
     * the <code>getBindingOperation</code> method.
     * 
     * @param name
     *            the name of the operation binding to be removed.
     * 
     * @throws IllegalArgumentException
     *             if duplicate operations are found.
     * 
     * @see #getBindingOperation(String)
     */
    BO removeBindingOperation(String name);

    /**
     * get the transport protocol
     */
    String getTransportProtocol();

    /**
     * set the transport protocol
     * @param transportProtocol TODO
     */
    void setTransportProtocol(String transportProtocol);

    /**
     * get style
     */
    StyleConstant getStyle();

    /**
     * get type of binding
     */
    BindingConstants getTypeOfBinding();

    /**
     * get the version of soap binding
     * 
     * @return
     */
    String getVersion();

    /*
     * Http binding attribute (for WSDL 2.0)
     */
    String getHttpDefaultMethod();

    String getHttpQueryParameterSeparatorDefault();

    boolean isHttpCookies();

    String getHttpContentEncodingDefault();
}
