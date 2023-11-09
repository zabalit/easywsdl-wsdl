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

import java.io.IOException;
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
import org.ow2.easywsdl.schema.api.SchemaReader;
import org.ow2.easywsdl.schema.api.SchemaReader.FeatureConstants;
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
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TTypes;
import org.ow2.easywsdl.wsdl.util.Util;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class TypesImpl extends AbstractTypesImpl<TTypes, Schema, Import> implements Types {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Schema soap11encTypesSchema = null;

	private static Schema soap12encTypesSchema = null;

	static {
		// load default soap type
		try {
			SchemaReader schemaReader = SchemaFactory.newInstance().newSchemaReader();
			soap11encTypesSchema = schemaReader.read(DescriptionImpl.class.getResource("/org/ow2/easywsdl/wsdl/wsdl11/extensions/soapenc11.xsd"));
			soap12encTypesSchema = schemaReader.read(DescriptionImpl.class.getResource("/org/ow2/easywsdl/wsdl/wsdl11/extensions/soapenc12.xsd"));
		} catch (SchemaException e) {
			throw new RuntimeException("SOAP 11 or SOAP 12 schema is missing in resources", e);
		} catch (URISyntaxException e) {
			throw new RuntimeException("SOAP 11 or SOAP 12 schema is missing in resources", e);
		} catch (final IOException e) {
			throw new RuntimeException("SOAP 11 or SOAP 12 schema is missing in resources", e);
		}

	}

	public TypesImpl(final TTypes types, final DescriptionImpl desc, final Map<URI, AbsItfSchema> imports, WSDLReaderImpl reader) throws WSDLException {
		super(types, desc);
		this.desc = desc;

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(this.model.getDocumentation(), this);

		final List<org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema> scs = new ArrayList<org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema>();
		for (final Object item : this.model.getAny()) {

			if (item instanceof org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema) {
				scs.add((org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Schema) item);
			}

			if (item instanceof org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Import) {
				org.ow2.easywsdl.schema.api.Import impt = null;
				try {
					// TODO : use the documentURI
					impt = new org.ow2.easywsdl.schema.impl.ImportImpl((org.ow2.easywsdl.schema.org.w3._2001.xmlschema.Import) item, Util.convertWSDLFeatures2SchemaFeature(this.desc), imports, this.desc.getDocumentBaseURI(), (AbstractSchemaReader) reader.getSchemaReader());
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

	@Override
	public List<Schema> getSchemas() {
		return super.getSchemas();
	}

	public static TTypes replaceDOMElementByTTypes(final WSDLElement parent, final Element childToReplace, WSDLReaderImpl reader) throws WSDLException {
		TTypes res = null;
		try {
			if ((childToReplace != null) && ((childToReplace.getLocalName().equals("types")))) {
				JAXBElement<TTypes> jaxbElement;

                Unmarshaller unmarshaller = WSDLJAXBContext.getJaxbContext().createUnmarshaller();

				jaxbElement = unmarshaller.unmarshal(childToReplace, TTypes.class);

				// change element by jaxb element
				((TDefinitions) ((AbstractWSDLElementImpl) parent).getModel()).getAny().remove(childToReplace);
				((TDefinitions) ((AbstractWSDLElementImpl) parent).getModel()).getAnyTopLevelOptionalElement().add(jaxbElement.getValue());

				// get element
				res = jaxbElement.getValue();
			}
		} catch (JAXBException e) {
			throw new WSDLException(e);
		}
		return res;
	}

	public static Schema getSoap11encTypesSchema() {
		return soap11encTypesSchema;
	}

	public static Schema getSoap12encTypesSchema() {
		return soap12encTypesSchema;
	}
}
