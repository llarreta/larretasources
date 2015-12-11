/**
 * 
 */
package ar.com.larreta.prode.services.impl;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.services.impl.UserServiceImpl;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.persistence.PlayerDAO;
import ar.com.larreta.prode.services.PlayerService;

@Service
@Transactional
public class PlayerServiceImpl extends UserServiceImpl implements PlayerService {
	
	public PlayerDAO getDao() {
		return (PlayerDAO) super.getDao();
	}

	@Autowired
	public void setDao(PlayerDAO dao) {
		super.setDao(dao);
	}
	
	public void update(Player player, Prediction prediction){
		prediction.setPlayer(player);
		saveOrUpdate(prediction);
	}
	
	public Collection getActivePlayers(){
		return new HashSet(getDao().getActivePlayers());
	}
	
}
