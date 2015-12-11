package ar.com.larreta.prode.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ar.com.larreta.commons.utils.SessionUtils;
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
import ar.com.larreta.prode.domain.SupposeScore;
import ar.com.larreta.prode.persistence.CompetitionDAO;

public class CalculateSupposeScoreState extends CalculateScoreState {

	public CalculateSupposeScoreState(CompetitionDAO dao) {
		super(dao);
	}

	@Override
	public PredictionScore getPredictionScore(Prediction prediction) {
		return dao.getSupposePredictionScore(prediction);
	}

	/**
	 * No hacemos nada en este caso, para no afectar a los partidos reales
	 */
	@Override
	public void saveAddedGames(Map<Long, Game> games) {
		//Do nothing
	}

	@Override
	public CompetitionScore getCompetitionScore(Player player, Competition competition) {
		SupposeCompetitionScore supposeCompetitionScore = (SupposeCompetitionScore) dao.getSupposeCompetitionScore(player, competition);
		supposeCompetitionScore.setPlayerSuppose((Player) SessionUtils.getActualUser());
		return supposeCompetitionScore;
	}

	@Override
	public Score newScore(Bet bet) {
		return new SupposeScore();
	}

	@Override
	public List<CompetitionScore> getOrdererScores() {
		List score = (List) dao.getSupposeCompetitionsScores((Player) SessionUtils.getActualUser());
		Collections.sort(score);
		return score;
	}

	@Override
	public Collection<PredictionScore> getPredictionScoreForRound(Round round) {
		return dao.getSupposePredictionScoreForRound(round, (Player) SessionUtils.getActualUser());
	}

}
