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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import junit.framework.TestCase;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation.MEPPatternConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLDescriptorTest extends TestCase {

	WSDLReader reader = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reader = WSDLFactory.newInstance().newWSDLReader();
	}

	/**
     * Test the read of a WSDL 1.1 using the generic reader and its DOM Document API.
     */
    public void testReaderWSDL11FromDocument() throws URISyntaxException, WSDLException, ParserConfigurationException, SAXException, IOException {

		// create the Document object 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/WeatherForecast.wsdl");
		Document doc= db.parse(wsdlURL.toURI().toString());
		// the document URI is needed to be able to resolve URI of imported parts. 
		doc.setDocumentURI(wsdlURL.toURI().toString());
		
		final Description desc = reader.read(doc);
		
		this.assertWSDLWeatherForecast(desc);
	}

    /**
     * Test the read of a WSDL 1.1 defining a service with two endpoints that
     * share a port type, and check {@link Service#getInterface()} that must
     * return the port type name as interface name.
     */
    public void testReaderWSDL11_1Svc2PortSamePortType() throws URISyntaxException, WSDLException,
            ParserConfigurationException, SAXException, IOException {
        final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/1svc-2ports-samePortype.wsdl");
        final Description desc = reader.read(wsdlURL);

        Assert.assertNotNull(desc);
        // We check if the expected service exists
        Assert.assertEquals(1, desc.getServices().size());
        Assert.assertEquals("Service not found", QName
                .valueOf("{http://www.webservicex.net}WeatherForecast"), desc.getServices().get(0)
                .getQName());
        // We check if the expected endpoint number is right
        Assert.assertNotNull("No endpoint found", desc.getServices().get(0).getEndpoints());
        Assert.assertEquals("Expected endpoint number is wrong", 2, desc.getServices().get(0)
                .getEndpoints().size());

        // We check the service interface (port type)
        Assert.assertNotNull("Service interface not found", desc.getServices().get(0)
                .getInterface());
        Assert.assertEquals("Expected service interface name is wrong", new QName(
                "http://www.webservicex.net", "WeatherForecastPortType"), desc.getServices().get(0)
                .getInterface().getQName());
    }

    /**
     * Test the read of a WSDL 1.1 defining a service with two endpoints that
     * are associated to two different port types, and check
     * {@link Service#getInterface()} that must return an exception about
     * inconsistent endpoint.
     */
    public void testReaderWSDL11_1Svc2PortDifferentPortType() throws URISyntaxException, WSDLException,
            ParserConfigurationException, SAXException, IOException {
        final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/1svc-2ports-differentPortype.wsdl");
        final Description desc = reader.read(wsdlURL);

        Assert.assertNotNull(desc);
        // We check if the expected service exists
        Assert.assertEquals(1, desc.getServices().size());
        Assert.assertEquals("Service not found", QName
                .valueOf("{http://www.webservicex.net}WeatherForecast"), desc.getServices().get(0)
                .getQName());
        // We check if the expected endpoint number is right
        Assert.assertNotNull("No endpoint found", desc.getServices().get(0).getEndpoints());
        Assert.assertEquals("Expected endpoint number is wrong", 2, desc.getServices().get(0)
                .getEndpoints().size());

        // We check the service interface (port type)
        try {
            final InterfaceType itf = desc.getServices().get(0).getInterface();
            Assert.assertTrue("Interface found: " + itf.getQName().toString(), false);
        } catch (final WSDLException e) {
            // NOP, expected result
        }
    }

	private void assertWSDLWeatherForecast(final Description desc) {

		// assert on description
		Assert.assertNotNull(desc);
		Assert.assertEquals("http://www.webservicex.net", desc.getTargetNamespace());

		// assert on service
		Assert.assertEquals(1, desc.getServices().size());
		Assert.assertEquals(QName.valueOf("{http://www.webservicex.net}WeatherForecast"), desc
				.getServices().get(0).getQName());
		Assert.assertEquals(
				"Get one week weather forecast for valid zip code or Place name in USA", desc
				.getServices().get(0).getDocumentation().getContent());

		// assert on endpoint

		// endpoint O
		Assert.assertEquals(3, desc.getServices().get(0).getEndpoints().size());
		Assert.assertEquals("WeatherForecastSoap", desc.getServices().get(0).getEndpoints().get(0)
				.getName());
		Assert.assertEquals("http://www.webservicex.net/WeatherForecast.asmx", desc.getServices()
				.get(0).getEndpoints().get(0).getAddress());

		// endpoint O => binding
		Assert.assertEquals(new QName("http://www.webservicex.net", "WeatherForecastSoap"), desc.getServices()
				.get(0).getEndpoints().get(0).getBinding().getQName());
		Assert.assertEquals("http://schemas.xmlsoap.org/soap/http", desc.getServices().get(0)
				.getEndpoints().get(0).getBinding().getTransportProtocol());

		// endpoint 0 => binding => binding operation
		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByZipCode"), desc.getServices()
				.get(0).getEndpoints().get(0).getBinding().getBindingOperations().get(0).getQName());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getMEP().toString());

		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByPlaceName"), desc.getServices()
				.get(0).getEndpoints().get(0).getBinding().getBindingOperations().get(1).getQName());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(1).getMEP().toString());

		// endpoint 1
		Assert.assertEquals("WeatherForecastHttpGet", desc.getServices().get(0).getEndpoints().get(
				1).getName());
		Assert.assertEquals("http://www.webservicex.net/WeatherForecast.asmx", desc.getServices()
				.get(0).getEndpoints().get(1).getAddress());

		// endpoint 1 => binding
		Assert.assertEquals(new QName("http://www.webservicex.net", "WeatherForecastHttpGet"), desc
				.getServices().get(0).getEndpoints().get(1).getBinding().getQName());
		Assert.assertEquals("GET", desc.getServices().get(0).getEndpoints().get(1).getBinding()
				.getTransportProtocol());

		// endpoint 1 => binding => binding operation
		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByZipCode"), desc.getServices()
				.get(0).getEndpoints().get(1).getBinding().getBindingOperations().get(0).getQName());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(1).getBinding().getBindingOperations()
				.get(0).getMEP().toString());

		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByPlaceName"), desc.getServices()
				.get(0).getEndpoints().get(1).getBinding().getBindingOperations().get(1).getQName());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(1).getBinding().getBindingOperations()
				.get(1).getMEP().toString());

		// endpoint 2
		Assert.assertEquals("WeatherForecastHttpPost", desc.getServices().get(0).getEndpoints()
				.get(2).getName());
		Assert.assertEquals("http://www.webservicex.net/WeatherForecast.asmx", desc.getServices()
				.get(0).getEndpoints().get(1).getAddress());

		// endpoint 2 => binding
		Assert.assertEquals(new QName("http://www.webservicex.net", "WeatherForecastHttpPost"), desc
				.getServices().get(0).getEndpoints().get(2).getBinding().getQName());
		Assert.assertEquals("POST", desc.getServices().get(0).getEndpoints().get(2).getBinding()
				.getTransportProtocol());

		// endpoint 2 => binding => binding operation
		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByZipCode"), desc.getServices()
				.get(0).getEndpoints().get(2).getBinding().getBindingOperations().get(0).getQName());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(2).getBinding().getBindingOperations()
				.get(0).getMEP().toString());

		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByPlaceName"), desc.getServices()
				.get(0).getEndpoints().get(2).getBinding().getBindingOperations().get(1).getQName());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(2).getBinding().getBindingOperations()
				.get(1).getMEP().toString());

		// assert on interface
		Assert.assertEquals(new QName("http://www.webservicex.net", "WeatherForecastSoap"), desc.getServices()
				.get(0).getEndpoints().get(0).getBinding().getInterface().getQName());
		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByZipCode"), desc.getServices()
				.get(0).getEndpoints().get(0).getBinding().getBindingOperations().get(0)
				.getOperation().getQName());
		Assert.assertEquals(MEPPatternConstants.IN_OUT, desc.getServices().get(0).getEndpoints()
				.get(0).getBinding().getBindingOperations().get(0).getOperation().getPattern());

		// assert on param
		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByZipCodeSoapIn"), desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getOperation().getInput().getMessageName());
		Assert.assertEquals(new QName("http://www.webservicex.net", "GetWeatherByZipCodeSoapOut"), desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getOperation().getOutput().getMessageName());

	}

	public void testReaderWSDL11MissingImport() throws URISyntaxException, WSDLException {
		// WSDL 1.1
		try {
		    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/otherElmts/wsdl11/wsdl-missing-import.wsdl");
			final Description desc = reader.read(wsdlURL);
			fail("No exception thrown.");
		} catch(WSDLException e) {
		    // NOP: it's the expected exception
		} catch (final IOException e) {
		    fail("IOException thrown.");
		}
	}
	
	public void testReaderWSDL11AntislashImport() throws URISyntaxException, WSDLException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/otherElmts/wsdl11/wsdl-antislash-import.wsdl");
		final Description desc = reader.read(wsdlURL);
		assertNotNull(desc);
	}

