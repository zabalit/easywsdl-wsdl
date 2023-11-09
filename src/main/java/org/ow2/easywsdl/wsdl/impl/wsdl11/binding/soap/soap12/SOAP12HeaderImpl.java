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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12HeaderFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeader;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeaderFault;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP12HeaderImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Header {

    private final THeader header;

    private List<SOAP12HeaderFault> headerFaults = null;

    public SOAP12HeaderImpl(final THeader header) {
        this.header = header;

        if (this.header.getHeaderfault() != null) {
            this.headerFaults = new ArrayList<SOAP12HeaderFault>();
            for (final THeaderFault hf : this.header.getHeaderfault()) {
                this.headerFaults
                        .add(new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12.SOAP12HeaderFaultImpl(
                                hf));
            }
        }
    }

    public String getEncodingStyle() {
        return this.header.getEncodingStyle();
    }

    public String getNamespace() {
        return this.header.getNamespace();
    }

    public UseConstants getUse() {
        UseConstants res = null;
        if (this.header.getUse() != null) {
            res = UseConstants.valueOf(this.header.getUse().value());
        }
        return res;
    }

    public QName getMessage() {
        return this.header.getMessage();
    }

    public String getPart() {
        return this.header.getPart();
    }

    public boolean isRequired() {
        return this.header.isRequired();
    }

    public List<SOAP12HeaderFault> getHeaderFaults() {
        return this.headerFaults;
    }
}
