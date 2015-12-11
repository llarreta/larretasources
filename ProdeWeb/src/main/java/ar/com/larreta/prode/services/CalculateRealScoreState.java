package ar.com.larreta.prode.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Game;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.RealScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Score;
import ar.com.larreta.prode.persistence.CompetitionDAO;

public class CalculateRealScoreState extends CalculateScoreState {

	private Competition competition;
	
	public CalculateRealScoreState(CompetitionDAO dao) {
		super(dao);
	}

	public CalculateRealScoreState(Competition competition) {
		super();
		this.competition = competition;
	}

	public CalculateRealScoreState(Competition competition, CompetitionDAO dao) {
		super();
		this.competition = competition;
		this.dao = dao;
	}
	
	@Override
	public PredictionScore getPredictionScore(Prediction prediction) {
		return dao.getPredictionScore(prediction);
	}
	
	public void saveAddedGames(Map<Long, Game> games) {
		if (games!=null){
			Iterator<Game> it = games.values().iterator();
			while (it.hasNext()) {
				dao.saveOrUpdate((Game) it.next());
			}
		}
	}

	@Override
	public CompetitionScore getCompetitionScore(Player player,
			Competition competition) {
		return dao.getCompetitionScore(player, competition);
	}

	@Override
	public Score newScore(Bet bet) {
		Score score = new RealScore();
		score.setId(bet.getId());
		return score;
	}

	@Override
	public List<CompetitionScore> getOrdererScores() {
		return dao.getOrdererCompetitionScores(competition);
	}

	@Override
	public Collection<PredictionScore> getPredictionScoreForRound(Round round) {
		return dao.getPredictionScoreForRound(round);
	}

}
