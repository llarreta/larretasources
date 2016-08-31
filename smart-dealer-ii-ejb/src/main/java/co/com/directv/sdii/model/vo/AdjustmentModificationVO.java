package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.AdjustmentModification;

/**
 * Objeto que encapsula la información de un AdjustmentModification 
 * 
 * Fecha de Creación: 28/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AdjustmentModificationVO extends AdjustmentModification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7907697716351519380L;
	private UserVO modificationUser;

	public UserVO getModificationUser() {
		return modificationUser;
	}

	public void setModificationUser(UserVO modificationUser) {
		this.modificationUser = modificationUser;
	} 

}
