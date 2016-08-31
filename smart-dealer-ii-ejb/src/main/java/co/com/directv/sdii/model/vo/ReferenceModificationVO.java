/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ReferenceModification;

/**
 * Objeto que encapsula la información de un ReferenceModification
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReferenceModification    
 */
public class ReferenceModificationVO extends ReferenceModification implements Serializable {
	
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
