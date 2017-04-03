package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class ReferencedEqual extends ReferencedWhere {
	
	public ReferencedEqual(LoadArguments args, String field, String referencedValue) {
		super(args, field, Equal.EQUAL_TEXT, referencedValue);
	}
	
}
