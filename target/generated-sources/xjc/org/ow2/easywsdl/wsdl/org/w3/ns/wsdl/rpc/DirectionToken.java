//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.09 at 10:58:42 PM CET 
//


package org.ow2.easywsdl.wsdl.org.w3.ns.wsdl.rpc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for directionToken.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="directionToken"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="#in"/&gt;
 *     &lt;enumeration value="#out"/&gt;
 *     &lt;enumeration value="#inout"/&gt;
 *     &lt;enumeration value="#return"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "directionToken", namespace = "http://www.w3.org/ns/wsdl/rpc")
@XmlEnum
public enum DirectionToken {

    @XmlEnumValue("#in")
    IN("#in"),
    @XmlEnumValue("#out")
    OUT("#out"),
    @XmlEnumValue("#inout")
    INOUT("#inout"),
    @XmlEnumValue("#return")
    RETURN("#return");
    private final String value;

    DirectionToken(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DirectionToken fromValue(String v) {
        for (DirectionToken c: DirectionToken.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}