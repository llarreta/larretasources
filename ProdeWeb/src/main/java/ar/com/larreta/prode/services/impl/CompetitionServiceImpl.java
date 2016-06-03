/**
 * 
 */
package ar.com.larreta.prode.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.reports.HTML;
import ar.com.larreta.commons.reports.Report;
import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.commons.utils.DateUtils;
import ar.com.larreta.commons.utils.MailSender;
import ar.com.larreta.commons.utils.SessionUtils;
import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Game;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.Prediction;
import ar.com.larreta.prode.domain.PredictionScore;
import ar.com.larreta.prode.domain.RealCompetitionScore;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Score;
import ar.com.larreta.prode.domain.SupposeCompetitionScore;
import ar.com.larreta.prode.persistence.CompetitionDAO;
import ar.com.larreta.prode.services.CalculateRealScoreState;
import ar.com.larreta.prode.services.CalculateScoreState;
import ar.com.larreta.prode.services.CalculateSupposeScoreState;
import ar.com.larreta.prode.services.CompetitionService;


@Service
@Transactional
public class CompetitionServiceImpl extends StandardServiceImpl implements CompetitionService {

	private static final String REPORT_ALERT_DATE_FORMAT = "report.alert.date.format";
	private static final String REPORT_ALERTS_PREVIOUS_DATE = "report.alerts.previousDate";
	private static final String REPORT_TEMPLATE_ALERTS = "report.template.alerts";
	
	@Override
	public CompetitionDAO getDao() {
		return (CompetitionDAO) super.getDao();
	}

	@Autowired
	public void setDao(CompetitionDAO dao) {
		super.setDao(dao);
	}

	public void execute(){
		System.out.println("Running CompetitionService");	
	}
	
	public void saveOrUpdate(Entity entity){
		dao.saveOrUpdate(entity);
	}
	
	public void delete(Entity entity){
		dao.delete(entity);
	}
	
	public List<Competition> list(){
		return (List<Competition>) load(Competition.class);
	}
	
	/**
	 * Retorna la competicion que actualmente se encuentra disponible para inscripcion de existir
	 * @return
	 */
	public Collection<Competition> getAvaiableRegisterCompetitions(Player player){
		return getDao().getAvaiableRegisterCompetitions(player);
	}
	
	public Collection<Competition> getRegisterCompetitions(Player player){
		LoadArguments args = new LoadArguments(Competition.class);
		args.addInnerJoin("competitionScores");
		args.addWhereEqual("competitionScores.player.id", player.getId());
		return getDao().load(args);
	}
	
	public Collection<Round> getRoundsForCompetition(Competition competition){
		return getDao().getRoundsForCompetition(competition);
	}
	
	public Boolean isRegistered(Player player, Competition competition){
		return getDao().isRegistered(player, competition);
	}
	
	
	/**
	 * Retorna la prediccion realizada por un usuario en particular en una fecha
	 * @param player
	 * @param round
	 * @return
	 */
	public Prediction getPredictionForRound(Player player, Round round){
		Collection<Prediction> predictions = getPredictionForPlayer(player, round.getCompetition());
		if (predictions!=null){
			Iterator<Prediction> it = predictions.iterator();
			while (it.hasNext()) {
				Prediction prediction = (Prediction) it.next();
				if (prediction.getRound().equals(round)){
					return prediction;
				}
			}
		}
		return null;
	}
	
