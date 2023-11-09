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

import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractBindingOperationImpl;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractWSDLElementImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfBinding.StyleConstant;
import org.ow2.easywsdl.wsdl.api.binding.BindingProtocol.SOAPMEPConstants;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperation;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperationFault;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperationMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.ObjectFactory;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TStyleChoice;
import org.w3c.dom.Element;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class BindingOperationImpl
        extends
        AbstractBindingOperationImpl<TBindingOperation, Operation, BindingInput, BindingOutput, BindingFault>
        implements org.ow2.easywsdl.wsdl.api.BindingOperation {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(BindingOperationImpl.class.getName());
    
    private ObjectFactory soap11BindingFactory = new ObjectFactory();

    public BindingOperationImpl(final TBindingOperation bindingOperation,
            final BindingImpl bindingImpl) {
        super(bindingOperation, bindingImpl);
        this.binding = bindingImpl;

        // get the documentation
        this.documentation = new org.ow2.easywsdl.wsdl.impl.wsdl11.DocumentationImpl(
                this.model.getDocumentation(), this);

        // get input binding
        if (this.model.getInput() != null) {
            this.input = new org.ow2.easywsdl.wsdl.impl.wsdl11.BindingInputImpl(
                    this.model.getInput(), this);
        }

        // get output binding
        if (this.model.getOutput() != null) {
            this.output = new org.ow2.easywsdl.wsdl.impl.wsdl11.BindingOutputImpl(
                    this.model.getOutput(), this);
        }
        // get faults bindings
        if (this.model.getFault() != null) {
            for (final TBindingOperationFault fault : this.model.getFault()) {
                this.faults.add(new org.ow2.easywsdl.wsdl.impl.wsdl11.BindingFaultImpl(
                        fault, this));
            }
        }
    }

    @Override
	public void setInput(BindingInput input) {
		super.setInput(input);
		this.model.setInput(
                (TBindingOperationMessage) ((AbstractWSDLElementImpl) input).getModel());
	}

	@Override
	public void setOutput(BindingOutput output) {
		super.setOutput(output);
		this.model.setOutput(
                (TBindingOperationMessage) ((AbstractWSDLElementImpl) output).getModel());
	}

	public void addFault(final BindingFault bindingFault) {
        this.faults.add(bindingFault);
        this.model.getFault().add(
                (TBindingOperationFault) ((AbstractWSDLElementImpl) bindingFault).getModel());
    }

    public BindingFault removeFault(final String name) {
        throw new UnsupportedOperationException();
    }

    public void setQName(final QName name) {
        this.model.setName(name.getLocalPart());
    }


    @SuppressWarnings("unchecked")
    public StyleConstant getStyle() {
        StyleConstant style = null;
        for (final Object element : this.model.getAny()) {
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) {
                if (((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) ((JAXBElement) element)
                        .getValue()).getStyle() != null) {
                    style = StyleConstant
                            .valueOf(((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) ((JAXBElement) element)
                                    .getValue()).getStyle().value().toUpperCase());
                }
                break;
            }
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) {
                if (((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) ((JAXBElement) element)
                        .getValue()).getStyle() != null) {
                    style = StyleConstant
                            .valueOf(((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) ((JAXBElement) element)
                                    .getValue()).getStyle().value().toUpperCase());
                }
                break;
            }
        }
        if (style == null) {
            style = this.binding.getStyle();
        }

        return style;
    }

    @SuppressWarnings("unchecked")
    public QName getQName() {
        return new QName(
                ((AbstractBindingImpl) this.binding).getDescription().getTargetNamespace(),
                this.model.getName());
    }

    public org.ow2.easywsdl.wsdl.api.BindingOperation removeBindingOperation(
            final String name, final String inputName, final String outputName) {
        throw new UnsupportedOperationException();
    }

    public SOAPMEPConstants getMEP() {
        SOAPMEPConstants mep = null;
        if ((this.getModel().getInput() != null) && (this.getModel().getOutput() != null)) {
            mep = SOAPMEPConstants.REQUEST_RESPONSE;
        } else if (this.getModel().getInput() != null) {
            mep = SOAPMEPConstants.ONE_WAY;
        }
        return mep;
    }

    public void setMEP(final SOAPMEPConstants mep) {
    	LOG.warning("Do nothing: No mep attribute in wsdl 1.1 description");
    }

    @SuppressWarnings("unchecked")
    public String getHttpLocation() {
        String httpLocation = null;
        for (final Object element : this.model.getAny()) {
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.OperationType) {
                httpLocation = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.http.OperationType) ((JAXBElement) element)
                        .getValue()).getLocation();
                break;
            }
        }
        return httpLocation;
    }

    @SuppressWarnings("unchecked")
    public String getSoapAction() {
        String soapAction = null;
        for (final Object element : this.model.getAny()) {
        	if(element instanceof Element) {
        		soapAction = ((Element)element).getAttribute("soapAction");
        	} else if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) {
                soapAction = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) ((JAXBElement) element)
                        .getValue()).getSoapAction();
                break;
            } else if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) {
                soapAction = ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) ((JAXBElement) element)
                        .getValue()).getSoapAction();
                break;
            }
        }

        return soapAction;
    }
    
    @SuppressWarnings("unchecked")
    public void setSoapAction(String soapAction) {
        boolean find = false;
        for (final Object element : this.model.getAny()) {
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) {
                ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation) ((JAXBElement) element)
                        .getValue()).setSoapAction(soapAction);
                find = true;
                break;
            }
            if (((JAXBElement) element).getValue() instanceof org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) {
                ((org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12.TOperation) ((JAXBElement) element)
                        .getValue()).setSoapAction(soapAction);
                find = true;
                break;
            }
        }
        
        if(!find) {
        	// create default soap 1.1 operation in document mode 
        	org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation op = new org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TOperation();
        	op.setSoapAction(soapAction);
        	op.setStyle(TStyleChoice.DOCUMENT);
        	
        	// add in model
        	this.model.getAny().add(this.soap11BindingFactory.createOperation(op));
        }

    }

    public String getHttpContentEncodingDefault() {
        return null;
    }

    public String getHttpFaultSerialization() {
        return null;
    }

    public String getHttpInputSerialization() {
        return null;
    }

    public String getHttpMethod() {
        return this.getBinding().getTransportProtocol();
    }

    public String getHttpOutputSerialization() {
        return null;
    }

    public String getHttpQueryParameterSeparator() {
        return null;
    }

    public boolean isHttpIgnoreUncited() {
        return false;
    }

	public BindingFault createFault() {
		return new BindingFaultImpl(new TBindingOperationFault(), this);
	}

	public BindingInput createInput() {
		return new BindingInputImpl(new TBindingOperationMessage(), this);
	}

	public BindingOutput createOutput() {
		return new BindingOutputImpl(new TBindingOperationMessage() , this);
	}

}
