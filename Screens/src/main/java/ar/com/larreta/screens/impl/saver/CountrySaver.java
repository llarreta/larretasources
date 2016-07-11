package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;

@Component
public class CountrySaver extends ParametricEntitySaver {

	public CountrySaver() {
		super();
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		super.makeColumn(screen);
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("abbreviation", 	"app.abbreviation", 	"tableElement.abbreviation",  	"40%"));
	}
	
	protected void makeBody(CreateScreen screen) {
		super.makeBody(screen);
		Integer index = -3;
		index = screen.addInput(index, "app.abbreviation", "abbreviation");
	}

	@Override
	public Class getABMClass() {
		return Country.class;
	}

}