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
 
package org.ow2.easywsdl.wsdl.api.abstractItf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.absItf.AbsItfRedefine;
import org.ow2.easywsdl.schema.api.extensions.NamespaceMapperImpl;
import org.ow2.easywsdl.wsdl.api.WSDLElement;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLImportException;

/**
 * This interface represents a WSDL definition.
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public interface AbsItfDescription<S extends AbsItfService, E extends AbsItfEndpoint, B extends AbsItfBinding, I extends AbsItfInterfaceType, Incl extends AbsItfInclude, Impt extends AbsItfImport, T extends AbsItfTypes> extends WSDLElement {

	/**
	 * Constants for WSDL version.
	 */
	public enum WSDLVersionConstants {
		WSDL11(org.ow2.easywsdl.wsdl.impl.wsdl11.Constants.WSDL_11_NAMESPACE), WSDL20(org.ow2.easywsdl.wsdl.impl.wsdl20.Constants.WSDL_20_NAMESPACE);

		/**
		 * @param pattern
		 * @return
		 */
		public static WSDLVersionConstants valueOf(final URI pattern) {
			WSDLVersionConstants result = null;
			if (pattern != null) {
				for (final WSDLVersionConstants version : WSDLVersionConstants.values()) {
					if (version.nameSpace.equals(pattern.toString())) {
						result = version;
					}
				}
			}
			return result;
		}

		private final String nameSpace;

		private final URI version;

		/**
		 * Creates a new instance of {@link WSDLVersionConstants}
		 * 
		 * @param nameSpace
		 */
		private WSDLVersionConstants(final String nameSpace) {
			this.nameSpace = nameSpace;
			if (this.nameSpace != null) {
				try {
					this.version = new URI(nameSpace);
				} catch (final URISyntaxException e) {
					throw new Error("Unexpected Error in URI namespace syntax", e); 
				}
			} else {
				this.version = null;
			}
		}

		/**
		 * @return
		 */
		public URI value() {
			return this.version;
		}

		/**
		 * Please use this equals method instead of using :<code>
		 * value().equals(mep)
		 * </code> which is almost 10 times slower...
		 * 
		 * @param version
		 * @return
		 */
		public boolean equals(final URI version) {
			return this.toString().equals(version.toString());
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.nameSpace;
		}
	}

	/**
     * Get the document base URI of this definition.
     * 
     * @return the document base URI.
     */
    URI getDocumentBaseURI();

    /**
     * Set the document base URI of this definition.
     */
    void setDocumentBaseURI(URI documentBaseURI);

	/**
	 * Set the name of this definition.
	 * 
	 * @param name
	 *            the desired name
	 * @throws WSDLException
	 */
	void setQName(QName name) throws WSDLException;

	/**
	 * Get the name of this definition.
	 * 
	 * @return the definition name
	 * @throws WSDLException
	 */
	QName getQName() throws WSDLException;

	/**
	 * Set the target namespace in which WSDL elements are defined.
	 * 
	 * @param targetNamespace
	 *            the target namespace
	 */
	void setTargetNamespace(String targetNamespace);

	/**
	 * Get the target namespace in which the WSDL elements are defined.
	 * 
	 * @return the target namespace
	 */
	String getTargetNamespace();

	/**
	 * This is a way to add a namespace association to a definition. It is similar to adding a namespace prefix declaration to the top of a &lt;wsdl:definition&gt; element. This has nothing to do with the &lt;wsdl:import&gt; element; there are separate methods for dealing with information described by
	 * &lt;wsdl:import&gt; elements.
	 * 
	 * @param prefix
	 *            the prefix to use for this namespace (when rendering this information as XML). Use null or an empty string to describe the default namespace (i.e. xmlns="...").
	 * @param namespaceURI
	 *            the namespace URI to associate the prefix with. If you use null, the namespace association will be removed.
	 */
	void addNamespace(String prefix, String namespaceURI);

	/**
	 * Remove the namespace URI associated with this prefix.
	 * 
	 * @param prefix
	 *            the prefix of the namespace to be removed.
	 * @return the namespace URI which was removed.
	 */
	String removeNamespace(String prefix);

	/**
	 * Get all namespace associations in this definition. The keys are the prefixes, and the namespace URIs are the values. This is unrelated to the &lt;wsdl:import&gt; element.
	 * 
	 * @see #addNamespace(String, String)
	 */
	NamespaceMapperImpl getNamespaces();

	/**
	 * Set the types section.
	 */
	void setTypes(T types);

	/**
	 * Get the types section.
	 * 
	 * @return the types section
	 */
	T getTypes();

	/**
	 * Add an import to this WSDL description.
	 * 
	 * @param importDef
	 *            the import to be added
	 */
	void addImport(Impt importDef);

	/**
	 * Remove an import from this WSDL description.
	 * 
	 * @param importDef
	 *            the import to be removed
	 * @return the removed ImportImpl
	 */
	Impt removeImport(Impt importDef);

	/**
	 * Get the list of imports for the specified namespaceURI.
	 * 
	 * @param namespaceURI
	 *            the namespaceURI associated with the desired imports.
	 * @return a list of the corresponding imports, or null if there weren't any matching imports
	 */
	List<Impt> getImports(String namespaceURI);

	/**
	 * Get a map of lists containing all the imports defined here. The map's keys are the namespaceURIs, and the map's values are lists. There is one list for each namespaceURI for which imports have been defined.
	 */
	List<Impt> getImports();

	/**
	 * Add an include to this WSDL description.
	 * 
	 * @param includeDef
	 *            the include to be added
	 * @throws WSDLException
	 */
	void addInclude(Incl includeDef) throws WSDLException;

	/**
	 * Remove an include from this WSDL description.
	 * 
	 * @param includeDef
	 *            the include to be removed
	 * @return the removed include
	 * @throws WSDLException
	 */
	Incl removeInclude(Incl includeDef) throws WSDLException;

    /**
     * Get the include of the specified locationURI.
     * 
     * @param locationURI
     *            the locationURI associated with the desired includes.
     * @return the corresponding include, or null if there weren't any matching
     *         includes
     */
    Incl getInclude(URI locationURI);

    /**
	 * Get a map of lists containing all the includes defined here. The map's keys are the namespaceURIs, and the map's values are lists. There is one list for each locationURI for which imports have been defined.
	 */
	List<Incl> getIncludes();

	/**
	 * Add a binding to this WSDL description.
	 * 
	 * @param binding
	 *            the binding to be added
	 */
	void addBinding(B binding);

	/**
	 * Get the specified binding. Also checks imported documents.
	 * 
	 * @param name
	 *            the name of the desired binding.
	 * @return the corresponding binding, or null if there wasn't any matching binding
	 */
	B getBinding(QName name);

	/**
	 * Remove the specified binding from this definition.
	 * 
	 * @param name
	 *            the name of the binding to remove
	 * @return the binding previously associated with this qname, if there was one; may return null
	 */
	B removeBinding(QName name);

	/**
	 * Get all the bindings defined in this Definition.
	 */
	List<B> getBindings();

	/**
	 * Add a service to this WSDL description.
	 * 
	 * @param service
	 *            the service to be added
	 */
	void addService(S service);

	/**
	 * Get the specified service. Also checks imported documents.
	 * 
	 * @param name
	 *            the name of the desired service.
	 * @return the corresponding service, or null if there wasn't any matching service
	 */
	S getService(QName name);

	/**
	 * Remove the specified service from this definition.
	 * 
	 * @param name
	 *            the name of the service to remove
	 * @return the service previously associated with this qname, if there was one; may return null
	 */
	S removeService(QName name);

	/**
	 * Get all the services defined in this Definition.
	 */
	List<S> getServices();

	/**
	 * Create a new binding.
	 * 
	 * @return the newly created binding
	 */
	B createBinding();

	/**
	 * Create a new import.
	 * 
	 * @return the newly created import
	 * @throws WSDLException
	 * @throws WSDLImportException
	 *             if the import can't be correctly parsed
	 */
	Impt createImport() throws WSDLException, WSDLImportException;

	/**
	 * Create a new interface.
	 * 
	 * @return the newly created interface
	 */
	I createInterface();

	/**
	 * Create a new service.
	 * 
	 * @return the newly created service
	 */
	S createService();

	/**
	 * Create a new types section.
	 * 
	 * @return the newly created types section
	 */
	T createTypes();

	/**
	 * get the version of the wsdl
	 */
	WSDLVersionConstants getVersion();

	/**
	 * get the schema location
	 * 
	 * @return
	 */
	Map<String, String> getSchemaLocation();

	/**
	 * Add a portType to this WSDL description.
	 * 
	 * @param interfaceType
	 *            the portType to be added
	 */
	void addInterface(I interfaceType);

	/**
	 * Get the specified portType. Also checks imported documents.
	 * 
	 * @param name
	 *            the name of the desired portType.
	 * @return the corresponding portType, or null if there wasn't any matching portType
	 */
	I getInterface(QName name);

	/**
	 * Remove the specified portType from this definition.
	 * 
	 * @param name
	 *            the name of the portType to remove
	 * @return the portType previously associated with this qname, if there was one; may return null
	 */
	I removeInterface(QName name);

	/**
	 * Get all the portTypes defined in this Definition.
	 */
	List<I> getInterfaces();

	/**
	 * Create default soap binding
	 */
	B createDefaultSoapBinding(String bindingName, E endpoint, I itf);

	List<E> findEndpointsImplementingInterface(I itf);

}
