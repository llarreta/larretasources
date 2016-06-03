package ar.com.larreta.commons.persistence.dao.impl;

import org.hibernate.Query;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class ReferencedWhere extends Where {

	public ReferencedWhere(LoadArguments args, String field, String operator, String referencedValue) {
		super(args, field, operator, referencedValue);
	}

	/**
	 * Como el valor es referenciado no se asigna nada a la query
	 */
	@Override
	public void setQueryValue(Query query){
		// do nothing
	}
	
	/**
	 * Se retorna el nombre del campo referenciado
	 */
	public String getPostOperatorSection(){
		return getSymbolicName((String) getValue(), Boolean.FALSE);
	}
}