//	public void testReaderWSDL11CyclicImport() throws URISyntaxException, WSDLException {
//		// WSDL 1.1
//		final Description desc = WSDLFactory.newInstance().newWSDLReader().readWSDL(
//				new URI("./src/test/resources/descriptors/otherElmts/wsdl11/wsdl67.wsdl"));
//		// No exception, exception just logged
//	}

	/**
	 * Test the read of a WSDL 1.1 using the generic reader and its URL API.
	 */
	public void testReaderWSDL11FromURL() throws URISyntaxException, WSDLException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/WeatherForecast.wsdl");
        final Description desc = reader.read(wsdlURL);
        
        this.assertWSDLWeatherForecast(desc);



//		System.out.println(desc);
//		System.out.println(desc.getTargetNamespace());
//		System.out.println(desc.getServices().size());
//		System.out.println(desc.getServices().get(0).getQName());
//		System.out.println(desc.getServices().get(0).getDocumentation().getContent());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().size());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getAddress());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getTransportProtocol());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getAddress());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getBinding().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getBinding()
//				.getTransportProtocol());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getAddress());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getBinding().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getBinding()
//				.getTransportProtocol());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(1).getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(1).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getBinding()
//				.getBindingOperations().get(0).getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getBinding()
//				.getBindingOperations().get(0).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getBinding()
//				.getBindingOperations().get(1).getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(1).getBinding()
//				.getBindingOperations().get(1).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getBinding()
//				.getBindingOperations().get(0).getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getBinding()
//				.getBindingOperations().get(0).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getBinding()
//				.getBindingOperations().get(1).getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(2).getBinding()
//				.getBindingOperations().get(1).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getInterface().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getPattern());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getInput().getMessageName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getOutput().getMessageName());

