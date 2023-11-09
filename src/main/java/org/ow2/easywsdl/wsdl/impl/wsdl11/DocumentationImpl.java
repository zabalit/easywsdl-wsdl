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
 
package org.ow2.easywsdl.wsdl.impl.wsdl11;

import org.ow2.easywsdl.schema.api.Documentation;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDocumentation;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class DocumentationImpl extends AbstractWSDLElementImpl<TDocumentation>
		implements Documentation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentationImpl() {
		super(new TDocumentation(), null);
	}

	public DocumentationImpl(final TDocumentation doc,
			final AbstractWSDLElementImpl parent) {
		super(doc, parent);
	}

	public String getContent() {
        final StringBuilder res = new StringBuilder();
		if (this.model != null) {
			if (this.model.getContent() != null) {
				for (final Object item : this.model
						.getContent()) {
					res.append(item);
				}
			}
		}
		return res.toString();
	}

	public void setContent(final String content) {
		this.model.getContent().clear();
		this.model.getContent().add(content);
	}

}
