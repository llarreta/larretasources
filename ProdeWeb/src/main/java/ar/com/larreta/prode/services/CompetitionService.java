/**
 * 
 */
package ar.com.larreta.prode.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Game;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Score;
import ar.com.larreta.prode.domain.SupposeCompetitionScore;
import ar.com.larreta.prode.persistence.CompetitionDAO;


public interface CompetitionService extends StandardService {

	@Override
	public CompetitionDAO getDao();
	public void setDao(CompetitionDAO dao);
	public void execute();	
	public void saveOrUpdate(Entity entity);
	public void delete(Entity entity);
	public List<Competition> list();
	public Collection<Competition> getAvaiableRegisterCompetitions(Player player);
	public Collection<Competition> getRegisterCompetitions(Player player);
	public Collection<Round> getRoundsForCompetition(Competition competition);
	public Boolean isRegistered(Player player, Competition competition);
	public Prediction getPredictionForRound(Player player, Round round);
	public Collection<Prediction> getPredictionForPlayer(Player player, Competition competition);
	public void updatePlayerPredictions(Round round);
	public Collection<Prediction> getOrdererPredictionForPlayer(Player player, Competition competition);
	public Collection<Prediction> getPredictionForRound(Round round);
	@Deprecated
	public void updatePlayerPredictions(Competition competition);
	public void processRound(Round round);
	public List<SupposeCompetitionScore> getSupposeCompetitionScores();
	public void convertScoresToSuppose(Round round);
	public void updateSupposePositions(Round round);
	public void updatePositions(Round round);
	public void updatePositions(Round round, CalculateScoreState calculateScoreState);
	public void updatePositionsPredictions(Round round);
	public void updatePositionsPredictions(Round round, CalculateScoreState calculateScoreState);
	public void deleteSupposeCompetitionScores();
	public void processSupposeScoresForRound(Round round);
	public void processScoresForRound(Round round);
	public void processScoresForRound(Round round, CalculateScoreState calculateScoreState);
	public void calculateScores(Map<Long, Game> games, PredictionScore predictionScore, Set<Bet> bets, CalculateScoreState calculateScoreState);
	public Score evaluatePrevious(Score score);
	public Integer getValue(Bet bet, Game game) ;
	public Integer getHardBetFactor(Bet bet);
	public Map<Long, Game> getGamesMap(Round round);
	public Collection<Score> getScoresForCompetition(Competition competition);
	public Collection<Player> getPlayersWithoutPredictionForRound(Round round);
	public Collection<Player> getPlayersInCompetition(Competition competition);
	public void processAlerts();
	public List<CompetitionScore> getOrdererScores(Competition competition);
}
