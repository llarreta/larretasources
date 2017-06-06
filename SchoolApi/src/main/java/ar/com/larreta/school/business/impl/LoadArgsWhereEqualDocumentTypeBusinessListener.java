package ar.com.larreta.school.business.impl;

import org.springframework.stereotype.Component;

import ar.com.larreta.rest.business.impl.LoadArgsWhereEqualPropertyBusinessListener;

@Component
public class LoadArgsWhereEqualDocumentTypeBusinessListener extends LoadArgsWhereEqualPropertyBusinessListener {

	@Override
	public String getSourceProperty() {
		return "documentType";
	}

	@Override
	public String getTargetProperty() {
		return "documentType.id";
	}

}
