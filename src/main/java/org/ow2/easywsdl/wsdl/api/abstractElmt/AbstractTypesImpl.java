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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.absItf.AbsItfImport;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractSchemaImpl;
import org.ow2.easywsdl.schema.impl.Constants;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfTypes;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractTypesImpl<E, S extends Schema, Impt extends AbsItfImport> extends
AbstractWSDLElementImpl<E> implements AbsItfTypes<S, Impt> {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the desc
	 */
	protected AbsItfDescription desc;

	/**
	 * the list of schemas
	 */
	protected List<S> schemas = new ArrayList<S>();

	/**
	 * the list of imported schemas
	 */
	protected List<Impt> importedSchemas = new ArrayList<Impt>();

	public AbstractTypesImpl(E model, AbstractWSDLElementImpl parent) {
		super(model, parent);
	}

	protected void addImportedSchemasInAllList() {
		for (final Impt impt : this.importedSchemas) {
			if (impt.getSchema() != null) {
				this.schemas.add((S) impt.getSchema());
			}
		}
	}

	public List<S> getSchemas() {
		if (this.schemas == null) {
			this.schemas = new ArrayList<S>();
		}
		return this.schemas;
	}

	public List<Impt> getImportedSchemas() {
		if (this.importedSchemas == null) {
			this.importedSchemas = new ArrayList<Impt>();
		}
		return this.importedSchemas;
	}

	protected void setAllNamespacesInAllSchemas() {
		for (final Schema schema : this.schemas) {
			final Map<String, String> ns = ((org.ow2.easywsdl.schema.api.extensions.NamespaceMapperImpl) this.desc
					.getNamespaces()).getNamespaces();
			for (final Entry<String, String> entry : ns.entrySet()) {
				((org.ow2.easywsdl.schema.api.extensions.NamespaceMapperImpl) schema
						.getAllNamespaces()).addNamespace(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public String toString() {
		return this.getClass().getCanonicalName();
	}
	
	public void addSchema(S schema) {
		this.schemas.add(schema);
	}
	
	protected void reloadAllListInAllSchema() {
		List<AbsItfSchema> alls = new ArrayList<AbsItfSchema>();
		for(AbsItfSchema s: this.schemas) {
			if(!alls.contains(s)) {
				alls.add(s);
			}
			for(AbsItfImport impt: (List<AbsItfImport>) s.getImports()) {
				if(impt.getSchema() != null) {
					if(!alls.contains(impt.getSchema())) {
						alls.add(impt.getSchema());
					}
				}
			}
		}	
		
		
		for(AbsItfSchema s: alls) {
		    ((AbstractSchemaImpl)s).addImportElementsInAllList();
			((AbstractSchemaImpl)s).addIncludeElementsInAllList();
			((AbstractSchemaImpl)s).addRedefineElementsInAllList();
		}
	}
	
	
	protected void setSchemaInAllImport() {
		boolean found = false;
		for(AbsItfSchema s: this.schemas) {
			for(AbsItfImport impt: (List<AbsItfImport>) s.getImports()) {
				if(impt.getSchema() == null) {
					impt.setSchema(findSchema(impt.getNamespaceURI()));
					found = true;
				}
			}
		}
		
		if(found) {
			this.reloadAllListInAllSchema();
		}
	}

	protected AbsItfSchema findSchema(String namespaceURI) {
		AbsItfSchema res = null;
		if(Constants.SCHEMA_NAMESPACE.equals(namespaceURI)){
				res = (AbsItfSchema)org.ow2.easywsdl.schema.SchemaFactory.getDefaultSchema();
		} else {
			for(AbsItfSchema s: this.schemas) {
				if(s != null && s.getTargetNamespace() != null && s.getTargetNamespace().equals(namespaceURI)) {
					res = s;
					break;
				}
			}
		}
		return res;
	}
}
