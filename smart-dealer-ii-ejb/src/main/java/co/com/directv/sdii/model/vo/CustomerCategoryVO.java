package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.CustomerCategory;

/**
 * 
 * CustomerCategory Value Object
 * 
 * Fecha de Creaci�n: Oct 23, 2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerCategory
 */
public class CustomerCategoryVO extends CustomerCategory implements Serializable {

	public CustomerCategoryVO() {
		super();
	}

	public CustomerCategoryVO(CustomerCategory copy) {
		super(copy);
	}
	
}
