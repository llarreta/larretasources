package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Level;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class LevelSaver extends ParametricEntitySaver {
	public LevelSaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return Level.class;
	}

}
