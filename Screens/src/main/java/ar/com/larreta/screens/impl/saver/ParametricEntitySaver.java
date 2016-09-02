package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.validators.Required;
import ar.com.larreta.screens.validators.Validator;

public abstract class ParametricEntitySaver extends ABMSaver {
	
	public static final String WIDTH = "40%";
	public static final String TABLE_ELEMENT_DESCRIPTION = "tableElement.description";
	public static final String APP_DESCRIPTION = "app.description";
	public static final String DESCRIPTION = "description";
	
	public ParametricEntitySaver() {
		super();
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, APP_DESCRIPTION, DESCRIPTION, Validator.REQUIRED);
	}
	
	protected void makeColumn(MainScreen screen){
		screen.getTable().addColumn(0, screen.getColumnWithContainsFilter(DESCRIPTION, 	APP_DESCRIPTION, 		TABLE_ELEMENT_DESCRIPTION, 	WIDTH));
	}

}
