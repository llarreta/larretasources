package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.UpdateScreen;

public abstract class ParametricEntitySaver extends ABMSaver {
	
	public static final String WIDTH = "40%";
	public static final String TABLE_ELEMENT_DESCRIPTION = "tableElement.description";
	public static final String APP_DESCRIPTION = "app.description";
	public static final String DESCRIPTION = "description";

	public abstract Long getMainId();
	public abstract Long getCreateId();
	public abstract Long getUpdateId();
	public abstract Class getABMClass();
	
	public ParametricEntitySaver() {
		super();

		mainScreen = new MainScreen(getMainId(), getABMClass()) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty(DESCRIPTION, 	APP_DESCRIPTION, 		TABLE_ELEMENT_DESCRIPTION, 	WIDTH));
			}
			
			@Override
			public Long getCreateScreenId() {
				return getCreateId();
			}

			@Override
			public Long getUpdateScreenId() {
				return getUpdateId();
			}
		};
		
		
		createScreen = new CreateScreen(getCreateId(), getABMClass()) {
			
			@Override
			protected void makeBody() {
				ParametricEntitySaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return getMainId();
			}
		};
		
		updateScreen = new UpdateScreen(getUpdateId(), getABMClass()) {
			
			@Override
			protected void makeBody() {
				ParametricEntitySaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return getMainId();
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, APP_DESCRIPTION, DESCRIPTION);
	}

}
