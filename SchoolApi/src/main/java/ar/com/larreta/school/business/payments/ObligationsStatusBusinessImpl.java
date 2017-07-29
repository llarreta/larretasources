package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.school.messages.ObligationStatusData;
import ar.com.larreta.school.persistence.ObligationStatus;
import ar.com.larreta.stepper.aspects.BeforeCallStep;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.CleanLimitsResultsBusinessListener;
import ar.com.larreta.stepper.impl.FirstResultBusinessListener;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;
import ar.com.larreta.stepper.impl.MaxResultsBusinessListener;

@Service(ObligationsStatusBusiness.BUSINESS_NAME)
@Transactional
public class ObligationsStatusBusinessImpl extends LoadBusinessImpl<ObligationStatusData, ObligationStatus> implements ObligationsStatusBusiness {

	
	@BeforeCallStep(steps={	CleanLimitsResultsBusinessListener.BUSINESS_LISTENER_NAME, 
							MaxResultsBusinessListener.BUSINESS_LISTENER_NAME, 
							FirstResultBusinessListener.BUSINESS_LISTENER_NAME})
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		return super.execute(source, target, args);
	}
	
}
