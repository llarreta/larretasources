package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerType;

/**
 * 
 * CustomerCodeType Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerType
 */
public class CustomerTypeVO extends CustomerType implements Serializable {
	
	public CustomerTypeVO(){
		super();
	}

	public CustomerTypeVO(CustomerType copy){
		super(copy);
	}
	
	private static final long serialVersionUID = -7951771730573470945L;

}