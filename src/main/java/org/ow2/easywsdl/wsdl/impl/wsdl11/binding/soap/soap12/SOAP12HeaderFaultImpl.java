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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeaderFault;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP12HeaderFaultImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12HeaderFault {

    private final THeaderFault headerFault;

    public SOAP12HeaderFaultImpl(final THeaderFault headerFault) {
        this.headerFault = headerFault;
    }

    public String getEncodingStyle() {
        return this.headerFault.getEncodingStyle();
    }

    public String getNamespace() {
        return this.headerFault.getNamespace();
    }

    public UseConstants getUse() {
        UseConstants res = null;
        if (this.headerFault.getUse() != null) {
            res = UseConstants.valueOf(this.headerFault.getUse().value());
        }
        return res;
    }

    public QName getMessage() {
        return this.headerFault.getMessage();
    }

    public String getPart() {
        return this.headerFault.getPart();
    }
}
