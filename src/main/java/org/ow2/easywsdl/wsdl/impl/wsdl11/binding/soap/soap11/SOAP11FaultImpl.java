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

import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.UseChoice;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP11FaultImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Fault {

    private final TFault fault;

    public SOAP11FaultImpl(final TFault fault) {
        this.fault = fault;
    }

    public List<String> getEncodingStyles() {
        List<String> res = null;
        if ((this.fault.getEncodingStyle() != null)
                && (this.fault.getEncodingStyle().size() > 0)) {
            res = new ArrayList<String>();
            for (final String item : this.fault.getEncodingStyle()) {
                res.add(item);
            }
        }
        return res;
    }

    public String getName() {
        return this.fault.getName();
    }
    
    public void setName(final String name) {
    	this.fault.setName(name);
    }

    public String getNamespace() {
        return this.fault.getNamespace();
    }

    public UseConstants getUse() {
        UseConstants res = null;
        if (this.fault.getUse() != null) {
            if (this.fault.getUse().value().equals(UseConstants.ENCODED.value())) {
                res = UseConstants.ENCODED;
            } else if (this.fault.getUse().value().equals(UseConstants.LITERAL.value())) {
                res = UseConstants.LITERAL;
            }
        }
        return res;
    }

    public List<String> getParts() {
        List<String> res = null;
        if ((this.fault.getParts() != null) && (this.fault.getParts().size() > 0)) {
            res = new ArrayList<String>();
            for (final String item : this.fault.getParts()) {
                res.add(item);
            }
        }
        return res;
    }

    public Boolean getRequired() {
        return this.fault.isRequired();
    }

	public void setUse(UseConstants use) {
		if (use.value().equals(UseChoice.ENCODED.value())) {
            this.fault.setUse(UseChoice.ENCODED);
        } else if (use.equals(UseChoice.LITERAL.value())) {
        	this.fault.setUse(UseChoice.LITERAL);
        }
	}

	public TFault getModel() {
		return this.fault;
	}
}
