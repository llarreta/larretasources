package ar.com.larreta.prode.controllers;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.controllers.SessionInitializer;
import ar.com.larreta.commons.utils.SessionData;
import ar.com.larreta.commons.utils.SessionUtils;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.Player;
import ar.com.larreta.prode.services.CompetitionService;

@Component
public class ProdeInitializer extends AppObjectImpl implements SessionInitializer {
	
	@Autowired
	private CompetitionService competitionService;
	
	@Override
	public void execute() {
		SessionData sessionData = AppManager.getInstance().getSessionData();
		getLog().debug("inicializando prode");
		if (SessionUtils.getActualUser() instanceof Player) {
			Collection<Competition> competitions = competitionService.getAvaiableRegisterCompetitions((Player) SessionUtils.getActualUser());
			if (competitions!=null){
				Iterator<Competition> it = competitions.iterator();
				while (it.hasNext()) {
					Competition competition = (Competition) it.next();
					CompetitionRegisterNotification notification = (CompetitionRegisterNotification) AppManager.getInstance().getAppContext().getBean(CompetitionRegisterNotification.NOTIFICATION_NAME);
					notification.setCompetition(competition);
					sessionData.add(notification);
				}
			}
		}
	}



}
