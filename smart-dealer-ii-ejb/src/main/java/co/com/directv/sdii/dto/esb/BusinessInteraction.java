
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BusinessInteraction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessInteraction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interactionDateComplete" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="interactionStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initiatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessInteraction", propOrder = {
    "id",
    "interactionDate",
    "description",
    "interactionDateComplete",
    "interactionStatus",
    "initiatedBy"
})
@XmlSeeAlso({
    Request.class
})
public abstract class BusinessInteraction {

    @XmlElement(name = "ID")
    protected String id;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar interactionDate;
    protected String description;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar interactionDateComplete;
    protected String interactionStatus;
    protected String initiatedBy;

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
     * Gets the value of the interactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInteractionDate() {
        return interactionDate;
    }

    /**
     * Sets the value of the interactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInteractionDate(XMLGregorianCalendar value) {
        this.interactionDate = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the interactionDateComplete property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInteractionDateComplete() {
        return interactionDateComplete;
    }

    /**
     * Sets the value of the interactionDateComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInteractionDateComplete(XMLGregorianCalendar value) {
        this.interactionDateComplete = value;
    }

    /**
     * Gets the value of the interactionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInteractionStatus() {
        return interactionStatus;
    }

    /**
     * Sets the value of the interactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInteractionStatus(String value) {
        this.interactionStatus = value;
    }

    /**
     * Gets the value of the initiatedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitiatedBy() {
        return initiatedBy;
    }

    /**
     * Sets the value of the initiatedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitiatedBy(String value) {
        this.initiatedBy = value;
    }

}
