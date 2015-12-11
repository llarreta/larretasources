package ar.com.larreta.prode.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.domain.Notification;
import ar.com.larreta.commons.utils.SessionData;
import ar.com.larreta.commons.utils.SessionUtils;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.CompetitionScore;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.domain.RealCompetitionScore;
import ar.com.larreta.prode.services.CompetitionService;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class CompetitionRegisterNotification extends Notification {

	public static final String NOTIFICATION_NAME = "competitionRegisterNotification";
	
	private Competition competition;
	
	@Autowired
	private CompetitionService competitionService;
	
	@Autowired
	protected SessionData sessionData;
	
	public void setCompetition(Competition competition){
		this.competition = competition;
		setHeader(StandardControllerImpl.getMessage("prode.register.competition.header"));
		setDescription(StandardControllerImpl.getMessage("prode.register.competition.content") + competition.getName());
	}
	
	@Override
	public void execute() {
		if (SessionUtils.getActualUser() instanceof Player) {
			Player player = (Player) SessionUtils.getActualUser();
			player = (Player) competitionService.getEntity(player);
			CompetitionScore competitionScore = new RealCompetitionScore();
			competitionScore.setPlayer(player);
			competitionScore.setCompetition(competition);
			player.addParticipateOf(competitionScore);
			competitionService.update(player);
			sessionData.remove(this);
		}
	}

}
