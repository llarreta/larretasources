package ar.com.larreta.commons.persistence.dao.impl;

import org.apache.commons.lang3.StringUtils;

public class NotExists extends Subquery {
	private static final String NOT_EXISTS = "NOT EXISTS";

	public NotExists(LoadArguments args, LoadArguments argsSub) {
		super(args, StringUtils.EMPTY, NOT_EXISTS, argsSub);
		mainReference = Boolean.FALSE;
	}
}
