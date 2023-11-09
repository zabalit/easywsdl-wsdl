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
 
package org.ow2.easywsdl.wsdl.test.util;

import java.net.URI;

import org.apache.woden.WSDLSource;
import org.xml.sax.InputSource;

/**
 * This abstract class implements methods of the WSDLSource interface that are
 * common across all concrete implementations. 
 * <p>
 * The only method on this interface
 * that is specific to each concrete implementation is the
 * <code>setSource</code> method and this method is declared abstract on this
 * class.
 * </p>
 * 
 * @author John Kaputin (jkaputin@apache.org)
 */
public class BaseWSDLSource implements WSDLSource {

    private URI fBaseURI = null;

    protected Object fSource = null;

    public BaseWSDLSource(final InputSource inputSource, final URI wsdlURI) {
        this.fBaseURI = wsdlURI;
        this.fSource = inputSource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.WSDLSource#setSource(java.lang.Object)
     */
    public void setSource(final Object wsdlSource) {
        this.fSource = wsdlSource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.WSDLSource#getSource()
     */
    public Object getSource() {
        return this.fSource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.WSDLSource#setBaseURI(java.net.URI)
     */
    public void setBaseURI(final URI baseURI) {
        this.fBaseURI = baseURI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.WSDLSource#getBaseURI()
     */
    public URI getBaseURI() {
        return this.fBaseURI;
    }

}
