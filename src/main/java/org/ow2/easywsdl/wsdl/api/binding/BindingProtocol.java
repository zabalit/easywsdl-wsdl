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
 
package org.ow2.easywsdl.wsdl.api.binding;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface BindingProtocol {

    /**
     * Constants for the Message Exchange Patterns.
     * 
     */
    public enum SOAPMEPConstants {
        ONE_WAY("http://www.w3.org/2006/08/soap/mep/one-way/"), REQUEST_RESPONSE(
                "http://www.w3.org/2003/05/soap/mep/request-response"), SOAP_RESPONSE(
                "http://www.w3.org/2003/05/soap/mep/soap-response/");

        /**
         * 
         * @param pattern
         * @return
         */
        public static SOAPMEPConstants valueOf(final URI pattern) {
            SOAPMEPConstants result = null;
            if (pattern != null) {
                for (final SOAPMEPConstants mep : SOAPMEPConstants.values()) {
                    if (mep.nameSpace.equals(pattern.toString())) {
                        result = mep;
                    }
                }
            }
            return result;
        }

        private final String nameSpace;

        private final URI mepURI;

        /**
         * Creates a new instance of {@link SOAPMEPConstants}
         * 
         * @param nameSpace
         */
        private SOAPMEPConstants(final String nameSpace) {
            this.nameSpace = nameSpace;
            try {
                this.mepURI = new URI(nameSpace);
            } catch (final URISyntaxException e) {
                throw new Error("Unexpected Error in URI namespace syntax", e); 
            }
        }

        /**
         * 
         * @return
         */
        public URI value() {
            return this.mepURI;
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

}
