package ar.com.larreta.rest.business.impl;

import org.springframework.stereotype.Component;

@Component
public class LoadArgsWhereEqualDescriptionBusinessListener extends LoadArgsWhereEqualPropertyBusinessListener {

	private static final String DESCRIPTION = "description";

	@Override
	public String getSourceProperty() {
		return DESCRIPTION;
	}

	@Override
	public String getTargetProperty() {
		return DESCRIPTION;
	}

}
