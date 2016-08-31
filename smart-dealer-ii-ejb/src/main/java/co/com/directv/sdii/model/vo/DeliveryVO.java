/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Delivery;

/**
 * Objeto que encapsula la información de un Delivery
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Delivery    
 */
public class DeliveryVO extends Delivery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5382434041406840581L;
	private UserVO registerUSer;

	public UserVO getRegisterUSer() {
		return registerUSer;
	}

	public void setRegisterUSer(UserVO registerUSer) {
		this.registerUSer = registerUSer;
	}
	
}
