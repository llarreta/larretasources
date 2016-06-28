package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Language;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

@Component
public class LanguageSaver extends ABMSaver {
	private Class abmClass = Language.class;
	
	public LanguageSaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.LANGUAGE_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("abbreviation", 	"app.abbreviation", 	"tableElement.abbreviation",  	"40%"));
				table.addColumn(1, getColumnWithLabelProperty("description", 	"app.description", 		"tableElement.description", 	"40%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.LANGUAGE_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.LANGUAGE_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.LANGUAGE_CREATE, abmClass) {
			
			@Override
			protected void makeBody() {
				LanguageSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.LANGUAGE_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.LANGUAGE_UPDATE, abmClass) {
			
			@Override
			protected void makeBody() {
				LanguageSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.LANGUAGE_MAIN;
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.abbreviation", "abbreviation");
		index = screen.addInput(index, "app.description", "description");
	}
}
