//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.15 at 10:51:09 AM ART 
//


package co.com.directv.sdii.dto.esb.notifyWorkOrdersOptimus;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkOrderStatusChange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkOrderStatusChange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestMetadata" type="{http://directvla.com/schema/util/commondatatypes/v2-0}RequestMetadataType"/>
 *         &lt;element name="CustWorkOrd" type="{http://directvla.com/schema/businessdomain/common/v1-0}WorkOrder"/>
 *         &lt;element name="Status" type="{http://directvla.com/schema/businessdomain/common/v1-0}Category" minOccurs="0"/>
 *         &lt;element name="Reason" type="{http://directvla.com/schema/businessdomain/common/v1-0}Category" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrderStatusChange", namespace = "http://xmlns.oracle.com/bpel", propOrder = {
    "requestMetadata",
    "custWorkOrd",
    "status",
    "reason"
})
public class WorkOrderStatusChange
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "RequestMetadata", required = true)
    protected RequestMetadataType requestMetadata;
    @XmlElement(name = "CustWorkOrd", required = true)
    protected WorkOrder custWorkOrd;
    @XmlElement(name = "Status")
    protected Category status;
    @XmlElement(name = "Reason")
    protected Category reason;

    /**
     * Gets the value of the requestMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetadataType }
     *     
     */
    public RequestMetadataType getRequestMetadata() {
        return requestMetadata;
    }

    /**
     * Sets the value of the requestMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetadataType }
     *     
     */
    public void setRequestMetadata(RequestMetadataType value) {
        this.requestMetadata = value;
    }

    /**
     * Gets the value of the custWorkOrd property.
     * 
     * @return
     *     possible object is
     *     {@link WorkOrder }
     *     
     */
    public WorkOrder getCustWorkOrd() {
        return custWorkOrd;
    }

    /**
     * Sets the value of the custWorkOrd property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkOrder }
     *     
     */
    public void setCustWorkOrd(WorkOrder value) {
        this.custWorkOrd = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setStatus(Category value) {
        this.status = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setReason(Category value) {
        this.reason = value;
    }

}