	/**
	 * Retorna las predicciones de un usuario en un determinado campeonato
	 * para la fecha que no encuentra prediccion retorna una prediccion vacia
	 * @param user
	 * @param competition
	 * @return
	 */
	public Collection<Prediction> getPredictionForPlayer(Player player, Competition competition){
		if (isRegistered(player, competition)){
			
			//Obtengo las predicciones previas
			List<Prediction> predictions = new ArrayList<Prediction>();
			predictions.addAll(getDao().getPredictionForPlayer(player, competition));
			
			//Obtengo las fechas para las que aun no pronostico
			Collection<Round> rounds = 	getDao().getRoundsNotPrediction(competition, player);
			if (rounds!=null){
				Iterator<Round> itRounds = rounds.iterator();
				while (itRounds.hasNext()) {
					Round round = (Round) itRounds.next();
					
					Prediction prediction = new Prediction();
					prediction.setPlayer(player);
					prediction.setRound(round);
					
					Collection<Game> games = round.getGames();
					if (games!=null){
						Iterator<Game> itGame = games.iterator();
						while (itGame.hasNext()) {
							Game game = (Game) itGame.next();
							Bet bet = new Bet();
							bet.setGame(game);
							bet.setPrediction(prediction);
							prediction.addBet(bet);
						}
						if ((prediction.getBets()!=null) && (prediction.getBets().size()>0)){
							predictions.add(prediction);
						}
					}
				}
			}
			
			Collections.sort(predictions);
			
			// Reviso las predicciones que no estan pronosticadas para poner el pronostico anterior o todo 1 a 1 si es el primer pronostico
			updatePlayerPredictions(predictions);
			
			return predictions;
		}
		return new ArrayList<Prediction>();
	}
	
	
	public void updatePlayerPredictions(Round round){
		// solo se actualizan las predicciones si la fecha para la cual actualizamos se encuentra en curso
	//	if (round.onGoing()){
			Collection<Player> players = getPlayersWithoutPredictionForRound(round);
			if (players!=null){
				Iterator<Player> itPlayers = players.iterator();
				while (itPlayers.hasNext()) {
					Player player = (Player) itPlayers.next();
					
					Prediction predictionByDefault = new Prediction();
					predictionByDefault.setRound(round);
					predictionByDefault.setPlayer(player);
					Set<Bet> bets = new HashSet<Bet>();
					predictionByDefault.setBets(bets);
					
					//Obtengo la ultima prediccion para el jugador actual
					Collection<Prediction> predictions = player.getPredictions();
					if ((predictions!=null)&&(predictions.size()>0)){
						List<Prediction> list = new ArrayList<Prediction>(predictions);
						Collections.sort(list, new Comparator<Prediction>() {
							@Override
							public int compare(Prediction o1, Prediction o2) {
								return o1.getRound().getStart().compareTo(o2.getRound().getStart());
							}
						});
						Prediction lastPrediction = list.iterator().next(); 

						Collection<Bet> lastBets = lastPrediction.getOrderBets();
						List<Game> games = new ArrayList<Game>(round.getGames());
						Collections.sort(games);
						if ((lastBets!=null) && (games!=null)){
							Iterator<Bet> itLastBets = lastBets.iterator();
							Iterator<Game> itGames = games.iterator();
							while ((itLastBets.hasNext())&&(itGames.hasNext())) {
								Bet lastBet = (Bet) itLastBets.next();
								Game game = itGames.next();
								
								Bet defaultBet = new Bet();
								defaultBet.setGame(game);
								defaultBet.setPrediction(predictionByDefault);
								defaultBet.setLocalGoals(lastBet.getLocalGoals());
								defaultBet.setVisitorGoals(lastBet.getVisitorGoals());
								defaultBet.setHardBet(lastBet.getHardBet());
								bets.add(defaultBet);
							}
						}
					} else {
						// Crear prediccion por defecto con todos los resultados 1 a 1
						List<Game> games = new ArrayList<Game>(round.getGames());
						Collections.sort(games);
						if (games!=null){
							Iterator<Game> itGames = games.iterator();
							while (itGames.hasNext()) {
								Bet bet = new Bet();
								Game game = (Game) itGames.next();
								bet.setGame(game);
								bet.setPrediction(predictionByDefault);
								bet.setLocalGoals(1);
								bet.setVisitorGoals(1);
								//Falta setear el doble
								bets.add(bet);
							}
						}
					}
					
					getDao().save(predictionByDefault);
					
				}
				
			}
	// }
	}

