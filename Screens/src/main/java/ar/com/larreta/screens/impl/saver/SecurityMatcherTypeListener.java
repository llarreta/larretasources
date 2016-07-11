package ar.com.larreta.screens.impl.saver;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.AuthenticatedSecurityMatcher;
import ar.com.larreta.commons.domain.PermitAllSecurityMatcher;
import ar.com.larreta.commons.domain.RolesSecurityMatcher;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.screens.ComboBox;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.controllers.ScreensControllerImpl;
import ar.com.larreta.screens.impl.ComboBoxListener;

public class SecurityMatcherTypeListener implements ComboBoxListener {

	public void process(FacesEvent facesEvent, StandardController controller, ComboBox comboBox) {
		ScreensControllerImpl screensControllerImpl = (ScreensControllerImpl) controller;
		Screen screen = screensControllerImpl.getScreen();
		
		AjaxBehaviorEvent event = (AjaxBehaviorEvent) facesEvent;
		
		SecurityMatcher old = (SecurityMatcher) controller.getDataView().getSelected();
		SecurityMatcher newSecurityMatcher = null;
		
		String value = old.getType();
		
		PanelGrid panelPermitAll = (PanelGrid) screen.getSearchMap().recursiveFind(new Long((String)ScreenUtils.getEventAttribute(facesEvent, SecurityMatcherSaver.PANEL_PERMIT_ALL_ID)));
		PanelGrid panelRoles = (PanelGrid) screen.getSearchMap().recursiveFind(new Long((String)ScreenUtils.getEventAttribute(facesEvent, SecurityMatcherSaver.PANEL_ROLES_ID)));
		PanelGrid panelAuthenticated = (PanelGrid) screen.getSearchMap().recursiveFind(new Long((String)ScreenUtils.getEventAttribute(facesEvent, SecurityMatcherSaver.PANEL_AUTHENTICATED_ID)));
		
		panelPermitAll.getSearchMap().recursiveFind(Label.class);
		panelRoles.getSearchMap().recursiveFind(Label.class);
		panelAuthenticated.getSearchMap().recursiveFind(Label.class);
		
		panelPermitAll.setRendered(Boolean.FALSE);
		panelRoles.setRendered(Boolean.FALSE);
		panelAuthenticated.setRendered(Boolean.FALSE);
		
		if (PermitAllSecurityMatcher.PERMIT_ALL.equals(value)){
			newSecurityMatcher = new PermitAllSecurityMatcher();
			panelPermitAll.setRendered(Boolean.TRUE);
		}
		
		if (RolesSecurityMatcher.ROLES.equals(value)){
			newSecurityMatcher = new RolesSecurityMatcher();
			panelRoles.setRendered(Boolean.TRUE);
		}
		
		if (AuthenticatedSecurityMatcher.AUTHENTICATED.equals(value)){
			newSecurityMatcher = new AuthenticatedSecurityMatcher();
			panelAuthenticated.setRendered(Boolean.TRUE);
		}
		
		if (old!=null){
			newSecurityMatcher.setId(old.getId());
			newSecurityMatcher.setSecurity(old.getSecurity());
			newSecurityMatcher.setPattern(old.getPattern());
		}
		
		controller.getDataView().setSelected(newSecurityMatcher);
	}

}