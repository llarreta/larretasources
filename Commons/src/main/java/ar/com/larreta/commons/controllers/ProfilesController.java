package ar.com.larreta.commons.controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.commons.services.impl.UserServiceImpl;
import ar.com.larreta.commons.views.ProfileDataView;

public class ProfilesController extends StandardControllerImpl {
	
	public UserService getService() throws NotServiceAssignedException {
		return (UserService) super.getService();
	}

	@Autowired
	@Qualifier(UserServiceImpl.USER_SERVICE)
	public void setService(UserService standardService) {
		super.setService(standardService);
	}
	
	public Collection<Role> getRoles() throws NotServiceAssignedException{
		return getService().load(Role.class);
	}

	@Override
	public void preCreate(RequestContext flowRequestContext) {
		super.preCreate(flowRequestContext);
		ProfileDataView dataView = (ProfileDataView) getDataView();
		dataView.getSelected().setRoles(new HashSet<Role>(dataView.getRoles().getTarget()));
	}
	
	@Override
	public void preUpdate(RequestContext flowRequestContext) {
		super.preUpdate(flowRequestContext);
		ProfileDataView dataView = (ProfileDataView) getDataView();
		dataView.getSelected().setRoles(new HashSet<Role>(dataView.getRoles().getTarget()));
	}

	@Override
	public void initCreate(RequestContext flowRequestContext) {
		super.initCreate(flowRequestContext);
		ProfileDataView dataView = (ProfileDataView) getDataView();
		try {
			dataView.setRoles(getDualListModel(dataView.getSelected().getRoles(), getRoles()));
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		ProfileDataView dataView = (ProfileDataView) getDataView();
		Profile profile = dataView.getSelected();
		try {
			dataView.setRoles(getDualListModel(getService().getRoles(profile), getRoles()));
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

}
