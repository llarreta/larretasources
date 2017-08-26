package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.LoadBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;

@Component(CleanLimitsResultsBusinessListener.BUSINESS_LISTENER_NAME)
public class CleanLimitsResultsBusinessListener extends StepImpl {

	public static final String BUSINESS_LISTENER_NAME = "CleanLimitsResultsBusinessListener";
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		LoadBusiness loadBusiness = (LoadBusiness) source;
		loadBusiness.getSelect().cleanResultsLimits();
		return null;
	}

}
