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
 
package org.ow2.easywsdl.wsdl.test.wsdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.OperationType;
import javax.wsdl.Port;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.http.HTTPAddress;
import javax.wsdl.extensions.http.HTTPBinding;
import javax.wsdl.extensions.http.HTTPOperation;
import javax.wsdl.extensions.http.HTTPUrlEncoded;
import javax.wsdl.extensions.http.HTTPUrlReplacement;
import javax.wsdl.extensions.mime.MIMEContent;
import javax.wsdl.extensions.mime.MIMEMimeXml;
import javax.wsdl.extensions.mime.MIMEMultipartRelated;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPFault;
import javax.wsdl.extensions.soap.SOAPHeader;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.extensions.soap12.SOAP12Address;
import javax.wsdl.extensions.soap12.SOAP12Binding;
import javax.wsdl.extensions.soap12.SOAP12Body;
import javax.wsdl.extensions.soap12.SOAP12Fault;
import javax.wsdl.extensions.soap12.SOAP12Header;
import javax.wsdl.extensions.soap12.SOAP12Operation;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.junit.Assert;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBindingParam;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation.MEPPatternConstants;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfParam;
import org.w3c.dom.Document;

import com.ebmwebsourcing.easycommons.xml.XMLHelper;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDL4JComparisonTest extends TestCase {

	public void testWSDL4J() throws Exception {
	
		WSDLReader readerDesc = WSDLFactory.newInstance().newWSDLReader();
		javax.wsdl.xml.WSDLReader readerDef = javax.wsdl.factory.WSDLFactory.newInstance().newWSDLReader();
		readerDef.setFeature("javax.wsdl.verbose", false);
		
		// WSDL 1.1
		for (final URL wsdlUrl : WSDLList.getWsdls11()) {
			final Description desc = readerDesc.read(wsdlUrl);
			final Definition def = readerDef.readWSDL(wsdlUrl.toString());
			this.assertWithWSDL4JModel(desc, def);
//			System.out.println("Test on: " + wsdl + " => OK");
		}

	}

	public void testWSDL4J2() throws Exception {
		// DOM root and DOM import
		String rootString = readFileAsString("/descriptors/otherswsdl/separated/root/sendMail.wsdl");
		String importString = readFileAsString("/descriptors/otherswsdl/separated/imports/sendMailInterface.wsdl");

		Document rootDocument = XMLHelper.createDocumentFromString(rootString);
		rootDocument.setDocumentURI(".");
		Document importDocument = XMLHelper
				.createDocumentFromString(importString);
		importDocument.setDocumentURI(".");
		HashMap<URI, AbsItfDescription> imports = new HashMap<URI, AbsItfDescription>();
		imports.put(URI.create("sendMailInterface.wsdl"), WSDLFactory.newInstance().newWSDLReader().read(importDocument));
	}

	private String readFileAsString(String filePath) throws java.io.IOException {
		URL resourceURL = this.getClass().getResource(filePath);
		File wsdl = new File(URI.create(resourceURL.toString()));
        StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(wsdl));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	@SuppressWarnings("unchecked")
	private void assertWithWSDL4JModel(final Description desc,
			final Definition def) throws WSDLException {
		// assert on description
		Assert.assertNotNull(desc);
		Assert
				.assertEquals(def.getTargetNamespace(), desc
						.getTargetNamespace());
		// Assert.assertTrue(def.getDocumentBaseURI().contains(
		// desc.getDocumentBaseURI().toString()));
		Assert.assertEquals(def.getQName(), desc.getQName());

		// assert on service
		Assert.assertEquals(def.getAllServices().size(), desc.getServices()
				.size());
		for (final javax.wsdl.Service expectedService : (Collection<javax.wsdl.Service>) def
				.getAllServices().values()) {

			final Service actualService = desc.getService(expectedService
					.getQName());
			Assert.assertNotNull(actualService);
			Assert.assertEquals(expectedService.getQName(), actualService
					.getQName());

			// assert on endpoint
			Assert.assertEquals(expectedService.getPorts().size(),
					actualService.getEndpoints().size());
			for (final Port expectedEp : (Collection<Port>) expectedService
					.getPorts().values()) {

				final Endpoint actualEp = actualService.getEndpoint(expectedEp
						.getName());
				Assert.assertNotNull(actualEp);

				Assert.assertEquals(expectedEp.getName(), actualEp.getName());

				// assert on endpoint address
				final List extEpEls = expectedEp.getExtensibilityElements();
				if (extEpEls.size() > 0) {
					final ExtensibilityElement extEl = (ExtensibilityElement) extEpEls
							.get(0);
					if (extEl instanceof SOAPAddress) {
						final SOAPAddress soapAddress = (SOAPAddress) extEl;
						Assert.assertEquals(soapAddress.getLocationURI(),
								actualEp.getAddress());
					} else if (extEl instanceof SOAP12Address) {
						final SOAP12Address soapAddress = (SOAP12Address) extEl;
						Assert.assertEquals(soapAddress.getLocationURI(),
								actualEp.getAddress());
					} else if (extEl instanceof HTTPAddress) {
						final HTTPAddress httpAddress = (HTTPAddress) extEl;
						Assert.assertEquals(httpAddress.getLocationURI(),
								actualEp.getAddress());
					} else {
						Assert.fail("unknown endpoint address type");
					}
				} else {
					Assert.fail("no extensible element for this endpoint: "
							+ expectedEp.getName());
				}

				// assert on binding
				Assert.assertEquals(def.getAllBindings().size(), desc
						.getBindings().size());
				Assert.assertEquals(expectedEp.getBinding().getQName(),
						actualEp.getBinding().getQName());

				// assert on binding protocol and style
				final List extBdgEls = expectedEp.getBinding()
						.getExtensibilityElements();

				if (extBdgEls.size() > 0) {
					for (Object extEl : extBdgEls) {
						if (extEl instanceof JAXBElement) {
							if (extEl instanceof SOAPBinding) {
								final SOAPBinding soapBinding = (SOAPBinding) extEl;
								Assert.assertEquals(soapBinding
										.getTransportURI(), actualEp
										.getBinding().getTransportProtocol());
								if (soapBinding.getStyle() != null) {
									Assert.assertEquals(soapBinding.getStyle(),
											actualEp.getBinding().getStyle()
													.value());
								} else {
									Assert.assertNull(actualEp.getBinding()
											.getStyle());
								}
							} else if (extEl instanceof SOAP12Binding) {
								final SOAP12Binding soapBinding = (SOAP12Binding) extEl;
								Assert.assertEquals(soapBinding
										.getTransportURI(), actualEp
										.getBinding().getTransportProtocol());
								if (soapBinding.getStyle() != null) {
									Assert.assertEquals(soapBinding.getStyle(),
											actualEp.getBinding().getStyle()
													.value());
								} else {
									Assert.assertNull(actualEp.getBinding()
											.getStyle());
								}
							} else if (extEl instanceof HTTPBinding) {
								final HTTPBinding httpBinding = (HTTPBinding) extEl;
								Assert.assertEquals(httpBinding.getVerb(),
										actualEp.getBinding()
												.getTransportProtocol());
								Assert.assertEquals(null, actualEp.getBinding()
										.getStyle());
							} else {
								Assert.fail("unknown endpoint address type");
							}
						}
					}
				} else {
					Assert.fail("no extensible element for this binding: "
							+ expectedEp.getBinding().getQName());
				}

				// assert on binding operation
				Assert.assertEquals(expectedEp.getBinding()
						.getBindingOperations().size(), actualEp.getBinding()
						.getBindingOperations().size());
				for (final javax.wsdl.BindingOperation expectedbop : (Collection<javax.wsdl.BindingOperation>) expectedEp
						.getBinding().getBindingOperations()) {
					final BindingOperation actualbop = actualEp.getBinding()
							.getBindingOperation(expectedbop.getName());
					Assert.assertNotNull(actualbop);

					Assert.assertEquals(expectedbop.getName(), actualbop
							.getQName().getLocalPart());

					// assert on soap action or http location
					String soapActionURI = null;
					String httpLocationURI = null;
					final List extElList = expectedbop
							.getExtensibilityElements();

					if (extElList != null) {
						final Iterator extIterator = extElList.iterator();

						while (extIterator.hasNext()) {
							final ExtensibilityElement extEl = (ExtensibilityElement) extIterator
									.next();

							if (extEl instanceof SOAPOperation) {
								final SOAPOperation soapOp = (SOAPOperation) extEl;

								soapActionURI = soapOp.getSoapActionURI();
							} else if (extEl instanceof SOAP12Operation) {
								final SOAP12Operation soapOp = (SOAP12Operation) extEl;

								soapActionURI = soapOp.getSoapActionURI();
							} else if (extEl instanceof HTTPOperation) {
								final HTTPOperation httpLoc = (HTTPOperation) extEl;
								httpLocationURI = httpLoc.getLocationURI();
							}
						}
					}
					Assert.assertEquals(soapActionURI, actualbop
							.getSoapAction());
					Assert.assertEquals(httpLocationURI, actualbop
							.getHttpLocation());

					// assert on binding input
					if (expectedbop.getBindingInput() != null) {
						Assert.assertEquals(expectedbop.getBindingInput()
								.getName(), actualbop.getInput().getName());
						this.assertOnBindingParam(expectedbop.getBindingInput()
								.getExtensibilityElements(), actualbop
								.getInput());
					} else {
						Assert.assertNull(actualbop.getInput());
					}

					// assert on binding output
					if (expectedbop.getBindingOutput() != null) {
						Assert.assertEquals(expectedbop.getBindingOutput()
								.getName(), actualbop.getOutput().getName());
						this.assertOnBindingParam(expectedbop
								.getBindingOutput().getExtensibilityElements(),
								actualbop.getOutput());
					} else {
						Assert.assertNull(actualbop.getOutput());
					}

					// assert on binding faults
					if (expectedbop.getBindingFaults() != null) {
						Assert.assertEquals(expectedbop.getBindingFaults()
								.size(), actualbop.getFaults().size());
						for (final javax.wsdl.BindingFault expectedfault : (Collection<javax.wsdl.BindingFault>) expectedbop
								.getBindingFaults().values()) {
							final BindingFault actualfault = actualbop
									.getFault(expectedfault.getName());
							Assert.assertNotNull(actualfault);

							Assert.assertEquals(expectedfault.getName(),
									actualfault.getName());
							this.assertOnBindingParam(expectedfault
									.getExtensibilityElements(), actualfault);
						}
					} else {
						Assert.assertNotNull(actualbop.getFaults());
					}

					// assert on interface
					Assert.assertEquals(def.getAllPortTypes().size(), desc
							.getInterfaces().size());
					for (final javax.wsdl.PortType expecteditf : (Collection<javax.wsdl.PortType>) def
							.getAllPortTypes().values()) {
						final InterfaceType actualitf = desc
								.getInterface(expecteditf.getQName());

						Assert.assertNotNull(actualitf);
						Assert.assertEquals(expecteditf.getQName(), actualitf
								.getQName());

						// assert on operations
						Assert.assertEquals(expecteditf.getOperations().size(),
								actualitf.getOperations().size());
						for (final javax.wsdl.Operation expectedop : (Collection<javax.wsdl.Operation>) expecteditf
								.getOperations()) {

							final Operation actualop = actualitf
									.getOperation(new QName(expecteditf
											.getQName().getNamespaceURI(),
											expectedop.getName()));
							Assert.assertNotNull(actualop);

							Assert.assertEquals(expectedop.getName(), actualop
									.getQName().getLocalPart());

							// assert on pattern operation
							if (expectedop.getStyle().equals(
									OperationType.ONE_WAY)) {
								Assert.assertTrue(actualop.getPattern().equals(
										MEPPatternConstants.IN_ONLY));
							} else if (expectedop.getStyle().equals(
									OperationType.REQUEST_RESPONSE)) {
								Assert.assertTrue(actualop.getPattern().equals(
										MEPPatternConstants.IN_OUT));
							} else if (expectedop.getStyle().equals(
									OperationType.SOLICIT_RESPONSE)) {
								Assert.assertTrue(actualop.getPattern().equals(
										MEPPatternConstants.ROBUST_IN_ONLY));
							} else {
								Assert.fail("unknown pattern operation");
							}

							// assert on parameter ordering
							Assert.assertEquals(expectedop
									.getParameterOrdering(), actualop
									.getParameterOrdering());

							// assert on input operation
							if (expectedop.getInput() != null) {
								this.assertOnOperationParam(expectedop
										.getInput().getName(), expectedop
										.getInput().getMessage(), actualop
										.getInput());
							}

							// assert on output operation
							if (expectedop.getOutput() != null) {
								this.assertOnOperationParam(expectedop
										.getOutput().getName(), expectedop
										.getOutput().getMessage(), actualop
										.getOutput());
							}

							// assert on fault operation
							if (expectedop.getFaults() != null) {
								Assert.assertEquals(expectedop.getFaults()
										.size(), actualop.getFaults().size());
								for (final javax.wsdl.Fault expectedfault : (Collection<javax.wsdl.Fault>) expectedop
										.getFaults().values()) {
									final Fault actualfault = actualop
											.getFault(expectedfault
													.getMessage().getQName()
													.getLocalPart());

									this.assertOnOperationParam(expectedfault
											.getName(), expectedfault
											.getMessage(), actualfault);

								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void assertOnBindingParam(final List extElList2,
			final AbsItfBindingParam actualparam) {
		SOAPBody soapBody = null;
		SOAPFault soapFault = null;
		SOAP12Body soap12Body = null;
		SOAP12Fault soap12Fault = null;
		final List<SOAPHeader> soapHeader = new ArrayList<SOAPHeader>();
		final List<SOAP12Header> soap12Header = new ArrayList<SOAP12Header>();
		HTTPUrlEncoded httpUrlEncoded = null;
		HTTPUrlReplacement httpUrlReplacement = null;
		final List<MIMEContent> mimeContents = new ArrayList<MIMEContent>();
		final List<MIMEMimeXml> mimeXmls = new ArrayList<MIMEMimeXml>();
		final List<javax.wsdl.extensions.mime.MIMEMultipartRelated> mpartr = new ArrayList<MIMEMultipartRelated>();
		if (extElList2 != null) {
			final Iterator extIterator = extElList2.iterator();

			while (extIterator.hasNext()) {
				final ExtensibilityElement extEl = (ExtensibilityElement) extIterator
						.next();

				if (extEl instanceof SOAPBody) {
					soapBody = (SOAPBody) extEl;
				} else if (extEl instanceof SOAP12Body) {
					soap12Body = (SOAP12Body) extEl;
				} else if (extEl instanceof SOAPHeader) {
					soapHeader.add((SOAPHeader) extEl);
				} else if (extEl instanceof SOAP12Header) {
					soap12Header.add((SOAP12Header) extEl);
				} else if (extEl instanceof SOAPFault) {
					soapFault = (SOAPFault) extEl;
				} else if (extEl instanceof SOAP12Fault) {
					soap12Fault = (SOAP12Fault) extEl;
				} else if (extEl instanceof HTTPUrlEncoded) {
					httpUrlEncoded = (HTTPUrlEncoded) extEl;
				} else if (extEl instanceof HTTPUrlReplacement) {
					httpUrlReplacement = (HTTPUrlReplacement) extEl;
				} else if (extEl instanceof MIMEContent) {
					mimeContents.add((MIMEContent) extEl);
				} else if (extEl instanceof MIMEMimeXml) {
					mimeXmls.add((MIMEMimeXml) extEl);
				} else if (extEl instanceof MIMEMultipartRelated) {
					mpartr.add((MIMEMultipartRelated) extEl);
				}
			}
		}

		if ((soapBody != null) || (soapHeader.size() > 0)
				|| (soapFault != null)) {
			Assert.assertNotNull(actualparam.getSOAP11Binding4Wsdl11());
			if (soapBody != null) {
				Assert.assertEquals(soapBody.getNamespaceURI(), actualparam
						.getSOAP11Binding4Wsdl11().getBody().getNamespace());
				Assert.assertEquals(soapBody.getUse(), actualparam
						.getSOAP11Binding4Wsdl11().getBody().getUse().value());
				Assert.assertEquals(soapBody.getRequired(), actualparam
						.getSOAP11Binding4Wsdl11().getBody().getRequired());

				if (soapBody.getEncodingStyles() != null) {
					for (final String item : (List<String>) soapBody
							.getEncodingStyles()) {
						Assert.assertTrue(actualparam.getSOAP11Binding4Wsdl11()
								.getBody().getEncodingStyles().contains(item));
					}
				} else {
					Assert.assertNull(actualparam.getSOAP11Binding4Wsdl11()
							.getBody().getEncodingStyles());
				}
				if (soapBody.getParts() != null) {
					for (final String item : (List<String>) soapBody.getParts()) {
						Assert.assertTrue(actualparam.getSOAP11Binding4Wsdl11()
								.getBody().getParts().contains(item));
					}
				} else {
					Assert.assertNull(actualparam.getSOAP11Binding4Wsdl11()
							.getBody().getParts());
				}

			} else {
				Assert.assertNull(actualparam.getSOAP11Binding4Wsdl11()
						.getBody());
			}

			Assert.assertEquals(soapHeader.size(), actualparam
					.getSOAP11Binding4Wsdl11().getHeaders().size());

			if (soapFault != null) {
				Assert.assertEquals(soapFault.getNamespaceURI(), actualparam
						.getSOAP11Binding4Wsdl11().getFault().getNamespace());
				Assert.assertEquals(soapFault.getName(), actualparam
						.getSOAP11Binding4Wsdl11().getFault().getName());
				Assert.assertEquals(soapFault.getUse(), actualparam
						.getSOAP11Binding4Wsdl11().getFault().getUse().value());
				Assert.assertEquals(soapFault.getRequired(), actualparam
						.getSOAP11Binding4Wsdl11().getFault().getRequired());

				if (soapFault.getEncodingStyles() != null) {
					for (final String item : (List<String>) soapFault
							.getEncodingStyles()) {
						Assert.assertTrue(actualparam.getSOAP11Binding4Wsdl11()
								.getFault().getEncodingStyles().contains(item));
					}
				} else {
					Assert.assertNull(actualparam.getSOAP11Binding4Wsdl11()
							.getFault().getEncodingStyles());
				}

			} else {
				Assert.assertNull(actualparam.getSOAP11Binding4Wsdl11()
						.getFault());
			}

		} else if ((soap12Body != null) || (soap12Header.size() > 0)
				|| (soap12Fault != null)) {
			Assert.assertNotNull(actualparam.getSOAP12Binding4Wsdl11());

			if (soap12Body != null) {
				Assert.assertEquals(soap12Body.getNamespaceURI(), actualparam
						.getSOAP12Binding4Wsdl11().getBody().getNamespace());
				Assert.assertEquals(soap12Body.getUse(), actualparam
						.getSOAP12Binding4Wsdl11().getBody().getUse().value());
				Assert.assertEquals(soap12Body.getRequired(), actualparam
						.getSOAP12Binding4Wsdl11().getBody().getRequired());
				Assert
						.assertEquals(soap12Body.getEncodingStyle(),
								actualparam.getSOAP12Binding4Wsdl11().getBody()
										.getEncodingStyle());

				if (soap12Body.getParts() != null) {
					for (final String item : (List<String>) soap12Body
							.getParts()) {
						Assert.assertTrue(actualparam.getSOAP12Binding4Wsdl11()
								.getBody().getParts().contains(item));
					}
				} else {
					Assert.assertNull(actualparam.getSOAP12Binding4Wsdl11()
							.getBody().getParts());
				}

			} else {
				Assert.assertNull(actualparam.getSOAP12Binding4Wsdl11()
						.getBody());
			}

			Assert.assertEquals(soap12Header.size(), actualparam
					.getSOAP12Binding4Wsdl11().getHeaders().size());

			if (soap12Fault != null) {
				Assert.assertEquals(soap12Fault.getNamespaceURI(), actualparam
						.getSOAP12Binding4Wsdl11().getFault().getNamespace());
				Assert.assertEquals(soap12Fault.getUse(), actualparam
						.getSOAP12Binding4Wsdl11().getFault().getUse().value());
				Assert.assertEquals(soap12Fault.getRequired(), actualparam
						.getSOAP12Binding4Wsdl11().getFault().getRequired());
				Assert.assertEquals(soap12Fault.getEncodingStyle(), actualparam
						.getSOAP12Binding4Wsdl11().getFault().getRequired());

			} else {
				Assert.assertNull(actualparam.getSOAP12Binding4Wsdl11()
						.getFault());
			}

		} else if ((httpUrlEncoded != null) || (httpUrlReplacement != null)) {
			Assert.assertNotNull(actualparam.getHTTPBinding4Wsdl11());

			if (httpUrlEncoded != null) {
				Assert.assertTrue(actualparam.getHTTPBinding4Wsdl11()
						.isUrlEncoded());
			} else {
				Assert.assertFalse(actualparam.getHTTPBinding4Wsdl11()
						.isUrlEncoded());
			}

			if (httpUrlReplacement != null) {
				Assert.assertTrue(actualparam.getHTTPBinding4Wsdl11()
						.isUrlReplacement());
			} else {
				Assert.assertFalse(actualparam.getHTTPBinding4Wsdl11()
						.isUrlReplacement());
			}

		} else if ((mimeContents.size() > 0) || (mimeXmls.size() > 0)
				|| (mpartr.size() > 0)) {
			Assert.assertNotNull(actualparam.getMIMEBinding4Wsdl11());

			Assert.assertEquals(mimeContents.size(), actualparam
					.getMIMEBinding4Wsdl11().getContents().size());

			Assert.assertEquals(mimeXmls.size(), actualparam
					.getMIMEBinding4Wsdl11().getMimeXml().size());

			Assert.assertEquals(mpartr.size(), actualparam
					.getMIMEBinding4Wsdl11().getMultipartRelateds().size());

		} else {
			Assert.fail("unknown binding");
		}
	}

	@SuppressWarnings("unchecked")
	private void assertOnOperationParam(final String expectedName,
			final javax.wsdl.Message expectedMessage,
			final AbsItfParam actualparam) {
		Assert.assertNotNull(actualparam);
		Assert.assertEquals(expectedName, actualparam.getName());
		Assert.assertEquals(expectedMessage.getQName(), actualparam
				.getMessageName());

		Assert.assertEquals(expectedMessage.getParts().size(), actualparam
				.getParts().size());

		for (final javax.wsdl.Part expectedpart : (Collection<javax.wsdl.Part>) expectedMessage
				.getParts().values()) {
			final Part actualpart = actualparam.getPart(expectedpart.getName());
			Assert.assertNotNull(actualpart);

			Assert.assertEquals(expectedpart.getName(), actualpart
					.getPartQName().getLocalPart());
			if (expectedpart.getElementName() != null) {
				Assert.assertEquals(expectedpart.getElementName(), actualpart
						.getElement().getQName());
			} else if (expectedpart.getTypeName() != null) {
				Assert.assertEquals(expectedpart.getTypeName(), actualpart
						.getType().getQName());
			} else {
				Assert.fail("this part has an unknown type or element");
			}
		}
	}
}
