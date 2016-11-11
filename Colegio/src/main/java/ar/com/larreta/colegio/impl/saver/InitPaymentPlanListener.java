package ar.com.larreta.colegio.impl.saver;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.screens.impl.ScreenListener;

public class InitPaymentPlanListener extends AppObjectImpl implements ScreenListener {

	@Override
	public void execute(RequestContext paramRequestContext, StandardController paramStandardController,
			Entity paramEntity) {
	}

}
