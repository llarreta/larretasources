//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.15 at 10:51:09 AM ART 
//


package co.com.directv.sdii.dto.esb.notifyWorkOrdersOptimus;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeographicDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeographicDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GeographicAreas" type="{http://directvla.com/schema/businessdomain/common/v1-0}GeographicAreaCollection" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://directvla.com/schema/businessdomain/common/v1-0}GeographicLocationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeographicDefinition", propOrder = {
    "active",
    "description",
    "geographicAreas",
    "name",
    "type"
})
public class GeographicDefinition
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Active")
    protected Boolean active;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "GeographicAreas")
    protected GeographicAreaCollection geographicAreas;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Type")
    protected GeographicLocationType type;

    /**
     * Gets the value of the active property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(Boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the geographicAreas property.
     * 
     * @return
     *     possible object is
     *     {@link GeographicAreaCollection }
     *     
     */
    public GeographicAreaCollection getGeographicAreas() {
        return geographicAreas;
    }

    /**
     * Sets the value of the geographicAreas property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicAreaCollection }
     *     
     */
    public void setGeographicAreas(GeographicAreaCollection value) {
        this.geographicAreas = value;
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
     *     {@link GeographicLocationType }
     *     
     */
    public GeographicLocationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicLocationType }
     *     
     */
    public void setType(GeographicLocationType value) {
        this.type = value;
    }

}
