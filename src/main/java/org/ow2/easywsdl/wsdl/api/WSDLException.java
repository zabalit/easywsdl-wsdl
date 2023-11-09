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
 
package org.ow2.easywsdl.wsdl.api;

import org.ow2.easywsdl.schema.api.SchemaException;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLException extends SchemaException {
    public static final long serialVersionUID = 1;

    public static final String INVALID_WSDL = "INVALID_WSDL";

    public static final String PARSER_ERROR = "PARSER_ERROR";

    public static final String OTHER_ERROR = "OTHER_ERROR";

    public static final String CONFIGURATION_ERROR = "CONFIGURATION_ERROR";

    public static final String UNBOUND_PREFIX = "UNBOUND_PREFIX";

    public static final String NO_PREFIX_SPECIFIED = "NO_PREFIX_SPECIFIED";

    public WSDLException(final String msg, final Throwable t) {
        super(msg, t);
    }

    public WSDLException(final Throwable t) {
        super(t);
    }

    public WSDLException(final String msg) {
        super(msg);
    }

}
