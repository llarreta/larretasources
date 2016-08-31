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
 * <p>Java class for RequestMetadataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestMetadataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="systemID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sourceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="debug" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="userProof" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userApp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="boxUsage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="executionToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditData" type="{http://directvla.com/schema/util/commondatatypes/v2-0}AuditDataType" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://directvla.com/schema/util/commondatatypes/v2-0}SenderType"/>
 *         &lt;element name="target" type="{http://directvla.com/schema/util/commondatatypes/v2-0}TargetType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestMetadataType", namespace = "http://directvla.com/schema/util/commondatatypes/v2-0", propOrder = {
    "requestID",
    "systemID",
    "sourceID",
    "userID",
    "validate",
    "debug",
    "userProof",
    "userApp",
    "boxUsage",
    "executionToken",
    "auditData",
    "sender",
    "target"
})
public class RequestMetadataType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected String systemID;
    protected String sourceID;
    protected String userID;
    protected Boolean validate;
    protected Boolean debug;
    protected String userProof;
    protected String userApp;
    protected String boxUsage;
    protected String executionToken;
    protected AuditDataType auditData;
    @XmlElement(required = true)
    protected SenderType sender;
    protected TargetType target;

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
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
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

    /**
     * Gets the value of the userProof property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserProof() {
        return userProof;
    }

    /**
     * Sets the value of the userProof property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserProof(String value) {
        this.userProof = value;
    }

    /**
     * Gets the value of the userApp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserApp() {
        return userApp;
    }

    /**
     * Sets the value of the userApp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserApp(String value) {
        this.userApp = value;
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

    /**
     * Gets the value of the executionToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecutionToken() {
        return executionToken;
    }

    /**
     * Sets the value of the executionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecutionToken(String value) {
        this.executionToken = value;
    }

    /**
     * Gets the value of the auditData property.
     * 
     * @return
     *     possible object is
     *     {@link AuditDataType }
     *     
     */
    public AuditDataType getAuditData() {
        return auditData;
    }

    /**
     * Sets the value of the auditData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditDataType }
     *     
     */
    public void setAuditData(AuditDataType value) {
        this.auditData = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link SenderType }
     *     
     */
    public SenderType getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderType }
     *     
     */
    public void setSender(SenderType value) {
        this.sender = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link TargetType }
     *     
     */
    public TargetType getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetType }
     *     
     */
    public void setTarget(TargetType value) {
        this.target = value;
    }

}
