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
 
package org.ow2.easywsdl.wsdl.api.abstractElmt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingOperation;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingParam;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.http.HTTPBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.mime.MIMEBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Header;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.http.HTTPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11BodyImpl;
import org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11FaultImpl;
import org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11HeaderImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.ContentType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.MultipartRelatedType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TMimeXml;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractBindingParamImpl<E> extends AbstractWSDLElementImpl<E> implements
AbsItfBindingParam {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the parent operation
	 */
	@SuppressWarnings("unchecked")
	protected AbsItfBindingOperation bindingOperation;

	private static ObjectFactory soap11BindingFactory = new ObjectFactory();


	/**
	 * the binding protocol
	 */
	protected BindingProtocol bindingProtocol;


	public AbstractBindingParamImpl(E model, AbstractWSDLElementImpl parent) {
		super(model, parent);
	}

	/**
	 * @return the binding
	 */
	@SuppressWarnings("unchecked")
	public AbsItfBindingOperation getBindingOperation() {
		return this.bindingOperation;
	}

	@SuppressWarnings("unchecked")
	public static BindingProtocol extractBindingProtocol(final List<Object> items,
			final AbstractBindingParamImpl param) {
		BindingProtocol bindingProtocol = null;
		// get the binding protocol
		org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlEncoded encoded = null;
		org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlReplacement replacement = null;

		final List<ContentType> contents = new ArrayList<ContentType>();
		final List<TMimeXml> mimeXmls = new ArrayList<TMimeXml>();
		final List<MultipartRelatedType> multiparts = new ArrayList<MultipartRelatedType>();

		org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody soap11body = null;
		final List<org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader> soap11headers = new ArrayList<org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader>();
		org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault soap11fault = null;

		org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBody soap12body = null;
		final List<org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeader> soap12headers = new ArrayList<org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeader>();
		org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TFault soap12fault = null;

		final List<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.http.Header> httpHeaders = new ArrayList<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.http.Header>();
		final List<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Header> soapHeaders = new ArrayList<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Header>();
		final List<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Module> soapModules = new ArrayList<org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Module>();

		Object value = null;
		for (final Object item : items) {
			if (item instanceof JAXBElement) {
				value = ((JAXBElement) item).getValue();
			} else {
				value = item;
			}
			if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlEncoded) {
				encoded = (org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlEncoded) value;
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlReplacement) {
				replacement = (org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.UrlReplacement) value;
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.ContentType) {
				contents
				.add((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.ContentType) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TMimeXml) {
				mimeXmls
				.add((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.TMimeXml) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.MultipartRelatedType) {
				multiparts
				.add((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.mime.MultipartRelatedType) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault) {
				soap11fault = (org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault) value;
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TFault) {
				soap12fault = (org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TFault) value;
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody) {
				soap11body = (org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody) value;
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBody) {
				soap12body = (org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TBody) value;
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader) {
				soap11headers
				.add((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeader) {
				soap12headers
				.add((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.THeader) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.http.Header) {
				httpHeaders.add((org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.http.Header) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Header) {
				soapHeaders.add((org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Header) value);
			} else if (value instanceof org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Module) {
				soapModules.add((org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.soap.Module) value);
			}

		}

		/**
		 * WSDL 1.1
		 */
		// contains http binding
		if ((encoded != null) || (replacement != null)) {
			bindingProtocol = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.http.HTTPBindingImpl(
					encoded, replacement);
		}

		// contain mime binding
		if ((!contents.isEmpty()) || (!mimeXmls.isEmpty()) || (!multiparts.isEmpty())) {
			bindingProtocol = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.mime.MIMEBindingImpl(
					contents, mimeXmls, multiparts, param);
		}

		// contain soap 1.1 binding
		if ((soap11body != null) || (!soap11headers.isEmpty()) || (soap11fault != null)) {
			bindingProtocol = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11BindingImpl(
					soap11headers, soap11body, soap11fault, param);
		}

		// contain soap 1.2 binding
		if ((soap12body != null) || (!soap12headers.isEmpty()) || (soap12fault != null)) {
			bindingProtocol = new org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12.SOAP12BindingImpl(
					soap12headers, soap12body, soap12fault);
		}

		/**
		 * WSDL 2.0
		 */
		// contains http binding
		if (!httpHeaders.isEmpty()) {
			bindingProtocol = new org.ow2.easywsdl.wsdl.impl.wsdl20.binding.http.HTTPBindingImpl(
					httpHeaders, param);
		}

		// contains soap binding
		if ((!soapHeaders.isEmpty()) || (!soapModules.isEmpty())) {
			bindingProtocol = new org.ow2.easywsdl.wsdl.impl.wsdl20.binding.soap.SOAPBindingImpl(
					soapHeaders, soapModules, param);
		}

		return bindingProtocol;
	}

	public static void setBindingProtocol(final BindingProtocol bindingProtocol, final List<Object> items,
			final AbstractBindingParamImpl param) {

		Object value = null;
		Iterator<Object> it = items.iterator();

		// delete last binding
		while(it.hasNext()) {
			Object item = it.next();
			if (item instanceof JAXBElement) {
				value = ((JAXBElement) item).getValue();
			} else {
				value = item;
			}
			if(bindingProtocol instanceof SOAP11Binding4Wsdl11) {
				// delete all
				if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.THeader) {
					items.remove(item);
					it = items.iterator();
				} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody) {
					items.remove(item);
					it = items.iterator();
				} else if (value instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TFault) {
					items.remove(item);
					it = items.iterator();
				}
			}


		}

		// set new binding
		if(bindingProtocol instanceof SOAP11Binding4Wsdl11) {
			SOAP11Binding4Wsdl11 soap11Binding = (SOAP11Binding4Wsdl11) bindingProtocol;

			Object item = null;

			// set the body
			if (soap11Binding.getBody() != null) {
				item = soap11BindingFactory.createBody(((SOAP11BodyImpl)soap11Binding.getBody()).getModel());
				items.add(item);
			}

			// set headers
			for(SOAP11Header header: soap11Binding.getHeaders()) {
				// set the header
				item = soap11BindingFactory.createHeader(((SOAP11HeaderImpl)header).getModel());
				items.add(item);
			}

			// set the fault
			if (soap11Binding.getFault() != null) {
				item = soap11BindingFactory.createFault(((SOAP11FaultImpl)soap11Binding.getFault()).getModel());
				items.add(item);
			}
		}

	}

	public HTTPBinding4Wsdl11 getHTTPBinding4Wsdl11() {
		HTTPBinding4Wsdl11 res = null;
		if (this.bindingProtocol instanceof HTTPBinding4Wsdl11) {
			res = (HTTPBinding4Wsdl11) this.bindingProtocol;
		}
		return res;
	}

	public MIMEBinding4Wsdl11 getMIMEBinding4Wsdl11() {
		MIMEBinding4Wsdl11 res = null;
		if (this.bindingProtocol instanceof MIMEBinding4Wsdl11) {
			res = (MIMEBinding4Wsdl11) this.bindingProtocol;
		}
		return res;
	}

	public SOAP11Binding4Wsdl11 getSOAP11Binding4Wsdl11() {
		SOAP11Binding4Wsdl11 res = null;
		if (this.bindingProtocol instanceof SOAP11Binding4Wsdl11) {
			res = (SOAP11Binding4Wsdl11) this.bindingProtocol;
		}
		return res;
	}

	public SOAP12Binding4Wsdl11 getSOAP12Binding4Wsdl11() {
		SOAP12Binding4Wsdl11 res = null;
		if (this.bindingProtocol instanceof SOAP12Binding4Wsdl11) {
			res = (SOAP12Binding4Wsdl11) this.bindingProtocol;
		}
		return res;
	}

	public HTTPBinding4Wsdl20 getHTTPBinding4Wsdl20() {
		HTTPBinding4Wsdl20 res = null;
		if (this.bindingProtocol instanceof HTTPBinding4Wsdl20) {
			res = (HTTPBinding4Wsdl20) this.bindingProtocol;
		}
		return res;
	}

	public SOAPBinding4Wsdl20 getSOAP12Binding4Wsdl20() {
		SOAPBinding4Wsdl20 res = null;
		if (this.bindingProtocol instanceof SOAPBinding4Wsdl20) {
			res = (SOAPBinding4Wsdl20) this.bindingProtocol;
		}
		return res;
	}
}
