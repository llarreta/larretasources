
package co.com.directv.sdii.dto.esb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShippingOrder">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}Request">
 *       &lt;sequence>
 *         &lt;element name="shippingOrderKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="installationPrice" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="activationPrice" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ProductList" type="{http://directvla.com/schema/crm/common/v1-1}ProductCollection" minOccurs="0"/>
 *         &lt;element name="SoldBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShippingMethodKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contractNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShippingOrder", propOrder = {
    "shippingOrderKey",
    "name",
    "code",
    "installationPrice",
    "activationPrice",
    "productList",
    "soldBy",
    "shippingMethodKey",
    "contractNumber",
    "contractCode"
})
public class ShippingOrder
    extends Request
{

    @XmlElement(required = true)
    protected String shippingOrderKey;
    protected String name;
    protected String code;
    @XmlElement(type = Long.class)
    protected List<Long> installationPrice;
    @XmlElement(type = Long.class)
    protected List<Long> activationPrice;
    @XmlElement(name = "ProductList")
    protected ProductCollection productList;
    @XmlElement(name = "SoldBy")
    protected String soldBy;
    @XmlElement(name = "ShippingMethodKey", required = true)
    protected String shippingMethodKey;
    @XmlElement(required = true)
    protected String contractNumber;
    @XmlElement(required = true)
    protected String contractCode;

    public void setInstallationPrice(List<Long> installationPrice) {
		this.installationPrice = installationPrice;
	}

	public void setActivationPrice(List<Long> activationPrice) {
		this.activationPrice = activationPrice;
	}

	/**
     * Gets the value of the shippingOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingOrderKey() {
        return shippingOrderKey;
    }

    /**
     * Sets the value of the shippingOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingOrderKey(String value) {
        this.shippingOrderKey = value;
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
     * Gets the value of the installationPrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installationPrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstallationPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getInstallationPrice() {
        if (installationPrice == null) {
            installationPrice = new ArrayList<Long>();
        }
        return this.installationPrice;
    }

    /**
     * Gets the value of the activationPrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activationPrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivationPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getActivationPrice() {
        if (activationPrice == null) {
            activationPrice = new ArrayList<Long>();
        }
        return this.activationPrice;
    }

    /**
     * Gets the value of the productList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCollection }
     *     
     */
    public ProductCollection getProductList() {
        return productList;
    }

    /**
     * Sets the value of the productList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCollection }
     *     
     */
    public void setProductList(ProductCollection value) {
        this.productList = value;
    }

    /**
     * Gets the value of the soldBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoldBy() {
        return soldBy;
    }

    /**
     * Sets the value of the soldBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoldBy(String value) {
        this.soldBy = value;
    }

    /**
     * Gets the value of the shippingMethodKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingMethodKey() {
        return shippingMethodKey;
    }

    /**
     * Sets the value of the shippingMethodKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingMethodKey(String value) {
        this.shippingMethodKey = value;
    }

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the contractCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * Sets the value of the contractCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractCode(String value) {
        this.contractCode = value;
    }

}
