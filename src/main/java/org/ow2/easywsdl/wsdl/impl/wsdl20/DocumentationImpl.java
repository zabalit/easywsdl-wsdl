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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ow2.easywsdl.schema.api.Documentation;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DocumentationType;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class DocumentationImpl extends
		AbstractWSDLElementImpl<List<DocumentationType>> implements
		Documentation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentationImpl() {
		super(new ArrayList<DocumentationType>(), null);
		final DocumentationType doc = new DocumentationType();
		this.model.add(doc);
	}

	public DocumentationImpl(final List<DocumentationType> docs,
			AbstractWSDLElementImpl parent) {
		super(docs, parent);
	}

	public String getContent() {
        final StringBuilder res = new StringBuilder();
		if (this.model != null) {
			for (final DocumentationType doc : this.model) {
				final Iterator<Object> it = doc
						.getContent().iterator();
				String text = null;
				while (it.hasNext()) {
					text = it.next().toString();
					res.append(text + "\n");
				}
			}
		}
		String result = "";
		if (res.length() > 0) {
			result = res.substring(0, res.lastIndexOf("\n"));
		}
		return result;
	}

	public void setContent(final String content) {
		this.model.clear();
		final DocumentationType doc = new DocumentationType();
		doc.getContent().add(content);
		this.model.add(doc);
	}

}
