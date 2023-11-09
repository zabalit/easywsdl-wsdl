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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.absItf.AbsItfSchema;
import org.ow2.easywsdl.schema.api.extensions.NamespaceMapperImpl;
import org.ow2.easywsdl.schema.api.extensions.SchemaLocatorImpl;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Import;
import org.ow2.easywsdl.wsdl.api.Include;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLImportException;
import org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractDescriptionImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractInterfaceTypeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfImport;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11.UseConstants;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Body;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Fault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBinding;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDefinitions;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TDocumented;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TImport;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TPortType;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TService;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TTypes;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class DescriptionImpl extends AbstractDescriptionImpl<TDefinitions, Service, Endpoint, Binding, InterfaceType, Include, Import, Types> implements Description {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the list of messages
	 */
	private List<MessageImpl> messages = new ArrayList<MessageImpl>();

	private WSDLReaderImpl reader = null;
	
	@SuppressWarnings("unchecked")
	public DescriptionImpl(final URI baseURI, final TDefinitions definition, final NamespaceMapperImpl namespaceMapper, final SchemaLocatorImpl schemaLocator, final Map<FeatureConstants, Object> features, Map<URI, AbsItfDescription> descriptionImports, Map<URI, AbsItfSchema> schemaImports, WSDLReaderImpl reader)
	throws WSDLException, WSDLImportException {
		
		super(baseURI, definition, namespaceMapper, schemaLocator, features);

		// get the documentation
		this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(this.model.getDocumentation(), this);

		// get the reader
		if(reader == null) {
			this.reader = new WSDLReaderImpl();
		} else {
			this.reader = reader;
		}

		boolean find = false;
		for (final TDocumented part : this.model.getAnyTopLevelOptionalElement()) {
			// get imports
			if (part instanceof TImport) {
				final Import impt = new org.ow2.easywsdl.wsdl.impl.wsdl11.ImportImpl((TImport) part, this, descriptionImports, schemaImports, this.documentURI, this.reader);
				this.imports.add(impt);
				find = true;
			}
		}
		if (!find) {
			Iterator<Object> it = this.model.getAny().iterator();
			while (it.hasNext()) {
				Object part = it.next();

				// get import
				if (part instanceof Element) {
					if ((((Element) part).getLocalName().equals("import")) && verifWSDL11Ns(((Element) part).getNamespaceURI())) {
						TImport tImpt = ImportImpl.replaceDOMElementByTImport(this, (Element) part, this.reader);
						it = this.model.getAny().iterator();
						final Import impt = new org.ow2.easywsdl.wsdl.impl.wsdl11.ImportImpl((TImport) tImpt, this, descriptionImports, schemaImports, this.documentURI, this.reader);
						this.imports.add(impt);
					}
				}
			}
		}

		this.addImportElementsInAllList();
		this.addIncludeElementsInAllList();

		find = false;
		for (final TDocumented part : this.model.getAnyTopLevelOptionalElement()) {
			// get types
			if (part instanceof TTypes) {
				this.types = new org.ow2.easywsdl.wsdl.impl.wsdl11.TypesImpl((TTypes) part, this, schemaImports, this.reader);
				find = true;
				break;
			}
		}
		if (!find) {
			Iterator<Object> it = this.model.getAny().iterator();
			while (it.hasNext()) {
				Object part = it.next();

				// get import
				if (part instanceof Element) {
					
					if ((((Element) part).getLocalName().equals("types")) && verifWSDL11Ns(((Element) part).getNamespaceURI())) {
						if(reader == null) {
							reader = new WSDLReaderImpl();
						}
						TTypes tTypes = TypesImpl.replaceDOMElementByTTypes(this, (Element) part, reader);
						it = this.model.getAny().iterator();
						this.types = new org.ow2.easywsdl.wsdl.impl.wsdl11.TypesImpl((TTypes) tTypes, this, schemaImports, this.reader);

					}
				}
			}
		}

		this.messages = this.findAllMessages(this);

		find = false;
		for (final TDocumented part : this.model.getAnyTopLevelOptionalElement()) {
			// get interfaces
			if (part instanceof TPortType) {
				final InterfaceType itf = new org.ow2.easywsdl.wsdl.impl.wsdl11.InterfaceTypeImpl((TPortType) part, this);
				this.interfaces.add(itf);
				find = true;
			}
		}
		if (!find) {
			Iterator<Object> it = this.model.getAny().iterator();
			while (it.hasNext()) {
				Object part = it.next();

				// get import
				if (part instanceof Element) {
					if ((((Element) part).getLocalName().equals("portType")) && verifWSDL11Ns(((Element) part).getNamespaceURI())) {
						if(reader == null) {
							reader = new WSDLReaderImpl();
						}
						TPortType tPortType = InterfaceTypeImpl.replaceDOMElementByTPortType(this, (Element) part, reader);
						it = this.model.getAny().iterator();
						final InterfaceType itf = new org.ow2.easywsdl.wsdl.impl.wsdl11.InterfaceTypeImpl((TPortType) tPortType, this);
						this.interfaces.add(itf);
					}
				}
			}
		}

		find = false;
		for (final TDocumented part : this.model.getAnyTopLevelOptionalElement()) {
			// get bindings
			if (part instanceof TBinding) {
				final Binding b = new org.ow2.easywsdl.wsdl.impl.wsdl11.BindingImpl((TBinding) part, this);
				this.bindings.add(b);
				find = true;
			}
		}
		if (!find) {
			Iterator<Object> it = this.model.getAny().iterator();
			while (it.hasNext()) {
				Object part = it.next();

				// get import
				if (part instanceof Element) {
					if ((((Element) part).getLocalName().equals("binding")) && verifWSDL11Ns(((Element) part).getNamespaceURI())) {
						if(reader == null) {
							reader = new WSDLReaderImpl();
						}
						TBinding tBinding = BindingImpl.replaceDOMElementByTBinding(this, (Element) part, reader);
						it = this.model.getAny().iterator();
						final Binding b = new org.ow2.easywsdl.wsdl.impl.wsdl11.BindingImpl((TBinding) tBinding, this);
						this.bindings.add(b);
					}
				}
			}
		}

		find = false;
		for (final TDocumented part : this.model.getAnyTopLevelOptionalElement()) {
			// get services
			if (part instanceof TService) {
				final Service s = new org.ow2.easywsdl.wsdl.impl.wsdl11.ServiceImpl((TService) part, this);
				this.services.add(s);
				find = true;
			}
		}
		if (!find) {
			Iterator<Object> it = this.model.getAny().iterator();
			while (it.hasNext()) {
				Object part = it.next();

				// get import
				if (part instanceof Element) {
					if ((((Element) part).getLocalName().equals("service")) && verifWSDL11Ns(((Element) part).getNamespaceURI())) {
						if(reader == null) {
							reader = new WSDLReaderImpl();
						}
						TService tService = ServiceImpl.replaceDOMElementByTService(this, (Element) part, reader);
						it = this.model.getAny().iterator();
						final Service s = new org.ow2.easywsdl.wsdl.impl.wsdl11.ServiceImpl((TService) tService, this);
						this.services.add(s);
					}
				}
			}
		}
	}
	
	private boolean verifWSDL11Ns(String ns) {
		boolean res = false;
		if(ns == null || ns.equals(Constants.WSDL_11_NAMESPACE)) {
			res = true;
		}
		return res;
	}

	public DescriptionImpl() throws WSDLException {
		try {
			this.model = new TDefinitions();
			this.documentURI = new URI(".");

			// get the documentation
			this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(this.model.getDocumentation(), this);

		} catch (URISyntaxException e) {
			throw new WSDLException(e);
		}
	}

	public List<MessageImpl> findAllMessages(AbsItfDescription parent)  {
		List<MessageImpl> messages = new ArrayList<MessageImpl>();
		for (final AbsItfImport impt : (List<AbsItfImport>)parent.getImports()) {
			if (impt.getDescription() != null) {
				final AbsItfDescription absDesc = impt.getDescription();
				if (absDesc instanceof DescriptionImpl) {
					messages.addAll(((DescriptionImpl) absDesc).getMessages());
				}
				messages.addAll(findAllMessages(impt.getDescription()));
			}
		}
		for (final Include incl : this.includes) {
			if (incl.getDescription() != null) {
				final AbsItfDescription absDesc = incl.getDescription();
				if (absDesc instanceof DescriptionImpl) {
					messages.addAll(((DescriptionImpl) absDesc).getMessages());
				}
				messages.addAll(findAllMessages(incl.getDescription()));
			}
		}

		boolean find = false;
		for (final TDocumented part : this.model.getAnyTopLevelOptionalElement()) {
			// get messages
			if (part instanceof TMessage) {
				messages.add(new MessageImpl((TMessage) part, this));
				find = true;
			}
		}
		if (!find) {
			Iterator<Object> it = this.model.getAny().iterator();
			while (it.hasNext()) {
				Object part = it.next();

				// get import
				if (part instanceof Element) {
					if ((((Element) part).getLocalName().equals("message")) && verifWSDL11Ns((((Element) part).getNamespaceURI()))) {
						try {
							if(reader == null) {
								reader = new WSDLReaderImpl();
							}
							TMessage tMessage = MessageImpl.replaceDOMElementByTMessage(this, (Element) part, reader);

							it = this.model.getAny().iterator();
							messages.add(new MessageImpl((TMessage) tMessage, this));
						} catch (WSDLException e) {
							// do nothing
						}
					}
				}
			}
		}
		return messages;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addBinding(final Binding binding) {
		super.addBinding(binding);
		this.model.getAnyTopLevelOptionalElement().add((TDocumented) ((AbstractWSDLElementImpl) binding).getModel());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInterface(final InterfaceType interfaceType) {
		super.addInterface(interfaceType);
		this.model.getAnyTopLevelOptionalElement().add((TDocumented) ((AbstractWSDLElementImpl) interfaceType).getModel());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addService(final Service service) {
		super.addService(service);
		this.model.getAnyTopLevelOptionalElement().add((TDocumented) ((AbstractWSDLElementImpl) service).getModel());
	}

	public Binding createBinding() {
		return new BindingImpl(new TBinding(), this);
	}

	public Import createImport() throws WSDLException, WSDLImportException {
		return new ImportImpl(new TImport(), this, null, null, this.documentURI, this.reader);
	}

	@Override
	public void addImport(Import impt) {
		super.addImport(impt);
		this.model.getAnyTopLevelOptionalElement().add((TDocumented) ((AbstractWSDLElementImpl) impt).getModel());
	}

	public InterfaceType createInterface() {
		return new InterfaceTypeImpl(new TPortType(), this);
	}

	public Service createService() {
		return new ServiceImpl(new TService(), this);
	}

	public Types createTypes() {
		Types res = null;
		try {
			res = new TypesImpl(new TTypes(), this, null, this.reader);
		} catch (WSDLException e) {
			// Do nothing
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void setTypes(Types types) {
		super.setTypes(types);
		this.model.getAnyTopLevelOptionalElement().add((TDocumented) ((AbstractWSDLElementImpl) types).getModel());
	}

	public QName getQName() {
		QName res = null;
		if (this.model.getName() != null) {
			res = new QName(this.getTargetNamespace(), this.model.getName());
		}
		return res;
	}

	public String getTargetNamespace() {
		return this.model.getTargetNamespace();
	}

	public Binding removeBinding(final QName name) {
        throw new UnsupportedOperationException();
	}

	public Import removeImport(final Import importDef) {
        throw new UnsupportedOperationException();
	}

	public String removeNamespace(final String prefix) {
        throw new UnsupportedOperationException();
	}

	public InterfaceType removeInterface(final QName name) {
        throw new UnsupportedOperationException();
	}

	public Service removeService(final QName name) {
        throw new UnsupportedOperationException();
	}

	public void setQName(final QName name) {
		this.model.setName(name.getLocalPart());
	}

	public void setTargetNamespace(final String targetNamespace) {
		this.model.setTargetNamespace(targetNamespace);
	}

	@Override
	public void addInclude(final Include incl) throws WSDLException {
		throw new WSDLException(Constants.NOT_SUPPORTED);
	}

	public Include removeInclude(final Include includeDef) throws WSDLException {
		throw new WSDLException(Constants.NOT_SUPPORTED);
	}

	public WSDLVersionConstants getVersion() {
		return WSDLVersionConstants.WSDL11;
	}

	/**
	 * @return the messages
	 */
	public List<MessageImpl> getMessages() {
		this.messages = this.findAllMessages(this);
		return this.messages;
	}

	public MessageImpl getMessage(QName name) {
		MessageImpl res = null;
		if(this.messages == null) {
			this.messages = this.findAllMessages(this);
		}
		for (final MessageImpl msg : this.messages) {
			if ((msg.getQName().getLocalPart().equals(name.getLocalPart())) && (msg.getQName().getNamespaceURI().equals(name.getNamespaceURI()))) {
				res = msg;
				break;
			}
		}

		return res;
	}

	public void removeAllMessages() {
		this.messages.clear();
		Iterator<TDocumented> it = this.model.getAnyTopLevelOptionalElement().iterator();
		while (it.hasNext()) {
			TDocumented part = it.next();

			// get messages
			if (part instanceof TMessage) {
				this.model.getAnyTopLevelOptionalElement().remove(part);
				it = this.model.getAnyTopLevelOptionalElement().iterator();
			}
		}
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(final List<MessageImpl> messages) {
		this.messages = messages;
	}

	public Binding createDefaultSoapBinding(String bindingName, Endpoint endpoint, InterfaceType itf) {
		// create binding
		Binding binding = (Binding) ((AbstractInterfaceTypeImpl) itf).getDescription().createBinding();
		binding.setQName(new QName(((AbstractInterfaceTypeImpl) itf).getDescription().getTargetNamespace(), bindingName));
		binding.setInterface(itf);
		binding.setTransportProtocol(org.ow2.easywsdl.wsdl.impl.wsdl11.Constants.HTTP_SCHEMAS_XMLSOAP_ORG_SOAP_HTTP);


		for (Operation operation : itf.getOperations()) {
			BindingOperation bindingOperation = binding.createBindingOperation();
			bindingOperation.setQName(operation.getQName());

			// We set soapAction attribut as an URI build from the operation
			// qualified name.
			bindingOperation
			.setSoapAction(operation.getQName().getNamespaceURI()
					+ (operation.getQName().getNamespaceURI().endsWith(
					"/") ? "" : "/")
					+ operation.getQName().getLocalPart());

			// input
			if (operation.getInput() != null) {
				BindingInput binput = bindingOperation.createInput();
				bindingOperation.setInput(binput);
				SOAP11Binding4Wsdl11 soap11binding = binput.createSOAP11Binding4Wsdl11();

				SOAP11Body body = soap11binding.createBody();
				body.setUse(UseConstants.LITERAL);
				try {
					soap11binding.setBody(body);
				} catch (WSDLException e) {
					// do nothing
				}
				binput.setSOAP11Binding4Wsdl11(soap11binding);

			}

			// output
			if (operation.getOutput() != null) {
				BindingOutput boutput = bindingOperation.createOutput();
				bindingOperation.setOutput(boutput);
				SOAP11Binding4Wsdl11 soap11binding = boutput.createSOAP11Binding4Wsdl11();

				SOAP11Body body = soap11binding.createBody();
				body.setUse(UseConstants.LITERAL);
				try {
					soap11binding.setBody(body);
				} catch (WSDLException e) {
					// do nothing
				}
				boutput.setSOAP11Binding4Wsdl11(soap11binding);

			}

			// fault
			if (operation.getFaults() != null) {
				for (Fault faultop : operation.getFaults()) {
					BindingFault bfault = bindingOperation.createFault();
					bfault.setName(faultop.getName());
					bindingOperation.addFault(bfault);
					SOAP11Binding4Wsdl11 soap11binding = bfault.createSOAP11Binding4Wsdl11();

					SOAP11Fault soap11fault = soap11binding.createFault();
					soap11fault.setName(faultop.getName());
					soap11fault.setUse(UseConstants.LITERAL);
					try {
						soap11binding.setFault(soap11fault);
					} catch (WSDLException e) {
						// do nothing
					}
					bfault.setSOAP11Binding4Wsdl11(soap11binding);

				}
			}

			binding.addBindingOperation(bindingOperation);
		}

		// set the binding to endpoint
		endpoint.setBinding(binding);

		return binding;
	}

	
	public void addOtherElements(Element elmt) {
		this.model.getAny().add(elmt);
	}

}
