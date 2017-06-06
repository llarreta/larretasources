package ar.com.larreta.rest.business.impl;

import org.springframework.stereotype.Component;

@Component
public class LoadArgsWhereLikeDocumentNumberBusinessListener extends LoadArgsWhereLikePropertyBusinessListener {
	private static final String PROPERTY = "documentNumber";

	@Override
	public String getProperty() {
		return PROPERTY;
	}
}
