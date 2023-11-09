//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.09 at 10:58:42 PM CET 
//


package org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals2;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy2;
import org.jvnet.jaxb2_commons.lang.HashCode2;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy2;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString2;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy2;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TExtensibilityElement;


/**
 * <p>Java class for tBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tBody"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/}tExtensibilityElement"&gt;
 *       &lt;attGroup ref="{http://schemas.xmlsoap.org/wsdl/soap/}tBodyAttributes"/&gt;
 *       &lt;attribute name="parts" type="{http://www.w3.org/2001/XMLSchema}NMTOKENS" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBody")
@XmlSeeAlso({
    TFaultRes.class
})
public class TBody
    extends TExtensibilityElement
    implements Equals2, HashCode2, ToString2
{

    @XmlAttribute(name = "parts")
    @XmlSchemaType(name = "NMTOKENS")
    protected List<String> parts;
    @XmlAttribute(name = "encodingStyle")
    protected List<String> encodingStyle;
    @XmlAttribute(name = "use")
    protected UseChoice use;
    @XmlAttribute(name = "namespace")
    @XmlSchemaType(name = "anyURI")
    protected String namespace;

    /**
     * Gets the value of the parts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getParts() {
        if (parts == null) {
            parts = new ArrayList<String>();
        }
        return this.parts;
    }

    /**
     * Gets the value of the encodingStyle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the encodingStyle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEncodingStyle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEncodingStyle() {
        if (encodingStyle == null) {
            encodingStyle = new ArrayList<String>();
        }
        return this.encodingStyle;
    }

    /**
     * Gets the value of the use property.
     * 
     * @return
     *     possible object is
     *     {@link UseChoice }
     *     
     */
    public UseChoice getUse() {
        return use;
    }

    /**
     * Sets the value of the use property.
     * 
     * @param value
     *     allowed object is
     *     {@link UseChoice }
     *     
     */
    public void setUse(UseChoice value) {
        this.use = value;
    }

    /**
     * Gets the value of the namespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets the value of the namespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    public String toString() {
        final ToStringStrategy2 strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy2 strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy2 strategy) {
        super.appendFields(locator, buffer, strategy);
        {
            List<String> theParts;
            theParts = (((this.parts!= null)&&(!this.parts.isEmpty()))?this.getParts():null);
            strategy.appendField(locator, this, "parts", buffer, theParts, ((this.parts!= null)&&(!this.parts.isEmpty())));
        }
        {
            List<String> theEncodingStyle;
            theEncodingStyle = (((this.encodingStyle!= null)&&(!this.encodingStyle.isEmpty()))?this.getEncodingStyle():null);
            strategy.appendField(locator, this, "encodingStyle", buffer, theEncodingStyle, ((this.encodingStyle!= null)&&(!this.encodingStyle.isEmpty())));
        }
        {
            UseChoice theUse;
            theUse = this.getUse();
            strategy.appendField(locator, this, "use", buffer, theUse, (this.use!= null));
        }
        {
            String theNamespace;
            theNamespace = this.getNamespace();
            strategy.appendField(locator, this, "namespace", buffer, theNamespace, (this.namespace!= null));
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy2 strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!super.equals(thisLocator, thatLocator, object, strategy)) {
            return false;
        }
        final TBody that = ((TBody) object);
        {
            List<String> lhsParts;
            lhsParts = (((this.parts!= null)&&(!this.parts.isEmpty()))?this.getParts():null);
            List<String> rhsParts;
            rhsParts = (((that.parts!= null)&&(!that.parts.isEmpty()))?that.getParts():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "parts", lhsParts), LocatorUtils.property(thatLocator, "parts", rhsParts), lhsParts, rhsParts, ((this.parts!= null)&&(!this.parts.isEmpty())), ((that.parts!= null)&&(!that.parts.isEmpty())))) {
                return false;
            }
        }
        {
            List<String> lhsEncodingStyle;
            lhsEncodingStyle = (((this.encodingStyle!= null)&&(!this.encodingStyle.isEmpty()))?this.getEncodingStyle():null);
            List<String> rhsEncodingStyle;
            rhsEncodingStyle = (((that.encodingStyle!= null)&&(!that.encodingStyle.isEmpty()))?that.getEncodingStyle():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "encodingStyle", lhsEncodingStyle), LocatorUtils.property(thatLocator, "encodingStyle", rhsEncodingStyle), lhsEncodingStyle, rhsEncodingStyle, ((this.encodingStyle!= null)&&(!this.encodingStyle.isEmpty())), ((that.encodingStyle!= null)&&(!that.encodingStyle.isEmpty())))) {
                return false;
            }
        }
        {
            UseChoice lhsUse;
            lhsUse = this.getUse();
            UseChoice rhsUse;
            rhsUse = that.getUse();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "use", lhsUse), LocatorUtils.property(thatLocator, "use", rhsUse), lhsUse, rhsUse, (this.use!= null), (that.use!= null))) {
                return false;
            }
        }
        {
            String lhsNamespace;
            lhsNamespace = this.getNamespace();
            String rhsNamespace;
            rhsNamespace = that.getNamespace();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "namespace", lhsNamespace), LocatorUtils.property(thatLocator, "namespace", rhsNamespace), lhsNamespace, rhsNamespace, (this.namespace!= null), (that.namespace!= null))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy2 strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy2 strategy) {
        int currentHashCode = super.hashCode(locator, strategy);
        {
            List<String> theParts;
            theParts = (((this.parts!= null)&&(!this.parts.isEmpty()))?this.getParts():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "parts", theParts), currentHashCode, theParts, ((this.parts!= null)&&(!this.parts.isEmpty())));
        }
        {
            List<String> theEncodingStyle;
            theEncodingStyle = (((this.encodingStyle!= null)&&(!this.encodingStyle.isEmpty()))?this.getEncodingStyle():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "encodingStyle", theEncodingStyle), currentHashCode, theEncodingStyle, ((this.encodingStyle!= null)&&(!this.encodingStyle.isEmpty())));
        }
        {
            UseChoice theUse;
            theUse = this.getUse();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "use", theUse), currentHashCode, theUse, (this.use!= null));
        }
        {
            String theNamespace;
            theNamespace = this.getNamespace();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "namespace", theNamespace), currentHashCode, theNamespace, (this.namespace!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
