
package co.com.directv.sdii.dto.esb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UrbanPropertyAddressCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UrbanPropertyAddressCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UrbanPropertyAddress" type="{http://directvla.com/schema/crm/common/v1-1}UrbanPropertyAddress" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UrbanPropertyAddressCollection", propOrder = {
    "urbanPropertyAddress"
})
public class UrbanPropertyAddressCollection {

    @XmlElement(name = "UrbanPropertyAddress")
    protected List<UrbanPropertyAddress> urbanPropertyAddress;

    /**
     * Gets the value of the urbanPropertyAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the urbanPropertyAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUrbanPropertyAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UrbanPropertyAddress }
     * 
     * 
     */
    public List<UrbanPropertyAddress> getUrbanPropertyAddress() {
        if (urbanPropertyAddress == null) {
            urbanPropertyAddress = new ArrayList<UrbanPropertyAddress>();
        }
        return this.urbanPropertyAddress;
    }

}
