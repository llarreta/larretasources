package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.CustomerClass;

/**
 * 
 * CustomerClass Value Object
 * 
 * Fecha de Creación: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerClass
 */
public class CustomerClassVO extends CustomerClass implements Serializable {

	public CustomerClassVO() {
		super();
	}

	public CustomerClassVO(CustomerClass copy) {
		super(copy);
	}

	private static final long serialVersionUID = 9039475929026745452L;

}
