package ar.com.larreta.prode.alerts;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.threads.Thread;
import ar.com.larreta.commons.utils.DateUtils;
import ar.com.larreta.prode.services.CompetitionService;

//@Component
//FIXME: luego volver a habilitar
public class PredictionAlerts extends Thread {
	
	private static final String COMPETITION_SERVICE = "competitionService";
	private static final int REPEAT_COUNT = 2;
	private static final int INTERVAL_HOURS = 6;

	private CompetitionService competitionService;
	
	public CompetitionService getCompetitionService() {
		if ((competitionService==null) && (AppManager.getInstance()!=null)){
			competitionService = (CompetitionService) AppManager.getInstance().getAppContext().getBean(COMPETITION_SERVICE);
		}
		return competitionService;
	}

	public PredictionAlerts(){
		super(new Long(DateUtils.ONE_HOUR * INTERVAL_HOURS), new Long(REPEAT_COUNT));
	}
	
	private PredictionAlerts(CompetitionService competitionService, Date executeTime, Long interval, Long executeCount){
		super(interval, executeCount);
		this.competitionService = competitionService;
	}

	@Override
	protected void execute() {
		getLog().info("Ejecutando alerta de pronosticos.");
		if (getCompetitionService()!=null){
			getCompetitionService().processAlerts();
		} else {
			getLog().info("Precaucion! no puedo procesar alertas!");
		}
	}

	@Override
	protected void restart(Long executeCount, Long interval, Date executeTime){
		new PredictionAlerts(competitionService, getExecuteTime(), interval, executeCount);
	}

}
