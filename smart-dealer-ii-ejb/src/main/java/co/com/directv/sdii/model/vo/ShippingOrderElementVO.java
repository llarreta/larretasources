/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ShippingOrderElement;

/**
 * Objeto que encapsula la información de un ShippingOrderElement
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrderElement    
 */
public class ShippingOrderElementVO extends ShippingOrderElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5528525476981177328L;
	
	private String serial;
	private TechnologyVO technology;

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public TechnologyVO getTechnology() {
		return technology;
	}

	public void setTechnology(TechnologyVO technology) {
		this.technology = technology;
	}
	
}
