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
import org.ow2.easywsdl.wsdl.api.WSDLWriter;
import org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription.WSDLVersionConstants;

/**
 * This abstract class defines a factory API that enables applications to obtain
 * a WSDLFactory capable of producing new Definitions, new WSDLReaders, and new
 * WSDLWriters.
 *
 * <p>
 * Some ideas used here have been shamelessly copied from the wonderful JAXP and
 * Xerces work.
 * </p>
 *
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class WSDLFactory {

    /**
     * Get a new instance of a WSDLFactory. This method follows (almost) the
     * same basic sequence of steps that JAXP follows to determine the
     * fully-qualified class name of the class which implements WSDLFactory.
     * <p>
     * The steps in order are:
     * <ol>
     * <li>Check the property file
     * META-INF/services/javax.wsdl.factory.WSDLFactory.</li>
     * <li>Check the javax.wsdl.factory.WSDLFactory system property.</li>
     * <li>Check the lib/wsdl.properties file in the JRE directory. The key will
     * have the same name as the above system property.</li>
     * <li>Use the default class name provided by the implementation.</li>
     * </ol>
     * <p>
     * Once an instance of a WSDLFactory is obtained, invoke newDefinition(),
     * newWSDLReader(), or newWSDLWriter(), to create the desired instances.
     */
    public static WSDLFactory newInstance() throws WSDLException {
        return new WSDLFactoryImpl();
    }

    /**
     * Create a new instance of a Definition.
     * @throws WSDLException
     */
    public abstract Description newDescription(WSDLVersionConstants version) throws WSDLException;

    /**
     * Create a new instance of a WSDLReaderImpl.
     */
    public abstract WSDLReader newWSDLReader() throws WSDLException;
    
    /**
     * Create a new instance of a WSDLReaderImpl.
     */
    public abstract WSDLReader newWSDLReader(Map<FeatureConstants, Object> features) throws WSDLException;

    /**
     * Create a new instance of a WSDLWriterImpl.
     * @throws WSDLException 
     */
    public abstract WSDLWriter newWSDLWriter() throws WSDLException;


}
