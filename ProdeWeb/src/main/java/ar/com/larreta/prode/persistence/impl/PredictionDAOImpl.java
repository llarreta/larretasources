package ar.com.larreta.prode.persistence.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.larreta.commons.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.persistence.PredictionDAO;

@Repository
public class PredictionDAOImpl extends StandardDAOImpl implements PredictionDAO {
	
	private Criteria getCriteria() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Prediction.class);
		return criteria;
	}
	
	public Collection<Prediction> getPredictionsForRound(Round round){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT PR ");
		hql.append("FROM Prediction PR ");
		hql.append("WHERE PR.round.id = :id ");
		hql.append("AND PR.player.deleted IS NULL");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		Date actual = new Date();
		query.setLong("id", round.getId());
		
		return query.list();
	}

}
