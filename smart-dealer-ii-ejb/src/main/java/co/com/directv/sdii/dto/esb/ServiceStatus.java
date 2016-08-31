
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceStatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceStatusName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceStatus", propOrder = {
    "serviceStatusCode",
    "serviceStatusName"
})
public class ServiceStatus {

    protected String serviceStatusCode;
    protected String serviceStatusName;

    /**
     * Gets the value of the serviceStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceStatusCode() {
        return serviceStatusCode;
    }

    /**
     * Sets the value of the serviceStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceStatusCode(String value) {
        this.serviceStatusCode = value;
    }

    /**
     * Gets the value of the serviceStatusName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceStatusName() {
        return serviceStatusName;
    }

    /**
     * Sets the value of the serviceStatusName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceStatusName(String value) {
        this.serviceStatusName = value;
    }

}
