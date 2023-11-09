//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.09 at 10:58:42 PM CET 
//


package org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
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


/**
 * <p>Java class for tOperation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tOperation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/soap12/}tExtensibilityElementOpenAttrs"&gt;
 *       &lt;attribute name="soapAction" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *       &lt;attribute name="soapActionRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="style" type="{http://schemas.xmlsoap.org/wsdl/soap12/}tStyleChoice" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tOperation")
public class TOperation
    extends TExtensibilityElementOpenAttrs
    implements Equals2, HashCode2, ToString2
{

    @XmlAttribute(name = "soapAction")
    @XmlSchemaType(name = "anyURI")
    protected String soapAction;
    @XmlAttribute(name = "soapActionRequired")
    protected Boolean soapActionRequired;
    @XmlAttribute(name = "style")
    protected TStyleChoice style;

    /**
     * Gets the value of the soapAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoapAction() {
        return soapAction;
    }

    /**
     * Sets the value of the soapAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoapAction(String value) {
        this.soapAction = value;
    }

    /**
     * Gets the value of the soapActionRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSoapActionRequired() {
        return soapActionRequired;
    }

    /**
     * Sets the value of the soapActionRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSoapActionRequired(Boolean value) {
        this.soapActionRequired = value;
    }

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link TStyleChoice }
     *     
     */
    public TStyleChoice getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStyleChoice }
     *     
     */
    public void setStyle(TStyleChoice value) {
        this.style = value;
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
            String theSoapAction;
            theSoapAction = this.getSoapAction();
            strategy.appendField(locator, this, "soapAction", buffer, theSoapAction, (this.soapAction!= null));
        }
        {
            Boolean theSoapActionRequired;
            theSoapActionRequired = this.isSoapActionRequired();
            strategy.appendField(locator, this, "soapActionRequired", buffer, theSoapActionRequired, (this.soapActionRequired!= null));
        }
        {
            TStyleChoice theStyle;
            theStyle = this.getStyle();
            strategy.appendField(locator, this, "style", buffer, theStyle, (this.style!= null));
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
        final TOperation that = ((TOperation) object);
        {
            String lhsSoapAction;
            lhsSoapAction = this.getSoapAction();
            String rhsSoapAction;
            rhsSoapAction = that.getSoapAction();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "soapAction", lhsSoapAction), LocatorUtils.property(thatLocator, "soapAction", rhsSoapAction), lhsSoapAction, rhsSoapAction, (this.soapAction!= null), (that.soapAction!= null))) {
                return false;
            }
        }
        {
            Boolean lhsSoapActionRequired;
            lhsSoapActionRequired = this.isSoapActionRequired();
            Boolean rhsSoapActionRequired;
            rhsSoapActionRequired = that.isSoapActionRequired();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "soapActionRequired", lhsSoapActionRequired), LocatorUtils.property(thatLocator, "soapActionRequired", rhsSoapActionRequired), lhsSoapActionRequired, rhsSoapActionRequired, (this.soapActionRequired!= null), (that.soapActionRequired!= null))) {
                return false;
            }
        }
        {
            TStyleChoice lhsStyle;
            lhsStyle = this.getStyle();
            TStyleChoice rhsStyle;
            rhsStyle = that.getStyle();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "style", lhsStyle), LocatorUtils.property(thatLocator, "style", rhsStyle), lhsStyle, rhsStyle, (this.style!= null), (that.style!= null))) {
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
            String theSoapAction;
            theSoapAction = this.getSoapAction();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "soapAction", theSoapAction), currentHashCode, theSoapAction, (this.soapAction!= null));
        }
        {
            Boolean theSoapActionRequired;
            theSoapActionRequired = this.isSoapActionRequired();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "soapActionRequired", theSoapActionRequired), currentHashCode, theSoapActionRequired, (this.soapActionRequired!= null));
        }
        {
            TStyleChoice theStyle;
            theStyle = this.getStyle();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "style", theStyle), currentHashCode, theStyle, (this.style!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}