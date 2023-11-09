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
 
package org.ow2.easywsdl.wsdl.decorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.absItf.AbsItfRedefine;
import org.ow2.easywsdl.schema.api.extensions.NamespaceMapperImpl;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLImportException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractDescriptionImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfEndpoint;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfImport;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInclude;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfInterfaceType;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfService;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfTypes;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription.WSDLVersionConstants;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public abstract class DecoratorDescriptionImpl<S extends AbsItfService, E extends AbsItfEndpoint, B extends AbsItfBinding, I extends AbsItfInterfaceType, Incl extends AbsItfInclude, Impt extends AbsItfImport, T extends AbsItfTypes>
extends Decorator<AbsItfDescription<S, E, B, I, Incl, Impt, T>> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Class<? extends DecoratorTypesImpl> typesImpl;

	public DecoratorDescriptionImpl(final AbsItfDescription<S, E, B, I, Incl, Impt, T> wsdl, Class<? extends DecoratorTypesImpl> typesImpl)
	throws WSDLException {
		final AbsItfDescription wsdlT = wsdl;
		this.internalObject = wsdlT;
		this.typesImpl = typesImpl;
	}

	public Description getFirstDescription() {
		Description res = null;
		AbsItfDescription tmp = this.internalObject;
		while ((tmp != null) && (tmp instanceof DecoratorDescriptionImpl)) {
			tmp = ((DecoratorDescriptionImpl) tmp).getFirstDescription();
		}
		if (tmp instanceof Description) {
			res = (Description) tmp;
		}
		return res;
	}

	public void addBinding(final B arg0) {
		this.internalObject.addBinding(arg0);
	}

	public void addImport(final Impt arg0) {
		this.internalObject.addImport(arg0);
	}

	public void addInclude(final Incl arg0) throws WSDLException {
		this.internalObject.addInclude(arg0);
	}

	public void addNamespace(final String arg0, final String arg1) {
		this.internalObject.addNamespace(arg0, arg1);
	}

	public void addService(final S arg0) {
		this.internalObject.addService(arg0);
	}

	public B createBinding() {
		return this.internalObject.createBinding();
	}

	public Impt createImport() throws WSDLException, WSDLImportException {
		return this.internalObject.createImport();
	}

	public S createService() {
		return this.internalObject.createService();
	}

	public T createTypes() {
		return this.internalObject.createTypes();
	}

	public B getBinding(final QName arg0) {
		return this.internalObject.getBinding(arg0);
	}

	public List<B> getBindings() {
		return this.internalObject.getBindings();
	}

	public List<Impt> getImports() {
		return this.internalObject.getImports();
	}

	public List<Impt> getImports(final String arg0) {
		return this.internalObject.getImports(arg0);
	}

	public List<Incl> getIncludes() {
		return this.internalObject.getIncludes();
	}
	
	
    public Incl getInclude(final URI arg0) {
        return this.internalObject.getInclude(arg0);
    }

	public NamespaceMapperImpl getNamespaces() {
		return this.internalObject.getNamespaces();
	}

	public QName getQName() throws WSDLException {
		return this.internalObject.getQName();
	}

	public Map<String, String> getSchemaLocation() {
		return this.internalObject.getSchemaLocation();
	}

	public S getService(final QName arg0) {
		return this.internalObject.getService(arg0);
	}

	public List<S> getServices() {
		return this.internalObject.getServices();
	}

	public String getTargetNamespace() {
		return this.internalObject.getTargetNamespace();
	}

/*	public T getTypes() {
		return this.wsdl.getTypes();
	}*/

	public T getTypes() {
		T types = null;
		if(typesImpl != null) {
			try {
				final Constructor c = typesImpl.getConstructors()[0];
				types = (T) c.newInstance(this.internalObject.getTypes());
			} catch (IllegalArgumentException e) {
				// do nothing
				e.printStackTrace();
			} catch (InstantiationException e) {
				// do nothing
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// do nothing
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// do nothing
				e.printStackTrace();
			}
		} else {
			types = this.internalObject.getTypes();
		}
		return types;
	}
	
	public WSDLVersionConstants getVersion() {
		return this.internalObject.getVersion();
	}

	public B removeBinding(final QName arg0) {
		return this.internalObject.removeBinding(arg0);
	}

	public Impt removeImport(final Impt arg0) {
		return this.internalObject.removeImport(arg0);
	}

	public Incl removeInclude(final Incl arg0) throws WSDLException {
		return this.internalObject.removeInclude(arg0);
	}

	public String removeNamespace(final String arg0) {
		return this.internalObject.removeNamespace(arg0);
	}

	public S removeService(final QName arg0) {
		return this.internalObject.removeService(arg0);
	}

    public void setDocumentBaseURI(final URI arg0) {
        this.internalObject.setDocumentBaseURI(arg0);
    }
    
	public void setQName(final QName arg0) throws WSDLException {
		this.internalObject.setQName(arg0);
	}

	public void setTargetNamespace(final String arg0) {
		this.internalObject.setTargetNamespace(arg0);
	}

	public void setTypes(final T type) {
		if((type instanceof DecoratorTypesImpl)&&(this.internalObject instanceof AbstractDescriptionImpl)) {
			((AbstractDescriptionImpl)this.internalObject).setTypes( (AbsItfTypes) ((DecoratorTypesImpl)type).getInternalObject());
		} else {
			this.internalObject.setTypes(type);
		}
	}

	public I createInterface() {
		return this.internalObject.createInterface();
	}

	public I getInterface(final QName name) {
		return this.internalObject.getInterface(name);
	}

	public List<I> getInterfaces() {
		return this.internalObject.getInterfaces();
	}

	public I removeInterface(final QName name) {
		return this.internalObject.removeInterface(name);
	}

	public void addInterface(final I interfaceType) {
		this.internalObject.addInterface(interfaceType);
	}

	public B createDefaultSoapBinding(String bindingName,
			E endpoint, I itf) {
		return this.internalObject.createDefaultSoapBinding(bindingName, endpoint, itf);
	}
	
	public List<E> findEndpointsImplementingInterface(I itf) {
		return this.internalObject.findEndpointsImplementingInterface(itf);
	}

    public URI getDocumentBaseURI() {
        return this.internalObject.getDocumentBaseURI();
    }

	public String toString() {
		String res = null;
		if(this.getInternalObject() != null) {
			res = this.getInternalObject().toString();
		}
		return res;
	}
}
