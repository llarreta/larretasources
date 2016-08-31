//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.12 at 05:28:12 PM ART 
//


package co.com.directv.sdii.dto.esb.event.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Customer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Customer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/businessdomain/common/v1-0}PartyRole">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerRank" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CustomerAccountList" type="{http://directvla.com/schema/businessdomain/customer/v1-0}CustomerAccountCollection" minOccurs="0"/>
 *         &lt;element name="IndividualRole" type="{http://directvla.com/schema/businessdomain/common/v1-0}Individual" minOccurs="0"/>
 *         &lt;element name="BusinessUnitId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="magazines" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="agreement" type="{http://directvla.com/schema/businessdomain/common/v1-0}Agreement" minOccurs="0"/>
 *         &lt;element name="characteristics" type="{http://directvla.com/schema/businessdomain/common/v1-0}CharacteristicsCollection" minOccurs="0"/>
 *         &lt;element name="SegmentationKey" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", namespace = "http://directvla.com/schema/businessdomain/customer/v1-0", propOrder = {
    "id",
    "customerRank",
    "customerAccountList",
    "individualRole",
    "businessUnitId",
    "magazines",
    "agreement",
    "characteristics",
    "segmentationKey"
})
public class Customer
    extends PartyRole
{

    @XmlElement(name = "ID")
    protected String id;
    protected Long customerRank;
    @XmlElement(name = "CustomerAccountList")
    protected CustomerAccountCollection customerAccountList;
    @XmlElement(name = "IndividualRole")
    protected Individual individualRole;
    @XmlElement(name = "BusinessUnitId")
    protected Integer businessUnitId;
    protected Integer magazines;
    protected Agreement agreement;
    protected CharacteristicsCollection characteristics;
    @XmlElement(name = "SegmentationKey")
    protected Integer segmentationKey;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
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
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the customerRank property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCustomerRank() {
        return customerRank;
    }

    /**
     * Sets the value of the customerRank property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCustomerRank(Long value) {
        this.customerRank = value;
    }

    /**
     * Gets the value of the customerAccountList property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAccountCollection }
     *     
     */
    public CustomerAccountCollection getCustomerAccountList() {
        return customerAccountList;
    }

    /**
     * Sets the value of the customerAccountList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAccountCollection }
     *     
     */
    public void setCustomerAccountList(CustomerAccountCollection value) {
        this.customerAccountList = value;
    }

    /**
     * Gets the value of the individualRole property.
     * 
     * @return
     *     possible object is
     *     {@link Individual }
     *     
     */
    public Individual getIndividualRole() {
        return individualRole;
    }

    /**
     * Sets the value of the individualRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link Individual }
     *     
     */
    public void setIndividualRole(Individual value) {
        this.individualRole = value;
    }

    /**
     * Gets the value of the businessUnitId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBusinessUnitId() {
        return businessUnitId;
    }

    /**
     * Sets the value of the businessUnitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBusinessUnitId(Integer value) {
        this.businessUnitId = value;
    }

    /**
     * Gets the value of the magazines property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMagazines() {
        return magazines;
    }

    /**
     * Sets the value of the magazines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMagazines(Integer value) {
        this.magazines = value;
    }

    /**
     * Gets the value of the agreement property.
     * 
     * @return
     *     possible object is
     *     {@link Agreement }
     *     
     */
    public Agreement getAgreement() {
        return agreement;
    }

    /**
     * Sets the value of the agreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Agreement }
     *     
     */
    public void setAgreement(Agreement value) {
        this.agreement = value;
    }

    /**
     * Gets the value of the characteristics property.
     * 
     * @return
     *     possible object is
     *     {@link CharacteristicsCollection }
     *     
     */
    public CharacteristicsCollection getCharacteristics() {
        return characteristics;
    }

    /**
     * Sets the value of the characteristics property.
     * 
     * @param value
     *     allowed object is
     *     {@link CharacteristicsCollection }
     *     
     */
    public void setCharacteristics(CharacteristicsCollection value) {
        this.characteristics = value;
    }

    /**
     * Gets the value of the segmentationKey property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSegmentationKey() {
        return segmentationKey;
    }

    /**
     * Sets the value of the segmentationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSegmentationKey(Integer value) {
        this.segmentationKey = value;
    }

}
