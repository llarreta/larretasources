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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseMetadata" type="{http://directvla.com/schema/util/commondatatypes/v2-0}ResponseMetadataType"/>
 *         &lt;element name="DispatchWorkOrderResult" type="{http://directvla.com/contract/businessdomain/EventListener/v1-0}DispatchWorkOrderResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "responseMetadata",
    "dispatchWorkOrderResult"
})
@XmlRootElement(name = "DispatchWorkOrderResponse", namespace = "http://directvla.com/contract/businessdomain/EventListener/v1-0")
public class DispatchWorkOrderResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://directvla.com/contract/businessdomain/EventListener/v1-0", required = true)
    protected ResponseMetadataType responseMetadata;
    @XmlElement(name = "DispatchWorkOrderResult", namespace = "http://directvla.com/contract/businessdomain/EventListener/v1-0", required = true)
    protected DispatchWorkOrderResult dispatchWorkOrderResult;

    /**
     * Gets the value of the responseMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMetadataType }
     *     
     */
    public ResponseMetadataType getResponseMetadata() {
        return responseMetadata;
    }

    /**
     * Sets the value of the responseMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMetadataType }
     *     
     */
    public void setResponseMetadata(ResponseMetadataType value) {
        this.responseMetadata = value;
    }

    /**
     * Gets the value of the dispatchWorkOrderResult property.
     * 
     * @return
     *     possible object is
     *     {@link DispatchWorkOrderResult }
     *     
     */
    public DispatchWorkOrderResult getDispatchWorkOrderResult() {
        return dispatchWorkOrderResult;
    }

    /**
     * Sets the value of the dispatchWorkOrderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispatchWorkOrderResult }
     *     
     */
    public void setDispatchWorkOrderResult(DispatchWorkOrderResult value) {
        this.dispatchWorkOrderResult = value;
    }

}