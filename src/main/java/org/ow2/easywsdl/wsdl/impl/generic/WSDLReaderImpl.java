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
 
package org.ow2.easywsdl.wsdl.impl.generic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLReaderImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription.WSDLVersionConstants;
import org.ow2.easywsdl.wsdl.util.InputStreamForker;
import org.ow2.easywsdl.wsdl.util.WSDLVersionDetector;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ebmwebsourcing.easycommons.xml.SourceHelper;
import com.ebmwebsourcing.easycommons.xml.Transformers;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLReaderImpl extends AbstractWSDLReaderImpl implements
        WSDLReader {

	private final AbstractWSDLReaderImpl reader11;
	private final AbstractWSDLReaderImpl reader20;
	
	public WSDLReaderImpl() throws WSDLException {
		this.reader11 = new org.ow2.easywsdl.wsdl.impl.wsdl11.WSDLReaderImpl();
		this.reader20 = new org.ow2.easywsdl.wsdl.impl.wsdl20.WSDLReaderImpl();
	}



    /**
     * {@inheritDoc}
     * 
     */
    public Description read(final URL wsdlURL) throws WSDLException, MalformedURLException, IOException, URISyntaxException {
        InputStream is = wsdlURL.openStream();
        try {
            InputSource inputSource = new InputSource();
            inputSource.setSystemId(wsdlURL.toString());

            final WSDLVersionConstants version = WSDLVersionDetector.getVersion(inputSource);

            inputSource = new InputSource(wsdlURL.openStream());
            inputSource.setSystemId(wsdlURL.toString());

            return this.read(version, inputSource, new HashMap<URI, AbsItfDescription>(), new HashMap<URI, AbsItfSchema>());
        } catch (final MalformedURLException e) {
            throw new WSDLException("The provided well-formed URL has been detected as malformed !!", e);
        } finally {
            if (is != null) is.close();
        }
    }

	
    /**
     * {@inheritDoc}
     */
    public Description read(final InputSource inputSource, final Map<URI, AbsItfDescription> descriptionImports, final Map<URI, AbsItfSchema> schemaImports) throws WSDLException, MalformedURLException, URISyntaxException {
        
        if (inputSource.getByteStream() != null) {
        	
            final String systemId = inputSource.getSystemId();
            if (systemId != null ) {
                this.setDocumentBaseURI(new URI(systemId));
            }
            
            
            final InputStream originalInputStream = inputSource.getByteStream();
            final InputStreamForker isf = new InputStreamForker(originalInputStream);
       
            
            inputSource.setByteStream(isf.getInputStreamOne());
            
            WSDLVersionConstants version = null;
            try {
                DOMSource source = SourceHelper.toDOMSource(inputSource);
				version = WSDLVersionDetector.getVersion(((Document)source.getNode()));
            } catch (final IOException e) {
				throw new WSDLException(e);
			}
            
              
            inputSource.setByteStream(isf.getInputStreamTwo());
            
                       
            return this.read(version, inputSource, descriptionImports, schemaImports);
        }
        else {
            throw new UnsupportedOperationException("This method supports only InputSource with byte stream.");
        }

	}
    
    private Description read(final WSDLVersionConstants version, final InputSource source, final Map<URI, AbsItfDescription> descriptionImports, final Map<URI, AbsItfSchema> schemaImports) throws WSDLException, MalformedURLException, URISyntaxException {
        
        final AbstractWSDLReaderImpl reader;
        if (version == WSDLVersionConstants.WSDL11) {
            reader = reader11;
        } else if (version == WSDLVersionConstants.WSDL20) {
            reader = reader20;
        } else {
            throw new WSDLException("unknown version of wsdl");
        }

        reader.setFeatures(this.getFeatures());
        return reader.read(source, descriptionImports, schemaImports);
    }

	/**
     * {@inheritDoc} 
     */
    public Description read(final Document document) throws WSDLException, URISyntaxException {
        final WSDLVersionConstants version = WSDLVersionDetector.getVersion(document);

        // The DOM Document needs to be converted into an InputStource
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StreamResult streamResult = new StreamResult(baos);
        
        Transformer transformer = null;
        try {
            transformer = Transformers.takeTransformer();
            transformer.transform(new DOMSource(document), streamResult);
            baos.flush();
            baos.close();

            final InputSource documentInputSource = new InputSource(new ByteArrayInputStream(
                    baos.toByteArray()));
            documentInputSource.setSystemId(document.getBaseURI());

            return this.read(version, documentInputSource, null, new HashMap<URI, AbsItfSchema>());
        } catch (final TransformerException e) {
            throw new WSDLException(e);
        } catch (final IOException e) {
            throw new WSDLException(e);
        } finally {
            if (transformer != null) {
                Transformers.releaseTransformer(transformer);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Description read(InputSource inputSource) throws WSDLException, MalformedURLException, URISyntaxException {
        
        return this.read(inputSource, new HashMap<URI, AbsItfDescription>(), new HashMap<URI, AbsItfSchema>());

    }
}
