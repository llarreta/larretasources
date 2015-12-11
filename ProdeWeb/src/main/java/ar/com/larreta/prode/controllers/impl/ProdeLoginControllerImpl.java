package ar.com.larreta.prode.controllers.impl;

import java.util.Collection;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ar.com.larreta.commons.controllers.impl.LoginControllerImpl;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.reports.Report;
import ar.com.larreta.commons.views.LoginDataView;
import ar.com.larreta.prode.controllers.ProdeLoginController;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.services.PlayerService;
import ar.com.larreta.prode.views.ProdeLoginDataView;

public class ProdeLoginControllerImpl extends LoginControllerImpl implements ProdeLoginController{

	@Autowired
	private PlayerService playerService;
	
	public PlayerService getPlayerService() {
		return playerService;
	}

	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	public Collection<Player> getFriends() throws NotImplementedException{
		return playerService.getActivePlayers();
	}

	@Override
	public void setExtraUserData(LoginDataView loginView, User user) {
		ProdeLoginDataView dataView = (ProdeLoginDataView) loginView;
		Player player = (Player) user;
		player.setName(dataView.getName());
		player.setSurname(dataView.getSurname());
		if (dataView.getFile()!=null){
			player.setImage(dataView.getFile().getContents());
		}
		player.setFriend(dataView.getFriend());
	}

	@Override
	public User getUser() {
		return new Player();
	}
	
	public void fileUpload(FileUploadEvent event) {
		((ProdeLoginDataView)getDataView()).setFile(event.getFile());
    }
	
	protected void addMailParams(User user, Report report) {
		Player player = (Player) user;
		report.addParameter("name", player.getName());
		report.addParameter("surname", player.getSurname());
	}
	
}