//		System.out.println("input = "
//				+ desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getInput().getElement()
//				.getQName());
//		System.out.println("output = "
//				+ desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getOutput().getElement()
//				.getQName());

//		for (final InterfaceType itf : desc.getInterfaces()) {
//			for (final org.ow2.easywsdl.wsdl.api.Operation op : itf.getOperations()) {
//				System.out.println(op.getSignature());
//			}
//		}
	}
	
	
    /**
     * Test the read of a WSDL 2.0 containing space in its name, using the
     * generic reader and its URL API.
     */
    public void testReaderWSDL20_DocumentBaseURIContainingSpaces()
			throws WSDLException, IOException, URISyntaxException {
	    
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/with space in name.wsdl");	
		final Description desc = reader.read(wsdlURL);
		
		Assert.assertFalse("The document base URI contains unencoded spaces", desc
				.getDocumentBaseURI().toString().contains(" "));
	}

    /**
     * Test the read of a WSDL 2.0 using the generic reader and its URL API.
     */
    public void testReaderWSDL20() throws URISyntaxException, WSDLException, IOException {
		// WSDL 2.0
        final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/reservationList.wsdl");    
        final Description desc = reader.read(wsdlURL);

		// assert on description
		Assert.assertNotNull(desc);
		Assert.assertNotNull(desc.getDocumentation().getContent());
		Assert.assertEquals("http://greath.example.com/2004/services/reservationList", desc
				.getTargetNamespace());

		// assert on service
		Assert.assertEquals(1, desc.getServices().size());
		Assert
		.assertEquals(
				QName
				.valueOf("{http://greath.example.com/2004/services/reservationList}reservationListService"),
				desc.getServices().get(0).getQName());
		Assert.assertEquals("the reservationListEndpoint\nthe reservationListEndpoint", desc
				.getServices().get(0).getDocumentation().getContent());

		// assert on endpoint
		Assert.assertEquals(1, desc.getServices().get(0).getEndpoints().size());
		Assert.assertEquals("reservationListEndpoint", desc.getServices().get(0).getEndpoints()
				.get(0).getName());
		Assert.assertEquals("http://greath.example.com/2004/reservationList", desc.getServices()
				.get(0).getEndpoints().get(0).getAddress());

		// assert on binding
		Assert
		.assertEquals(
				"{http://greath.example.com/2004/services/reservationList}reservationListSOAPBinding",
				desc.getServices().get(0).getEndpoints().get(0).getBinding().getQName()
				.toString());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/bindings/HTTP/", desc.getServices()
				.get(0).getEndpoints().get(0).getBinding().getTransportProtocol());

		// assert on binding operation
		Assert.assertEquals("{http://greath.example.com/2004/services/reservationList}retrieve",
				desc.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getQName().toString());
		Assert.assertEquals("http://www.w3.org/2003/05/soap/mep/request-response", desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getMEP().toString());

		// assert on interface
		Assert
		.assertEquals(
				"{http://greath.example.com/2004/services/reservationList}reservationListInterface",
				desc.getServices().get(0).getEndpoints().get(0).getBinding().getInterface()
				.getQName().toString());
		Assert.assertEquals("{http://greath.example.com/2004/services/reservationList}retrieve",
				desc.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getOperation().getQName().toString());
		Assert.assertEquals(MEPPatternConstants.IN_OUT, desc.getServices().get(0).getEndpoints()
				.get(0).getBinding().getBindingOperations().get(0).getOperation().getPattern());

		// assert on param
		Assert.assertEquals("{http://greath.example.com/2004/services/reservationList}In", desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getOperation().getInput().getMessageName().toString());
		Assert.assertEquals("{http://greath.example.com/2004/services/reservationList}Out", desc
				.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations()
				.get(0).getOperation().getOutput().getMessageName().toString());

