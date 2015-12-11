package ar.com.larreta.commons.controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.views.UserDataView;

public class UsersController extends StandardControllerImpl {
	
	public UsersController() {
		super();
		try {
			setEntityClass(User.class);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	public Collection<Profile> getProfiles() throws NotServiceAssignedException{
		return getService().load(Profile.class);
	}

	@Override
	public void preUpdate(RequestContext flowRequestContext) {
		super.preUpdate(flowRequestContext);
		UserDataView dataView = (UserDataView) getDataView();
		dataView.getSelected().setProfiles(new HashSet<Profile>(dataView.getProfiles().getTarget()));
	}

	@Override
	public void initCreate(RequestContext flowRequestContext) {
		super.initCreate(flowRequestContext);
		UserDataView dataView = (UserDataView) getDataView();
		//dataView.setProfiles(getDualListModel(dataView.getSelected().getProfiles(), getProfiles()));
	}

	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		UserDataView dataView = (UserDataView) getDataView();
		//dataView.setProfiles(getDualListModel(dataView.getSelected().getProfiles(), getProfiles()));
	}
	
}
