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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for WorkOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrder">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/businessdomain/common/v1-0}Request">
 *       &lt;sequence>
 *         &lt;element name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="possibleDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Stockhandler" type="{http://directvla.com/schema/businessdomain/common/v1-0}FunctionOrProcessProvider" minOccurs="0"/>
 *         &lt;element name="InvoiceNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="realDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="WorkOrderItemList" type="{http://directvla.com/schema/businessdomain/common/v1-0}WorkOrderItemCollection" minOccurs="0"/>
 *         &lt;element name="ServiceProvider" type="{http://directvla.com/schema/businessdomain/common/v1-0}ServiceProvider" minOccurs="0"/>
 *         &lt;element name="addressId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isAppointment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="workingTime" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ShippingOrder" type="{http://directvla.com/schema/businessdomain/common/v1-0}ShippingOrder" minOccurs="0"/>
 *         &lt;element name="Technician" type="{http://directvla.com/schema/businessdomain/common/v1-0}TelecomTechnician" minOccurs="0"/>
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
    "requestedDeliveryDate",
    "possibleDeliveryDate",
    "stockhandler",
    "invoiceNr",
    "realDate",
    "workOrderItemList",
    "serviceProvider",
    "addressId",
    "isAppointment",
    "workingTime",
    "shippingOrder",
    "technician"
})
public class WorkOrder
    extends Request
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedDeliveryDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar possibleDeliveryDate;
    @XmlElement(name = "Stockhandler")
    protected FunctionOrProcessProvider stockhandler;
    @XmlElement(name = "InvoiceNr")
    protected String invoiceNr;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar realDate;
    @XmlElement(name = "WorkOrderItemList")
    protected WorkOrderItemCollection workOrderItemList;
    @XmlElement(name = "ServiceProvider")
    protected ServiceProvider serviceProvider;
    protected String addressId;
    protected String isAppointment;
    protected Double workingTime;
    @XmlElement(name = "ShippingOrder")
    protected ShippingOrder shippingOrder;
    @XmlElement(name = "Technician")
    protected TelecomTechnician technician;

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
     * Gets the value of the stockhandler property.
     * 
     * @return
     *     possible object is
     *     {@link FunctionOrProcessProvider }
     *     
     */
    public FunctionOrProcessProvider getStockhandler() {
        return stockhandler;
    }

    /**
     * Sets the value of the stockhandler property.
     * 
     * @param value
     *     allowed object is
     *     {@link FunctionOrProcessProvider }
     *     
     */
    public void setStockhandler(FunctionOrProcessProvider value) {
        this.stockhandler = value;
    }

    /**
     * Gets the value of the invoiceNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNr() {
        return invoiceNr;
    }

    /**
     * Sets the value of the invoiceNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNr(String value) {
        this.invoiceNr = value;
    }

    /**
     * Gets the value of the realDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRealDate() {
        return realDate;
    }

    /**
     * Sets the value of the realDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRealDate(XMLGregorianCalendar value) {
        this.realDate = value;
    }

    /**
     * Gets the value of the workOrderItemList property.
     * 
     * @return
     *     possible object is
     *     {@link WorkOrderItemCollection }
     *     
     */
    public WorkOrderItemCollection getWorkOrderItemList() {
        return workOrderItemList;
    }

    /**
     * Sets the value of the workOrderItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkOrderItemCollection }
     *     
     */
    public void setWorkOrderItemList(WorkOrderItemCollection value) {
        this.workOrderItemList = value;
    }

    /**
     * Gets the value of the serviceProvider property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceProvider }
     *     
     */
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    /**
     * Sets the value of the serviceProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceProvider }
     *     
     */
    public void setServiceProvider(ServiceProvider value) {
        this.serviceProvider = value;
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
     * Gets the value of the technician property.
     * 
     * @return
     *     possible object is
     *     {@link TelecomTechnician }
     *     
     */
    public TelecomTechnician getTechnician() {
        return technician;
    }

    /**
     * Sets the value of the technician property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecomTechnician }
     *     
     */
    public void setTechnician(TelecomTechnician value) {
        this.technician = value;
    }

}
