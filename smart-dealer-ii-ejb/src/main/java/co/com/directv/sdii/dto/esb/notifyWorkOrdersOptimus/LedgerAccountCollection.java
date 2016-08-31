//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.15 at 10:51:09 AM ART 
//


package co.com.directv.sdii.dto.esb.notifyWorkOrdersOptimus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LedgerAccountCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LedgerAccountCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LedgerAccount" type="{http://directvla.com/schema/businessdomain/customer/v1-0}LedgerAccount" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LedgerAccountCollection", namespace = "http://directvla.com/schema/businessdomain/customer/v1-0", propOrder = {
    "ledgerAccount"
})
public class LedgerAccountCollection
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "LedgerAccount")
    protected List<LedgerAccount> ledgerAccount;

    /**
     * Gets the value of the ledgerAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ledgerAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLedgerAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LedgerAccount }
     * 
     * 
     */
    public List<LedgerAccount> getLedgerAccount() {
        if (ledgerAccount == null) {
            ledgerAccount = new ArrayList<LedgerAccount>();
        }
        return this.ledgerAccount;
    }

}
