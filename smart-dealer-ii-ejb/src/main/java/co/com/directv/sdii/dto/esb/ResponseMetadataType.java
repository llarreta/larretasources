
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ResponseMetadataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseMetadataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://directvla.com/schema/crm/common/v1-1}RequestIDType"/>
 *         &lt;element name="timeStamp" type="{http://directvla.com/schema/crm/common/v1-1}TimeStampType"/>
 *         &lt;element name="boxUsage" type="{http://directvla.com/schema/crm/common/v1-1}BoxUsageType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseMetadataType", propOrder = {
    "requestID",
    "timeStamp",
    "boxUsage"
})
public class ResponseMetadataType {

    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected XMLGregorianCalendar timeStamp;
    protected String boxUsage;

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the boxUsage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoxUsage() {
        return boxUsage;
    }

    /**
     * Sets the value of the boxUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoxUsage(String value) {
        this.boxUsage = value;
    }

}
