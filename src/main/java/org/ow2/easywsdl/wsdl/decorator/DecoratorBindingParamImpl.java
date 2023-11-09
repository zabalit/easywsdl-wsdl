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
 
package org.ow2.easywsdl.wsdl.decorator;

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingParam;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.http.HTTPBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.http.HTTPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class DecoratorBindingParamImpl<BP extends AbsItfBindingParam>  extends Decorator<BP> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DecoratorBindingParamImpl(final AbsItfBindingParam bindingParam) throws WSDLException {
        this.internalObject = (BP)bindingParam;
    }

    public HTTPBinding4Wsdl11 getHTTPBinding4Wsdl11() {
        return this.internalObject.getHTTPBinding4Wsdl11();
    }

    public HTTPBinding4Wsdl20 getHTTPBinding4Wsdl20() {
        return this.internalObject.getHTTPBinding4Wsdl20();
    }

    public String getHttpContentEncoding() {
        return this.internalObject.getHttpContentEncoding();
    }

    public MIMEBinding4Wsdl11 getMIMEBinding4Wsdl11() {
        return this.internalObject.getMIMEBinding4Wsdl11();
    }

    public String getName() {
        return this.internalObject.getName();
    }

    public SOAP11Binding4Wsdl11 getSOAP11Binding4Wsdl11() {
        return this.internalObject.getSOAP11Binding4Wsdl11();
    }

    public SOAP12Binding4Wsdl11 getSOAP12Binding4Wsdl11() {
        return this.internalObject.getSOAP12Binding4Wsdl11();
    }

    public SOAPBinding4Wsdl20 getSOAP12Binding4Wsdl20() {
        return this.internalObject.getSOAP12Binding4Wsdl20();
    }

    public void setName(final String arg0){
        this.internalObject.setName(arg0);
    }

	public SOAP11Binding4Wsdl11 createSOAP11Binding4Wsdl11() {
		return this.internalObject.createSOAP11Binding4Wsdl11();
	}

	public SOAPBinding4Wsdl20 createSOAP12Binding4Wsdl20() {
		return this.internalObject.createSOAP12Binding4Wsdl20();
	}

	public void setSOAP11Binding4Wsdl11(SOAP11Binding4Wsdl11 soap11binding) {
		this.internalObject.setSOAP11Binding4Wsdl11(soap11binding);
	}

	public void setSOAP12Binding4Wsdl20(SOAPBinding4Wsdl20 soap12binding) {
		this.internalObject.setSOAP12Binding4Wsdl20(soap12binding);
	}

}
