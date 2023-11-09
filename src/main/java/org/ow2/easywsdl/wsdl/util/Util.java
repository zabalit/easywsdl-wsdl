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
 
package org.ow2.easywsdl.wsdl.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ow2.easywsdl.schema.api.SchemaReader.FeatureConstants;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.abstractElmt.AbstractDescriptionImpl;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfDescription;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class Util {

	@SuppressWarnings("unchecked")
	public static Map<FeatureConstants, Object> convertWSDLFeatures2SchemaFeature(
			final AbsItfDescription desc) {
		final Map<FeatureConstants, Object> features = new HashMap<FeatureConstants, Object>();
		features
		.put(
				FeatureConstants.VERBOSE,
				((AbstractDescriptionImpl) desc)
				.getFeatureValue(org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants.VERBOSE));
		features
		.put(
				FeatureConstants.IMPORT_DOCUMENTS,
				((AbstractDescriptionImpl) desc)
				.getFeatureValue(org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants.IMPORT_DOCUMENTS));
		return features;
	}

	public static String getPrefix(final String name) {
		String res = null;
		if ((name != null)&&(name.indexOf(":") != -1)) {
			res = name.substring(0, name.indexOf(":"));
		}
		return res;
	}

	public static String getLocalPartWithoutPrefix(final String name) {
		String res = name;
		if (name.indexOf(':') != -1) {
			res = name.substring(name.indexOf(':') + 1, name.length());
		}
		return res;
	}

	public static String convertSchemaLocationIntoString(Description wsdlDef) {
        final StringBuilder buf = new StringBuilder();
		if(wsdlDef.getSchemaLocation().size() > 0) {
			for(Entry<String, String> entry: wsdlDef.getSchemaLocation().entrySet()) {
				buf.append(entry.getKey() + " " + entry.getValue() + " ");
			}
		} else {
			return null;
		}
		return buf.toString();
	}
}
