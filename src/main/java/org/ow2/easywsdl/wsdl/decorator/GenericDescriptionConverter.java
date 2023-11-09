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

import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
@SuppressWarnings("unchecked")
public class GenericDescriptionConverter<DNew extends AbsItfDescription, DImpl extends DecoratorDescriptionImpl> {


    public GenericDescriptionConverter() {
    }


    public DNew convertDescription(final AbsItfDescription odlDesc, final Class<DImpl> dimpl)
            throws WSDLException {
        DNew newDesc = null;
        try {
            // create new description
            final Constructor c = dimpl.getConstructors()[0];
            newDesc = (DNew) c.newInstance(odlDesc);
        } catch (final IllegalArgumentException e) {
            throw new WSDLException(e);
        } catch (final InstantiationException e) {
            throw new WSDLException(e);
        } catch (final IllegalAccessException e) {
            throw new WSDLException(e);
        } catch (final InvocationTargetException e) {
            throw new WSDLException(e);
        }
        return newDesc;
    }

}
