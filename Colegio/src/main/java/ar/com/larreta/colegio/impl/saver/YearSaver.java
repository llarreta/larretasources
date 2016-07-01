package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Year;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class YearSaver extends ParametricEntitySaver {

	public YearSaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return Year.class;
	}

}
