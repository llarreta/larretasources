package ar.com.larreta.prode.persistence.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.com.larreta.commons.domain.UserState;
import ar.com.larreta.commons.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.persistence.PlayerDAO;

@Repository
public class PlayerDAOImpl extends StandardDAOImpl implements PlayerDAO {
	private Criteria getPlayerCriteria() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Player.class);
		return criteria;
	}
	
	public Collection getActivePlayers(){
		Criteria criteria = getPlayerCriteria();
		criteria.add(Restrictions.eq("userState", UserState.ACTIVE));
		
		return criteria.list();
	}
}
