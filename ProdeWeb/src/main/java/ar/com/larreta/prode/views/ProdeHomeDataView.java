package ar.com.larreta.prode.views;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.controllers.UsersPaginator;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Message;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.commons.faces.ColumnModel;
import ar.com.larreta.commons.reports.XLS;
import ar.com.larreta.commons.services.MessageService;
import ar.com.larreta.commons.utils.SessionUtils;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.SupposeCompetitionScore;
import ar.com.larreta.prode.services.CompetitionService;

public class ProdeHomeDataView extends DataView {
	
	public static final String PRODE_HOME_DATA_VIEW = "prodeHomeDataView";

	@Autowired
	private CompetitionService competitionService;
	
	private Collection<Competition> competitions;
	
	private Competition competition;
	
	private Collection<Prediction> predictions;
	
	private String privateMessage;
	
	private byte[] file;

	private Bet betSelected;
	
	private Prediction predictionSelected;
	
	private Player player;
	
	private Collection<Player> playersInCompetition;
	
	private Integer activeRound;
	private Round actualRound;
	
	private List<SupposeCompetitionScore> supposeScores;
	
	private List<CompetitionScore> ordererScores;
	
	public List<CompetitionScore> getOrdererScores() {
		return ordererScores;
	}

	public void setOrdererScores(List<CompetitionScore> ordererScores) {
		this.ordererScores = ordererScores;
	}

	public List<SupposeCompetitionScore> getSupposeScores() {
		return supposeScores;
	}

	public void setSupposeScores(List<SupposeCompetitionScore> list) {
		this.supposeScores = list;
	}

	public Round getActualRound() {
		return actualRound;
	}

	public void setActualRound(Round actualRound) {
		this.actualRound = actualRound;
	}

	public Integer getActiveRound() {
		return activeRound;
	}

	public void setActiveRound(Integer activeRound) {
		this.activeRound = activeRound;
	}

	//FIXME: Poniendo el service aca, estamos salteando al controller. Corregir.
	@Autowired
	private MessageService messageService;

	public Collection<Player> getPlayersInCompetition() {
		return playersInCompetition;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		refreshPredictions();
	}

	public DefaultStreamedContent getFile() {
		if (file==null){
			return null;
		}
		return new DefaultStreamedContent(new ByteArrayInputStream(file),  XLS.XLS_CONTENT_TYPE, ((Prediction) getSelected()).getRound().getName() + ".xls"); 
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public Integer getMessageRefreshTime(){
		return AppManager.getInstance().getAppConfigData().getMessageRefreshTime();
	}
	
	public Collection<Message> getMessages() throws NotImplementedException{
		return messageService.load();
	}
	
	public String getPrivateMessage() {
		return privateMessage;
	}

	public void setPrivateMessage(String privateMessage) {
		this.privateMessage = privateMessage;
	}

	public UsersPaginator getPaginator() throws PaginatorNotFoundException {
		return (UsersPaginator) super.getPaginator();
	}
	
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Override
	public Entity newSelected() {
		return null;
	}
	
	public Boolean getVisibleHome(){
		return (SessionUtils.getActualUser() instanceof Player) && (competitions!=null) && (competitions.size()>0);
	}
	
	public Collection<Competition> getCompetitionsParticipateOf(){
		return competitions;
	}

	
	
	public Collection<Prediction> getPredictions(){
		if (predictions!=null){
			Iterator<Prediction> it = predictions.iterator();
			while (it.hasNext()) {
				Prediction prediction = (Prediction) it.next();
				prediction.setUpdated(Boolean.FALSE);
			}
		}
		return predictions;
	}
	
	public Prediction getUpdatedPrediction(){
		if (predictions!=null){
			Iterator<Prediction> it = predictions.iterator();
			while (it.hasNext()) {
				Prediction prediction = (Prediction) it.next();
				if (prediction.getUpdated()){
					prediction.setUpdated(Boolean.FALSE);
					return prediction;
				}
			}
		}
		return null;
	}

	public void resetMessages(){
		privateMessage = StringUtils.EMPTY;
	}
	
	public void initialize() {
		resetMessages();
		if (SessionUtils.getActualUser() instanceof Player) {
			competitions = competitionService.getRegisterCompetitions((Player) SessionUtils.getActualUser());
		
			if ((competitions!=null)&&(competitions.size()>0)){
				competition = competitions.iterator().next();
				ordererScores = competitionService.getOrdererScores(competition);
			}
			
			if (competition==null){
				predictions = new ArrayList<Prediction>();
			} else {
				player = (Player) SessionUtils.getActualUser();
				playersInCompetition = competitionService.getPlayersInCompetition(competition);
				refreshPredictions();
				
			}
		}
	}
	
	public Boolean getEditablePredictions(){
		return player.equals(SessionUtils.getActualUser());
	}

	public void refreshPredictions() {
		predictions = competitionService.getPredictionForPlayer(player, competition);
		
		if ((predictions!=null)&&(activeRound==null)){
			Integer index = -1;
			Date today = new Date();
			Iterator<Prediction> it = predictions.iterator();
			while (it.hasNext()) {
				index++;
				Prediction prediction = (Prediction) it.next();
				actualRound = prediction.getRound();
				if ((prediction.getRound()!=null) && (prediction.getRound().getFinish().after(today))){
					break;
				}
			}
			activeRound = index;
		}
	}

	public Bet getBetSelected() {
		return betSelected;
	}

	public void setBetSelected(Bet betSelected) {
		this.betSelected = betSelected;
	}

	public Prediction getPredictionSelected() {
		return predictionSelected;
	}

	public void setPredictionSelected(Prediction predictionSelected) {
		this.predictionSelected = predictionSelected;
	}
	
	public Collection<ColumnModel> getColumns(){
		Collection<ColumnModel> columns = new ArrayList<ColumnModel>();
		if (competition!=null){
			if (competitions!=null){
				Iterator<CompetitionScore> it = ordererScores.iterator();
				if (it.hasNext()) {
					CompetitionScore competitionScore = (CompetitionScore) it.next();
					Collection<PredictionScore> predictions = competitionScore.getOrdererPredictions();
					Iterator<PredictionScore> itPredictions = predictions.iterator();
					while (itPredictions.hasNext()) {
						PredictionScore predictionScore = (PredictionScore) itPredictions.next();
						columns.add(new ColumnModel(predictionScore.getPrediction().getRound().getName(), StringUtils.EMPTY));
					}
				}
			}
		}
		return columns;
	}

	public Collection<ColumnModel> getSupposeColumns(){
		Collection<ColumnModel> columns = new ArrayList<ColumnModel>();
		if (supposeScores!=null){
			List<SupposeCompetitionScore> competitions = supposeScores;
			if (competitions!=null){
				Iterator<SupposeCompetitionScore> it = competitions.iterator();
				if (it.hasNext()) {
					CompetitionScore competitionScore = (CompetitionScore) it.next();
					Collection<PredictionScore> predictions = competitionScore.getOrdererPredictions();
					Iterator<PredictionScore> itPredictions = predictions.iterator();
					while (itPredictions.hasNext()) {
						PredictionScore predictionScore = (PredictionScore) itPredictions.next();
						columns.add(new ColumnModel(predictionScore.getPrediction().getRound().getName(), StringUtils.EMPTY));
					}
				}
			}
		}
		return columns;
	}
	
	/**
	 * Evento tabChange que se invoca con AJAX
	 * @param actionEvent
	 */
	public final void tabChange(TabChangeEvent event){
		
		System.err.println();
		//Do nothing
	}
	
}
