//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.09 at 10:58:42 PM CET 
//


package org.ow2.easywsdl.wsdl.org.w3.ns.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
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
import org.w3c.dom.Element;


/**
 * <p>Java class for BindingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BindingType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.w3.org/ns/wsdl}ExtensibleDocumentedType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element name="operation" type="{http://www.w3.org/ns/wsdl}BindingOperationType"/&gt;
 *         &lt;element name="fault" type="{http://www.w3.org/ns/wsdl}BindingFaultType"/&gt;
 *         &lt;any processContents='lax' namespace='##other'/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" /&gt;
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *       &lt;attribute name="interface" type="{http://www.w3.org/2001/XMLSchema}QName" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BindingType", propOrder = {
    "operationOrFaultOrAny"
})
public class BindingType
    extends ExtensibleDocumentedType
    implements Equals2, HashCode2, ToString2
{

    @XmlElementRefs({
        @XmlElementRef(name = "fault", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "operation", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class, required = false)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> operationOrFaultOrAny;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "type", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String type;
    @XmlAttribute(name = "interface")
    protected QName _interface;

    /**
     * Gets the value of the operationOrFaultOrAny property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operationOrFaultOrAny property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperationOrFaultOrAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BindingFaultType }{@code >}
     * {@link JAXBElement }{@code <}{@link BindingOperationType }{@code >}
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getOperationOrFaultOrAny() {
        if (operationOrFaultOrAny == null) {
            operationOrFaultOrAny = new ArrayList<Object>();
        }
        return this.operationOrFaultOrAny;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the interface property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getInterface() {
        return _interface;
    }

    /**
     * Sets the value of the interface property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setInterface(QName value) {
        this._interface = value;
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
            List<Object> theOperationOrFaultOrAny;
            theOperationOrFaultOrAny = (((this.operationOrFaultOrAny!= null)&&(!this.operationOrFaultOrAny.isEmpty()))?this.getOperationOrFaultOrAny():null);
            strategy.appendField(locator, this, "operationOrFaultOrAny", buffer, theOperationOrFaultOrAny, ((this.operationOrFaultOrAny!= null)&&(!this.operationOrFaultOrAny.isEmpty())));
        }
        {
            String theName;
            theName = this.getName();
            strategy.appendField(locator, this, "name", buffer, theName, (this.name!= null));
        }
        {
            String theType;
            theType = this.getType();
            strategy.appendField(locator, this, "type", buffer, theType, (this.type!= null));
        }
        {
            QName theInterface;
            theInterface = this.getInterface();
            strategy.appendField(locator, this, "_interface", buffer, theInterface, (this._interface!= null));
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
        final BindingType that = ((BindingType) object);
        {
            List<Object> lhsOperationOrFaultOrAny;
            lhsOperationOrFaultOrAny = (((this.operationOrFaultOrAny!= null)&&(!this.operationOrFaultOrAny.isEmpty()))?this.getOperationOrFaultOrAny():null);
            List<Object> rhsOperationOrFaultOrAny;
            rhsOperationOrFaultOrAny = (((that.operationOrFaultOrAny!= null)&&(!that.operationOrFaultOrAny.isEmpty()))?that.getOperationOrFaultOrAny():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "operationOrFaultOrAny", lhsOperationOrFaultOrAny), LocatorUtils.property(thatLocator, "operationOrFaultOrAny", rhsOperationOrFaultOrAny), lhsOperationOrFaultOrAny, rhsOperationOrFaultOrAny, ((this.operationOrFaultOrAny!= null)&&(!this.operationOrFaultOrAny.isEmpty())), ((that.operationOrFaultOrAny!= null)&&(!that.operationOrFaultOrAny.isEmpty())))) {
                return false;
            }
        }
        {
            String lhsName;
            lhsName = this.getName();
            String rhsName;
            rhsName = that.getName();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "name", lhsName), LocatorUtils.property(thatLocator, "name", rhsName), lhsName, rhsName, (this.name!= null), (that.name!= null))) {
                return false;
            }
        }
        {
            String lhsType;
            lhsType = this.getType();
            String rhsType;
            rhsType = that.getType();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "type", lhsType), LocatorUtils.property(thatLocator, "type", rhsType), lhsType, rhsType, (this.type!= null), (that.type!= null))) {
                return false;
            }
        }
        {
            QName lhsInterface;
            lhsInterface = this.getInterface();
            QName rhsInterface;
            rhsInterface = that.getInterface();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "_interface", lhsInterface), LocatorUtils.property(thatLocator, "_interface", rhsInterface), lhsInterface, rhsInterface, (this._interface!= null), (that._interface!= null))) {
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
            List<Object> theOperationOrFaultOrAny;
            theOperationOrFaultOrAny = (((this.operationOrFaultOrAny!= null)&&(!this.operationOrFaultOrAny.isEmpty()))?this.getOperationOrFaultOrAny():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "operationOrFaultOrAny", theOperationOrFaultOrAny), currentHashCode, theOperationOrFaultOrAny, ((this.operationOrFaultOrAny!= null)&&(!this.operationOrFaultOrAny.isEmpty())));
        }
        {
            String theName;
            theName = this.getName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "name", theName), currentHashCode, theName, (this.name!= null));
        }
        {
            String theType;
            theType = this.getType();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "type", theType), currentHashCode, theType, (this.type!= null));
        }
        {
            QName theInterface;
            theInterface = this.getInterface();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "_interface", theInterface), currentHashCode, theInterface, (this._interface!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
