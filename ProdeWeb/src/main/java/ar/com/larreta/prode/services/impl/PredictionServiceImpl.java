package ar.com.larreta.prode.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.reports.Report;
import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.persistence.PredictionDAO;
import ar.com.larreta.prode.reports.PredictionsXLS;
import ar.com.larreta.prode.services.PredictionService;

@Service
@Transactional
public class PredictionServiceImpl extends StandardServiceImpl implements PredictionService {
	
	@Override
	public PredictionDAO getDao() {
		return (PredictionDAO) super.getDao();
	}
	
	@Autowired
	public void setDao(PredictionDAO dao) {
		super.setDao(dao);
	}
	
	public byte[] getAllPredictionsFile(Round round){
		getLog().debug("getAllPredictionsFile init");
		Collection<Prediction> predictions = getDao().getPredictionsForRound(round);
		
		if ((predictions!=null)&&(predictions.size()>0)){ 
			getLog().debug("getAllPredictionsFile PredictionsXLS");
			Report report = new PredictionsXLS(appConfigData, predictions);
			try {
				getLog().debug("getAllPredictionsFile getOutputStream");
				ByteArrayOutputStream outputStream = report.getOutputStream();
				
				getLog().debug("getAllPredictionsFile toByteArray");
				return outputStream.toByteArray();
			} catch (IOException e) {
				getLog().error(AppException.getStackTrace(e));
			}
		}
		return null;
	}
	
}
