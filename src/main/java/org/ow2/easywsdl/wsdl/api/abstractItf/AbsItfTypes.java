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

import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.WSDLElement;

/**
 * This interface represents the &lt;types&gt; section of a WSDL document.
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfTypes<S extends AbsItfSchema, Impt extends org.ow2.easywsdl.schema.api.absItf.AbsItfImport>
        extends WSDLElement {

    /**
     * get all schemas
     * 
     * @return
     */
    List<S> getSchemas();

    /**
     * get all imported schemas
     * 
     * @return
     */
    List<Impt> getImportedSchemas();
    
    /**
     * create the schema
     * @return
     */
    S createSchema();
    
    /**
     * 
     */
    void addSchema(S schema);
    
    /**
     * 
     */
    S removeSchema();
}
