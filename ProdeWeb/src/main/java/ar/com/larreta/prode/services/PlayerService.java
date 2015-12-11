/**
 * 
 */
package ar.com.larreta.prode.services;

import java.util.Collection;

import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.persistence.PlayerDAO;

public interface PlayerService extends UserService{
	
	public PlayerDAO getDao();
	public void setDao(PlayerDAO dao);
	public void update(Player player, Prediction prediction);
	public Collection getActivePlayers();
	
}