	/**
	 * Retorna las predicciones realizadas en una determinada competicion para un jugador
	 * @param round
	 * @return
	 */
	public Collection<Prediction> getOrdererPredictionForPlayer(Player player, Competition competition) {
		LoadArguments args = new LoadArguments(Prediction.class);
		args.addProjectedCollection("bets").addProjectedProperties("player");
		args.addWhereEqual("player.id", player.getId()).addWhereEqual("round.competition.id", competition.getId());
		args.addDescOrder("round.start");
		return getDao().load(args);
	}

	
	/**
	 * Retorna las predicciones realizadas en una determinada ronda
	 * @param round
	 * @return
	 */
	public Collection<Prediction> getPredictionForRound(Round round) {
		LoadArguments args = new LoadArguments(Prediction.class);
		args.addProjectedProperties("player");
		args.addWhereEqual("round.id", round.getId());
		return getDao().load(args);
	}
	
	
	/**
	 * Setea un valor por default a las predicciones de los jugadores que todavia no han pronosticado vencido el plazo
	 * @param competition
	 */
	@Deprecated
	//FIXME: Lo vamos a reemplazar por un metodo que solamente se encargue de actualizar las predicciones para la fecha actual
	public void updatePlayerPredictions(Competition competition) {
		Collection<Player> players = getDao().getPlayersInCompetition(competition);
		if (players!=null){
			Iterator<Player> it = players.iterator();
			while (it.hasNext()) {
				Player player = (Player) it.next();
				this.getPredictionForPlayer(player, competition);
			}
		}
	}

	/**
	 * Setea un valor por default a las predicciones de los jugadores que todavia no han pronosticado vencido el plazo
	 * @param predictions
	 */
	private void updatePlayerPredictions(Collection<Prediction> predictionsParam) {
		List<Prediction> predictions = new ArrayList<Prediction>(predictionsParam);
		Collections.sort(predictions);

		Iterator<Prediction> itPredictions = predictions.iterator();
		Prediction previous = null;
		while (itPredictions.hasNext()) {
			Prediction prediction = (Prediction) itPredictions.next();
			if ((prediction.getAuto()) && prediction.getRound().onGoing()){
				if (previous!=null){
					Collection<Bet> bets = prediction.getOrderBets();
					Iterator<Bet> itBets = bets.iterator();
					Iterator<Bet> itPrevious = previous.getOrderBets().iterator();
					while ((itBets.hasNext()) && (itPrevious.hasNext())) {
						Bet bet = (Bet) itBets.next();
						Bet previousBet = (Bet) itPrevious.next();
						bet.setLocalGoals(previousBet.getLocalGoals());
						bet.setVisitorGoals(previousBet.getVisitorGoals());
						bet.setHardBet(previousBet.getHardBet());
					}
				} else {
					Collection<Bet> bets = prediction.getBets();
					Iterator<Bet> itBets = bets.iterator();
					Boolean hardBet = Boolean.TRUE;
					while (itBets.hasNext()) {
						Bet bet = (Bet) itBets.next();
						bet.setLocalGoals(1);
						bet.setVisitorGoals(1);
						if (hardBet){
							bet.setHardBet(hardBet);
							hardBet=Boolean.FALSE;
						}
					}
				}
				getDao().save(prediction);
			}
			previous = prediction;
		}
	}
	
	public void processRound(Round round) {
		//updatePlayerPredictions(round);
		updatePlayerPredictions(round.getCompetition());
		getDao().clear();
		processScoresForRound(round);
	}
	
	public List<SupposeCompetitionScore> getSupposeCompetitionScores(){
		List scores = getDao().getSupposeCompetitionScores((Player) SessionUtils.getActualUser());
		Collections.sort(scores);
		return scores;
	}
	
