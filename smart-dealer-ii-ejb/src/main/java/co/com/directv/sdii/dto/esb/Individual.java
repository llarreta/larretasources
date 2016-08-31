
package co.com.directv.sdii.dto.esb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Individual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Individual">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/crm/common/v1-1}Party">
 *       &lt;sequence>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="maritalStatus" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="skills" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="disabilities" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="aliveDuring" type="{http://directvla.com/schema/crm/common/v1-1}TimePeriod" minOccurs="0"/>
 *         &lt;element name="IndividualName" type="{http://directvla.com/schema/crm/common/v1-1}IndividualName" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IndividualIdentification" type="{http://directvla.com/schema/crm/common/v1-1}IndividualIdentification" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Individual", propOrder = {
    "gender",
    "placeOfBirth",
    "nationality",
    "maritalStatus",
    "skills",
    "disabilities",
    "aliveDuring",
    "individualName",
    "individualIdentification"
})
public class Individual
    extends Party
{

    @XmlSchemaType(name = "anySimpleType")
    protected Object gender;
    @XmlSchemaType(name = "anySimpleType")
    protected Object placeOfBirth;
    @XmlSchemaType(name = "anySimpleType")
    protected Object nationality;
    @XmlSchemaType(name = "anySimpleType")
    protected Object maritalStatus;
    @XmlSchemaType(name = "anySimpleType")
    protected Object skills;
    @XmlSchemaType(name = "anySimpleType")
    protected Object disabilities;
    protected TimePeriod aliveDuring;
    @XmlElement(name = "IndividualName")
    protected List<IndividualName> individualName;
    @XmlElement(name = "IndividualIdentification")
    protected List<IndividualIdentification> individualIdentification;

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGender(Object value) {
        this.gender = value;
    }

    /**
     * Gets the value of the placeOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * Sets the value of the placeOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPlaceOfBirth(Object value) {
        this.placeOfBirth = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNationality(Object value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setMaritalStatus(Object value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the skills property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSkills() {
        return skills;
    }

    /**
     * Sets the value of the skills property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSkills(Object value) {
        this.skills = value;
    }

    /**
     * Gets the value of the disabilities property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDisabilities() {
        return disabilities;
    }

    /**
     * Sets the value of the disabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDisabilities(Object value) {
        this.disabilities = value;
    }

    /**
     * Gets the value of the aliveDuring property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriod }
     *     
     */
    public TimePeriod getAliveDuring() {
        return aliveDuring;
    }

    /**
     * Sets the value of the aliveDuring property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriod }
     *     
     */
    public void setAliveDuring(TimePeriod value) {
        this.aliveDuring = value;
    }

    /**
     * Gets the value of the individualName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the individualName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndividualName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndividualName }
     * 
     * 
     */
    public List<IndividualName> getIndividualName() {
        if (individualName == null) {
            individualName = new ArrayList<IndividualName>();
        }
        return this.individualName;
    }

    /**
     * Gets the value of the individualIdentification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the individualIdentification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndividualIdentification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndividualIdentification }
     * 
     * 
     */
    public List<IndividualIdentification> getIndividualIdentification() {
        if (individualIdentification == null) {
            individualIdentification = new ArrayList<IndividualIdentification>();
        }
        return this.individualIdentification;
    }

}