//		System.out.println(desc);
//		System.out.println(desc.getDocumentation().getContent());
//		System.out.println(desc.getTargetNamespace());
//		System.out.println(desc.getServices().size());
//		System.out.println(desc.getServices().get(0).getQName());
//		System.out.println(desc.getServices().get(0).getDocumentation().getContent());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().size());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getAddress());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getTransportProtocol());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getQName().toString());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getMEP());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getInterface().getQName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getQName().toString());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getPattern());
//
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getInput().getMessageName());
//		System.out.println(desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getOutput().getMessageName());
//
//		// System.out.println("input = " +
//		// desc.getServices().get(0).getEndpoints
//		// ().get(0).getBinding().getBindingOperations
//		// ().get(0).getOperation().getInput().getElement().getQName());
//		System.out.println("output = "
//				+ desc.getServices().get(0).getEndpoints().get(0).getBinding()
//				.getBindingOperations().get(0).getOperation().getOutput().getElement()
//				.getQName());
//
//		for (final InterfaceType itf : desc.getInterfaces()) {
//			for (final org.ow2.easywsdl.wsdl.api.Operation op : itf.getOperations()) {
//				System.out.println(op.getSignature());
//			}
//		}
	}

	public void testWriterWSDL11() throws WSDLException, URISyntaxException, WSDLException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/WeatherForecast.wsdl");    
        final Description desc = reader.read(wsdlURL);
		final String res = WSDLFactory.newInstance().newWSDLWriter().writeWSDL(desc);
		Assert.assertNotNull(res);

