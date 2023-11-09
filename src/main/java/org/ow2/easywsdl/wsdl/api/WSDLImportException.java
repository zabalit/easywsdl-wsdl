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

/**
 * This exception is thrown when an imported (or included) document cannot be parsed correctly.
 * @author Olivier FABRE - EBM WebSourcing
 */
public class WSDLImportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -903055334560628810L;

	public WSDLImportException(final String msg, final Throwable t) {
		super(msg, t);
	}

	public WSDLImportException(final Throwable t) {
		super(t);
	}

	public WSDLImportException(final String msg) {
		super(msg);
	}

}
