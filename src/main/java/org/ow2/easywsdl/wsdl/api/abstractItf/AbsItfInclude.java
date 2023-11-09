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

import org.ow2.easywsdl.wsdl.api.WSDLElement;

/**
 * This interface represents an include, and may contain a reference to the included definition.
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfInclude<D extends AbsItfDescription> extends WSDLElement {

    /**
     * Set the location URI of this import.
     * 
     * @param locationURI
     *            the desired location URI
     */
    void setLocationURI(URI locationURI);

    /**
     * Get the location URI of this import.
     * 
     * @return the location {@link URI} of this import or
     *         <code>null</code> if undefined.
     */
    URI getLocationURI();
    
    /**
     * This property can be used to hang a referenced Definition, and the
     * top-level Definition (i.e. the one with the &lt;import&gt;) will use this
     * Definition when resolving referenced WSDL parts. This would need to be
     * made into a generic reference to handle other types of referenced
     * documents.
     */
    void setDescription(D description);

    /**
     * This property can be used to hang a referenced Definition, and the
     * top-level Definition (i.e. the one with the &lt;import&gt;) will use this
     * Definition when resolving referenced WSDL parts. This would need to be
     * made into a generic reference to handle other types of referenced
     * documents.
     */
    D getDescription();
}
