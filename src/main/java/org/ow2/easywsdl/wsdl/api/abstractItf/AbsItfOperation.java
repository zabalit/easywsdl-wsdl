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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;

/**
 * This interface represents a WSDL operation. It includes information on input,
 * output and fault messages associated with usage of the operation.
 * 
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface AbsItfOperation<In extends AbsItfInput, Out extends AbsItfOutput, F extends AbsItfFault>
        extends WSDLElement {

    /**
     * Constants for the Patterns.
     * 
     */
    public enum MEPPatternConstants {

        IN_ONLY(URI.create("http://www.w3.org/ns/wsdl/in-only")),
        ROBUST_IN_ONLY(URI.create("http://www.w3.org/ns/wsdl/robust-in-only")),
        IN_OUT(URI.create("http://www.w3.org/ns/wsdl/in-out")),
        IN_OPTIONAL_OUT(URI.create("http://www.w3.org/ns/wsdl/in-opt-out")),
        OUT_ONLY(URI.create("http://www.w3.org/ns/wsdl/out-only")),
        ROBUST_OUT_ONLY(URI.create("http://www.w3.org/ns/wsdl/robust-out-only")),
        OUT_IN(URI.create("http://www.w3.org/ns/wsdl/out-in")),
        OUT_OPTIONAL_IN(URI.create("http://www.w3.org/ns/wsdl/out-opt-in"));

        protected static final Map<String, MEPPatternConstants> URI_MAP = new HashMap<String, MEPPatternConstants>();
        
        static {
            for (final MEPPatternConstants p : MEPPatternConstants.values()) {
                final String uri = p.patternString;
                URI_MAP.put(uri, p);
                final String name = uri.substring(uri.lastIndexOf('/') + 1);
                // from http://www.w3.org/TR/2004/WD-wsdl20-extensions-20040803/ sometimes used
                URI_MAP.put("http://www.w3.org/2004/08/wsdl/" + name, p);
                // from http://www.w3.org/TR/2006/CR-wsdl20-adjuncts-20060327/ sometimes used
                URI_MAP.put("http://www.w3.org/2006/01/wsdl/" + name, p);
            }
        }

        private final String patternString;

        private final URI patternURI;

        private MEPPatternConstants(final URI uri) {
            this.patternURI = uri;
            this.patternString = uri.toString();
        }

        public static MEPPatternConstants fromURI(final URI pattern) {
            MEPPatternConstants result = null;
            if (pattern != null) {
                result = URI_MAP.get(pattern.toString());
            }
            return result;
        }

        public static MEPPatternConstants fromString(final String pattern) {
            return URI_MAP.get(pattern);
        }

        public URI value() {
            return this.patternURI;
        }

        /**
         * Compare the enum to an URIs (matches also with deprecated wsdl URIs!)
         */
        public boolean equals(final URI pattern) {
            return pattern != null && this.equals(fromURI(pattern));
        }

        /**
         * Compare the enum to a string version of MEP URIs (matches also with deprecated wsdl URIs!)
         */
        public boolean equalsString(final String pattern) {
            return pattern != null && this.equals(fromString(pattern));
        }

        @Override
        public String toString() {
            return this.patternString;
        }
    }

    /**
     * Set the name of this operation.
     * 
     * @param name
     *            the desired name
     */
    void setQName(QName name);

    /**
     * Get the name of this operation.
     * 
     * @return the operation name
     */
    QName getQName();

    /**
     * Set the input message specification for this operation.
     * 
     * @param input
     *            the new input message
     */
    void setInput(In input);

    /**
     * Get the input message specification for this operation.
     * 
     * @return the input message
     */
    In getInput();

    /**
     * Set the output message specification for this operation.
     * 
     * @param output
     *            the new output message
     */
    void setOutput(Out output);

    /**
     * Get the output message specification for this operation.
     * 
     * @return the output message specification for the operation
     */
    Out getOutput();

    /**
     * Add a fault message that must be associated with this operation.
     * 
     * @param fault
     *            the new fault message
     */
    void addFault(F fault);

    /**
     * Get the specified fault .
     * 
     * @param name
     *            the name of the desired fault message.
     * @return the corresponding fault message, or null if there wasn't any
     *         matching message
     */
    F getFault(String name);

    /**
     * Get the specified fault .
     * 
     * @param name
     *            the name of the fault element.
     * @return the corresponding fault message, or null if there wasn't any
     *         matching message
     */
    F getFaultByElementName(QName name);

    /**
     * Remove the specified fault message.
     * 
     * @param name
     *            the name of the fault message to be removed.
     * @return the fault message which was removed
     */
    F removeFault(String name);

    /**
     * Remove the specified fault .
     * 
     * @param name
     *            the name of the fault element to be removed.
     * @return the fault message which was removed
     */
    F removeFaultByElementName(QName name);

    /**
     * Get all the fault messages associated with this operation.
     * 
     * @return names of fault messages
     */
    List<F> getFaults();

    /**
     * Set the parameter ordering for a request-response, or solicit-response
     * operation.
     * 
     * @param parameterOrder
     *            a list of named parameters containing the part names to
     *            reflect the desired order of parameters for RPC-style
     *            operations
     * @throws WSDLException
     */
    void setParameterOrdering(List<String> parameterOrder) throws WSDLException;

    /**
     * Get the parameter ordering for this operation.
     * 
     * @return the parameter ordering, a list consisting of message part names
     */
    List<String> getParameterOrdering();

    /**
     * get the pattern
     */
    MEPPatternConstants getPattern();

    /**
     * set the pattern
     * 
     * @param pattern
     *            the pattern
     * @throws WSDLException
     */
    void setPattern(MEPPatternConstants pattern) throws WSDLException;

    /**
     * get signature of the operation
     * 
     * @return the signature
     */
    String getSignature();

    In createInput();

    Out createOutput();

    F createFault();
}
