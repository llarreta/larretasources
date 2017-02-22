package ar.com.larreta.screens.impl.saver;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.AuthenticatedSecurityMatcher;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.PermitAllSecurityMatcher;
import ar.com.larreta.commons.domain.RolesSecurityMatcher;
import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.screens.Attribute;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.controllers.ScreensControllerImpl;
import ar.com.larreta.screens.impl.ScreenListener;

public class InitSecurityMatcherTypeListener extends AppObjectImpl implements ScreenListener {

	public void execute(RequestContext flowRequestContext, StandardController controller, Entity entity) {
		try {
			ScreensControllerImpl screensControllerImpl = (ScreensControllerImpl) controller;
			SecurityMatcher securityMatcher = (SecurityMatcher) controller.getDataView().getSelected();
			Screen screen = screensControllerImpl.getScreen();
			
			String value = null;
			
			if (securityMatcher!=null){
				Collection securities = controller.getService().load(Security.class);
				securityMatcher.setSecurity((Security) securities.iterator().next());
				value = securityMatcher.getSecurityMatcherType();
			} else {
				SecurityMatcher aux = new AuthenticatedSecurityMatcher();
				aux.setSecurityMatcherType(StringUtils.EMPTY);
				controller.getDataView().setSelected(aux);
			}
			
			PanelGrid panelPermitAll = null;
			PanelGrid panelRoles = null;
			PanelGrid panelAuthenticated = null;
			
			Collection<ScreenElement> attributes = screen.getSearchMap().recursiveFind(Attribute.class);
			Iterator<ScreenElement> it = attributes.iterator();
			while (it.hasNext()) {
				Attribute screenElement = (Attribute) it.next();
				if (screenElement instanceof Attribute) {
					Attribute attribute = (Attribute) screenElement;
					
					if (SecurityMatcherSaver.PANEL_PERMIT_ALL_ID.equals(attribute.getName())){
						panelPermitAll = (PanelGrid) screen.getSearchMap().recursiveFind(new Long(attribute.getValue()));
					}
					if (SecurityMatcherSaver.PANEL_ROLES_ID.equals(attribute.getName())){
						panelRoles = (PanelGrid) screen.getSearchMap().recursiveFind(new Long(attribute.getValue()));
					}
					if (SecurityMatcherSaver.PANEL_AUTHENTICATED_ID.equals(attribute.getName())){
						panelAuthenticated = (PanelGrid) screen.getSearchMap().recursiveFind(new Long(attribute.getValue()));
					}
				}
			}
			
			panelPermitAll.setRendered(Boolean.FALSE);
			panelRoles.setRendered(Boolean.FALSE);
			panelAuthenticated.setRendered(Boolean.FALSE);
			
			if (PermitAllSecurityMatcher.PERMIT_ALL.equals(value)){
				panelPermitAll.setRendered(Boolean.TRUE);
			}
			
			if (RolesSecurityMatcher.ROLES.equals(value)){
				panelRoles.setRendered(Boolean.TRUE);
			}
			
			if (AuthenticatedSecurityMatcher.AUTHENTICATED.equals(value)){
				panelAuthenticated.setRendered(Boolean.TRUE);
			}
		} catch (Exception e){
			getLog().error(e);
		}

	}

}
