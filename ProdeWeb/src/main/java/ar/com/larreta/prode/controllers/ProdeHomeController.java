package ar.com.larreta.prode.controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.HomeController;
import ar.com.larreta.commons.domain.Message;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.utils.SessionUtils;
import ar.com.larreta.commons.views.LoginDataView;
import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.exception.WithoutCompetitionAndNotificationsAvaiablesException;
import ar.com.larreta.prode.services.CompetitionService;
import ar.com.larreta.prode.services.PlayerService;
import ar.com.larreta.prode.services.PredictionService;
import ar.com.larreta.prode.views.ProdeHomeDataView;

public class ProdeHomeController extends HomeController {

	private static final String BET_SELECTED = "betSelected";
	
	@Autowired
	private CompetitionService competitionService;
	
	@Autowired
	private PredictionService predictionService;

	@ManagedProperty(value=OPEN_MB + ProdeHomeDataView.PRODE_HOME_DATA_VIEW + CLOSE_MB)
	private LoginDataView dataView;
	
	@Override
	public void home(RequestContext flowRequestContext) throws Exception {
		Long execStat = statisticsStart("home"); 
		
		super.home(flowRequestContext);
		
		ProdeHomeDataView dataView = (ProdeHomeDataView) getDataView();
		
		dataView.initialize();
		
		if ((dataView.getCompetition()==null) && (dataView.getCountNotifications()>0)){
			throw new WithoutCompetitionAndNotificationsAvaiablesException();
		}

		statisticsStop(execStat);
	}
	
	@Override
	public PlayerService getService() throws NotServiceAssignedException {
		return (PlayerService) super.getService();
	}
	
	@Autowired
	public void setService(PlayerService standardService) {
		super.setService(standardService);
	}
	
	public void hardBetSelection(ActionEvent actionEvent) throws NotServiceAssignedException{
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = null;
		dataView.setBetSelected((Bet)actionEvent.getComponent().getAttributes().get(BET_SELECTED));
		dataView.setPredictionSelected((Prediction) actionEvent.getComponent().getAttributes().get("predictionSelected"));
		getLog().info("hardBetSelection");
		
		Bet bet = dataView.getBetSelected();
		Prediction prediction = dataView.getPredictionSelected();
		for(Bet betAux : prediction.getBets()){
			betAux.setHardBet(bet.getId().equals(betAux.getId()));
		}

		getService().update((Player) SessionUtils.getActualUser(), prediction);
	}

	public void save(AjaxBehaviorEvent event) throws NotServiceAssignedException{
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = (ProdeHomeDataView) getDataView();
		Prediction prediction = dataView.getUpdatedPrediction();
		prediction.setAuto(Boolean.FALSE);
		getService().update((Player) SessionUtils.getActualUser(), prediction);
	}
	
	@Deprecated
	/**
	 * Ya no se utiliza, ahora funciona con autosave
	 * @param actionEvent
	 */
	public void predict(ActionEvent actionEvent) throws NotServiceAssignedException{
		setSelected(actionEvent);
		getLog().info("predict");
		
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = null;
		Prediction prediction = (Prediction) dataView.getSelected();
		
		// Validaciones sobre el pronostico realizado
		// Validar que todos los equipos tengan  goles
		Set<Bet> bets = prediction.getBets();
		Iterator<Bet> itBets = bets.iterator();
		Integer hardBetCount = 0;
		while (itBets.hasNext()) {
			Bet bet = (Bet) itBets.next();
			if ((bet.getLocalGoals()==null) || (bet.getVisitorGoals()==null)){
				//FIME: Lanzar error, hay apuestas para un equipo sin goles
			}
			if (bet.getHardBet()){
				hardBetCount++;
			}
		}
		
		if (hardBetCount==0){
			//FIXME: Lanzar excepcion indicando que debe colocar un doble
		}
		
		if (hardBetCount>1){
			//FIXME: Lanzar excepcion indicando que existen mas dobles que los que se permiten
		}
		
		getService().update((Player) SessionUtils.getActualUser(), prediction);
		
	}
	
	public void publicMessage(AjaxBehaviorEvent actionEvent) throws NotServiceAssignedException{
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = null;
		
		getLog().info("publicMessage:" + dataView.getPrivateMessage());
		Message message = new Message();
		message.setMessage(dataView.getPrivateMessage());
		message.setFrom(SessionUtils.getActualUser());
		message.setDate(new Date());
		getService().save(message);
		
		dataView.resetMessages();
	}

	public void download(ActionEvent actionEvent){
		try {
			//FIXME: Ver como obtener este valor
			ProdeHomeDataView dataView = null;
			
			setSelected(actionEvent);
			getLog().info("download");
			
			Prediction prediction = (Prediction) dataView.getSelected();
			competitionService.updatePlayerPredictions(prediction.getRound().getCompetition());
			
			dataView.setFile(predictionService.getAllPredictionsFile(prediction.getRound()));
		
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}

	}

	//FIXME: Mejorar el proceso de autopronosticos, en base a datos mas reales
	public void autoPredict(ActionEvent actionEvent) throws NotServiceAssignedException{
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = null;
		
		setSelected(actionEvent);
		getLog().info("autoPredict");
		
		Prediction prediction = (Prediction) dataView.getSelected();
	
		Integer min=0;
		Integer max=3;
		
		Set<Bet> bets = prediction.getBets();
		Integer hardBet = randInt(1, bets.size());
		Iterator<Bet> itBets = bets.iterator();
		Integer betIndex = 0;
		while (itBets.hasNext()) {
			betIndex++;
			Bet bet = (Bet) itBets.next();
			bet.setLocalGoals(randInt(min, max));
			bet.setVisitorGoals(randInt(min, max));
			bet.setHardBet(betIndex.equals(hardBet));
		}
		
		getService().update((Player) SessionUtils.getActualUser(), prediction);
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public void playerChangeEvent(ValueChangeEvent event){
		getLog().debug("playerChangeEvent");
		
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = null;
		
		Player player = (Player) event.getNewValue();
		if (player!=null){
			dataView.setPlayer((Player) event.getNewValue());
		}
	}
	
	public void suppose(ActionEvent actionEvent){
		getLog().info("suppose");
		
		setSelected(actionEvent);
		
		//FIXME: Ver como obtener este valor
		ProdeHomeDataView dataView = null;
		
		Round round = dataView.getActualRound();
		Competition competition = dataView.getCompetition();
		
		competitionService.updatePlayerPredictions(round);
		
		//competitionService.updatePlayerPredictions(getDataView().getCompetition());
		
		competitionService.deleteSupposeCompetitionScores();
		competitionService.convertScoresToSuppose(dataView.getActualRound());
		competitionService.processSupposeScoresForRound(dataView.getActualRound());

		// Las posiciones deberian autocalcularse
		competitionService.updateSupposePositions(dataView.getActualRound());
		competitionService.updatePositionsPredictions(dataView.getActualRound());
		
		dataView.setSupposeScores(competitionService.getSupposeCompetitionScores());
	}
}
