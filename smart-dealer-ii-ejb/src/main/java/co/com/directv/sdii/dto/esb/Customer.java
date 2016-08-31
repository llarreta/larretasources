
package co.com.directv.sdii.dto.esb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.com.directv.sdii.dto.esb.event.publishworkorderevent.CustomerAgreementCollection;
/**
 * <p>Java class for Customer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Customer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}PartyRole">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerRank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IndividualRole" type="{http://directvla.com/schema/crm/common/v1-1}Individual" minOccurs="0"/>
 *         &lt;element name="CustomerAccount" type="{http://directvla.com/schema/crm/common/v1-1}CustomerAccount" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", propOrder = {
    "id",
    "customerStatus",
    "customerRank",
    "individualRole",
    "customerAccount",
    "categorizedById",
    "productList",
    "customerAgreement", // este no es el orden de jerarquia en el mensaje encolado
    "product"	/*paquete de programacion del cliente*/
})
public class Customer
    extends PartyRole
{

    @XmlElement(name = "ID")
    protected String id;
    protected String customerStatus;
    protected String customerRank;
    @XmlElement(name = "IndividualRole")
    protected Individual individualRole;
    @XmlElement(name = "CustomerAccount")
    protected List<CustomerAccount> customerAccount;
    protected String categorizedById;
    protected ProductCollection productList;
	protected CustomerAgreementCollection customerAgreement; 
	protected String product;	/*paquete de programacion del cliente*/

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
     * Gets the value of the customerStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerStatus() {
        return customerStatus;
    }

    /**
     * Sets the value of the customerStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerStatus(String value) {
        this.customerStatus = value;
    }

    /**
     * Gets the value of the customerRank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerRank() {
        return customerRank;
    }

    /**
     * Sets the value of the customerRank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerRank(String value) {
        this.customerRank = value;
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
     * Gets the value of the customerAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerAccount }
     * 
     * 
     */
    public List<CustomerAccount> getCustomerAccount() {
        if (customerAccount == null) {
            customerAccount = new ArrayList<CustomerAccount>();
        }
        return this.customerAccount;
    }

    /**
     * Gets the value of the categorizedById property.
     * 
     * @return
     *     possible object is
     *     {@link Individual }
     *     
     */
	public String getCategorizedById() {
		return categorizedById;
	}
    /**
     * Sets the value of the categorizedById property.
     * 
     * @param value
     *     allowed object is
     *     {@link Individual }
     *     
     */
	public void setCategorizedById(String categorizedById) {
		this.categorizedById = categorizedById;
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

	public CustomerAgreementCollection getCustomerAgreement() {
		return customerAgreement;
	}

	public void setCustomerAgreement(CustomerAgreementCollection customerAgreement) {
		this.customerAgreement = customerAgreement;
    }

	public String getProduct(){
		return this.product;
	}
	
    public void setProduct(String product){
    	this.product = product;
    }
}
