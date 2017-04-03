package ar.com.larreta.persistence.dao.impl;

import org.hibernate.Query;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class Like extends Where {

	private static final String PERCENT = "%";
	public static final String LIKE_TEXT = "LIKE";
	
	public Like(LoadArguments args, String field, Object value) {
		super(args, field, LIKE_TEXT, value);
	}
	
	@Override
	public void setQueryValue(Query query){
		query.setParameter(alias, PERCENT + getValue() + PERCENT);
	}
}
