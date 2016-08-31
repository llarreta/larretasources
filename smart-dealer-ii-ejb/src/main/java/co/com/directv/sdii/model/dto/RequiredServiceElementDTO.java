package co.com.directv.sdii.model.dto;

import co.com.directv.sdii.model.vo.RequiredServiceElementVO;

/**
 * 
 * Data Transfer Object de elementos
 * requeridos por servicio
 * 
 * Fecha de Creación: 13/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class RequiredServiceElementDTO {

	private RequiredServiceElementVO requiredServiceElement;

	public RequiredServiceElementVO getRequiredServiceElement() {
		return requiredServiceElement;
	}

	public void setRequiredServiceElement(
			RequiredServiceElementVO requiredServiceElement) {
		this.requiredServiceElement = requiredServiceElement;
	}
	
	
}
