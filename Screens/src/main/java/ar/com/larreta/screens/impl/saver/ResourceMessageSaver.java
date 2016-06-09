package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class ResourceMessageSaver extends ABMSaver {

	private Class abmClass = ResourceMessage.class;
	
	public ResourceMessageSaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.setLazyProperties("country,language");
				table.addColumn(0, getColumnWithLabelProperty("country", 	"app.country", 	"tableElement.country",  	"10%"));
				table.addColumn(1, getColumnWithLabelProperty("language", 	"app.language", "tableElement.language", 	"10%"));
				table.addColumn(2, getColumnWithLabelProperty("key", 		"app.key", 		"tableElement.key", 		"30%"));
				table.addColumn(3, getColumnWithLabelProperty("textString",	"app.text", 	"tableElement.text", 		"30%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.RESOURCE_MESSAGE_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.RESOURCE_MESSAGE_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.RESOURCE_MESSAGE_CREATE, abmClass) {
			
			@Override
			protected void makeBody() {
				ResourceMessageSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.RESOURCE_MESSAGE_UPDATE, abmClass) {
			
			@Override
			protected void makeBody() {
				ResourceMessageSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN;
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.abbreviation", "abbreviation");
		index = screen.addInput(index, "app.description", "description");
	}

}
