
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UrbanPropertyAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UrbanPropertyAddress">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}GeographicAddress">
 *       &lt;sequence>
 *         &lt;element name="streetNrFirst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetNrFirstSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetNrLast" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetNrLastSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="additionalAttribute1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="additionalAttribute2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UrbanPropertyAddress", propOrder = {
    "id",
	"streetNrFirst",
    "streetNrFirstSuffix",
    "streetNrLast",
    "streetNrLastSuffix",
    "streetName",
    "streetType",
    "streetSuffix",
    "locality",
    "postCode",
    "addressType",
    "addressCode",
    "additionalAttribute1",
    "additionalAttribute2",
    "categorizedByName",
    "flatApartment"
})
public class UrbanPropertyAddress
    extends GeographicAddress
{
	protected String id;
    protected String streetNrFirst;
    protected String streetNrFirstSuffix;
    protected String streetNrLast;
    protected String streetNrLastSuffix;
    protected String streetName;
    protected String streetType;
    protected String streetSuffix;
    protected String locality;
    protected String postCode;
    protected String addressType;
    protected String addressCode;
    protected String additionalAttribute1;
    protected String additionalAttribute2;
    protected String categorizedByName;
    protected String flatApartment;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Gets the value of the streetNrFirst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNrFirst() {
        return streetNrFirst;
    }

    /**
     * Sets the value of the streetNrFirst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNrFirst(String value) {
        this.streetNrFirst = value;
    }

    /**
     * Gets the value of the streetNrFirstSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNrFirstSuffix() {
        return streetNrFirstSuffix;
    }

    /**
     * Sets the value of the streetNrFirstSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNrFirstSuffix(String value) {
        this.streetNrFirstSuffix = value;
    }

    /**
     * Gets the value of the streetNrLast property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNrLast() {
        return streetNrLast;
    }

    /**
     * Sets the value of the streetNrLast property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNrLast(String value) {
        this.streetNrLast = value;
    }

    /**
     * Gets the value of the streetNrLastSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNrLastSuffix() {
        return streetNrLastSuffix;
    }

    /**
     * Sets the value of the streetNrLastSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNrLastSuffix(String value) {
        this.streetNrLastSuffix = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the streetType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetType() {
        return streetType;
    }

    /**
     * Sets the value of the streetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetType(String value) {
        this.streetType = value;
    }

    /**
     * Gets the value of the streetSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetSuffix() {
        return streetSuffix;
    }

    /**
     * Sets the value of the streetSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetSuffix(String value) {
        this.streetSuffix = value;
    }

    /**
     * Gets the value of the locality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Sets the value of the locality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocality(String value) {
        this.locality = value;
    }

    /**
     * Gets the value of the postCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets the value of the postCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCode(String value) {
        this.postCode = value;
    }

    /**
     * Gets the value of the addressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the value of the addressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressType(String value) {
        this.addressType = value;
    }

    /**
     * Gets the value of the addressCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressCode() {
        return addressCode;
    }

    /**
     * Sets the value of the addressCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressCode(String value) {
        this.addressCode = value;
    }

    /**
     * Gets the value of the additionalAttribute1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalAttribute1() {
        return additionalAttribute1;
    }

    /**
     * Sets the value of the additionalAttribute1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalAttribute1(String value) {
        this.additionalAttribute1 = value;
    }

    /**
     * Gets the value of the additionalAttribute2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalAttribute2() {
        return additionalAttribute2;
    }

    /**
     * Sets the value of the additionalAttribute2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalAttribute2(String value) {
        this.additionalAttribute2 = value;
    }

    /**
     * Gets the value of the categorizedByName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategorizedByName() {
        return categorizedByName;
    }

    /**
     * Sets the value of the categorizedByName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategorizedByName(String value) {
        this.categorizedByName = value;
    }

    /**
     * Gets the value of the flatApartment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getFlatApartment() {
		return flatApartment;
	}

    /**
     * Sets the value of the flatApartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setFlatApartment(String flatApartment) {
		this.flatApartment = flatApartment;
	}
    
    
    
}
