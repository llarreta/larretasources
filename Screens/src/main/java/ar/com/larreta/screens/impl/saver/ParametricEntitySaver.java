package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;

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
		index = screen.addInput(index, APP_DESCRIPTION, DESCRIPTION);
	}
	
	protected void makeColumn(MainScreen screen){
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty(DESCRIPTION, 	APP_DESCRIPTION, 		TABLE_ELEMENT_DESCRIPTION, 	WIDTH));
	}

}
