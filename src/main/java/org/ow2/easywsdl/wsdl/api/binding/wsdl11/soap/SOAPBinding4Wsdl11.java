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
 
package org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap;

import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public interface SOAPBinding4Wsdl11 extends BindingProtocol {

    /**
     * Constants for the Message Exchange Patterns.
     * 
     */
    public enum UseConstants {
        LITERAL("literal"), ENCODED("encoded");

        private final String value;

        /**
         * Creates a new instance of {@link UseConstants}
         * 
         * @param value
         */
        private UseConstants(final String value) {
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
         * @param value
         * @return
         */
        public boolean equals(final String value) {
            return this.toString().equals(value);
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
}
