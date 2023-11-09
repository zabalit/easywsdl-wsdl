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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11HeaderFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeaderFault;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP11HeaderImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Header {

    private final THeader header;

    private List<SOAP11HeaderFault> headerFaults = null;

    public SOAP11HeaderImpl(final THeader header) {
        this.header = header;

        if (this.header.getHeaderfault() != null) {
            this.headerFaults = new ArrayList<SOAP11HeaderFault>();
            for (final THeaderFault hf : this.header.getHeaderfault()) {
                this.headerFaults
                        .add(new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11HeaderFaultImpl(
                                hf));
            }
        }
    }

    public List<String> getEncodingStyles() {
        List<String> res = null;
        if ((this.header.getEncodingStyle() != null)
                && (this.header.getEncodingStyle().size() > 0)) {
            res = new ArrayList<String>();
            for (final String item : this.header.getEncodingStyle()) {
                res.add(item);
            }
        }
        return res;
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

    public List<SOAP11HeaderFault> getHeaderFaults() {
        return this.headerFaults;
    }

    public THeader getModel() {
    	return this.header;
    }
}
