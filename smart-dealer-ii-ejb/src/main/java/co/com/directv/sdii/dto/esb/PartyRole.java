
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyRole">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="partyRoleId" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://directvla.com/schema/crm/common/v1-1}TimePeriod" minOccurs="0"/>
 *         &lt;element name="AddressList" type="{http://directvla.com/schema/crm/common/v1-1}UrbanPropertyAddressCollection" minOccurs="0"/>
 *         &lt;element name="ContactableVia" type="{http://directvla.com/schema/crm/common/v1-1}ContactMediumCollection" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyRole", propOrder = {
    "partyRoleId",
    "status",
    "name",
    "validFor",
    "addressList",
    "contactableVia"
})
@XmlSeeAlso({
    Customer.class
})
public abstract class PartyRole {

    @XmlSchemaType(name = "anySimpleType")
    protected Object partyRoleId;
    @XmlSchemaType(name = "anySimpleType")
    protected Object status;
    protected String name;
    protected TimePeriod validFor;
    @XmlElement(name = "AddressList")
    protected UrbanPropertyAddressCollection addressList;
    @XmlElement(name = "ContactableVia")
    protected ContactMediumCollection contactableVia;

    /**
     * Gets the value of the partyRoleId property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPartyRoleId() {
        return partyRoleId;
    }

    /**
     * Sets the value of the partyRoleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPartyRoleId(Object value) {
        this.partyRoleId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setStatus(Object value) {
        this.status = value;
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
     * Gets the value of the validFor property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriod }
     *     
     */
    public TimePeriod getValidFor() {
        return validFor;
    }

    /**
     * Sets the value of the validFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriod }
     *     
     */
    public void setValidFor(TimePeriod value) {
        this.validFor = value;
    }

    /**
     * Gets the value of the addressList property.
     * 
     * @return
     *     possible object is
     *     {@link UrbanPropertyAddressCollection }
     *     
     */
    public UrbanPropertyAddressCollection getAddressList() {
        return addressList;
    }

    /**
     * Sets the value of the addressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link UrbanPropertyAddressCollection }
     *     
     */
    public void setAddressList(UrbanPropertyAddressCollection value) {
        this.addressList = value;
    }

    /**
     * Gets the value of the contactableVia property.
     * 
     * @return
     *     possible object is
     *     {@link ContactMediumCollection }
     *     
     */
    public ContactMediumCollection getContactableVia() {
        return contactableVia;
    }

    /**
     * Sets the value of the contactableVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactMediumCollection }
     *     
     */
    public void setContactableVia(ContactMediumCollection value) {
        this.contactableVia = value;
    }

}
