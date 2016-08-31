/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.AdjustmentElements;

/**
 * Objeto que encapsula la información de un AdjustmentElements
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AdjustmentElements    
 */
public class AdjustmentElementsVO extends AdjustmentElements implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5299107870370343462L;
	
	private Double quantity;

	private String rid;
	
	public String getRid(){
		return rid;
	}

	public void setRid(String ridParam){
		rid=ridParam;
	}
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
}
