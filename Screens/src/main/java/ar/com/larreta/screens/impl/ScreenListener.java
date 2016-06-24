package ar.com.larreta.screens.impl;

import java.io.Serializable;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;

public interface ScreenListener extends Serializable{
	public void execute(RequestContext flowRequestContext, StandardController controller, Entity entity);
}
