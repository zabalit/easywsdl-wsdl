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
 
package org.ow2.easywsdl.wsdl;

import java.util.Map;

import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants;
import org.ow2.easywsdl.wsdl.api.WSDLWriter;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLReaderImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription.WSDLVersionConstants;

/**
 * This class is a concrete implementation of the abstract class WSDLFactory.
 * <p>
 * Some ideas used here have been shamelessly copied from the wonderful JAXP and
 * Xerces work.
 * </p>
 *
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLFactoryImpl extends WSDLFactory {


    /**
     * Create a new instance of a Definition, with an instance of a
     * PopulatedExtensionRegistry as its ExtensionRegistry.
     * @throws WSDLException
     */
    @Override
    public Description newDescription(WSDLVersionConstants version) throws WSDLException {
    	Description desc = null;
    	if(version.equals(WSDLVersionConstants.WSDL11)) {
    		desc = new org.ow2.easywsdl.wsdl.impl.wsdl11.DescriptionImpl();
    	} else if(version.equals(WSDLVersionConstants.WSDL20)) {
    		desc = new org.ow2.easywsdl.wsdl.impl.wsdl20.DescriptionImpl();
    	} 
        return desc;
    }

    /**
     * Create a new instance of a WSDLReaderImpl.
     * @throws WSDLException 
     */
    @Override
    public WSDLReader newWSDLReader() throws WSDLException {
        return new org.ow2.easywsdl.wsdl.impl.generic.WSDLReaderImpl();
    }

    /**
     * Create a new instance of a WSDLReaderImpl.
     * @throws WSDLException 
     */
    @Override
    public WSDLReader newWSDLReader(final Map<FeatureConstants, Object> features) throws WSDLException {
        final WSDLReader reader = this.newWSDLReader();
        ((AbstractWSDLReaderImpl) reader).getFeatures().putAll(features);
        return reader;

    }

    /**
     * Create a new instance of a WSDLWriterImpl.
     * @throws WSDLException 
     */
    @Override
    public WSDLWriter newWSDLWriter() throws WSDLException {
        return new org.ow2.easywsdl.wsdl.impl.generic.WSDLWriterImpl();
    }


}
