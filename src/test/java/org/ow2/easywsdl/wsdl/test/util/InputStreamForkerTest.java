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
 
package org.ow2.easywsdl.wsdl.test.util;

import java.io.IOException;
import java.io.InputStream;

import org.ow2.easywsdl.wsdl.util.InputStreamForker;

import junit.framework.TestCase;

/**
 * @author Christophe Deneux - Capgemini Sud
 */
public class InputStreamForkerTest extends TestCase {
    
    private static final String AVAILABLE_FILE = "org/ow2/easywsdl/wsdl/test/util/inputstreamforker/available-file.txt";

    public void testAvailableWithReading() {

        try {
            final InputStream isOriginal = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(AVAILABLE_FILE);

            final InputStreamForker isf = new InputStreamForker(isOriginal);

            final InputStream isOne = isf.getInputStreamOne();
            final byte[] bufferOne = new byte[698];
            int bytesRead = isOne.read(bufferOne);
            TestCase.assertEquals(698, bytesRead);
            TestCase.assertTrue(new String(bufferOne).endsWith("Hi,\n\nM"));

            int availableOne = isOne.available();
            int availableTwo = isf.getInputStreamTwo().available();

            TestCase.assertEquals(22, availableOne);
            TestCase.assertEquals(720, availableTwo);

        } catch (final IOException e) {
            e.printStackTrace();
            TestCase.fail();
        }

    }

    public void testReadFullAtOnce() {

        try {
            final InputStream isOriginal = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(AVAILABLE_FILE);

            final InputStreamForker isf = new InputStreamForker(isOriginal);

            final InputStream isOne = isf.getInputStreamOne();
            final byte[] bufferOne = new byte[1024];
            int bytesReadOne = isOne.read(bufferOne);
            TestCase.assertEquals(720, bytesReadOne);

            TestCase.assertEquals(0, isOne.available());
            TestCase.assertEquals(720, isf.getInputStreamTwo().available());

            final InputStream isTwo = isf.getInputStreamTwo();
            final byte[] bufferTwo = new byte[1024];
            final int bytesReadTwo = isTwo.read(bufferTwo);
            TestCase.assertEquals(720, bytesReadTwo);

            TestCase.assertEquals(0, isOne.available());
            TestCase.assertEquals(0, isf.getInputStreamTwo().available());

        } catch (IOException e) {
            e.printStackTrace();
            TestCase.fail();
        }

    }

    public void testReadPartial() {

        try {
            final InputStream isOriginal = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(AVAILABLE_FILE);

            final InputStreamForker isf = new InputStreamForker(isOriginal);

            final InputStream isOne = isf.getInputStreamOne();
            final InputStream isTwo = isf.getInputStreamTwo();

            final byte[] bufferOneOne = new byte[704];
            int bytesReadOneOne = isOne.read(bufferOneOne);
            TestCase.assertEquals(704, bytesReadOneOne);
            TestCase.assertEquals(16, isOne.available());

            TestCase.assertEquals(720, isTwo.available());

            final byte[] bufferOneTwo = new byte[10];
            int bytesReadOneTwo = isOne.read(bufferOneTwo);
            TestCase.assertEquals(10, bytesReadOneTwo);
            TestCase.assertEquals(6, isOne.available());

            TestCase.assertEquals(720, isTwo.available());

            final byte[] bufferOneThree = new byte[10];
            int bytesReadOneThree = isOne.read(bufferOneThree);
            TestCase.assertEquals(6, bytesReadOneThree);
            TestCase.assertEquals(0, isOne.available());

            TestCase.assertEquals(720, isTwo.available());
            TestCase.assertTrue((new String(
                    bufferOneOne)
                    + new String(bufferOneTwo)
                    + new String(bufferOneThree, 0, bytesReadOneThree)).endsWith("Hi,\n\nMy length is 27 bytes.\n"));

            final byte[] bufferTwoOne = new byte[15];
            int bytesReadTwoOne = isTwo.read(bufferTwoOne);
            TestCase.assertEquals(15, bytesReadTwoOne);
            TestCase.assertEquals(705, isTwo.available());

            final byte[] bufferTwoTwo = new byte[707];
            int bytesReadTwoTwo = isTwo.read(bufferTwoTwo);
            TestCase.assertEquals(705, bytesReadTwoTwo);
            TestCase.assertEquals(0, isTwo.available());

            TestCase.assertTrue((new String(bufferTwoOne) + new String(bufferTwoTwo, 0, bytesReadTwoTwo))
                    .endsWith("Hi,\n\nMy length is 27 bytes.\n"));
            
            final byte[] bufferTwoThree = new byte[150];
            int bytesReadTwoThree = isTwo.read(bufferTwoThree);
            TestCase.assertEquals("EOF must be reached", -1, bytesReadTwoThree);

        } catch (IOException e) {
            e.printStackTrace();
            TestCase.fail();
        }

    }

