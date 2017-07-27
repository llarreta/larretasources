package ar.com.larreta.school.business.paymentPlans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.LoadBusiness;
import ar.com.larreta.stepper.impl.StepImpl;

@Component(PaymentPlansAddProjectionsBusinessImpl.BUSINESS_LISTENER_NAME)
public class PaymentPlansAddProjectionsBusinessImpl extends StepImpl {
	public static final String BUSINESS_LISTENER_NAME = "PaymentPlansAddProjectionsBusinessImpl";
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		LoadBusiness loadBusiness = (LoadBusiness) source;
		loadBusiness.getSelect().cleanResultsLimits();
		
		Select select = loadBusiness.getSelect();
		select.addProjections("obligations");
		
		return null;
	}

}
