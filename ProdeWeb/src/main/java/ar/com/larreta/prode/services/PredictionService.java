package ar.com.larreta.prode.services;

import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.persistence.PredictionDAO;

public interface PredictionService extends StandardService {
	
	@Override
	public PredictionDAO getDao();
	public void setDao(PredictionDAO dao);
	public byte[] getAllPredictionsFile(Round round);
	
}
