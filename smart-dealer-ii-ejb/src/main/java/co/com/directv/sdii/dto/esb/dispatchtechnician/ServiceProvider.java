//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.07 at 10:11:01 AM ART 
//


package co.com.directv.sdii.dto.esb.dispatchtechnician;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceProvider complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceProvider">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/businessdomain/common/v1-0}PartyRole">
 *       &lt;sequence>
 *         &lt;element name="CommisionType" type="{http://directvla.com/schema/businessdomain/common/v1-0}Category" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="FinancialAccountId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceProvider", propOrder = {
    "commisionType",
    "id",
    "isActive",
    "financialAccountId"
})
public class ServiceProvider
    extends PartyRole
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "CommisionType")
    protected Category commisionType;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "IsActive", defaultValue = "false")
    protected Boolean isActive;
    @XmlElement(name = "FinancialAccountId")
    protected Integer financialAccountId;

    /**
     * Gets the value of the commisionType property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getCommisionType() {
        return commisionType;
    }

    /**
     * Sets the value of the commisionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setCommisionType(Category value) {
        this.commisionType = value;
    }

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
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsActive(Boolean value) {
        this.isActive = value;
    }

    /**
     * Gets the value of the financialAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFinancialAccountId() {
        return financialAccountId;
    }

    /**
     * Sets the value of the financialAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFinancialAccountId(Integer value) {
        this.financialAccountId = value;
    }

}
