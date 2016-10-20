//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.28 at 05:10:27 PM ART 
//


package co.com.directv.sdii.dto.esb.event.publishworkorderevent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Agreement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Agreement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/businessdomain/common/v1-0}BusinessInteraction">
 *       &lt;sequence>
 *         &lt;element name="agreementDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agreementStatementOfIntent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agreementPeriod" type="{http://directvla.com/schema/businessdomain/common/v1-0}TimePeriod" minOccurs="0"/>
 *         &lt;element name="AgreementConsistsOf" type="{http://directvla.com/schema/businessdomain/common/v1-0}AgreementItemCollection" minOccurs="0"/>
 *         &lt;element name="marketSegment" type="{http://directvla.com/schema/businessdomain/common/v1-0}Category" minOccurs="0"/>
 *         &lt;element name="financialAccountID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Agreement", propOrder = {
    "agreementDocumentNumber",
    "agreementStatementOfIntent",
    "agreementPeriod",
    "agreementConsistsOf",
    "marketSegment",
    "financialAccountID"
})
public class Agreement
    extends BusinessInteraction
{

    protected String agreementDocumentNumber;
    protected String agreementStatementOfIntent;
    protected TimePeriod agreementPeriod;
    @XmlElement(name = "AgreementConsistsOf")
    protected AgreementItemCollection agreementConsistsOf;
    protected Category marketSegment;
    protected Integer financialAccountID;

    /**
     * Gets the value of the agreementDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementDocumentNumber() {
        return agreementDocumentNumber;
    }

    /**
     * Sets the value of the agreementDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementDocumentNumber(String value) {
        this.agreementDocumentNumber = value;
    }

    /**
     * Gets the value of the agreementStatementOfIntent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementStatementOfIntent() {
        return agreementStatementOfIntent;
    }

    /**
     * Sets the value of the agreementStatementOfIntent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementStatementOfIntent(String value) {
        this.agreementStatementOfIntent = value;
    }

    /**
     * Gets the value of the agreementPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriod }
     *     
     */
    public TimePeriod getAgreementPeriod() {
        return agreementPeriod;
    }

    /**
     * Sets the value of the agreementPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriod }
     *     
     */
    public void setAgreementPeriod(TimePeriod value) {
        this.agreementPeriod = value;
    }

    /**
     * Gets the value of the agreementConsistsOf property.
     * 
     * @return
     *     possible object is
     *     {@link AgreementItemCollection }
     *     
     */
    public AgreementItemCollection getAgreementConsistsOf() {
        return agreementConsistsOf;
    }

    /**
     * Sets the value of the agreementConsistsOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreementItemCollection }
     *     
     */
    public void setAgreementConsistsOf(AgreementItemCollection value) {
        this.agreementConsistsOf = value;
    }

    /**
     * Gets the value of the marketSegment property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getMarketSegment() {
        return marketSegment;
    }

    /**
     * Sets the value of the marketSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setMarketSegment(Category value) {
        this.marketSegment = value;
    }

    /**
     * Gets the value of the financialAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFinancialAccountID() {
        return financialAccountID;
    }

    /**
     * Sets the value of the financialAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFinancialAccountID(Integer value) {
        this.financialAccountID = value;
    }

}