package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.DocumentType;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class DocumentTypeSaver extends ParametricEntitySaver {

	public DocumentTypeSaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return DocumentType.class;
	}

}
