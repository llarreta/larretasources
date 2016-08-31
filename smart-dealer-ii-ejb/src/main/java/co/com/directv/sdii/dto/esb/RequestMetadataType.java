
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestMetadataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestMetadataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://directvla.com/schema/crm/common/v1-1}RequestIDType"/>
 *         &lt;element name="sourceID" type="{http://directvla.com/schema/crm/common/v1-1}SourceIDType"/>
 *         &lt;element name="systemID" type="{http://directvla.com/schema/crm/common/v1-1}SystemIDType" minOccurs="0"/>
 *         &lt;element name="appID" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="validate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="debug" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestMetadataType", propOrder = {
    "requestID",
    "sourceID",
    "systemID",
    "appID",
    "validate",
    "debug"
})
public class RequestMetadataType {

    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected String sourceID;
    protected String systemID;
    protected Boolean appID;
    protected Boolean validate;
    protected Boolean debug;

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
     * Gets the value of the sourceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * Sets the value of the sourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceID(String value) {
        this.sourceID = value;
    }

    /**
     * Gets the value of the systemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemID() {
        return systemID;
    }

    /**
     * Sets the value of the systemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemID(String value) {
        this.systemID = value;
    }

    /**
     * Gets the value of the appID property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAppID() {
        return appID;
    }

    /**
     * Sets the value of the appID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAppID(Boolean value) {
        this.appID = value;
    }

    /**
     * Gets the value of the validate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidate() {
        return validate;
    }

    /**
     * Sets the value of the validate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidate(Boolean value) {
        this.validate = value;
    }

    /**
     * Gets the value of the debug property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDebug() {
        return debug;
    }

    /**
     * Sets the value of the debug property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDebug(Boolean value) {
        this.debug = value;
    }

}
