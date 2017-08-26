package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.State;
import ar.com.larreta.stepper.StatesLoadBusiness;
import ar.com.larreta.stepper.aspects.BeforeCallStep;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.RelatedBody;

@Service(StatesLoadBusiness.BUSINESS_NAME)
@Transactional
public class StatesLoadBusinessImpl extends LoadBusinessImpl<RelatedBody, State> implements StatesLoadBusiness {

	@BeforeCallStep(steps={	CleanLimitsResultsBusinessListener.BUSINESS_LISTENER_NAME, 
			MaxResultsBusinessListener.BUSINESS_LISTENER_NAME, 
			FirstResultBusinessListener.BUSINESS_LISTENER_NAME,
			RelatedBusinessImpl.NAME})
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		return super.execute(source, target, args);
	}

	@Override
	public String getRelatedProperty() {
		return "country.id";
	}

}
