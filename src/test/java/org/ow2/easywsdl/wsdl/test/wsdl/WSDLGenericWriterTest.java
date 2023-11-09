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
 
package org.ow2.easywsdl.wsdl.test.wsdl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Assert;
import junit.framework.TestCase;

import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.WSDLWriter;
import org.w3c.dom.Document;

/**
 * WSDL Writer test cases.
 * @author Christophe DENEUX - Capgemini Sud
 */
public class WSDLGenericWriterTest extends TestCase {

    /**
     * The generic WSDL reader used to read WSDLs.
     */
    WSDLFactory wsdlFactory = null;
    WSDLReader genericReader = null;
    WSDLWriter genericWriter = null; 
    
    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.wsdlFactory = WSDLFactory.newInstance();
        this.genericReader = this.wsdlFactory.newWSDLReader();
        this.genericWriter = this.wsdlFactory.newWSDLWriter();
    }

    /**
     * Test the generic WSDL writer to write a DOM document containing space in
     * its document URI.
     */
    public void testWriterWSDLGeneric_DocumentBaseURIContainingSpaces()
            throws WSDLException, IOException, URISyntaxException {

        final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/with space in name.wsdl");
        final Description descFromURL = this.genericReader.read(wsdlURL);

        Assert.assertFalse("The document base URI contains unencoded spaces",
                descFromURL.getDocumentBaseURI().toString().contains(" "));

        final Document doc = this.genericWriter.getDocument(descFromURL);
        
        Assert.assertFalse(
                "The DOM document base URI contains unencoded spaces", doc
                        .getDocumentURI().contains(" "));

        final Description descFromDoc = this.genericReader.read(doc);

        Assert.assertNotNull(descFromDoc);
        Assert.assertFalse("The document base URI contains unencoded spaces",
                descFromDoc.getDocumentBaseURI().toString().contains(" "));

    }
}
