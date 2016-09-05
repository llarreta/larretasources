package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.validators.Validator;

@Component
public class ProfileSaver extends ParametricEntitySaver {

	public ProfileSaver() {
		super();
		updateScreen.setLazyCollections("roles");
	}

	protected void makeBody(CreateScreen screen) {
		super.makeBody(screen);
		Integer index = 1;
		index = screen.addMultiCheckBox(index,  "app.roles.avaiables", "roles", Role.class.getName(), null, Validator.LIST_SELECTOR_REQUIRED);
		//index = screen.addMultiBox(index, "app.roles.avaiables", "app.roles.assigned", "roles", Role.class.getName(), "description");
	}

	@Override
	public Class getABMClass() {
		return Profile.class;
	}

}
