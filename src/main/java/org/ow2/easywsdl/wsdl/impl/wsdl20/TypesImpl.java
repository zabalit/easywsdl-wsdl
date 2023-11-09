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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ow2.easywsdl.schema.SchemaFactory;
import org.ow2.easywsdl.schema.api.Import;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.SchemaException;
import org.ow2.easywsdl.schema.api.SchemaReader.FeatureConstants;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractSchemaElementImpl;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractSchemaImpl;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractSchemaReader;
import org.ow2.easywsdl.schema.impl.SchemaImpl;
import org.ow2.easywsdl.schema.impl.SchemaReaderImpl;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractDescriptionImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractTypesImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.DescriptionType;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.TypesType;
import org.ow2.easywsdl.wsdl.util.Util;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class TypesImpl extends AbstractTypesImpl<TypesType, Schema, Import> implements Types {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TypesImpl(final TypesType types, final DescriptionImpl desc, final Map<URI, AbsItfSchema> imports, WSDLReaderImpl reader) throws WSDLException {
		super(types, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(this.model.getDocumentation(), this);

		final List<org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema> scs = new ArrayList<org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema>();
		for (final Object item : this.model.getAny()) {

			if (item instanceof org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema) {
				scs.add((org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema) item);
			}

			if (item instanceof org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Import) {
				org.ow2.easywsdl.schema.api.Import impt = null;
				try {
					impt = new org.ow2.easywsdl.schema.impl.ImportImpl((org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Import) item, Util.convertWSDLFeatures2SchemaFeature(this.desc), imports, desc.getDocumentBaseURI(), (AbstractSchemaReader)reader.getSchemaReader());
				} catch (SchemaException e) {
					throw new WSDLException(e);
				} catch (final URISyntaxException e) {
				    // TODO: Perhaps can we log a warning about this exception without throwing it ? 
                    throw new WSDLException(e);
                }
				this.importedSchemas.add(impt);
			}
		}

		this.addImportedSchemasInAllList();

		final Map<FeatureConstants, Object> features = Util.convertWSDLFeatures2SchemaFeature(this.desc);

		try {

			for (final org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema schema : scs) {
				try {
					Schema schemaImpt = new org.ow2.easywsdl.schema.impl.SchemaImpl(this.desc.getDocumentBaseURI(), schema, this.desc.getNamespaces(), ((AbstractDescriptionImpl) this.desc).getSchemaLocator(), features, imports, (SchemaReaderImpl) reader.getSchemaReader());
					((SchemaImpl) schemaImpt).initialize();
					
                    this.schemas.add(schemaImpt);
                } catch (final URISyntaxException e) {
                    // TODO: Perhaps can we log a warning about this exception without throwing it ? 
                    throw new WSDLException(e);
                }

			}
		} catch (final SchemaException e) {
			throw new WSDLException(e);
		}

		this.setAllNamespacesInAllSchemas();
		
		this.setSchemaInAllImport();
	}

	@Override
	public List<Element> getOtherElements() throws XmlException {
		final List<Element> res = super.getOtherElements();

		for (final Object item : this.model.getAny()) {
			if (item instanceof Element) {
				res.add((Element) item);
			}
		}

		return res;
	}

	public Schema createSchema() {
		Schema schema = null;
		try {
			schema = SchemaFactory.newInstance().newSchema();
			((AbstractSchemaImpl) schema).setParent(this);
		} catch (SchemaException e) {
			// Do nothing
			e.printStackTrace();
		}
		return schema;
	}

	@Override
	public void addSchema(Schema schema) {
		this.model.getAny().add(((AbstractSchemaElementImpl) schema).getModel());
		super.addSchema(schema);
	}

	public Schema removeSchema() {
        throw new UnsupportedOperationException();
	}

	public static TypesType replaceDOMElementByTypesType(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		TypesType res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("types")) && (childToReplace.getNamespaceURI().equals(Constants.WSDL_20_NAMESPACE)))) {
				JAXBElement<TypesType> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, TypesType.class);

				// change element by jaxb element
				((DescriptionType) ((AbstractWSDLElementImpl) parent).getModel()).getImportOrIncludeOrTypes().remove(childToReplace);
				((DescriptionType) ((AbstractWSDLElementImpl) parent).getModel()).getImportOrIncludeOrTypes().add(jaxbElement.getValue());

				// get element
				res = jaxbElement.getValue();
			}
		} catch (JAXBException e) {
			throw new WSDLException(e);
		}
		return res;
	}
}
