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
 
package org.ow2.easywsdl.wsdl.api.abstractElmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.SchemaFactory;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfImport;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfParam;
import org.ow2.easywsdl.wsdl.impl.wsdl11.MessageImpl;
import org.ow2.easywsdl.wsdl.impl.wsdl11.TypesImpl;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public abstract class AbstractPartImpl<E> extends AbstractWSDLElementImpl<E> implements Part {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(AbstractPartImpl.class.getName());


	/**
	 * the element name
	 */
	protected QName elementName;

	/**
	 * the type name
	 */
	protected QName typeName;

        private Element schemaElement;


	public AbstractPartImpl(E model, AbstractWSDLElementImpl parent) {
		super(model, parent);
                schemaElement = null;
	}

	/**
	 * @return the interface
	 */
	public AbsItfParam getParam() {
		AbsItfParam param = null;
		if(parent instanceof AbstractParamImpl) {
			param = (AbsItfParam) parent;
		}
		return param;
	}

	public final Element getElement() {
            if (schemaElement == null)
                schemaElement = findSchemaElement();
            return schemaElement;
        }


	@SuppressWarnings("unchecked")
	private final Element findSchemaElement() {
		Element res = null;
		Element item = null;

		AbsItfDescription desc = null;
		if(parent instanceof AbstractParamImpl) {
			desc = ((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) ((AbstractParamImpl) this.parent)
					.getOperation()).getInterface()).getDescription();
		}
		if(parent instanceof MessageImpl) {
			desc = ((MessageImpl)parent).getDescription();
		}

		//final Types types = (Types) desc.getTypes();
		if (this.elementName != null) {
			List<? extends org.ow2.easywsdl.schema.api.Element> elmts = this.findElementsInAllSchema(desc, this.elementName);
			if(elmts.size() == 1) {
				res = elmts.get(0);
			}
			if(elmts.size() > 1) {
				LOG.warning("several same elements exists: optimistic result => take the first element");
				res = elmts.get(0);
			}
		}
		return res;
	}

	private List<? extends org.ow2.easywsdl.schema.api.Element> findElementsInAllSchema(
			AbsItfDescription desc, QName element) {
		List<org.ow2.easywsdl.schema.api.Element> res = new ArrayList<org.ow2.easywsdl.schema.api.Element>();

		if(desc != null && element != null) {
			for(AbsItfSchema schema: (List<AbsItfSchema>)desc.getTypes().getSchemas()) {
				res.addAll((Collection<? extends org.ow2.easywsdl.schema.api.Element>) schema.findElementsInAllSchema(element));
				for(org.ow2.easywsdl.schema.api.absItf.AbsItfImport imptSchema: (List<org.ow2.easywsdl.schema.api.absItf.AbsItfImport>)schema.getImports()) {
					if(imptSchema.getSchema() != null) {
						res.addAll((Collection<? extends org.ow2.easywsdl.schema.api.Element>) imptSchema.getSchema().findElementsInAllSchema(element));
					} else {
						LOG.severe("No schema linked to the import with namespace: " + imptSchema.getNamespaceURI());
					}
				}
				for(org.ow2.easywsdl.schema.api.absItf.AbsItfInclude inclSchema: (List<org.ow2.easywsdl.schema.api.absItf.AbsItfInclude>)schema.getIncludes()) {
					if(inclSchema.getSchema() != null) {
						res.addAll((Collection<? extends org.ow2.easywsdl.schema.api.Element>) inclSchema.getSchema().findElementsInAllSchema(element));
					}
				}
			}

			for(AbsItfSchema schema: (List<AbsItfSchema>)desc.getTypes().getImportedSchemas()) {
				if(schema != null) {
					res.addAll((Collection<? extends org.ow2.easywsdl.schema.api.Element>) schema.findElementsInAllSchema(element));
				}
			}
			for(AbsItfImport impt: (List<AbsItfImport>)desc.getImports()) {
				if(impt != null) {
					res.addAll((Collection<? extends org.ow2.easywsdl.schema.api.Element>) this.findElementsInAllSchema((AbsItfDescription) impt.getDescription(), element));
				}
			}
		}
		return res;
	}

	public Type getType() {
		Type res = null;
		Type item = null;
		AbsItfDescription desc = null;
		if(parent instanceof AbstractParamImpl) {
			desc = ((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) ((AbstractParamImpl) this.parent)
					.getOperation()).getInterface()).getDescription();
		}
		if(parent instanceof MessageImpl) {
			desc = ((MessageImpl)parent).getDescription();
		}
		final Types types = (Types) desc.getTypes();
		if (this.typeName != null) {
			if ((types != null) && (types.getSchemas() != null)) {
				for (final Schema schema : types.getSchemas()) {
					item = schema.getType(this.typeName);
					if (item != null) {
						res = item;
						break;
					}
				}
			}

			if(res == null) {
				item = (Type) SchemaFactory.getDefaultSchema().getType(this.typeName);
				if (item != null) {
					res = item;
				}
			}

			if(res == null) {
				item = (Type) TypesImpl.getSoap11encTypesSchema().getType(this.typeName);
				if (item != null) {
					res = item;
				}
			}

			if(res == null) {
				item = (Type) TypesImpl.getSoap12encTypesSchema().getType(this.typeName);
				if (item != null) {
					res = item;
				}
			}
		}
		return res;
	}
}
