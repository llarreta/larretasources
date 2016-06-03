package ar.com.larreta.prode.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.dao.impl.Equal;
import ar.com.larreta.commons.persistence.dao.impl.ReferencedEqual;
import ar.com.larreta.commons.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.commons.persistence.dao.impl.Where;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.RealCompetitionScore;
import ar.com.larreta.prode.domain.RealPredictionScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Score;
import ar.com.larreta.prode.domain.SupposeCompetitionScore;
import ar.com.larreta.prode.domain.SupposePredictionScore;
import ar.com.larreta.prode.persistence.CompetitionDAO;

@Repository
public class CompetitionDAOImpl extends StandardDAOImpl implements CompetitionDAO {

	private Criteria getCriteria() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Competition.class);
		return criteria;
	}
	
	public Boolean isRegistered(Player player, Competition competition){
		LoadArguments args = new LoadArguments(RealCompetitionScore.class, "id");
		args.addWhereEqual("player.id", player.getId()).addWhereEqual("competition.id", competition.getId());
		Collection<Entity> result = load(args);
		return (result.size()>0);
	}
	
	public Collection<Competition> getAvaiableRegisterCompetitions(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT C ");
		hql.append("FROM Competition C ");
		hql.append("WHERE C.registrationStart <= :registrationStart ");
		hql.append("AND C.registrationEnd >= :registrationEnd ");
		hql.append("AND C NOT IN ( ");
			hql.append("SELECT CS.competition ");
			hql.append("FROM RealCompetitionScore CS ");
			hql.append("WHERE CS.player.id = :id) ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		Date actual = new Date();
		query.setDate("registrationStart", actual);
		query.setDate("registrationEnd", actual);
		query.setLong("id", player.getId());
		
		return query.list();
	}

	public Collection<Competition> getSupposeCompetitionsScores(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT S ");
		hql.append("FROM SupposeCompetitionScore S ");
		hql.append("WHERE S.playerSuppose.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", player.getId());
		
		return query.list();
	}
	 
	@Deprecated
	//FIXME: Borrar, esto ya se puede hacer directamente con el nuevo load de standar dao
	public Collection<Competition> getRegisterCompetitions(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT CS.competition ");
		hql.append("FROM RealCompetitionScore CS ");
		hql.append("WHERE CS.player.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", player.getId());
		
		return query.list();
	}

	public Collection<Round> getRoundsForCompetition(Competition competition){
		LoadArguments args = new LoadArguments(Round.class);
		args.addProjectedCollection("games").addProjectedProperties("games.local").addProjectedProperties("games.visitor");
		args.addWhereEqual("competition.id", competition.getId());
		return load(args);
	}
	
	@Deprecated
	//FIXME: Borrar, esto ya se puede hacer directamente con el nuevo load de standar dao
	public List<CompetitionScore> getOrdererCompetitionScores(Competition competition){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT S ");
		hql.append("FROM Competition C ");
		hql.append("INNER JOIN C.competitionScores S ");
		hql.append("WHERE C.id = :id ");
		hql.append("ORDER BY S.value DESC ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", competition.getId());
		
		return query.list();
	}
	
	public Collection<Round> getRoundsNotPrediction(Competition competition, Player player){
		LoadArguments args = new LoadArguments(Round.class);
		args.addProjectedCollection("games").addProjectedProperties("games.local").addProjectedProperties("games.visitor");
		args.addLeftJoin("predictions");
		args.addWhereEqual("competition.id", competition.getId());
		
		Collection<Where> wheres = new ArrayList<Where>();
		wheres.add(new ReferencedEqual(args, "round.id", args.getMainEntity().getAlias() + DOT + "id"));
		wheres.add(new Equal(args, "player.id", player.getId()));
		args.addWhereNotExists(Prediction.class, wheres);
		
		return  load(args);
	}

	/**
	 * Obtener los scores de las predicciones para una determinada ronda
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<PredictionScore> getSupposePredictionScoreForRound(Round round, Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT PS ");
		hql.append("FROM SupposePredictionScore PS ");
		hql.append("WHERE PS.prediction.round.id = :id ");
		hql.append("AND PS.competitionScore.playerSuppose.id = :player ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", round.getId());
		query.setLong("player", player.getId());
		
		return query.list();
	}
	
	/**
	 * Obtener los scores de las predicciones para una determinada ronda
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<PredictionScore> getPredictionScoreForRound(Round round){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT PS ");
		hql.append("FROM RealPredictionScore PS ");
		hql.append("WHERE PS.prediction.round.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", round.getId());
		
		return query.list();
	}
	
	/**
	 * Obtener las predicciones de un jugador en un campeonato en particular
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<Prediction> getPredictionForPlayer(Player player, Competition competition){
		LoadArguments args = new LoadArguments(Prediction.class);
		args.addProjectedCollection("round.games").addProjectedProperties("round.games.local").addProjectedProperties("round.games.visitor");
		args.addProjectedCollection("bets").addProjectedProperties("bets.game.local").addProjectedProperties("bets.game.visitor");
		args.addWhereEqual("player.id", player.getId()).addWhereEqual("round.competition.id", competition.getId());
		return load(args);
	}
	
	@Deprecated
	//FIXME: Borrar, esto ya se puede hacer directamente con el nuevo load de standar dao
	public Collection<Player> getPlayersInCompetition(Competition competition){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT CS.player ");
		hql.append("FROM RealCompetitionScore CS ");
		hql.append("WHERE CS.competition.id = :competition ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("competition", competition.getId());
		
		return query.list();
	}
	
	/**
	 * Obtener las predicciones de una fecha
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<Prediction> getPredictionForRound(Round round){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT PR ");
		hql.append("FROM Prediction PR ");
		hql.append("WHERE PR.round.id = :round ");
		hql.append("AND PR.player.deleted IS NULL ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("round", round.getId());
		
		return query.list();
	}

	public Collection<Score> getScoresForCompetition(Competition competition){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT S ");
		hql.append("FROM RealScore S ");
		hql.append("WHERE S.bet.game.round.competition.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		Date actual = new Date();
		query.setLong("id", competition.getId());
		
		return query.list();
	}
	
	public PredictionScore getPredictionScore(Prediction prediction){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT P ");
		hql.append("FROM RealPredictionScore P ");
		hql.append("WHERE P.prediction.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", prediction.getId());
		
		PredictionScore actualPrediction = (PredictionScore) query.uniqueResult();
		if (actualPrediction==null){
			actualPrediction = new RealPredictionScore();
			actualPrediction.setPrediction(prediction);
			actualPrediction.setId(prediction.getId());
		}
		
		return actualPrediction;
	}

	public PredictionScore getSupposePredictionScore(Prediction prediction){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT P ");
		hql.append("FROM SupposePredictionScore P ");
		hql.append("WHERE P.prediction.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", prediction.getId());
		
		PredictionScore actualPrediction = (PredictionScore) query.uniqueResult();
		if (actualPrediction==null){
			actualPrediction = new SupposePredictionScore();
			actualPrediction.setPrediction(prediction);
			actualPrediction.setId(prediction.getId());
		}
		
		return actualPrediction;
	}
	
	public CompetitionScore getCompetitionScore(Player player, Competition competition){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT C ");
		hql.append("FROM RealCompetitionScore C ");
		hql.append("WHERE C.competition.id = :competition ");
		hql.append("AND C.player.id = :player ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("competition", competition.getId());
		query.setLong("player", player.getId());
		
		CompetitionScore actual = (CompetitionScore) query.uniqueResult();
		if (actual==null){
			actual = new RealCompetitionScore();
		}
		
		return actual;
	}

	public List<SupposeCompetitionScore> getSupposeCompetitionScores(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT C ");
		hql.append("FROM SupposeCompetitionScore C ");
		hql.append("WHERE C.playerSuppose.id = :player ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("player", player.getId());
		
		return query.list();
	}
	
	public CompetitionScore getSupposeCompetitionScore(Player player, Competition competition){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT C ");
		hql.append("FROM SupposeCompetitionScore C ");
		hql.append("WHERE C.competition.id = :competition ");
		hql.append("AND C.player.id = :player ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("competition", competition.getId());
		query.setLong("player", player.getId());
		
		CompetitionScore actual = (CompetitionScore) query.uniqueResult();
		if (actual==null){
			actual = new SupposeCompetitionScore();
		}
		
		return actual;
	}
	
	public Collection<PredictionScore> getBestPredictionsForRound(Round round){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT P1 ");
		hql.append("FROM RealPredictionScore P1 ");
		hql.append("WHERE P1.prediction.round.id = :id ");
		hql.append("AND P1.value =  (");
					hql.append("SELECT MAX(P2.value) ");
					hql.append("FROM RealPredictionScore P2 ");
					hql.append("WHERE P2.prediction.round.id = P1.prediction.round.id) ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", round.getId());
		
		return query.list();
	}

	public Collection<PredictionScore> getBestPredictionsForCompetition(Competition competition){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT P1 ");
		hql.append("FROM RealPredictionScore P1 ");
		hql.append("WHERE P1.prediction.round.competition.id = :id ");
		hql.append("AND P1.value =  (");
					hql.append("SELECT MAX(P2.value) ");
					hql.append("FROM RealPredictionScore P2 ");
					hql.append("WHERE P2.prediction.round.competition.id = P1.prediction.round.competition.id) ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", competition.getId());
		
		return query.list();
	}

	public Integer getTotalScoreUntilRound(Round round, Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT SUM(P1.value) ");
		hql.append("FROM RealPredictionScore P1 ");
		hql.append("WHERE P1.prediction.round.start <= :start ");
		hql.append("AND P1.prediction.player.id =  :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setDate("start", round.getStart());
		query.setLong("id", player.getId());
		
		Long result = (Long) query.uniqueResult();
		Integer total = 0;
		if (result!=null){
			total = result.intValue();
		}
		
		return total;
	}
	
	public Collection<Round> getRoundsOnRange(Date start, Date end){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT R ");
		hql.append("FROM Round R ");
		hql.append("WHERE R.start >= :start ");
		hql.append("AND R.start <=  :end");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setDate("start", start);
		query.setDate("end", end);
		
		return query.list();		
	}

	public Collection<Player> getPlayersWithoutPredictionForRound(Round round){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT P ");
		hql.append("FROM Player P ");
		hql.append("WHERE NOT EXISTS ( ");
			hql.append("SELECT 1 ");
			hql.append("FROM Prediction PR ");
			hql.append("WHERE PR.player.id = P.id ");
			hql.append("AND PR.round.id = :id ");
		hql.append(")");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", round.getId());
		
		return query.list();
	}

	/**
	 * Elimina los SupposeCompetitionScore para el jugador
	 * @param player
	 * @param competition
	 * @return
	 */ 
	public void deleteSupposeCompetitionScores(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE ");
		hql.append("FROM SupposeCompetitionScore S ");
		hql.append("WHERE S.playerSuppose.id = :id ");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", player.getId());
		
		query.executeUpdate();
	}

	/**
	 * Elimina los SupposePredictionScore para el jugador
	 * @param player
	 * @param competition
	 * @return
	 */ 
	public void deleteSupposePredictionScores(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE ");
		hql.append("FROM SupposePredictionScore SP ");
		hql.append("WHERE SP.competitionScore.id IN");
		
		hql.append("(SELECT SC.id ");
		hql.append("FROM SupposeCompetitionScore SC ");
		hql.append("WHERE SC.playerSuppose.id = :id )");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", player.getId());
		
		query.executeUpdate();
	}

	public void deleteSupposeScores(Player player){
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE ");
		hql.append("FROM SupposeScore SS ");
		hql.append("WHERE SS.predictionScore.id IN");

		hql.append("(SELECT SP.id ");
		hql.append("FROM SupposePredictionScore SP ");
		hql.append("WHERE SP.competitionScore.id IN");
		
		hql.append("(SELECT SC.id ");
		hql.append("FROM SupposeCompetitionScore SC ");
		hql.append("WHERE SC.playerSuppose.id = :id ))");
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", player.getId());
		
		query.executeUpdate();
	}
	
	
}
