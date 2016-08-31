
package co.com.directv.sdii.dto.esb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Product complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Product">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://directvla.com/schema/crm/common/v1-1}TimePeriod" minOccurs="0"/>
 *         &lt;element name="productPrice" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modelKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = {
    "productKey",
    "name",
    "code",
    "description",
    "productStatus",
    "productSerialNumber",
    "validFor",
    "productPrice",
    "modelKey",
    "type",
    "productStatusName",
    "provisionedDevices",
    "provisionedDevicesRelated"
})
public class Product {

    @XmlElement(required = true)
    protected String productKey;
    protected String name;
    protected String code;
    protected String description;
    protected String productStatus;
    protected String productSerialNumber;
    protected TimePeriod validFor;
    @XmlElement(type = Long.class)
    protected List<Long> productPrice;
    @XmlElement(required = true)
    protected String modelKey;
    @XmlElement(name = "Type", required = true)
    protected String type;
    protected String productStatusName;
    @XmlElement(name = "ProvisionedDevices")
    protected String provisionedDevices;
    @XmlElement(name = "ProvisionedDevicesRelated")
    protected String provisionedDevicesRelated;

    /**
     * Gets the value of the productKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductKey() {
        return productKey;
    }

    /**
     * Sets the value of the productKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductKey(String value) {
        this.productKey = value;
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
     * Gets the value of the productStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductStatus() {
        return productStatus;
    }

    /**
     * Sets the value of the productStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductStatus(String value) {
        this.productStatus = value;
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

    public void setProductPrice(List<Long> productPrice) {
		this.productPrice = productPrice;
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
     * Gets the value of the productPrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productPrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getProductPrice() {
        if (productPrice == null) {
            productPrice = new ArrayList<Long>();
        }
        return this.productPrice;
    }

    /**
     * Gets the value of the modelKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelKey() {
        return modelKey;
    }

    /**
     * Sets the value of the modelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelKey(String value) {
        this.modelKey = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the provisionedDevices property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvisionedDevices() {
        return provisionedDevices;
    }

    /**
     * Sets the value of the provisionedDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvisionedDevices(String value) {
        this.provisionedDevices = value;
    }
    
    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductStatusName() {
        return productStatusName;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductStatusName(String value) {
        this.productStatusName = value;
    }
    
    /**
     * Gets the value of the provisionedDevicesRelated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvisionedDevicesRelated() {
        return provisionedDevicesRelated;
    }

    /**
     * Sets the value of the provisionedDevicesRelated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvisionedDevicesRelated(String value) {
        this.provisionedDevicesRelated = value;
    }

}
