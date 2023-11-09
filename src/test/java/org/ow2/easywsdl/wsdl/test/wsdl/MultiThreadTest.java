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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;

/**
 * @author Nicolas Boissel-Dallier - EBM WebSourcing
 */
public class MultiThreadTest  extends TestCase {

	public void testMultiThread() throws WSDLException, URISyntaxException, javax.wsdl.WSDLException, InterruptedException, Throwable {


		WSDLReader readerDesc = WSDLFactory.newInstance().newWSDLReader();
		javax.wsdl.xml.WSDLReader readerDef = javax.wsdl.factory.WSDLFactory.newInstance().newWSDLReader();
		readerDef.setFeature("javax.wsdl.verbose", false);

		List<ThreadWSDLReader> readers = new ArrayList<ThreadWSDLReader>();
		List<URL> wsdls = null;
		Iterator<URL> it = WSDLList.getWsdls11().iterator();
		while(it.hasNext()) {
			wsdls = new ArrayList<URL>();
			if(it.hasNext()) {
				wsdls.add(it.next());
			}
			if(it.hasNext()) {
				wsdls.add(it.next());
			}
			if(it.hasNext()) {
				wsdls.add(it.next());
			}
			readers.add(new ThreadWSDLReader(readerDesc, readerDef, wsdls));
		}

		for(ThreadWSDLReader reader: readers) {
			reader.start();
		}

		for(ThreadWSDLReader reader: readers) {
				reader.join();
		}
		
		for(ThreadWSDLReader reader: readers) {
            if (reader.error != null) {
                throw reader.error;
            }
		}
		
	}

	private static class ThreadWSDLReader extends Thread {

		private WSDLReader reader;
		
		private Throwable error = null;

		private List<URL> wsdls;

		public ThreadWSDLReader(WSDLReader reader, javax.wsdl.xml.WSDLReader readerDef, List<URL> wsdls) {
			this.reader = reader;
			this.wsdls = wsdls;
		}

		@Override
		public void run() {
			Description desc = null;
			try {
				for(int i = 0; i < 10; i++) {
					for(URL url: wsdls) {
						desc = reader.read(url);
						//System.out.println("desc.getQName() = " + desc.getQName());
						if(desc == null){
							throw new AssertionFailedError();
						}
					}
				}
			} catch (WSDLException e) {
				this.error = e;
			} catch (IOException e) {
                this.error = e;
            } catch (URISyntaxException e) {
                this.error = e;
            } catch (AssertionFailedError e) {
                this.error = e;
            }
		}
	}
}
