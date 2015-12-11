/**
 * 
 */
package ar.com.larreta.prode.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.faces.event.ActionEvent;

import org.bouncycastle.asn1.cmp.CMPCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.Game;
import ar.com.larreta.prode.domain.Round;
import ar.com.larreta.prode.domain.Team;
import ar.com.larreta.prode.services.CompetitionService;
import ar.com.larreta.prode.services.TeamService;
import ar.com.larreta.prode.views.CompetitionDataView;

public class CompetitionController extends StandardControllerImpl{
	
	private static final String ROUND_SELECTED = "roundSelected";
	
	@Autowired
	private TeamService teamService;
	
	public CompetitionService getService() throws NotServiceAssignedException {
		return (CompetitionService) super.getService();
	}

	@Autowired
	public void setService(CompetitionService standardService) {
		super.setService(standardService);
	}
	
	public TeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public void addRound(ActionEvent actionEvent){
		Round round = new Round();
		CompetitionDataView dataView = (CompetitionDataView) getDataView();
		dataView.getRounds().add(round);
		round.setName("Fecha " + dataView.getRounds().size());
	}
	
	public void addGame(ActionEvent actionEvent){
		Round round = (Round) getEventAttribute(actionEvent, ROUND_SELECTED);
		Game game = new Game();
		game.setRound(round);
		round.addGame(game);
	}	
	
	public Collection<Team> getAvaiablesTeams(){
		try {
			return getTeamService().load();
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return new ArrayList<Team>();
	}
	
	@Override
	public void preCreate(RequestContext flowRequestContext) {
		super.preCreate(flowRequestContext);
		Competition competition = new Competition();
		setCompetitionParams(competition, flowRequestContext);
		getDataView().setSelected(competition);
	}

	@Override
	public void preUpdate(RequestContext flowRequestContext) {
		super.preUpdate(flowRequestContext);
		Competition competition = (Competition) getDataView().getSelected();
		setCompetitionParams(competition, flowRequestContext);
	}
	
	public void setCompetitionParams(Competition competition, RequestContext flowRequestContext) {
		CompetitionDataView competitionDataView = (CompetitionDataView) getDataView();
		competition.setName(competitionDataView.getName());
		competition.setRegistrationStart(competitionDataView.getRegistrationStart());
		competition.setRegistrationEnd(competitionDataView.getRegistrationEnd());
		competition.setRounds(competitionDataView.getRounds());
	}	

	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		CompetitionDataView dataView = (CompetitionDataView) getDataView();
		Competition competition = (Competition) dataView.getSelected();
		dataView.setName(competition.getName());
		dataView.setRegistrationStart(competition.getRegistrationStart());
		dataView.setRegistrationEnd(competition.getRegistrationEnd());
		
		List<Round> rounds;
		try {
			rounds = (List<Round>) getService().getRoundsForCompetition(competition);
			dataView.setRounds(rounds);
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		} 
		
		
		dataView.setSelected(competition);
	}

	public void processRound(ActionEvent actionEvent) {
		Long execStat = statisticsStart("processRound"); 
		try {
			Round round = (Round) getEventAttribute(actionEvent, ROUND_SELECTED);	
			getService().processRound(round);
			getService().updatePositions(round);
			getService().updatePositionsPredictions(round);
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		} finally {
			statisticsStop(execStat);
		}
	}
	
	public void autoResult(ActionEvent actionEvent){
		getLog().info("autoResult");
		
		Round round = (Round) getEventAttribute(actionEvent, ROUND_SELECTED);	
		Integer min=0;
		Integer max=3;
		
		Set<Game> games = round.getGames();
		Iterator<Game> itGames = games.iterator();
		Integer betIndex = 0;
		while (itGames.hasNext()) {
			betIndex++;
			Game game = (Game) itGames.next();
			game.setLocalGoals(randInt(min, max));
			game.setVisitorGoals(randInt(min, max));
		}
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
	
}
