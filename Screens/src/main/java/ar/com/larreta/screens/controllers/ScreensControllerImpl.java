package ar.com.larreta.screens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreensControllerImpl extends StandardControllerImpl {

	private static final String SCREEN_REF = "screen";

	public ScreensService getService() throws NotServiceAssignedException {
		return (ScreensService) super.getService();
	}

	@Autowired
	@Qualifier(ScreenServiceImpl.SCREEN_SERVICE)
	public void setService(ScreensService standardService) {
		super.setService(standardService);
	}

	@Override
	public void starting(RequestContext flowRequestContext) throws PaginatorNotFoundException {
		setEntityClass(User.class);
		super.starting(flowRequestContext);
		try {
			Screen screen = (Screen) getService().getScreen(new Long(1));
			flowRequestContext.getFlowScope().put(SCREEN_REF, screen);
		} catch (Exception e){
			getLog().error("Ocurrio un error", e);
		}

	}


}
