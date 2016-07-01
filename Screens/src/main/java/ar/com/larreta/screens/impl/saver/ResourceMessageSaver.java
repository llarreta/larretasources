package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.commons.domain.Language;
import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;

@Component
public class ResourceMessageSaver extends ABMSaver {
	
	public ResourceMessageSaver() {
		super();
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addCombo(index, "app.country", 		"country", 		Country.class.getName());
		index = screen.addCombo(index, "app.language", 		"language", 	Language.class.getName());
		index = screen.addInput(index, "app.key", 			"key");
		index = screen.addInput(index, "app.text", 			"textString");
	}

	@Override
	public Class getABMClass() {
		return ResourceMessage.class;
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().setLazyProperties("country,language");
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("country", 	"app.country", 	"tableElement.country",  	"10%"));
		screen.getTable().addColumn(1, screen.getColumnWithLabelProperty("language", 	"app.language", "tableElement.language", 	"10%"));
		screen.getTable().addColumn(2, screen.getColumnWithLabelProperty("key", 		"app.key", 		"tableElement.key", 		"30%"));
		screen.getTable().addColumn(3, screen.getColumnWithLabelProperty("textString",	"app.text", 	"tableElement.text", 		"30%"));
	}

}
