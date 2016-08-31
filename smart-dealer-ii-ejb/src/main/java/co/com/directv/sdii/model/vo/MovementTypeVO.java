/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.MovementType;

/**
 * Objeto que encapsula la información de un MovementType
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MovementType    
 */
public class MovementTypeVO extends MovementType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7160901041551704785L;
	
	private String movTypeToPrint;

	public String getMovTypeToPrint() {
		return movTypeToPrint;
	}

	public void setMovTypeToPrint(String movTypeToPrint) {
		this.movTypeToPrint = movTypeToPrint;
	}
	
	
	

}
