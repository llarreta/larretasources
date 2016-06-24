package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.commons.domain.Language;
import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class ResourceMessageSaver extends ABMSaver {

	private Class abmClass = ResourceMessage.class;
	
	public ResourceMessageSaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN, abmClass, DeleteResourceMessageListener.class.getName()) {
			
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
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.RESOURCE_MESSAGE_CREATE, abmClass, ChangeResourceMessageListener.class.getName()) {
			
			@Override
			protected void makeBody() {
				ResourceMessageSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.RESOURCE_MESSAGE_UPDATE, abmClass, ChangeResourceMessageListener.class.getName()) {
			
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
		index = screen.addCombo(index, "app.country", 		"country", 		Country.class.getName());
		index = screen.addCombo(index, "app.language", 		"language", 	Language.class.getName());
		index = screen.addInput(index, "app.key", 			"key");
		index = screen.addInput(index, "app.text", 			"textString");
	}

}
