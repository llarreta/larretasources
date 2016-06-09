package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class CountrySaver extends ABMSaver {

	private Class abmClass = Country.class;
	
	public CountrySaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.COUNTRY_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("abbreviation", 	"app.abbreviation", 	"tableElement.abbreviation",  	"40%"));
				table.addColumn(1, getColumnWithLabelProperty("description", 	"app.description", 		"tableElement.description", 	"40%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.COUNTRY_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.COUNTRY_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.COUNTRY_CREATE, abmClass) {
			
			@Override
			protected void makeBody() {
				CountrySaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.COUNTRY_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.COUNTRY_UPDATE, abmClass) {
			
			@Override
			protected void makeBody() {
				CountrySaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.COUNTRY_MAIN;
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.abbreviation", "abbreviation");
		index = screen.addInput(index, "app.description", "description");
	}

}
