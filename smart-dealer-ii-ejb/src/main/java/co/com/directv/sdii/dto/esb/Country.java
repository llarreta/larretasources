
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Country complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Country">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}GeographicLocation">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="Iso2Code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="Iso3Code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Country", propOrder = {
    "name",
    "iso2Code",
    "iso3Code",
    "type"
})
public class Country
    extends GeographicLocation
{

    @XmlSchemaType(name = "anySimpleType")
    protected Object name;
    @XmlElement(name = "Iso2Code")
    @XmlSchemaType(name = "anySimpleType")
    protected Object iso2Code;
    @XmlElement(name = "Iso3Code")
    @XmlSchemaType(name = "anySimpleType")
    protected Object iso3Code;
    @XmlSchemaType(name = "anySimpleType")
    protected Object type;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setName(Object value) {
        this.name = value;
    }

    /**
     * Gets the value of the iso2Code property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getIso2Code() {
        return iso2Code;
    }

    /**
     * Sets the value of the iso2Code property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setIso2Code(Object value) {
        this.iso2Code = value;
    }

    /**
     * Gets the value of the iso3Code property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getIso3Code() {
        return iso3Code;
    }

    /**
     * Sets the value of the iso3Code property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setIso3Code(Object value) {
        this.iso3Code = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setType(Object value) {
        this.type = value;
    }

}
