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
 * <p>Java class for WorkOrderResultItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderResultItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkOrder" type="{http://directvla.com/schema/businessdomain/common/v1-0}WorkOrder"/>
 *         &lt;element name="Technician" type="{http://directvla.com/schema/businessdomain/common/v1-0}TelecomTechnician"/>
 *         &lt;element name="SharedApplicationCollection" type="{http://directvla.com/contract/businessdomain/EventListener/v1-0}SharedApplicationCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrderResultItem", namespace = "http://directvla.com/contract/businessdomain/EventListener/v1-0", propOrder = {
    "workOrder",
    "technician",
    "sharedApplicationCollection"
})
public class WorkOrderResultItem
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "WorkOrder", required = true)
    protected WorkOrder workOrder;
    @XmlElement(name = "Technician", required = true)
    protected TelecomTechnician technician;
    @XmlElement(name = "SharedApplicationCollection", required = true)
    protected SharedApplicationCollection sharedApplicationCollection;

    /**
     * Gets the value of the workOrder property.
     * 
     * @return
     *     possible object is
     *     {@link WorkOrder }
     *     
     */
    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    /**
     * Sets the value of the workOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkOrder }
     *     
     */
    public void setWorkOrder(WorkOrder value) {
        this.workOrder = value;
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

    /**
     * Gets the value of the sharedApplicationCollection property.
     * 
     * @return
     *     possible object is
     *     {@link SharedApplicationCollection }
     *     
     */
    public SharedApplicationCollection getSharedApplicationCollection() {
        return sharedApplicationCollection;
    }

    /**
     * Sets the value of the sharedApplicationCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharedApplicationCollection }
     *     
     */
    public void setSharedApplicationCollection(SharedApplicationCollection value) {
        this.sharedApplicationCollection = value;
    }

}
