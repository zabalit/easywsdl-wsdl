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

import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.UseChoice;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP11BodyImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Body {

    private final TBody body;

    public SOAP11BodyImpl(final TBody body, AbstractWSDLElementImpl parent) {
        this.body = body;
    }

    public List<String> getEncodingStyles() {
        List<String> res = null;
        if ((this.body.getEncodingStyle() != null)
                && (this.body.getEncodingStyle().size() > 0)) {
            res = new ArrayList<String>();
            for (final String item : this.body.getEncodingStyle()) {
                res.add(item);
            }
        }
        return res;
    }

    public String getNamespace() {
        return this.body.getNamespace();
    }

    public UseConstants getUse() {
        UseConstants res = null;
        if (this.body.getUse() != null) {
            if (this.body.getUse().value().equals(UseConstants.ENCODED.value())) {
                res = UseConstants.ENCODED;
            } else if (this.body.getUse().value().equals(UseConstants.LITERAL.value())) {
                res = UseConstants.LITERAL;
            }
        }
        return res;
    }

    public List<String> getParts() {
        List<String> res = null;
        if ((this.body.getParts() != null) && (this.body.getParts().size() > 0)) {
            res = new ArrayList<String>();
            for (final String item : this.body.getParts()) {
                res.add(item);
            }
        }
        return res;
    }

    public Boolean getRequired() {
        return this.body.isRequired();
    }

    public TBody getModel() {
    	return this.body;
    }

	public void setUse(UseConstants use) {
		if (use.value().equals(UseChoice.ENCODED.value())) {
            this.body.setUse(UseChoice.ENCODED);
        } else if (use.equals(UseChoice.LITERAL.value())) {
        	this.body.setUse(UseChoice.LITERAL);
        }
	}
}
