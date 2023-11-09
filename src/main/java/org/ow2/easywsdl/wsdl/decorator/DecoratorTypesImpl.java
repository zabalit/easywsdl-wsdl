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

import org.ow2.easywsdl.schema.api.absItf.AbsItfImport;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfTypes;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class DecoratorTypesImpl<S extends AbsItfSchema, Impt extends AbsItfImport> extends Decorator<AbsItfTypes<S, Impt>> {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public DecoratorTypesImpl(final AbsItfTypes<S, Impt> types) throws WSDLException {
        this.internalObject = types;
    }

    public List<S> getSchemas() {
        return this.internalObject.getSchemas();
    }

    public List<Impt> getImportedSchemas() {
        return this.internalObject.getImportedSchemas();
    }

	public void addSchema(S schema) {
		this.internalObject.addSchema(schema);
	}

	public S createSchema() {
		return this.internalObject.createSchema();
	}

	public S removeSchema() {
		return this.internalObject.removeSchema();
	}
}
