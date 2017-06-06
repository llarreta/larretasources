package ar.com.larreta.rest.business.impl;

import org.springframework.stereotype.Component;

@Component
public class LoadArgsWhereLikeSurnameBusinessListener extends LoadArgsWhereLikePropertyBusinessListener {
	private static final String PROPERTY = "surname";

	@Override
	public String getProperty() {
		return PROPERTY;
	}
}