	/**
	 * Convierte los scores reales actuales en scores supuestos
	 */
	public void convertScoresToSuppose(Round round){
		Competition competition = (Competition) getEntity(round.getCompetition());
		Collection<CompetitionScore> competitionScores = competition.getCompetitionScores();
		if (competitionScores!=null){
			Iterator<CompetitionScore> it = competitionScores.iterator();
			while (it.hasNext()) {
				CompetitionScore competitionScore = (CompetitionScore) it.next();
				SupposeCompetitionScore supposeCompetitionScore = new SupposeCompetitionScore(competitionScore, round);
				supposeCompetitionScore.setPlayerSuppose((Player) SessionUtils.getActualUser());
				getDao().save(supposeCompetitionScore);
			}
		}		
	}

	public void updateSupposePositions(Round round) {
		updatePositions(round, new CalculateSupposeScoreState(getDao()));
	}
	
	public void updatePositions(Round round) {
		updatePositions(round, new CalculateRealScoreState((Competition) getEntity(round.getCompetition()), getDao()));
	}
	
	public void updatePositions(Round round, CalculateScoreState calculateScoreState) {
		Collection<CompetitionScore> scores = calculateScoreState.getOrdererScores();
		
		if (scores!=null){
			Iterator<CompetitionScore> it = scores.iterator();
			CompetitionScore score = null;
			Integer position = 1;
			while (it.hasNext()) {
				CompetitionScore competitionScore = (CompetitionScore) it.next();
				if ((score!=null)&&(score.getValue().equals(competitionScore.getValue()))){
					competitionScore.setPosition(score.getPosition());
				} else {
					competitionScore.setPosition(position);
					position++;
				}
				competitionScore.refresh();
				score = competitionScore;
				getDao().update(competitionScore);
			}
		}
	}

	public void updatePositionsPredictions(Round round) {
		updatePositionsPredictions(round, new CalculateRealScoreState(getDao()));
	}
	
	public void updatePositionsPredictions(Round round, CalculateScoreState calculateScoreState) {
		List<PredictionScore> predictions = (List<PredictionScore>) calculateScoreState.getPredictionScoreForRound(round);
		if (predictions!=null){
			Collections.sort(predictions, new Comparator<PredictionScore>() {
				@Override
				public int compare(PredictionScore o1, PredictionScore o2) {
					return getAccumulated(o1).compareTo(getAccumulated(o2));
				}
			});
			
			Iterator<PredictionScore> it = predictions.iterator();
			PredictionScore score = null;
			Integer position = 1;
			while (it.hasNext()) {
				PredictionScore predictionScore = (PredictionScore) it.next();
				if ((score!=null)&&(score.getAccumulated().equals(predictionScore.getAccumulated()))){
					predictionScore.setPosition(score.getPosition());
				} else {
					predictionScore.setPosition(position);
					position++;
				}
				score = predictionScore;
				getDao().update(predictionScore);
			}
		}
	}

	private Integer getAccumulated(PredictionScore score) {
		Integer scoreValue = 0;
		if ((score!=null) && (score.getAccumulated()!=null)){
			scoreValue = score.getAccumulated();
		}
		return scoreValue;
	}

	public void deleteSupposeCompetitionScores(){
		getDao().deleteSupposeScores((Player) SessionUtils.getActualUser());
		getDao().deleteSupposePredictionScores((Player) SessionUtils.getActualUser());
		getDao().deleteSupposeCompetitionScores((Player) SessionUtils.getActualUser());
	}
	
	public void processSupposeScoresForRound(Round round) {
		processScoresForRound(round, new CalculateSupposeScoreState(getDao()));
	}
	
	public void processScoresForRound(Round round) {
		processScoresForRound(round, new CalculateRealScoreState(getDao()));
	}
	
