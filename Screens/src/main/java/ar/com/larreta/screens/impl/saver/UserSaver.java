package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.validators.Validator;

@Component
public class UserSaver extends ABMSaver {
	private Class abmClass = User.class;
	
	public UserSaver() {
		super();

		createScreen.setPreActionListenerName(UserListener.class.getName());
		updateScreen.setPreActionListenerName(UserListener.class.getName());
		updateScreen.setLazyCollections("profiles");

	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.nick", 				"nick",				Validator.REQUIRED);
		index = screen.addPassword(index, "app.password", 		"password", 		Validator.REQUIRED);
		index = screen.addInput(index, "app.email", 			"email",			Validator.REQUIRED);
		index = screen.addMultiBox(index, "app.profiles.avaiables", "app.profiles.assigned", "profiles", 
										Profile.class.getName(), "description", Validator.LIST_SELECTOR_REQUIRED);
	}

	@Override
	public Class getABMClass() {
		return User.class;
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("nick", 	"app.nick", 	"tableElement.nick",  	"40%"));
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("email", 	"app.email", 	"tableElement.email",  	"40%"));
	}

}
