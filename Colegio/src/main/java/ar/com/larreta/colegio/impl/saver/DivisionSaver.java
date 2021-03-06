package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Division;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class DivisionSaver extends ParametricEntitySaver {

	public DivisionSaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return Division.class;
	}

}
