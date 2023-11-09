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

import javax.xml.bind.JAXBElement;

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Body;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Fault;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Header;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperationFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperationMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class SOAP11BindingImpl implements
        org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11 {

    SOAP11Body soap11body = null;

    List<SOAP11Header> soap11headers = new ArrayList<SOAP11Header>();

    SOAP11Fault soap11fault = null;

    AbstractWSDLElementImpl parent = null;

    public SOAP11BindingImpl(final List<THeader> headers, final TBody body, final TFault fault, AbstractWSDLElementImpl parent) {
        this.parent = parent;
    	if (headers != null) {
            for (final THeader h : headers) {
                this.soap11headers
                        .add(new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11HeaderImpl(
                                h));
            }
        }
        if (body != null) {
            this.soap11body = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11BodyImpl(
                    body, parent);
        }
        if (fault != null) {
            this.soap11fault = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11FaultImpl(
                    fault);
        }
    }

    public List<SOAP11Header> getHeaders() {
        return this.soap11headers;
    }

    public SOAP11Body getBody() {
        return this.soap11body;
    }

    public SOAP11Fault getFault() {
        return this.soap11fault;
    }

	public void setBody(SOAP11Body body) throws WSDLException {
		this.soap11body = body;

		List<Object> items = null;
		if(parent.getModel() instanceof TBindingOperationMessage) {
			items = (List) ((TBindingOperationMessage)parent.getModel()).getAny();
		} else if(parent.getModel() instanceof TBindingOperationFault) {
			items = (List) ((TBindingOperationFault)parent.getModel()).getAny();
		} else {
			throw new WSDLException("Parent unknown");
		}

		boolean find = false;
		Object value = null;
        for (final Object item : items) {
            if (item instanceof JAXBElement) {
                value = ((JAXBElement) item).getValue();
            } else {
                value = item;
            }
            if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody) {
            	find = true;
            	break;
            }
        }

        if(!find) {
        	org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory factory = new org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory();
        	Object item = factory.createBody(((SOAP11BodyImpl)body).getModel());
        	if(parent.getModel() instanceof TBindingOperationMessage) {
    			((TBindingOperationMessage)parent.getModel()).getAny().add(item);
    		} else if(parent.getModel() instanceof TBindingOperationFault) {
    			((TBindingOperationFault)parent.getModel()).getAny().add(item);
    		}
        }
	}

	public SOAP11Body createBody() {
		return new SOAP11BodyImpl(new TBody(), parent);
	}

	public SOAP11Fault createFault() {
		return new SOAP11FaultImpl(new TFault());
	}

	public void setFault(SOAP11Fault fault) throws WSDLException {
		this.soap11fault = fault;

		List<Object> items = null;
		if(parent.getModel() instanceof TBindingOperationMessage) {
			items = (List) ((TBindingOperationMessage)parent.getModel()).getAny();
		} else if(parent.getModel() instanceof TBindingOperationFault) {
			items = (List) ((TBindingOperationFault)parent.getModel()).getAny();
		} else {
			throw new WSDLException("Parent unknown");
		}

		boolean find = false;
		Object value = null;
        for (final Object item : items) {
            if (item instanceof JAXBElement) {
                value = ((JAXBElement) item).getValue();
            } else {
                value = item;
            }
            if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault) {
            	find = true;
            	break;
            }
        }

        if(!find) {
        	org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory factory = new org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory();
        	Object item = factory.createFault(((SOAP11FaultImpl)fault).getModel());
        	if(parent.getModel() instanceof TBindingOperationMessage) {
    			((TBindingOperationMessage)parent.getModel()).getAny().add(item);
    		} else if(parent.getModel() instanceof TBindingOperationFault) {
    			((TBindingOperationFault)parent.getModel()).getAny().add(item);
    		}
        }
	}
}
