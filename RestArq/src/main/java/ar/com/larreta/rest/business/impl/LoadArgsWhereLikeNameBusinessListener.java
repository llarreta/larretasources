package ar.com.larreta.rest.business.impl;

import org.springframework.stereotype.Component;

@Component
public class LoadArgsWhereLikeNameBusinessListener extends LoadArgsWhereLikePropertyBusinessListener {
	private static final String PROPERTY = "name";

	@Override
	public String getProperty() {
		return PROPERTY;
	}
}
