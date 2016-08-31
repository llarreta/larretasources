
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Service complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Service">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://directvla.com/schema/crm/common/v1-1}TimePeriod" minOccurs="0"/>
 *         &lt;element name="isShippingOrderRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceCategory" type="{http://directvla.com/schema/crm/common/v1-1}serviceCategory" minOccurs="0"/>
 *         &lt;element name="serviceType" type="{http://directvla.com/schema/crm/common/v1-1}serviceType" minOccurs="0"/>
 *         &lt;element name="serviceStatus" type="{http://directvla.com/schema/crm/common/v1-1}serviceStatus" minOccurs="0"/>
 *         &lt;element name="workOrderKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Service", propOrder = {
    "serviceKey",
    "name",
    "code",
    "description",
    "productSerialNumber",
    "validFor",
    "isShippingOrderRequired",
    "serviceCategory",
    "serviceType",
    "serviceStatus",
    "workOrderKey"
})
public class Service {

    @XmlElement(required = true)
    protected String serviceKey;
    protected String name;
    protected String code;
    protected String description;
    protected String productSerialNumber;
    protected TimePeriod validFor;
    protected String isShippingOrderRequired;
    protected ServiceCategory serviceCategory;
    protected ServiceType serviceType;
    protected ServiceStatus serviceStatus;
    @XmlElement(required = true)
    protected String workOrderKey;

    /**
     * Gets the value of the serviceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceKey() {
        return serviceKey;
    }

    /**
     * Sets the value of the serviceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceKey(String value) {
        this.serviceKey = value;
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
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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
     * Gets the value of the productSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    /**
     * Sets the value of the productSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductSerialNumber(String value) {
        this.productSerialNumber = value;
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
     * Gets the value of the isShippingOrderRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsShippingOrderRequired() {
        return isShippingOrderRequired;
    }

    /**
     * Sets the value of the isShippingOrderRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsShippingOrderRequired(String value) {
        this.isShippingOrderRequired = value;
    }

    /**
     * Gets the value of the serviceCategory property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCategory }
     *     
     */
    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    /**
     * Sets the value of the serviceCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCategory }
     *     
     */
    public void setServiceCategory(ServiceCategory value) {
        this.serviceCategory = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceType }
     *     
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceType }
     *     
     */
    public void setServiceType(ServiceType value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the serviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStatus }
     *     
     */
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    /**
     * Sets the value of the serviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStatus }
     *     
     */
    public void setServiceStatus(ServiceStatus value) {
        this.serviceStatus = value;
    }

    /**
     * Gets the value of the workOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkOrderKey() {
        return workOrderKey;
    }

    /**
     * Sets the value of the workOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkOrderKey(String value) {
        this.workOrderKey = value;
    }

}
