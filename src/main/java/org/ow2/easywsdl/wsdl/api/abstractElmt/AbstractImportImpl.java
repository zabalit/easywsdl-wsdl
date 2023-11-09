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

import java.net.URI;
import java.util.Map;

import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLImportException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfImport;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractImportImpl<E, D extends AbsItfDescription>
		extends AbstractIncludeImpl<E, D> implements AbsItfImport<D> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * 
	 * @param model
	 *            the model
	 * @param parent
	 *            the parent description
	 * @throws WSDLException 
	 * @throws WSDLImportException 
	 */
	public AbstractImportImpl(final E model, final D parent,
			Map<URI, AbsItfDescription> descripionImports, Map<URI, AbsItfSchema> schemaImports, final URI baseURI, AbstractWSDLReaderImpl reader) throws WSDLException, WSDLImportException {
		super(model, parent, descripionImports, schemaImports, baseURI, reader);
	}

}
