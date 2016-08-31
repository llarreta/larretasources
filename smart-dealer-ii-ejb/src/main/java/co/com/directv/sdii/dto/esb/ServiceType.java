
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceType", propOrder = {
    "serviceTypeCode",
    "serviceTypeName"
})
public class ServiceType {

    protected String serviceTypeCode;
    protected String serviceTypeName;

    /**
     * Gets the value of the serviceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    /**
     * Sets the value of the serviceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceTypeCode(String value) {
        this.serviceTypeCode = value;
    }

    /**
     * Gets the value of the serviceTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceTypeName() {
        return serviceTypeName;
    }

    /**
     * Sets the value of the serviceTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceTypeName(String value) {
        this.serviceTypeName = value;
    }

}