//		System.out.println(res);
	}

	public void testWriterWSDL20() throws WSDLException, URISyntaxException, WSDLException, IOException {
		// WSDL 2.0
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/reservationList.wsdl");    
        final Description desc = reader.read(wsdlURL);
		final String res = WSDLFactory.newInstance().newWSDLWriter().writeWSDL(desc);
		Assert.assertNotNull(res);

//		System.out.println(res);
	}

	public void testModifierWSDL11() throws WSDLException, URISyntaxException, WSDLException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/WeatherForecast.wsdl");    
        final Description desc = reader.read(wsdlURL);

		// modify doc
		desc.setDocumentation(desc.createDocumentation());
		desc.getDocumentation().setContent("New DocumentationImpl");
		desc.getServices().get(0).getEndpoints().get(0).setName("modifyEndpoint");
		desc.getServices().get(0).getEndpoints().get(0).setAddress("modifyEndpointAddress");

		final Document doc = WSDLFactory.newInstance().newWSDLWriter().getDocument(desc);
		// The DOM Document is build from the WSDL URL
		doc.setDocumentURI(wsdlURL.toURI().toString());
		final Description descFromDoc = WSDLFactory.newInstance().newWSDLReader().read(doc);

		// verify modification
		Assert.assertEquals("New DocumentationImpl", descFromDoc.getDocumentation().getContent());
		Assert.assertEquals("modifyEndpoint", descFromDoc.getServices().get(0).getEndpoints().get(0)
				.getName());
		Assert.assertEquals("modifyEndpointAddress", descFromDoc.getServices().get(0).getEndpoints()
				.get(0).getAddress());

//		System.out.println(descFromDoc.getDocumentation().getContent());

//		System.out.println("\n\nDEBUT:");
//		System.out.println(WSDLFactory.newInstance().newWSDLWriter().writeWSDL(descFromDoc));

	}

	public void testModifierWSDL20() throws WSDLException, URISyntaxException, WSDLException, IOException {
		// WSDL 2.0
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/reservationList.wsdl");    
        final Description desc = reader.read(wsdlURL);

		// modify doc
		desc.getDocumentation().setContent("New DocumentationImpl");
		desc.getServices().get(0).getEndpoints().get(0).setName("modifyEndpoint");
		desc.getServices().get(0).getEndpoints().get(0).setAddress("modifyEndpointAddress");

		final Document doc = WSDLFactory.newInstance().newWSDLWriter().getDocument(desc);
        // The DOM Document is build from the WSDL URL
		doc.setDocumentURI(wsdlURL.toURI().toString());
		final Description descFromDoc = WSDLFactory.newInstance().newWSDLReader().read(doc);

		// verify modification
		Assert.assertEquals("New DocumentationImpl", descFromDoc.getDocumentation().getContent());
		Assert.assertEquals("modifyEndpoint", descFromDoc.getServices().get(0).getEndpoints().get(0)
				.getName());
		Assert.assertEquals("modifyEndpointAddress", descFromDoc.getServices().get(0).getEndpoints()
				.get(0).getAddress());

//		System.out.println(descFromDoc.getDocumentation().getContent());
	}

	public void testImportWSDL11() throws WSDLException, URISyntaxException, WSDLException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/importwsdl/CustomerSearch.wsdl");    
        final Description desc = reader.read(wsdlURL);

		// assert
		Assert.assertEquals(1, desc.getInterfaces().size());
		Assert.assertEquals("{http://searchcustomer.services.stv.orange.com}CustomerSearchService",
				desc.getInterfaces().get(0).getQName().toString());

