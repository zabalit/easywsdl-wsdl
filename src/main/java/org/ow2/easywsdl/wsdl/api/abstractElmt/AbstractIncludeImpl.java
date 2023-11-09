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

import java.net.URI;
import java.util.Map;
import java.util.logging.Logger;

import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.schema.api.abstractElmt.AbstractSchemaElementImpl;
import org.ow2.easywsdl.schema.impl.Constants;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLImportException;
import org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInclude;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class AbstractIncludeImpl<E, D extends AbsItfDescription> extends AbstractWSDLElementImpl<E> implements AbsItfInclude<D> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(AbstractIncludeImpl.class.getName());

	/**
	 * the desc
	 */
	protected D desc;


	/**
	 * Default constructor
	 * 
	 * @param model
	 *            the model
	 * @param parent
	 *            the parent description
	 * @throws WSDLException
	 * @throws WSDLImportException
	 */
	public AbstractIncludeImpl(final E model, final D parent, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports, final URI baseURI, AbstractWSDLReaderImpl reader) throws WSDLException, WSDLImportException {
		super(model, (AbstractWSDLElementImpl) parent);

		this.parent = (AbstractSchemaElementImpl) parent;

		final URI location = this.getLocationURI();

		if (this.parent != null) {
			if (!((AbstractDescriptionImpl) this.parent).getFeatures().isEmpty()) {
				if ((((AbstractDescriptionImpl) this.parent).getFeatures().get(FeatureConstants.IMPORT_DOCUMENTS) != null) && ((Boolean) ((AbstractDescriptionImpl) this.parent).getFeatures().get(FeatureConstants.IMPORT_DOCUMENTS))) {
					this.retrieveInclude(location, baseURI, descriptionImports, schemaImports, reader);
				} else if(descriptionImports != null && descriptionImports.get(location) != null) {
					this.desc = (D) descriptionImports.get(location);
				}
			} else if(descriptionImports != null && descriptionImports.get(location) != null) {
				this.desc = (D) descriptionImports.get(location);
			}
		}

		if ((this.parent != null) && (this.desc != null)) {
			if(this.desc instanceof AbstractDescriptionImpl) {
				((AbstractDescriptionImpl) this.desc).setFeatures(reader.getFeatures());
			} 
		}
	}

	private void retrieveInclude(final URI originalWsdlLocation, final URI baseURI, final Map<URI, AbsItfDescription> descriptionImports, final Map<URI, AbsItfSchema> schemaImports, final AbstractWSDLReaderImpl reader) throws WSDLException, WSDLImportException {
		assert descriptionImports != null;
		if (originalWsdlLocation != null) {

			try {
				URI wsdlLocation = null;

				// Check for well known schemas
				if (Constants.XML_URI.equals(originalWsdlLocation)) {
					wsdlLocation = this.getClass().getClassLoader().getResource(Constants.XML_XSD).toURI().normalize();
				} else {
					wsdlLocation = originalWsdlLocation;
				}

				// Try to identify a cyclic import loop
				if (!descriptionImports.containsKey(wsdlLocation)) {
					D externalDesc = (D) reader.readExternalPart(wsdlLocation, baseURI, descriptionImports, schemaImports);
					descriptionImports.put(wsdlLocation, externalDesc);
				}
				this.desc = (D) descriptionImports.get(wsdlLocation);
				
//				//if(!wsdlLocation.toString().startsWith("xpath")) {
//					if(reader.getImportList().containsKey(locationNorm)) {
//						LOG.warning("This import is already include : " + locationNorm + ". This probably mean there's a cyclic import");
//						this.desc = (D) reader.getImportList().get(locationNorm);
//					} else {	
//						if (descriptionImports != null && descriptionImports.containsKey(wsdlLocation)) {
//							this.desc = (D) descriptionImports.get(wsdlLocation);
//						} else {
//							reader.getImportList().put(locationNorm, null);
//							// Create schema reader
//							this.desc = (D)reader.readExternalPart(wsdlLocation, baseURI, descriptionImports, schemaImports, false);
//							/*if(reader instanceof org.ow2.easywsdl.wsdl.impl.wsdl11.WSDLReaderImpl) {
//							this.desc = (D) (reader).readWSDL(schemaLocation, descriptionImports, schemaImports, false);
//						} else if (reader instanceof org.ow2.easywsdl.wsdl.impl.wsdl20.WSDLReaderImpl) {
//							this.desc = (D) (reader).readWSDL(schemaLocation, descriptionImports, schemaImports, false);
//						} else {
//							throw new WSDLException("Reader unknowed");
//						}*/
//						}
//						reader.getImportList().put(locationNorm, this.desc);
//					}
//				//}
			} catch (final Exception e) {
				// do nothing
				// the document is not included in imported document
				throw new WSDLImportException("the imported document cannot be imported at: " + originalWsdlLocation, e);
			}
		}
	}

	/**
	 * @return the desc
	 */
	public D getDescription() {
		return this.desc;
	}

	public void setDescription(final D d) {
		this.desc = d;
		((AbstractDescriptionImpl)this.getParentDescription()).addIncludeElementsInAllList(this);
	}

	/**
	 * @return the parent desc
	 */
	public D getParentDescription() {
		return (D) this.parent;
	}

	public void setParentDescription(final D p) {
		this.parent = (AbstractSchemaElementImpl) p;
	}

}
