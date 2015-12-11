package ar.com.larreta.prode.persistence;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import ar.com.larreta.commons.persistence.dao.StandardDAO;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Score;
import ar.com.larreta.prode.domain.SupposeCompetitionScore;

public interface CompetitionDAO extends StandardDAO {

	public Boolean isRegistered(Player player, Competition competition);
	
	public Collection<Competition> getAvaiableRegisterCompetitions(Player player);

	public Collection<Competition> getSupposeCompetitionsScores(Player player);
	 
	@Deprecated
	//FIXME: Borrar, esto ya se puede hacer directamente con el nuevo load de standar dao
	public Collection<Competition> getRegisterCompetitions(Player player);

	public Collection<Round> getRoundsForCompetition(Competition competition);
	
	@Deprecated
	//FIXME: Borrar, esto ya se puede hacer directamente con el nuevo load de standar dao
	public List<CompetitionScore> getOrdererCompetitionScores(Competition competition);
	
	public Collection<Round> getRoundsNotPrediction(Competition competition, Player player);

	/**
	 * Obtener los scores de las predicciones para una determinada ronda
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<PredictionScore> getSupposePredictionScoreForRound(Round round, Player player);
	
	/**
	 * Obtener los scores de las predicciones para una determinada ronda
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<PredictionScore> getPredictionScoreForRound(Round round);
	
	/**
	 * Obtener las predicciones de un jugador en un campeonato en particular
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<Prediction> getPredictionForPlayer(Player player, Competition competition);
	
	@Deprecated
	//FIXME: Borrar, esto ya se puede hacer directamente con el nuevo load de standar dao
	public Collection<Player> getPlayersInCompetition(Competition competition);
	
	/**
	 * Obtener las predicciones de una fecha
	 * @param player
	 * @param competition
	 * @return
	 */
	public Collection<Prediction> getPredictionForRound(Round round);

	public Collection<Score> getScoresForCompetition(Competition competition);
	
	public PredictionScore getPredictionScore(Prediction prediction);

	public PredictionScore getSupposePredictionScore(Prediction prediction);
	
	public CompetitionScore getCompetitionScore(Player player, Competition competition);

	public List<SupposeCompetitionScore> getSupposeCompetitionScores(Player player);
	
	public CompetitionScore getSupposeCompetitionScore(Player player, Competition competition);
	
	public Collection<PredictionScore> getBestPredictionsForRound(Round round);

	public Collection<PredictionScore> getBestPredictionsForCompetition(Competition competition);
	
	public Integer getTotalScoreUntilRound(Round round, Player player);
	
	public Collection<Round> getRoundsOnRange(Date start, Date end);		

	public Collection<Player> getPlayersWithoutPredictionForRound(Round round);

	/**
	 * Elimina los SupposeCompetitionScore para el jugador
	 * @param player
	 * @param competition
	 * @return
	 */ 
	public void deleteSupposeCompetitionScores(Player player);

	/**
	 * Elimina los SupposePredictionScore para el jugador
	 * @param player
	 * @param competition
	 * @return
	 */ 
	public void deleteSupposePredictionScores(Player player);

	public void deleteSupposeScores(Player player);
	
}
