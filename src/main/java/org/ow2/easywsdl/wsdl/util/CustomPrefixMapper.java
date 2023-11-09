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
 
package org.ow2.easywsdl.wsdl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Mathias Beldame - EBM WebSourcing
 */
public class CustomPrefixMapper extends NamespacePrefixMapper {

	public Map<String, String> predefinedNamespaces = new HashMap<String, String>();

	/**
	 * Constructor.
	 */
	public CustomPrefixMapper() {
	    // nothing
	}

	public CustomPrefixMapper(String[] customNamespaces) {
		try {

			for (int i = 0; i < customNamespaces.length; i++) {

				String prefix = customNamespaces[i++];
				String namespace = customNamespaces[i];

				this.predefinedNamespaces.put(namespace, prefix);
			}
		} catch (Exception e) {
			System.out
					.println("Error while initialising custom namespaces. Using default namespaces.");
			this.predefinedNamespaces.clear();
		}

	}

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion,
			boolean requirePrefix) {

		if (namespaceUri.equals("http://schemas.xmlsoap.org/wsdl/")) {
			return "w";
		} else if (namespaceUri
				.equals("http://schemas.xmlsoap.org/wsdl/soap12/")) {
			return "soap12";
		} else if (namespaceUri.equals("http://schemas.xmlsoap.org/wsdl/soap/")) {
			return "soap";
		} else if (namespaceUri.equals("http://schemas.xmlsoap.org/wsdl/http/")) {
			return "http";
		} else if (namespaceUri.equals("http://schemas.xmlsoap.org/wsdl/mime/")) {
			return "mime";
		} else if (namespaceUri.equals("http://www.w3.org/2001/XMLSchema")) {
			return "xs";
		} else if (this.predefinedNamespaces.containsKey(namespaceUri)) {
			return this.predefinedNamespaces.get(namespaceUri);
		}

		return suggestion;
	}

	@Override
	public String[] getPreDeclaredNamespaceUris() {

		String[] custNS = new String[this.predefinedNamespaces.size() * 2];

        int i = 0;
		for (final Entry<String, String> entry : this.predefinedNamespaces.entrySet()) {
            final String ns = entry.getKey();
            final String prefix = entry.getValue();
            custNS[i++] = prefix;
            custNS[i++] = ns;
        }
		
		return custNS;
	}

}
