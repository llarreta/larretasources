/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.TransferReason;

/**
 * Objeto que encapsula la información de un TransferReason
 * 
 * Fecha de Creación: 12/07/2010
 * @author mrugeles <a href="mailto:mrugeles@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.TransferReason    
 */
public class TransferReasonVO extends TransferReason implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3967156550817201060L;
	
	private boolean isMovementType;

	public boolean isMovementType() {
		return isMovementType;
	}

	public void setMovementType(boolean isMovementType) {
		this.isMovementType = isMovementType;
	}
	

}
