
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Party complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Party">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="partyId" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://directvla.com/schema/crm/common/v1-1}TimePeriod" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party", propOrder = {
    "partyId",
    "validFor"
})
@XmlSeeAlso({
    Individual.class
})
public abstract class Party {

    @XmlSchemaType(name = "anySimpleType")
    protected Object partyId;
    protected TimePeriod validFor;

    /**
     * Gets the value of the partyId property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPartyId() {
        return partyId;
    }

    /**
     * Sets the value of the partyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPartyId(Object value) {
        this.partyId = value;
    }

    /**
     * Gets the value of the validFor property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriod }
     *     
     */
    public TimePeriod getValidFor() {
        return validFor;
    }

    /**
     * Sets the value of the validFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriod }
     *     
     */
    public void setValidFor(TimePeriod value) {
        this.validFor = value;
    }

}
