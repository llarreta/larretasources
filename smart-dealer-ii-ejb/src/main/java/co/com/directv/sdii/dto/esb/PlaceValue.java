
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlaceValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlaceValue">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}EntityValue">
 *       &lt;sequence>
 *         &lt;element name="validFor" type="{http://directvla.com/schema/crm/common/v1-1}TimePeriod" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlaceValue", propOrder = {
    "validFor"
})
@XmlSeeAlso({
    GeographicPlace.class
})
public class PlaceValue
    extends EntityValue
{

    protected TimePeriod validFor;

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
