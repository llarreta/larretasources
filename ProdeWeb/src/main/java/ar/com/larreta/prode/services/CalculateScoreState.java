package ar.com.larreta.prode.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Game;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Score;
import ar.com.larreta.prode.persistence.CompetitionDAO;

public abstract class CalculateScoreState implements Serializable {
	
	protected CompetitionDAO dao;
	
	public CalculateScoreState(){}
	
	public CalculateScoreState(CompetitionDAO dao){
		this.dao = dao;
	}
	
	public abstract PredictionScore getPredictionScore(Prediction prediction);
	public abstract void saveAddedGames(Map<Long, Game> games);
	public abstract CompetitionScore getCompetitionScore(Player player, Competition competition);
	public abstract Score newScore(Bet bet);
	public abstract List<CompetitionScore> getOrdererScores();
	public abstract Collection<PredictionScore> getPredictionScoreForRound(Round round);

}
