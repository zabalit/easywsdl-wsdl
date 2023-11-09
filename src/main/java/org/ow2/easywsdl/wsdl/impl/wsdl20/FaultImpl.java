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

import java.util.List;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractInterfaceTypeImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractOperationImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractParamImpl;
import org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.MessageRefFaultType;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class FaultImpl extends AbstractParamImpl<MessageRefFaultType> implements
        org.ow2.easywsdl.wsdl.api.Fault {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(FaultImpl.class.getName());

    public FaultImpl(final MessageRefFaultType param, final OperationImpl operationImpl) {
        super(param, operationImpl);
        this.operation = operationImpl;

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl20.DocumentationImpl(
                this.model.getDocumentation(), this);

        // get element name
        this.elementName = param.getRef();
    }

    @SuppressWarnings("unchecked")
    public QName getMessageName() {
        QName res = null;
        if (this.model.getMessageLabel() != null) {
            res = new QName(((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) this
                    .getOperation()).getInterface()).getDescription().getTargetNamespace(),
                    this.model.getMessageLabel());
        } else {
            res = new QName(((AbstractInterfaceTypeImpl) ((AbstractOperationImpl) this
                    .getOperation()).getInterface()).getDescription().getTargetNamespace(), "Out");
        }
        return res;
    }

    public void setMessageName(final QName name) {
        this.model.setMessageLabel(name.getLocalPart());
    }

    public List<Part> getParts() {
        return null;
    }

    public void setElement(final Element element) {
		this.elementName = element.getQName();
		this.model.setRef(element.getQName());
    }

    public String getName() {
        return null;
    }

    public void setName(final String name) throws WSDLException {
    	LOG.warning("Do nothing: No name in fault in wsdl 2.0");
    }

    public Part getPart(final String name) {
        return null;
    }

    @Override
    public List<org.w3c.dom.Element> getOtherElements() throws XmlException {
        final List<org.w3c.dom.Element> res = super.getOtherElements();

        for (final Object item : this.model.getAny()) {
            if (item instanceof org.w3c.dom.Element) {
                res.add((org.w3c.dom.Element) item);
            }
        }

        return res;
    }
}
