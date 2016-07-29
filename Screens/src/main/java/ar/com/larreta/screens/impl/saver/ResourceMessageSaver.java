package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.commons.domain.Language;
import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.screens.Column;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;

@Component
public class ResourceMessageSaver extends ABMSaver {
	
	public ResourceMessageSaver() {
		super();
		mainScreen.setPostActionListenerName(DeleteResourceMessageListener.class.getName());
		createScreen.setPostActionListenerName(ChangeResourceMessageListener.class.getName());
		updateScreen.setPostActionListenerName(ChangeResourceMessageListener.class.getName());
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
		Column columnCountry = screen.getColumnWithExactFilter("country", 	"app.country", 	"country",  		"10%", 		Country.class.getName(), "country.id");
		columnCountry.getFilterMatchMode().setValueType(Long.class.getName());
		screen.getTable().addColumn(0, columnCountry);
		Column columnLanguage =  screen.getColumnWithExactFilter("language", 	"app.language", "language", 		"10%", 		Language.class.getName(), "language.id");
		columnLanguage.getFilterMatchMode().setValueType(Long.class.getName());
		screen.getTable().addColumn(1, columnLanguage);
		screen.getTable().addColumn(2, screen.getColumnWithContainsFilter("key", 		"app.key", 		"key", 			"30%"));
		screen.getTable().addColumn(3, screen.getColumnWithLabelProperty("textString",	"app.text", 	"text", 		"30%"));
	}

}