    public void testPartialReadAndClose() {

        try {
            final InputStream isOriginal = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(AVAILABLE_FILE);

            final InputStreamForker isf = new InputStreamForker(isOriginal);

            final InputStream isOne = isf.getInputStreamOne();
            final InputStream isTwo = isf.getInputStreamTwo();

            final byte[] bufferOneOne = new byte[704];
            int bytesReadOneOne = isOne.read(bufferOneOne);
            TestCase.assertEquals(704, bytesReadOneOne);
            TestCase.assertEquals(16, isOne.available());

            TestCase.assertEquals(720, isTwo.available());

            final byte[] bufferOneTwo = new byte[10];
            int bytesReadOneTwo = isOne.read(bufferOneTwo);
            TestCase.assertEquals(10, bytesReadOneTwo);
            TestCase.assertEquals(6, isOne.available());
            
            isOne.close();
            // As the forked input stream 'isOne' is closed, available bytes of
            // the orihinal input stream are no more available for this forked
            // input stream.
            TestCase.assertEquals(0, isOne.available());

            TestCase.assertEquals(720, isTwo.available());

            TestCase.assertEquals(720, isTwo.available());
            TestCase.assertTrue(
                    (new String(bufferOneOne) + new String(bufferOneTwo)).endsWith("Hi,\n\nMy length is 27 b"));

            final byte[] bufferTwoOne = new byte[710];
            int bytesReadTwoOne = isTwo.read(bufferTwoOne);
            TestCase.assertEquals(710, bytesReadTwoOne);
            TestCase.assertEquals(10, isTwo.available());

            final byte[] bufferTwoTwo = new byte[15];
            int bytesReadTwoTwo = isTwo.read(bufferTwoTwo);
            TestCase.assertEquals(10, bytesReadTwoTwo);
            TestCase.assertEquals(0, isTwo.available());

            TestCase.assertTrue((new String(bufferTwoOne) + new String(bufferTwoTwo, 0, bytesReadTwoTwo))
                    .endsWith("Hi,\n\nMy length is 27 bytes.\n"));

        } catch (IOException e) {
            e.printStackTrace();
            TestCase.fail();
        }

    }

    public void testClosedInputStreamNoException() {
        try {
            final InputStream isOriginal = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(AVAILABLE_FILE);

            final InputStreamForker isf = new InputStreamForker(isOriginal);

            final InputStream isOne = isf.getInputStreamOne();
            final InputStream isTwo = isf.getInputStreamTwo();

            final byte[] bufferOneOne = new byte[1024];
            int bytesReadOneOne = isOne.read(bufferOneOne);
            TestCase.assertEquals(720, bytesReadOneOne);
            TestCase.assertEquals(0, isOne.available());
            TestCase.assertEquals(720, isTwo.available());
            TestCase.assertTrue(
                    new String(bufferOneOne, 0, bytesReadOneOne).endsWith("Hi,\n\nMy length is 27 bytes.\n"));

            isOriginal.close();

            final byte[] bufferTwoOne = new byte[720];
            int bytesReadTwoOne = isTwo.read(bufferTwoOne);
            TestCase.assertEquals(720, bytesReadTwoOne);
            try {
                TestCase.assertEquals(0, isTwo.available());
                TestCase.assertEquals("Not thrown IOException", true, false);
            } catch (final IOException e) {
                // NOP. It is the expected case.
            }
            TestCase.assertTrue(
                    new String(bufferTwoOne, 0, bytesReadTwoOne).endsWith("Hi,\n\nMy length is 27 bytes.\n"));

        } catch (IOException e) {
            e.printStackTrace();
            TestCase.fail();
        }
    }
    
    public void testClosedInputStreamWithException() {
        try {
            final InputStream isOriginal = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(AVAILABLE_FILE);

            final InputStreamForker isf = new InputStreamForker(isOriginal);

            final InputStream isOne = isf.getInputStreamOne();
            final InputStream isTwo = isf.getInputStreamTwo();

            final byte[] bufferOneOne = new byte[704];
            int bytesReadOneOne = isOne.read(bufferOneOne);
            TestCase.assertEquals(704, bytesReadOneOne);
            TestCase.assertEquals(16, isOne.available());
            TestCase.assertEquals(720, isTwo.available());
            TestCase.assertTrue(new String(bufferOneOne, 0, bytesReadOneOne).endsWith("Hi,\n\nMy leng"));

            isOriginal.close();

            final byte[] bufferTwoOne = new byte[1024];
            isTwo.read(bufferTwoOne);
            TestCase.assertEquals("Not thrown IOException", true, false);

        } catch (IOException e) {
            // NOP. It is the expected case. A IOEXception is thrown when calling read of the second forked stream
        }
    }

}
