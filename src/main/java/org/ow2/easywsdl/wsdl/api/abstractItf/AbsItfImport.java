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

/**
 * This interface represents an import, and may contain a reference to the imported definition.
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfImport<D extends AbsItfDescription> extends AbsItfInclude<D> {

    /**
     * Set the namespace URI of this import.
     * 
     * @param namespaceURI
     *            the desired namespace URI
     */
    void setNamespaceURI(String namespaceURI);

    /**
     * Get the namespace URI of this import.
     */
    String getNamespaceURI();

}
