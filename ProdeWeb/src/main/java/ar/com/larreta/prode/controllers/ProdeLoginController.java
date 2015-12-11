package ar.com.larreta.prode.controllers;

import java.util.Collection;

import org.primefaces.event.FileUploadEvent;

import ar.com.larreta.commons.controllers.LoginController;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.views.LoginDataView;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.services.PlayerService;

public interface ProdeLoginController extends LoginController {
	
	public PlayerService getPlayerService();

	public void setPlayerService(PlayerService playerService);
	
	public Collection<Player> getFriends() throws NotImplementedException;

	@Override
	public void setExtraUserData(LoginDataView loginView, User user);

	@Override
	public User getUser();
	
	public void fileUpload(FileUploadEvent event);
  
	
}
