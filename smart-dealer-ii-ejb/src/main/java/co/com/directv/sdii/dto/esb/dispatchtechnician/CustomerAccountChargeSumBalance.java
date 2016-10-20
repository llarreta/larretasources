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
 * <p>Java class for CustomerAccountChargeSumBalance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerAccountChargeSumBalance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validFor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerAccountChargeSum" type="{http://directvla.com/schema/businessdomain/common/v1-0}Money"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccountChargeSumBalance", namespace = "http://directvla.com/schema/businessdomain/customer/v1-0", propOrder = {
    "validFor",
    "customerAccountChargeSum"
})
public class CustomerAccountChargeSumBalance
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String validFor;
    @XmlElement(required = true)
    protected Money customerAccountChargeSum;

    /**
     * Gets the value of the validFor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFor() {
        return validFor;
    }

    /**
     * Sets the value of the validFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFor(String value) {
        this.validFor = value;
    }

    /**
     * Gets the value of the customerAccountChargeSum property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCustomerAccountChargeSum() {
        return customerAccountChargeSum;
    }

    /**
     * Sets the value of the customerAccountChargeSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCustomerAccountChargeSum(Money value) {
        this.customerAccountChargeSum = value;
    }

}