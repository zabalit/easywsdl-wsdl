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

import javax.xml.bind.JAXBContext;

import org.ow2.easywsdl.schema.api.SchemaException;
import org.ow2.easywsdl.schema.impl.SchemaJAXBContext;
import org.ow2.easywsdl.wsdl.api.WSDLException;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public final class WSDLJAXBContext {

    private static WSDLException fail = null;
    
    static {
    	try {
            SchemaJAXBContext.getInstance().addOtherObjectFactory(
                    org.ow2.easywsdl.wsdl.impl.wsdl11.WSDLJAXBContext.getDefaultObjectFactories());
            SchemaJAXBContext.getInstance().addOtherObjectFactory(
                    org.ow2.easywsdl.wsdl.impl.wsdl20.WSDLJAXBContext.getDefaultObjectFactories());
        } catch (final SchemaException e) {
            fail = new WSDLException(e);
        }
    }


	/**
	 * @return the jaxbContext
	 * @throws WSDLException 
	 */
	public JAXBContext getJaxbContext() throws WSDLException {
        if (fail != null) {
            throw fail;
        }
		try {
			return SchemaJAXBContext.getInstance().getJaxbContext();
		} catch (SchemaException e) {
			throw new WSDLException(e);
		}
	}
}
