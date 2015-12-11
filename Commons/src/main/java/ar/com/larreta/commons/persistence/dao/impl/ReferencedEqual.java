package ar.com.larreta.commons.persistence.dao.impl;

public class ReferencedEqual extends ReferencedWhere {
	
	public ReferencedEqual(LoadArguments args, String field, String referencedValue) {
		super(args, field, Equal.EQUAL_TEXT, referencedValue);
	}
	
}
