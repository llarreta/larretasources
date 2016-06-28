package ar.com.larreta.screens.impl.saver;

import java.util.HashSet;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.RolesSecurityMatcher;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.screens.impl.ScreenListener;

public class SecurityMatcherUpdateListener extends AppObjectImpl implements ScreenListener {

	public void execute(RequestContext flowRequestContext, StandardController controller, Entity entity) {
		SecurityMatcher securityMatcher = (SecurityMatcher) controller.getDataView().getSelected();
		try {
			SecurityMatcher old = (SecurityMatcher) controller.getService().getEntity(SecurityMatcher.class, securityMatcher.getId());
			if (old instanceof RolesSecurityMatcher) {
				RolesSecurityMatcher roleSecurityMatcher = (RolesSecurityMatcher) old;
				roleSecurityMatcher.setRoles(new HashSet());
				controller.getService().update(roleSecurityMatcher);
			}
			controller.getService().delete(old);
			controller.getService().save(securityMatcher);
		} catch (NotServiceAssignedException e) {
			getLog().error("Ocurrio un error en SecurityMatcherUpdateListener", e);
		}
		
	}

}
