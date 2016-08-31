package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.DocumentType;

/**
 * 
 * DocumentTypes Value Object 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class DocumentTypeVO extends DocumentType
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7855669805538072312L;

	public DocumentTypeVO() {
		super();
	}
	
	public DocumentTypeVO(Long id) {
		super();
		setId(id);
	}
	
}
