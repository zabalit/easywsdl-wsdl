//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.09 at 10:58:42 PM CET 
//


package org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap12;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
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
 * <p>Java class for tHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tHeader"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/soap12/}tExtensibilityElementOpenAttrs"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.xmlsoap.org/wsdl/soap12/}headerfault" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://schemas.xmlsoap.org/wsdl/soap12/}tHeaderAttributes"/&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tHeader", propOrder = {
    "headerfault"
})
public class THeader
    extends TExtensibilityElementOpenAttrs
    implements Equals2, HashCode2, ToString2
{

    protected List<THeaderFault> headerfault;
    @XmlAttribute(name = "message", required = true)
    protected QName message;
    @XmlAttribute(name = "part", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String part;
    @XmlAttribute(name = "use", required = true)
    protected UseChoice use;
    @XmlAttribute(name = "encodingStyle")
    @XmlSchemaType(name = "anyURI")
    protected String encodingStyle;
    @XmlAttribute(name = "namespace")
    @XmlSchemaType(name = "anyURI")
    protected String namespace;

    /**
     * Gets the value of the headerfault property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the headerfault property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeaderfault().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link THeaderFault }
     * 
     * 
     */
    public List<THeaderFault> getHeaderfault() {
        if (headerfault == null) {
            headerfault = new ArrayList<THeaderFault>();
        }
        return this.headerfault;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setMessage(QName value) {
        this.message = value;
    }

    /**
     * Gets the value of the part property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPart() {
        return part;
    }

    /**
     * Sets the value of the part property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPart(String value) {
        this.part = value;
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
     * Gets the value of the encodingStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodingStyle() {
        return encodingStyle;
    }

    /**
     * Sets the value of the encodingStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodingStyle(String value) {
        this.encodingStyle = value;
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
            List<THeaderFault> theHeaderfault;
            theHeaderfault = (((this.headerfault!= null)&&(!this.headerfault.isEmpty()))?this.getHeaderfault():null);
            strategy.appendField(locator, this, "headerfault", buffer, theHeaderfault, ((this.headerfault!= null)&&(!this.headerfault.isEmpty())));
        }
        {
            QName theMessage;
            theMessage = this.getMessage();
            strategy.appendField(locator, this, "message", buffer, theMessage, (this.message!= null));
        }
        {
            String thePart;
            thePart = this.getPart();
            strategy.appendField(locator, this, "part", buffer, thePart, (this.part!= null));
        }
        {
            UseChoice theUse;
            theUse = this.getUse();
            strategy.appendField(locator, this, "use", buffer, theUse, (this.use!= null));
        }
        {
            String theEncodingStyle;
            theEncodingStyle = this.getEncodingStyle();
            strategy.appendField(locator, this, "encodingStyle", buffer, theEncodingStyle, (this.encodingStyle!= null));
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
        final THeader that = ((THeader) object);
        {
            List<THeaderFault> lhsHeaderfault;
            lhsHeaderfault = (((this.headerfault!= null)&&(!this.headerfault.isEmpty()))?this.getHeaderfault():null);
            List<THeaderFault> rhsHeaderfault;
            rhsHeaderfault = (((that.headerfault!= null)&&(!that.headerfault.isEmpty()))?that.getHeaderfault():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "headerfault", lhsHeaderfault), LocatorUtils.property(thatLocator, "headerfault", rhsHeaderfault), lhsHeaderfault, rhsHeaderfault, ((this.headerfault!= null)&&(!this.headerfault.isEmpty())), ((that.headerfault!= null)&&(!that.headerfault.isEmpty())))) {
                return false;
            }
        }
        {
            QName lhsMessage;
            lhsMessage = this.getMessage();
            QName rhsMessage;
            rhsMessage = that.getMessage();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "message", lhsMessage), LocatorUtils.property(thatLocator, "message", rhsMessage), lhsMessage, rhsMessage, (this.message!= null), (that.message!= null))) {
                return false;
            }
        }
        {
            String lhsPart;
            lhsPart = this.getPart();
            String rhsPart;
            rhsPart = that.getPart();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "part", lhsPart), LocatorUtils.property(thatLocator, "part", rhsPart), lhsPart, rhsPart, (this.part!= null), (that.part!= null))) {
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
            String lhsEncodingStyle;
            lhsEncodingStyle = this.getEncodingStyle();
            String rhsEncodingStyle;
            rhsEncodingStyle = that.getEncodingStyle();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "encodingStyle", lhsEncodingStyle), LocatorUtils.property(thatLocator, "encodingStyle", rhsEncodingStyle), lhsEncodingStyle, rhsEncodingStyle, (this.encodingStyle!= null), (that.encodingStyle!= null))) {
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
            List<THeaderFault> theHeaderfault;
            theHeaderfault = (((this.headerfault!= null)&&(!this.headerfault.isEmpty()))?this.getHeaderfault():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "headerfault", theHeaderfault), currentHashCode, theHeaderfault, ((this.headerfault!= null)&&(!this.headerfault.isEmpty())));
        }
        {
            QName theMessage;
            theMessage = this.getMessage();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "message", theMessage), currentHashCode, theMessage, (this.message!= null));
        }
        {
            String thePart;
            thePart = this.getPart();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "part", thePart), currentHashCode, thePart, (this.part!= null));
        }
        {
            UseChoice theUse;
            theUse = this.getUse();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "use", theUse), currentHashCode, theUse, (this.use!= null));
        }
        {
            String theEncodingStyle;
            theEncodingStyle = this.getEncodingStyle();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "encodingStyle", theEncodingStyle), currentHashCode, theEncodingStyle, (this.encodingStyle!= null));
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
