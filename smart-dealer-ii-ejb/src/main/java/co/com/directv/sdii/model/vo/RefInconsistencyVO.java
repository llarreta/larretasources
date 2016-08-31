/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.RefInconsistency;

/**
 * Objeto que encapsula la información de un RefInconsistency
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RefInconsistency    
 */
public class RefInconsistencyVO extends RefInconsistency implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9106911596908562981L;
	private UserVO inconsistencyCreationUser;
	private UserVO answerInsconsistencyUser;

	public UserVO getAnswerInsconsistencyUser() {
		return answerInsconsistencyUser;
	}

	public void setAnswerInsconsistencyUser(UserVO answerInsconsistencyUser) {
		this.answerInsconsistencyUser = answerInsconsistencyUser;
	}

	public UserVO getInconsistencyCreationUser() {
		return inconsistencyCreationUser;
	}

	public void setInconsistencyCreationUser(UserVO inconsistencyCreationUser) {
		this.inconsistencyCreationUser = inconsistencyCreationUser;
	}

	/**
	 * Metodo: indica si el tipo de inconsistencia es de mas elementos
	 * @return verdadero si el tipo de inconsistencia es de mas elementos
	 * @throws PropertiesException si no se encuentra la configuración de INCONSISTENCY_TYPE_EXCEEDED_QUANTITY
	 * @author wjimenez
	 */
	public boolean isMoreElements() throws PropertiesException {
		return getRefIncType().getRefIncTypeCode().equals(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity());
	}
	
	/**
	 * Metodo: indica si el tipo de inconsistencia es de menos elementos
	 * @return verdadero si el tipo de inconsistencia es de menos elementos
	 * @throws PropertiesException si no se encuentra la configuración de INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY
	 * @author wjimenez
	 */
	public boolean isLessElements() throws PropertiesException {
		return getRefIncType().getRefIncTypeCode().equals(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity());
	}
	
}
