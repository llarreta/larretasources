package ar.com.larreta.screens.impl.saver;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.domain.UserState;
import ar.com.larreta.screens.impl.ScreenListener;

public class UserListener implements ScreenListener {

	public void execute(RequestContext flowRequestContext, StandardController controller, Entity entity) {
		User user = (User) entity;
		if (user.getUserState()==null){
			user.setUserState(UserState.ACTIVE);
		}
	}

}
