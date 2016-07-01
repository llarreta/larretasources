package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.screens.impl.CreateScreen;

@Component
public class ProfileSaver extends ParametricEntitySaver {

	public ProfileSaver() {
		super();
		updateScreen.setLazyCollections("roles");
	}

	protected void makeBody(CreateScreen screen) {
		super.makeBody(screen);
		Integer index = 0;
		index = screen.addMultiBox(index, "app.roles.avaiables", "app.roles.assigned", "roles", Role.class.getName(), "description");
	}

	@Override
	public Class getABMClass() {
		return Profile.class;
	}

}
