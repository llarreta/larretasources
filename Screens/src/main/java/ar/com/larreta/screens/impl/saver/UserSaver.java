package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;

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
		index = screen.addInput(index, "app.nick", 				"nick");
		index = screen.addPassword(index, "app.password", 		"password");
		index = screen.addInput(index, "app.email", 			"email");
		index = screen.addMultiBox(index, "app.profiles.avaiables", "app.profiles.assigned", "profiles", Profile.class.getName(), "description");
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
