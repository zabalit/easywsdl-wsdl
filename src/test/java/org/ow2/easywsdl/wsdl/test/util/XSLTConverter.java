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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

/**
 * @author Olivier Fabre - EBM WebSourcing
 */
public class XSLTConverter {

    private static final String CONVERTER_XSD = "/wsdl11to20.xsl";
    
    private final static XSLTConverter converter = new XSLTConverter();

    private Templates wsdl11to20;

    private XSLTConverter() {
        super();
    }

    static {
        XSLTConverter.converter.wsdl11to20 = XSLTConverter.generateTemplates();
    }
    
    public synchronized static XSLTConverter getInstance() {
        return XSLTConverter.converter;
    }

    public InputSource convert(final URL wsdlURL) {

        InputSource result = null;

        InputStream inputStream = null;
        try {
            inputStream = wsdlURL.openStream();
        } catch (final Exception e) {
            throw new RuntimeException("Can't read given WSDL URI",e);
        }
        
        final ByteArrayOutputStream outputStream = this.transform(new StreamSource(inputStream));

        final ByteArrayInputStream byteStream = new ByteArrayInputStream(outputStream.toByteArray());

        result = new InputSource(byteStream);

        return result;
    }

    private ByteArrayOutputStream transform(final Source xml) {
        try {
            final Transformer transformer = this.wsdl11to20.newTransformer();
            // TODO : Add possibility to use uri resolver if necessary
            // transformer.setURIResolver(uriResolver);
            final ByteArrayOutputStream outputStream = this.transformSource(xml, transformer);
            return outputStream;
        } catch (final TransformerConfigurationException e) {
            throw new RuntimeException("Can't create XSL transformer",e);
        } catch (final TransformerException e) {
            throw new RuntimeException(
                    "Can't transform XML content with the configured xsl sheet",e);
        }
    }

    /**
     * 
     * @param xml
     * @param transformer
     * @param pipedInputStream
     * @return
     * @throws TransformerException
     */
    private ByteArrayOutputStream transformSource(final Source xml, final Transformer transformer)
            throws TransformerException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Result streamResult = new StreamResult(outputStream);
        transformer.transform(xml, streamResult);
        return outputStream;
    }

    private static Templates generateTemplates() {
        InputStream xslStream;
        // try to get the XSL from the filesystem
        xslStream = XSLTConverter.class.getResourceAsStream(CONVERTER_XSD);

        try {
            return XSLTConverter.getTemplates(xslStream);
        } catch (final TransformerConfigurationException e) {
            throw new RuntimeException("Failed to build XSL templates",e);
        }
    }

    /**
     * Retrieve the Templates associated to this XSL
     * 
     * @param xslResourceInputStream
     * @return
     * @throws TransformerConfigurationException
     *             Failed to create the XSL template
     */
    private static Templates getTemplates(final InputStream xslResourceInputStream)
            throws TransformerConfigurationException {
        if (xslResourceInputStream == null) {
            return null;
        }
        final Source source = new StreamSource(xslResourceInputStream);
        // The WSDL 1.1 to 2.0 converter is based on a XSL 2.0, so Saxon is required instead of the JVM default XSL
        // transformer.
        final TransformerFactory transformerFactory = new net.sf.saxon.TransformerFactoryImpl();
        return transformerFactory.newTemplates(source);
    }
}