	public void processScoresForRound(Round round, CalculateScoreState calculateScoreState) {
		Map<Long, Game> games = getGamesMap(round);
		
		// Guardamos los encuentros por si se agrego alguno adicional o se actualizo alguno existente. Solo si corresponde
		calculateScoreState.saveAddedGames(games);
		
		//Obtenemos las predicciones para la ronda actual
		round = (Round) getEntity(round);
		Collection<Prediction> predictions = getDao().getPredictionForRound(round);

		if (predictions!=null){
			Iterator<Prediction> it = predictions.iterator();		
			while (it.hasNext()) {
				Prediction prediction = (Prediction) it.next();
				Player player = prediction.getPlayer();

				Competition competition = round.getCompetition();
				competition = (Competition) getEntity(competition);
				
				PredictionScore predictionScore = calculateScoreState.getPredictionScore(prediction);
				CompetitionScore competitionScore = calculateScoreState.getCompetitionScore(player, competition);
				
				competitionScore.setCompetition(competition);
				competitionScore.setPlayer(player);
				
				predictionScore.setCompetitionScore(competitionScore);
				
				Set<Bet> bets = prediction.getBets();
				if (bets!=null){

					predictionScore.initializeValue();
					
					getDao().saveOrUpdate(competitionScore);
					getDao().saveOrUpdate(predictionScore);

					calculateScores(games, predictionScore, bets, calculateScoreState);

					competitionScore.addScore(predictionScore);
					
					getDao().saveOrUpdate(competitionScore);
					getDao().saveOrUpdate(predictionScore);
				}
			}
		}
	}

	public void calculateScores(Map<Long, Game> games, PredictionScore predictionScore, Set<Bet> bets, CalculateScoreState calculateScoreState) {
		Iterator<Bet> itBets = bets.iterator();
		while (itBets.hasNext()) {
			Bet bet = (Bet) itBets.next();
			Game game = games.get(bet.getGame().getId());
			
			if (game.withResult()){
				Score score = calculateScoreState.newScore(bet);
				
				score = evaluatePrevious(score);
				
				score.setBet(bet);
				score.setValue(getValue(bet, game));
				score.setPredictionScore(predictionScore);
				
				predictionScore.addScore(score);
				
				getDao().saveOrUpdate(score);
			}
		}
	}

	public Score evaluatePrevious(Score score) {
		Score previous = (Score) this.getEntity(score);
		if (previous!=null){
			score = previous;
		}
		return score;
	}

	/**
	 * Retorna el puntaje obtenido
	 * @param bet
	 * @param game
	 * @param value
	 * @return
	 */
	public Integer getValue(Bet bet, Game game) {
		if (game.avaiableResult() && bet.avaiableResult()){
			// Si acierta el resultado son 3 puntos
			if ((game.getLocalGoals().intValue()==bet.getLocalGoals().intValue()) && 
						(game.getVisitorGoals().intValue()==bet.getVisitorGoals().intValue())){
				return 3 * getHardBetFactor(bet);
			}
			
			// Si acierta la diferencia de gol son 2 puntos. Este caso no se evalua si el resultado es un empate
			if ((game.getGoalDiff().intValue()!=0)&&(game.getGoalDiff().intValue()==bet.getGoalDiff().intValue())){
				return 2 * getHardBetFactor(bet);
			}
			
			// Si acierta pronostico (local, empate o visitante) es 1 punto
			if (game.getGameState().equals(bet.getGameState())){
				return 1 * getHardBetFactor(bet);
			}
		}
		return 0;
	}
	
	public Integer getHardBetFactor(Bet bet){
		if (bet.getHardBet()){
			return 2;
		}
		return 1;
	}

	public Map<Long, Game> getGamesMap(Round round) {
		Map<Long, Game> games = new HashMap<Long, Game>();
		Collection<Game> roundGames = round.getGames();
		if (roundGames!=null){
			Iterator<Game> itGames = roundGames.iterator();
			while (itGames.hasNext()) {
				Game game = (Game) itGames.next();
				games.put(game.getId(), game);
			}
		}
		return games;
	}
	
