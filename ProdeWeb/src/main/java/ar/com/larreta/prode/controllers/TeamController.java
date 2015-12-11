package ar.com.larreta.prode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.prode.services.TeamService;
import ar.com.larreta.prode.views.TeamDataView;

public class TeamController extends StandardControllerImpl{
	
	public TeamService getService() throws NotServiceAssignedException {
		return (TeamService) super.getService();
	}

	@Autowired
	public void setService(TeamService standardService) {
		super.setService(standardService);
	}


	@Override
	public void preCreate(RequestContext flowRequestContext) {
		TeamDataView dataView = (TeamDataView) getDataView();
		dataView.getSelected().setLibrary(dataView.getLibrary());
		dataView.getSelected().setName(dataView.getName());
		dataView.getSelected().setShield(dataView.getShield());
		super.preCreate(flowRequestContext);
	}
	@Override
	public void initCreate(RequestContext flowRequestContext) {
		super.initCreate(flowRequestContext);
	}
	@Override
	public void preUpdate(RequestContext flowRequestContext) {
		TeamDataView dataView = (TeamDataView) getDataView();
		dataView.getSelected().setLibrary(dataView.getLibrary());
		dataView.getSelected().setName(dataView.getName());
		dataView.getSelected().setShield(dataView.getShield());
		super.preUpdate(flowRequestContext);
	}
	
}
