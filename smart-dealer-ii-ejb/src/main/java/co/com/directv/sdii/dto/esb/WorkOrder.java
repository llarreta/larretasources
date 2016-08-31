
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import co.com.directv.sdii.dto.esb.event.publishworkorderevent.Category;


/**
 * <p>Java class for WorkOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrder">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}Request">
 *       &lt;sequence>
 *         &lt;element name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="possibleDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ServiceList" type="{http://directvla.com/schema/crm/common/v1-1}ServiceCollection" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://directvla.com/schema/crm/common/v1-1}Customer" minOccurs="0"/>
 *         &lt;element name="dealerId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="dealerISalesd" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="facilityCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="woTypeByPreviusWoTypeId" type="{http://directvla.com/schema/crm/common/v1-1}woType" minOccurs="0"/>
 *         &lt;element name="woTypeByWoTypeId" type="{http://directvla.com/schema/crm/common/v1-1}woType" minOccurs="0"/>
 *         &lt;element name="historyCodeEventId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="isAppointment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GeographicArea" type="{http://directvla.com/schema/crm/common/v1-1}GeographicArea" minOccurs="0"/>
 *         &lt;element name="workingTime" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="workorderStatusByActualStatusId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="workorderStatusByPreviusStatusId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShippingOrder" type="{http://directvla.com/schema/crm/common/v1-1}ShippingOrder" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrder", propOrder = {
    "createDate",
	"requestedDeliveryDate",
    "possibleDeliveryDate",
    "serviceList",
    "customer",
    "dealerId",
    "dealerISalesd",
    "facilityCode",
    "action",
    "code",
    "woTypeByPreviusWoTypeId",
    "woTypeByWoTypeId",
    "historyCodeEventId",
    "isAppointment",
    "geographicArea",
    "workingTime",
    "workorderStatusByActualStatusId",
    "workorderStatusByPreviusStatusId",
    "shippingOrder",
    "addressId",
    "ibsTechnical",
    "MDUCustomer"
})
@XmlRootElement
public class WorkOrder extends Request {

	@XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedDeliveryDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar possibleDeliveryDate;
    @XmlElement(name = "ServiceList")
    protected ServiceCollection serviceList;
    protected Customer customer;
    protected Long dealerId;
    protected Long dealerISalesd;
    protected Long facilityCode;
    protected String action;
    protected String code;
    protected WoType woTypeByPreviusWoTypeId;
    protected WoType woTypeByWoTypeId;
    protected Long historyCodeEventId;
    protected String isAppointment;
    @XmlElement(name = "GeographicArea")
    protected GeographicArea geographicArea;
    protected Double workingTime;
    protected String workorderStatusByActualStatusId;
    protected String workorderStatusByPreviusStatusId;
    @XmlElement(name = "ShippingOrder")
    protected ShippingOrder shippingOrder;
    protected String addressId;
    protected String ibsTechnical;
    protected Category reason;
    protected Customer MDUCustomer;
    
    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }
    
    /**
     * Gets the value of the requestedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Sets the value of the requestedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedDeliveryDate(XMLGregorianCalendar value) {
        this.requestedDeliveryDate = value;
    }

    /**
     * Gets the value of the possibleDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPossibleDeliveryDate() {
        return possibleDeliveryDate;
    }

    /**
     * Sets the value of the possibleDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPossibleDeliveryDate(XMLGregorianCalendar value) {
        this.possibleDeliveryDate = value;
    }

    /**
     * Gets the value of the serviceList property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCollection }
     *     
     */
    public ServiceCollection getServiceList() {
        return serviceList;
    }

    /**
     * Sets the value of the serviceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCollection }
     *     
     */
    public void setServiceList(ServiceCollection value) {
        this.serviceList = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomer(Customer value) {
        this.customer = value;
    }

    /**
     * Gets the value of the dealerId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDealerId() {
        return dealerId;
    }

    /**
     * Sets the value of the dealerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDealerId(Long value) {
        this.dealerId = value;
    }

    /**
     * Gets the value of the dealerISalesd property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDealerISalesd() {
        return dealerISalesd;
    }

    /**
     * Sets the value of the dealerISalesd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDealerISalesd(Long value) {
        this.dealerISalesd = value;
    }

    /**
     * Gets the value of the facilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFacilityCode() {
        return facilityCode;
    }

    /**
     * Sets the value of the facilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFacilityCode(Long value) {
        this.facilityCode = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
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
     * Gets the value of the woTypeByPreviusWoTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link WoType }
     *     
     */
    public WoType getWoTypeByPreviusWoTypeId() {
        return woTypeByPreviusWoTypeId;
    }

    /**
     * Sets the value of the woTypeByPreviusWoTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link WoType }
     *     
     */
    public void setWoTypeByPreviusWoTypeId(WoType value) {
        this.woTypeByPreviusWoTypeId = value;
    }

    /**
     * Gets the value of the woTypeByWoTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link WoType }
     *     
     */
    public WoType getWoTypeByWoTypeId() {
        return woTypeByWoTypeId;
    }

    /**
     * Sets the value of the woTypeByWoTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link WoType }
     *     
     */
    public void setWoTypeByWoTypeId(WoType value) {
        this.woTypeByWoTypeId = value;
    }

    /**
     * Gets the value of the historyCodeEventId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHistoryCodeEventId() {
        return historyCodeEventId;
    }

    /**
     * Sets the value of the historyCodeEventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHistoryCodeEventId(Long value) {
        this.historyCodeEventId = value;
    }

    /**
     * Gets the value of the isAppointment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAppointment() {
        return isAppointment;
    }

    /**
     * Sets the value of the isAppointment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAppointment(String value) {
        this.isAppointment = value;
    }

    /**
     * Gets the value of the geographicArea property.
     * 
     * @return
     *     possible object is
     *     {@link GeographicArea }
     *     
     */
    public GeographicArea getGeographicArea() {
        return geographicArea;
    }

    /**
     * Sets the value of the geographicArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicArea }
     *     
     */
    public void setGeographicArea(GeographicArea value) {
        this.geographicArea = value;
    }

    /**
     * Gets the value of the workingTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWorkingTime() {
        return workingTime;
    }

    /**
     * Sets the value of the workingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWorkingTime(Double value) {
        this.workingTime = value;
    }

    /**
     * Gets the value of the workorderStatusByActualStatusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkorderStatusByActualStatusId() {
        return workorderStatusByActualStatusId;
    }

    /**
     * Sets the value of the workorderStatusByActualStatusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkorderStatusByActualStatusId(String value) {
        this.workorderStatusByActualStatusId = value;
    }

    /**
     * Gets the value of the workorderStatusByPreviusStatusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkorderStatusByPreviusStatusId() {
        return workorderStatusByPreviusStatusId;
    }

    /**
     * Sets the value of the workorderStatusByPreviusStatusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkorderStatusByPreviusStatusId(String value) {
        this.workorderStatusByPreviusStatusId = value;
    }

    /**
     * Gets the value of the shippingOrder property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingOrder }
     *     
     */
    public ShippingOrder getShippingOrder() {
        return shippingOrder;
    }

    /**
     * Sets the value of the shippingOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingOrder }
     *     
     */
    public void setShippingOrder(ShippingOrder value) {
        this.shippingOrder = value;
    }

    /**
     * Gets the value of the addressId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * Sets the value of the addressId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressId(String value) {
        this.addressId = value;
    }
    
    
    /**
     * Gets the value of the ibsTechnical property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbsTechnical() {
        return ibsTechnical;
    }
    
    

    public Category getReason() {
		return reason;
	}

	public void setReason(Category reason) {
		this.reason = reason;
	}

	/**
     * Sets the value of the ibsTechnical property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbsTechnical(String value) {
        this.ibsTechnical = value;
    }
    
    /**
     * Gets the value of the MDUcustomer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getMDUCustomer() {
        return MDUCustomer;
    }

    /**
     * Sets the value of the MDUCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setMDUCustomer(Customer value) {
        this.MDUCustomer = value;
    }
}
