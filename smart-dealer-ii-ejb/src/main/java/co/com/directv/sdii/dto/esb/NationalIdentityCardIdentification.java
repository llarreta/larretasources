
package co.com.directv.sdii.dto.esb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NationalIdentityCardIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NationalIdentityCardIdentification">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}IndividualIdentification">
 *       &lt;sequence>
 *         &lt;element name="cardNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NationalIdentityCardIdentification", propOrder = {
    "cardNr"
})
public class NationalIdentityCardIdentification
    extends IndividualIdentification
{

    protected String cardNr;

    /**
     * Gets the value of the cardNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNr() {
        return cardNr;
    }

    /**
     * Sets the value of the cardNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNr(String value) {
        this.cardNr = value;
    }

}
