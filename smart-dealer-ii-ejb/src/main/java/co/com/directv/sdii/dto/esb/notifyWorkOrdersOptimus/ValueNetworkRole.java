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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             This is an abstract class with no attributes. Used to group roles that pertain to the eTOM value network [eTOM].
 *          
 * 
 * <p>Java class for ValueNetworkRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueNetworkRole">
 *   &lt;complexContent>
 *     &lt;extension base="{http://directvla.com/schema/businessdomain/common/v1-0}PartyRole">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueNetworkRole")
@XmlSeeAlso({
    Technician.class
})
public abstract class ValueNetworkRole
    extends PartyRole
    implements Serializable
{

    private final static long serialVersionUID = 1L;

}
