package ar.com.larreta.commons.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.services.StandardService;

public abstract class HomeController extends StandardControllerImpl {
	
	@Autowired
	@Qualifier("standardService")
	private StandardService standardService;
	
	public void home(RequestContext flowRequestContext) throws Exception {
	}

}