//		System.out.println("\n\nDEBUT IMPORT:");
//		System.out.println(WSDLFactory.newInstance().newWSDLWriter().writeWSDL(desc));
//
//		System.out.println(desc.getInterfaces().size());
//		System.out.println(desc.getInterfaces().get(0).getQName());

	}

	public void testOtherElmtsAndAttributesInWSDL11() throws URISyntaxException, XmlException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/otherElmts/wsdl11/CustomerSearch.wsdl");    
        final Description desc = reader.read(wsdlURL);

		Assert.assertNotNull(desc);
		Assert.assertNotNull(desc.getOtherElements());
		Assert.assertEquals(1, desc.getOtherElements().size());
		Assert.assertEquals("importedDocuments", desc.getOtherElements().get(0).getLocalName());

		Assert.assertNotNull(desc.getImports());
		Assert.assertEquals(1, desc.getImports().size());
		Assert.assertNotNull(desc.getImports().get(0).getOtherAttributes());
		Assert.assertEquals(1, desc.getImports().get(0).getOtherAttributes().size());
		Assert.assertEquals("verif", desc.getImports().get(0).getOtherAttributes().get(
				new QName("http://petals.ow2.org/wsdlExtensions", "test")));

	}

	public void testOtherElmtsAndAttributesInWSDL20() throws URISyntaxException, XmlException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/otherElmts/wsdl20/reservationList.wsdl");    
        final Description desc = reader.read(wsdlURL);

		Assert.assertNotNull(desc);
		Assert.assertNotNull(desc.getOtherElements());
		Assert.assertEquals(1, desc.getOtherElements().size());
		Assert.assertEquals("importedDocuments", desc.getOtherElements().get(0).getLocalName());

		Assert.assertNotNull(desc.getInterfaces());
		Assert.assertEquals(1, desc.getInterfaces().size());
		Assert.assertNotNull(desc.getInterfaces().get(0).getOtherAttributes());
		Assert.assertEquals(1, desc.getInterfaces().get(0).getOtherAttributes().size());
		Assert.assertEquals("verif", desc.getInterfaces().get(0).getOtherAttributes().get(
				new QName("http://petals.ow2.org/wsdlExtensions", "test")));

	}


	public void testSchemaLocationInWSDL() throws URISyntaxException, XmlException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/otherElmts/wsdl11/CustomerSearchService.wsdl");    
        final Description desc = reader.read(wsdlURL);

		Assert.assertNotNull(desc);

//		System.out.println("desc.getSchemaLocation() = " + desc.getSchemaLocation());

		assertEquals("{http://petals.ow2.org/schemaTest=schemaTest.xsd}", desc.getSchemaLocation().toString());

//		Document doc = WSDLFactory.newInstance().newWSDLWriter().getDocument(desc);
//
//		System.out.println("Doc:\n" + XMLPrettyPrinter.prettyPrint(doc));
	}
	
	
	/**
     * Test the read of a WSDL 2.0 using the generic reader and its DOM Document API.
     */
    public void testReaderWSDL20FromDocument() throws URISyntaxException, WSDLException, ParserConfigurationException, SAXException, IOException {

		// create the Document object 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/car.wsdl");
		Document doc= db.parse(wsdlURL.toURI().toString());
		// the document URI is needed to be able to resolve URI of imported parts. 
		doc.setDocumentURI(wsdlURL.toURI().toString());
		
		final Description desc = reader.read(doc);
		
		assertEquals(1, desc.getInterfaces().size());
	}
    
	public void testWSDLEquals() throws URISyntaxException, WSDLException, IOException {
		// WSDL 1.1
	    final URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("descriptors/WeatherForecast.wsdl");
        final Description desc = reader.read(wsdlURL);
        final Description desc2 = reader.read(wsdlURL);
        
        Assert.assertTrue(desc.equals(desc2));
        desc2.setQName(new QName("http://easywsdl.ow2.org/namespace", "testcase"));
        Assert.assertFalse(desc.equals(desc2));
        
	}
}
