package ar.com.larreta.screens.impl.saver;

import java.util.Collection;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.screens.impl.ScreenListener;

public class SecurityListener implements ScreenListener {

	public void execute(RequestContext flowRequestContext, StandardController controller, Entity entity) {
		Collection securities = AppManager.getInstance().getStandardService().load(Security.class);
		controller.getDataView().setSelected((Entity) securities.iterator().next());
	}

}
