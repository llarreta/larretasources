
package co.com.directv.sdii.dto.esb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactMediumCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactMediumCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContactMedium" type="{http://directvla.com/schema/crm/common/v1-1}ContactMedium" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactMediumCollection", propOrder = {
    "contactMedium"
})
public class ContactMediumCollection {

    @XmlElement(name = "ContactMedium")
    protected List<ContactMedium> contactMedium;

    /**
     * Gets the value of the contactMedium property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactMedium property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactMedium().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactMedium }
     * 
     * 
     */
    public List<ContactMedium> getContactMedium() {
        if (contactMedium == null) {
            contactMedium = new ArrayList<ContactMedium>();
        }
        return this.contactMedium;
    }

}
