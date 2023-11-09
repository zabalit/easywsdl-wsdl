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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.ow2.easywsdl.wsdl.test.util.XSLTConverter;
import org.xml.sax.InputSource;

/**
 * This test case has been created to display result of WSDL convertion.
 * @author Christophe DENEUX - Capgemini Sud
 */
public class WSDLConverterTest extends TestCase {

    public void testWSDLConverter11To20() throws IOException {

        final URL wsdl11URL = Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/WeatherForecast.wsdl");
        final InputSource wsdl20inputSource = XSLTConverter.getInstance().convert(wsdl11URL);

        final byte[] buffer = new byte[1024];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int readBytes;
        do {
            readBytes = wsdl20inputSource.getByteStream().read(buffer);
            if (readBytes != -1) {
                baos.write(buffer, 0, readBytes);
                //System.out.print(new String(buffer, 0, readBytes));
            }
        } while (readBytes > 0);

        baos.close();

    }

}
