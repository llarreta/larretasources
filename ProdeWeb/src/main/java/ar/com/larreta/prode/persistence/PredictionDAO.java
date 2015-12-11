package ar.com.larreta.prode.persistence;

import java.util.Collection;

import ar.com.larreta.commons.persistence.dao.StandardDAO;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.Round;

public interface PredictionDAO extends StandardDAO {
	public Collection<Prediction> getPredictionsForRound(Round round);
}
