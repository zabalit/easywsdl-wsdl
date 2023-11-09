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
 
package org.ow2.easywsdl.wsdl.impl.wsdl20;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingOperationImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding.StyleConstant;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol.SOAPMEPConstants;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.BindingOperationFaultType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.BindingOperationMessageType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.BindingOperationType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.ObjectFactory;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class BindingOperationImpl
		extends
		AbstractBindingOperationImpl<BindingOperationType, Operation, BindingInput, BindingOutput, BindingFault>
		implements org.ow2.easywsdl.wsdl.api.BindingOperation {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ObjectFactory factory = new ObjectFactory();

	public BindingOperationImpl(final BindingOperationType bindingOperation,
			final BindingImpl bindingImpl) {
		super(bindingOperation, bindingImpl);
		this.binding = bindingImpl;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(
				this.model.getDocumentation(), this);

		// get the input binding
		for (final Object item : this.model.getInputOrOutputOrInfault()) {
			if (item instanceof JAXBElement) {
				Object value = ((JAXBElement)item).getValue();
				// get input
				if (isInput((JAXBElement)item, value)) {
					this.input = new org.ow2.easywsdl.wsdl.impl.wsdl20.BindingInputImpl((BindingOperationMessageType)value, this);
				}

				// get output
				if (isOutput((JAXBElement)item, value)) {
					this.output = new org.ow2.easywsdl.wsdl.impl.wsdl20.BindingOutputImpl(
							(BindingOperationMessageType)value, this);
				}

				// get fault
				if (isInFault((JAXBElement)item, value)) {
					this.faults
							.add(new org.ow2.easywsdl.wsdl.impl.wsdl20.BindingFaultImpl(
									(BindingOperationFaultType)value, this));
				}

				// get fault
				if (isOutFault((JAXBElement)item, value)) {
					this.faults
							.add(new org.ow2.easywsdl.wsdl.impl.wsdl20.BindingFaultImpl(
									(BindingOperationFaultType)value, this));
				}
			}
		}
	}

	private boolean isInFault(final JAXBElement item,Object value) {
		return value instanceof BindingOperationFaultType
		&& item.getName()
				.equals(
						new QName("http://www.w3.org/ns/wsdl",
								"infault"));
	}
	
	private boolean isOutFault(final JAXBElement item,Object value) {
		return value instanceof BindingOperationFaultType
		&& item.getName()
				.equals(
						new QName("http://www.w3.org/ns/wsdl",
								"outfault"));
	}

	private boolean isOutput(final JAXBElement item, Object value) {
		return value instanceof BindingOperationMessageType
				&& item.getName()
						.equals(
								new QName("http://www.w3.org/ns/wsdl",
										"output"));
	}

	private boolean isInput(final JAXBElement item, Object value) {
		return value instanceof BindingOperationMessageType
				&& item.getName().equals(
						new QName("http://www.w3.org/ns/wsdl", "input"));
	}

	public void addFault(final BindingFault bindingFault) {
		this.faults.add(bindingFault);

		JAXBElement<BindingOperationFaultType> bf = factory
				.createBindingOperationTypeOutfault((((BindingOperationFaultType) ((AbstractWSDLElementImpl) bindingFault)
						.getModel())));
		this.model.getInputOrOutputOrInfault().add(bf);
	}

	public BindingFault removeFault(final String name) {
        throw new UnsupportedOperationException();
	}

	@Override
	public void setInput(BindingInput input) {
		super.setInput(input);
		JAXBElement<BindingOperationMessageType> bi = factory
				.createBindingOperationTypeInput((((BindingOperationMessageType) ((AbstractWSDLElementImpl) input)
						.getModel())));
		this.model.getInputOrOutputOrInfault().add(bi);
	}

	@Override
	public void setOutput(BindingOutput output) {
		super.setOutput(output);
		JAXBElement<BindingOperationMessageType> bo = factory
				.createBindingOperationTypeOutput((((BindingOperationMessageType) ((AbstractWSDLElementImpl) input)
						.getModel())));
		this.model.getInputOrOutputOrInfault().add(bo);
	}

	public void setQName(final QName name) {
		this.model.setRef(name);
	}

	public QName getQName() {
		return this.model.getRef();
	}

	public SOAPMEPConstants getMEP() {
		SOAPMEPConstants mep = null;
		for (final Entry<QName, String> attribute : this.model
				.getOtherAttributes().entrySet()) {
			if ((attribute.getKey().getLocalPart()
					.equals(Constants.MEP_ATTRIBUTE))
					&& (attribute.getKey().getNamespaceURI()
							.equals(org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20
									.value().toString()))) {
				try {
					mep = SOAPMEPConstants
							.valueOf(new URI(attribute.getValue()));
				} catch (final URISyntaxException e) {
					mep = null;
				}
				break;
			}
		}
		return mep;
	}

	public void setMEP(final SOAPMEPConstants mep) {
		this.model
				.getOtherAttributes()
				.put(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20
										.value().toString(),
								Constants.MEP_ATTRIBUTE),
						mep.value().toString());
	}

	public StyleConstant getStyle() {
		return StyleConstant.DOCUMENT;
	}

	public String getHttpLocation() {
		return this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(), "location"));
	}

	public String getSoapAction() {
		return this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20
										.value().toString(), "action"));
	}

	public void setSoapAction(String action) {
		this.model
				.getOtherAttributes()
				.put(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.SOAP_BINDING4WSDL20
										.value().toString(), "action"), action);
	}

	public String getHttpContentEncodingDefault() {
		return this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(),
								"contentEncodingDefault"));
	}

	public String getHttpFaultSerialization() {
		return this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(),
								"faultSerialization"));
	}

	public String getHttpInputSerialization() {
		String res = null;
		res = this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(),
								"inputSerialization"));
		if (res == null) {
			if (("GET".equals(this.getHttpMethod()))
					|| ("DELETE".equals(this.getHttpMethod()))) {
				res = "application/x-www-form-urlencoded";
			} else if (("POST".equals(this.getHttpMethod()))
					|| ("PUT".equals(this.getHttpMethod()))) {
				res = "	application/xml";
			}
		}
		return res;
	}

	public String getHttpMethod() {
		return this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(), "method"));
	}

	public String getHttpOutputSerialization() {
		String res = null;
		res = this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(),
								"outputSerialization"));
		if (res == null) {
			res = "	application/xml";
		}
		return res;
	}

	public String getHttpQueryParameterSeparator() {
		return this.model
				.getOtherAttributes()
				.get(
						new QName(
								org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
										.value().toString(),
								"queryParameterSeparator"));
	}

	public boolean isHttpIgnoreUncited() {
		return Boolean
				.valueOf(this.model
						.getOtherAttributes()
						.get(
								new QName(
										org.ow2.easywsdl.wsdl.api.Binding.BindingConstants.HTTP_BINDING4WSDL20
												.value().toString(),
										"ignoreUncited")));

	}

	@Override
	public List<Element> getOtherElements() throws XmlException {
		final List<Element> res = super.getOtherElements();

		for (final Object item : this.model.getInputOrOutputOrInfault()) {
			if (item instanceof Element) {
				res.add((Element) item);
			}
		}

		return res;
	}

	public BindingFault createFault() {
		return new BindingFaultImpl(new BindingOperationFaultType(), this);
	}

	public BindingInput createInput() {
		return new BindingInputImpl(new BindingOperationMessageType(), this);
	}

	public BindingOutput createOutput() {
		return new BindingOutputImpl(new BindingOperationMessageType(), this);
	}
}
