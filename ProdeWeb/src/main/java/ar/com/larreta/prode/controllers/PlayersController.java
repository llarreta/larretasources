package ar.com.larreta.prode.controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.services.PlayerService;
import ar.com.larreta.prode.views.PlayerDataView;

public class PlayersController extends StandardControllerImpl {

	public PlayerService getService() throws NotServiceAssignedException {
		return (PlayerService) super.getService();
	}

	@Autowired
	public void setService(PlayerService standardService) {
		super.setService(standardService);
	}
	
	public Collection<Player> getFriends() throws NotImplementedException, NotServiceAssignedException{
		return getService().getActivePlayers();
	}

	public Collection<Profile> getProfiles() throws NotServiceAssignedException{
		return getService().load(Profile.class);
	}

	@Override
	public void preUpdate(RequestContext flowRequestContext) {
		super.preUpdate(flowRequestContext);
		PlayerDataView dataView = (PlayerDataView) getDataView();
		dataView.getSelected().setProfiles(new HashSet<Profile>(dataView.getProfiles().getTarget()));
	}

	@Override
	public void initCreate(RequestContext flowRequestContext) {
		super.initCreate(flowRequestContext);
		PlayerDataView dataView = (PlayerDataView) getDataView();
		try {
			dataView.setProfiles(getDualListModel(dataView.getSelected().getProfiles(), getProfiles()));
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		PlayerDataView dataView = (PlayerDataView) getDataView();
		User user = (User) dataView.getSelected();
		try {
			dataView.setProfiles(getDualListModel(getService().getProfiles(user), getProfiles()));
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

}
