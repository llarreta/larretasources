//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.12 at 05:28:12 PM ART 
//


package co.com.directv.sdii.dto.esb.event.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductCharacteristicValueCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductCharacteristicValueCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductCharValue" type="{http://directvla.com/schema/businessdomain/product/v1-0}ProductCharacteristicValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductCharacteristicValueCollection", namespace = "http://directvla.com/schema/businessdomain/product/v1-0", propOrder = {
    "productCharValue"
})
public class ProductCharacteristicValueCollection {

    @XmlElement(name = "ProductCharValue")
    protected List<ProductCharacteristicValue> productCharValue;

    /**
     * Gets the value of the productCharValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productCharValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductCharValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductCharacteristicValue }
     * 
     * 
     */
    public List<ProductCharacteristicValue> getProductCharValue() {
        if (productCharValue == null) {
            productCharValue = new ArrayList<ProductCharacteristicValue>();
        }
        return this.productCharValue;
    }

}