	public Collection<Score> getScoresForCompetition(Competition competition){
		return getDao().getScoresForCompetition(competition);
	}

	/**
	 * Retorna los jugadores para los cuales no exista pronostico para la fecha pasada por parametro
	 * @param round
	 * @return
	 */
	public Collection<Player> getPlayersWithoutPredictionForRound(Round round){
		LoadArguments args = new LoadArguments(Player.class);
		args.addInnerJoin("competitionScores");
		args.addInnerJoin("predictions");
		args.addWhereEqual("competitionScores.competition.id", round.getCompetition().getId());
		
		LoadArguments argsSub = args.addWhereNotInSubquery("predictions.round.id", "id", Player.class);
		argsSub.addInnerJoin("predictions");
		argsSub.addWhereEqual("predictions.round.id", round.getId());
		
		return getDao().load(args);
	}
	
	/**
	 * Retorna todos los jugadores de una determinada competicion
	 * @param competition
	 * @return
	 */
	public Collection<Player> getPlayersInCompetition(Competition competition){
		LoadArguments args = new LoadArguments(Player.class);
		args.addInnerJoin("competitionScores");
		args.addWhereEqual("competitionScores.competition.id", competition.getId());
		args.addAscOrder("name").addAscOrder("surname");
		return getDao().load(args);
	}
	
	/**
	 * Procesa y envia alertas a los usuarios que aun no han pronosticado una determinada fecha
	 */
	public void processAlerts(){
		Date now = new Date();
		Collection<Round> rounds = getDao().getRoundsOnRange(now, DateUtils.add(now, Calendar.DATE, new Integer(appConfigData.getProperty(REPORT_ALERTS_PREVIOUS_DATE))));
		if (rounds!=null){
			Iterator<Round> it = rounds.iterator();
			while (it.hasNext()) {
				Round round = (Round) it.next();
				Collection<Player> players = getDao().getPlayersWithoutPredictionForRound(round);
				if (players!=null){
					Iterator<Player> itPlayer = players.iterator();
					while (itPlayer.hasNext()) {
						Player player = (Player) itPlayer.next();
						sendPredictionAlert(AppManager.getInstance().getAppURL(), player, round);
					}
				}
			}
		}
	}
	
	
	protected void sendPredictionAlert(String link, Player player, Round round) {
		try {
			Report report = new HTML(appConfigData);
			report.addParameter("round", round.getName());
			report.addParameter("name", player.getName());
			report.addParameter("surname", player.getSurname());
			report.addParameter("link", link);
			report.addParameter("dueDate", DateUtils.format(round.getStart(), appConfigData.getProperty(REPORT_ALERT_DATE_FORMAT)));
			ByteArrayOutputStream outputStream = report.getOutputStream(appConfigData.getProperty(REPORT_TEMPLATE_ALERTS));
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File("c://tmp/" + round.getName() + player.getAppPlayerName() + ".html"));
			fileOutputStream.write(outputStream.toByteArray());
			fileOutputStream.close();
			
			Collection<String> emails = new ArrayList<String>();
			emails.add(player.getEmail());
			
			AppManager.getInstance().getMailSender().send(new String(outputStream.toByteArray()), 
												MailSender.HTML_CONTENT_TYPE, 
												"No pronosticaste la " + round.getName() + "!!!", 
												appConfigData.decryptedMailUser(), 
												emails);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	public List<CompetitionScore> getOrdererScores(Competition competition){
		LoadArguments args = new LoadArguments(RealCompetitionScore.class);
		args.addProjectedCollection("scores").addProjectedProperties("scores.prediction.round").addProjectedProperties("player");
		args.addWhereEqual("competition.id", competition.getId());
		args.addDescOrder("value");
		return (List<CompetitionScore>) getDao().load(args);
	}

}
