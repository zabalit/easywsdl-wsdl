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

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.ow2.easywsdl.schema.SchemaFactory;
import org.ow2.easywsdl.schema.api.ComplexType;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.SchemaException;
import org.ow2.easywsdl.schema.api.Sequence;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Output;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLWriter;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription.WSDLVersionConstants;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation.MEPPatternConstants;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol.SOAPMEPConstants;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Body;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Fault;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import junit.framework.TestCase;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLGeneratorTest extends TestCase {

	private static final String TARGET_NAMESPACE = "http://petals.ow2.org/";

	public static QName cQName(String name) {
		return new QName(TARGET_NAMESPACE, name);
	}

	public Description createWSDL(Description desc) throws URISyntaxException, SchemaException {

		// set description
		desc.setTargetNamespace(TARGET_NAMESPACE);
		desc.addNamespace("tns", TARGET_NAMESPACE);
		if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
			desc.setQName(cQName("myDescription"));
		}
		desc.setDocumentBaseURI(new File("src/test/resources/descriptors").toURI());
		
		
		// create import relative to the WSDL document URI
		/*Import impt = null;
		try {
			impt = desc.createImport();
		} catch (WSDLImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		impt.setLocationURI("reservationList.xsd");
		impt.setNamespaceURI("http://greath.example.com/2004/schemas/reservationList");
		desc.addImport(impt);*/

		// create types
		Types types = desc.createTypes();
		Schema schema = types.createSchema();
		schema.setTargetNamespace(TARGET_NAMESPACE);
		schema.getAllNamespaces().addNamespace("tns", TARGET_NAMESPACE);
		
		final org.ow2.easywsdl.schema.api.Import typeImport = schema.createImport();
		typeImport.setLocationURI(URI.create("reservationList.xsd"));
		typeImport.setNamespaceURI("http://greath.example.com/2004/schemas/reservationList");
		schema.addImport(typeImport);
		
		final Type absComplexType = schema.createComplexType();
		absComplexType.setQName(new QName(TARGET_NAMESPACE, "seqEltType"));
		final ComplexType complexType = (ComplexType)absComplexType;
		final Sequence sequence = complexType.createSequence();
		final Element elementRef = sequence.createElement();
		elementRef.setRef(new QName("http://greath.example.com/2004/schemas/reservationList", "reservation"));
		elementRef.setMinOccurs(1);
		elementRef.setMaxOccurs("unbounded");
		
		sequence.addElement(elementRef);
		complexType.setSequence(sequence);
		schema.addType(complexType);
		
		
		Element inputElmt = schema.createElement();
		inputElmt.setQName(cQName("inputElmt"));
		inputElmt.setType(complexType);
		schema.addElement(inputElmt);

		Element outputElmt = schema.createElement();
		outputElmt.setQName(cQName("outputElmt"));
		outputElmt.setType(SchemaFactory.getDefaultSchema().getTypeString());
		schema.addElement(outputElmt);

		Element faultElmt = schema.createElement();
		faultElmt.setQName(cQName("faultElmt"));
		faultElmt.setType(SchemaFactory.getDefaultSchema().getTypeInt());
		schema.addElement(faultElmt);

		types.addSchema(schema);

		// set the types
		desc.setTypes(types);

		// create service
		Service service = desc.createService();
		service.setQName(cQName("myService"));

		// create endpoint
		Endpoint endpoint = service.createEndpoint();
		endpoint.setName("myEndpoint");
		endpoint.setAddress("http://localhost:8000/myEndpoint");

		// create interface
		InterfaceType itf = desc.createInterface();
		itf.setQName(cQName("myInterface"));

		// create operation
		Operation op = itf.createOperation();
		if (WSDLVersionConstants.WSDL20.equals(desc.getVersion())) {
			op.setPattern(MEPPatternConstants.IN_OUT);
		}
		op.setQName(cQName("myOperation"));

		// create input
		Input input = op.createInput();
		if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
			input.setName("myInput");
		}
		input.setMessageName(cQName("myInputMessage"));
		input.setElement(inputElmt);

		// create output
		Output output = op.createOutput();
		if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
			output.setName("myOutput");
		}
		output.setMessageName(cQName("myOutputMessage"));
		output.setElement(outputElmt);

		// create fault
		Fault fault = op.createFault();
		if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
			fault.setName("myFault");
		}
		fault.setMessageName(cQName("myFaultMessage"));
		fault.setElement(faultElmt);

		// add param to operation
		op.setInput(input);
		op.setOutput(output);
		op.addFault(fault);

		// add operation to interface
		itf.addOperation(op);

		// set interface to service
		if (WSDLVersionConstants.WSDL20.equals(desc.getVersion())) {
			service.setInterface(itf);
		}

		// add interface to description
		desc.addInterface(itf);

		// add endpoint to service
		service.addEndpoint(endpoint);





		// create binding
		Binding binding = desc.createBinding();
		binding.setQName(cQName("myBinding"));
		binding.setInterface(itf);
		binding.setTransportProtocol("http://schemas.xmlsoap.org/soap/http");

		for(Operation operation: itf.getOperations()) {
			BindingOperation bindingOperation = binding.createBindingOperation();
			bindingOperation.setQName(operation.getQName());

			if (WSDLVersionConstants.WSDL20.equals(desc.getVersion())) {
				if(operation.getOutput() != null) {
					bindingOperation.setMEP(SOAPMEPConstants.REQUEST_RESPONSE);
				} else {
					bindingOperation.setMEP(SOAPMEPConstants.ONE_WAY);
				}
			}
			bindingOperation
					.setSoapAction(operation.getQName().getNamespaceURI()
							+ (operation.getQName().getNamespaceURI().endsWith(
									"/") ? "" : "/")
							+ operation.getQName().getLocalPart().toString());

			// input
			if(operation.getInput() != null) {
				BindingInput binput = bindingOperation.createInput();
				bindingOperation.setInput(binput);
				
				if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
					SOAP11Binding4Wsdl11 soap11binding = binput.createSOAP11Binding4Wsdl11();
					SOAP11Body body = soap11binding.createBody();
					body.setUse(UseConstants.LITERAL);
					soap11binding.setBody(body);
					binput.setSOAP11Binding4Wsdl11(soap11binding);
				} else {
					// WSDL 2.0
					
					// TODO: add binding for WSDL 2.0
				}
			}

			// output
			if(operation.getOutput() != null) {
				BindingOutput boutput = bindingOperation.createOutput();
				bindingOperation.setOutput(boutput);
				
				if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
					SOAP11Binding4Wsdl11 soap11binding = boutput.createSOAP11Binding4Wsdl11();
					SOAP11Body body = soap11binding.createBody();
					body.setUse(UseConstants.LITERAL);
					soap11binding.setBody(body);
					boutput.setSOAP11Binding4Wsdl11(soap11binding);
				} else {
					// WSDL 2.0
					
					// TODO: add binding for WSDL 2.0
				}
			}

			// fault
			if(operation.getFaults() != null) {
				for(Fault faultop : operation.getFaults()) {
					BindingFault bfault = bindingOperation.createFault();
					bfault.setName(faultop.getName());
					bindingOperation.addFault(bfault);
					bfault.setRef(faultElmt.getQName());
					
					if (WSDLVersionConstants.WSDL11.equals(desc.getVersion())) {
						SOAP11Binding4Wsdl11 soap11binding = bfault.createSOAP11Binding4Wsdl11();
						SOAP11Fault soap11Fault = soap11binding.createFault();
						soap11Fault.setUse(UseConstants.LITERAL);
						soap11Fault.setName(faultop.getName());
						soap11binding.setFault(soap11Fault);
						bfault.setSOAP11Binding4Wsdl11(soap11binding);
					} else {
						// WSDL 2.0
						
						// TODO: add binding for WSDL 2.0
					}
				}
			}

			binding.addBindingOperation(bindingOperation);
		}


		// add binding to description
		desc.addBinding(binding);

		// create default binding
		Binding defaultBinding = desc.createDefaultSoapBinding("myDefaultBinding", endpoint, itf);

		// add default binding to description
		desc.addBinding(defaultBinding);

		// add service to description
		desc.addService(service);

		// assert on description
		Assert.assertNotNull(desc);
		Assert.assertEquals(TARGET_NAMESPACE, desc.getTargetNamespace());


		return desc;
	}
	
	private Source getXSDSchemaSource() throws SAXException  {
	    final XMLReader reader = XMLReaderFactory.createXMLReader();
	    
        // this will ignores the doctype declaration
        reader.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                if (systemId.endsWith(".dtd")) {
                    return new InputSource(new StringReader(" "));
                } else {
                    return null; // use default behavior
                }
            }
        });
        return new SAXSource(reader, new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                org.ow2.easywsdl.schema.impl.Constants.XSD_SCHEMA)));
	}

	public void testGeneratorWSDL11() throws URISyntaxException, SAXException, IOException, ParserConfigurationException, SchemaException {
		// WSDL 1.1
		Description desc = WSDLFactory.newInstance().newDescription(WSDLVersionConstants.WSDL11);
		desc = createWSDL(desc);
		WSDLWriter writer = WSDLFactory.newInstance().newWSDLWriter();
//		Document doc = writer.getDocument(desc);
//
//		System.out.println("WSDL 1.1:\n" + XMLPrettyPrinter.prettyPrint(doc));

		final Source xsdSchemaSource = getXSDSchemaSource();
		
        // Created WSDL validation
		
        this.validateDescription(
				desc,
				javax.xml.validation.SchemaFactory
						.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
						.newSchema(	new Source[] {
								// Default schemas needed by WSDL 1.1
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										org.ow2.easywsdl.schema.impl.Constants.XML_XSD)),
								xsdSchemaSource,
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										org.ow2.easywsdl.wsdl.impl.wsdl11.Constants.XSD_WSDL_11)),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl11/extensions/soap11.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl11/extensions/soap12.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl11/extensions/http.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl11/extensions/mime.xsd")),
										
								// Schemas needed by the test case
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"descriptors/wsdl20-instance.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"descriptors/reservationDetails.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"descriptors/reservationList.xsd"))}));

	}

	public void testGeneratorWSDL20() throws URISyntaxException, org.apache.woden.WSDLException, SAXException, IOException, ParserConfigurationException, SchemaException {
		// WSDL 2.0
		Description desc = WSDLFactory.newInstance().newDescription(WSDLVersionConstants.WSDL20);
		desc = createWSDL(desc);
		
		final Document generatedDoc = WSDLFactory.newInstance().newWSDLWriter().getDocument(desc);
		//System.out.println("WSDL 2.0:\n" + XMLPrettyPrinter.prettyPrint(generatedDoc));

		final Source xsdSchemaSource = getXSDSchemaSource();
		
		// Created WSDL validation
		this.validateDescription(
				desc,
				javax.xml.validation.SchemaFactory
						.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
						.newSchema(	new Source[] {
								// Default schemas needed by WSDL 2.0
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										org.ow2.easywsdl.schema.impl.Constants.XML_XSD)),
								xsdSchemaSource,
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										org.ow2.easywsdl.wsdl.impl.wsdl20.Constants.XSD_WSDL_20)),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl20/wsdl20-instance.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl20/extensions/soap.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl20/extensions/http.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"org/ow2/easywsdl/wsdl/wsdl20/extensions/rpc.xsd")),
										
								// Schemas needed by the test case
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"descriptors/reservationDetails.xsd")),
								new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
										"descriptors/reservationList.xsd"))}));


	}
	
	private void validateDescription(final Description desc, final javax.xml.validation.Schema schema) throws WSDLException, ParserConfigurationException, SAXException, IOException, URISyntaxException {
		
		final String wsdlStr = WSDLFactory.newInstance().newWSDLWriter().writeWSDL(desc);
		
		//System.out.println("wsdlStr:\n" + wsdlStr);
		final InputSource wsdlInputSource = new InputSource();
		wsdlInputSource.setCharacterStream(new StringReader(wsdlStr));
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(true);
		docBuilderFactory.setValidating(false);
		docBuilderFactory.setSchema(schema);
		final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		/*docBuilder.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) {

				return new InputSource(Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(
								publicId + systemId));
			}
		});*/
		
        final StringBuilder errors = new StringBuilder();
		docBuilder.setErrorHandler(new ErrorHandler() {

			public void error(final SAXParseException arg0) throws SAXException {
				errors.append("\n" + arg0.getMessage());
				
			}

			public void fatalError(SAXParseException arg0) throws SAXException {
				errors.append("\n" + arg0.getMessage());
			}

			public void warning(SAXParseException arg0) throws SAXException {
				errors.append("\n" + arg0.getMessage());
			}
			
		});
		
		final Document parsedDocument = docBuilder.parse(wsdlInputSource);
		parsedDocument.setDocumentURI(new File("src/test/resources/descriptors").toURI().toString());
		final Description parsedDescription = WSDLFactory.newInstance().newWSDLReader().read(parsedDocument);
		
		Assert.assertTrue("Validation errors exist:" + errors.toString(), errors.length() == 0);

	}


}
