package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.school.messages.PayData;
import ar.com.larreta.school.persistence.Payment;
import ar.com.larreta.stepper.aspects.AfterCallStep;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;

@Service(PayObligationBusiness.BUSINESS_NAME)
@Transactional
public class PayObligationBusinessImpl extends CreateBusinessImpl<PayData, Payment> implements PayObligationBusiness {

	@AfterCallStep(steps={CreateObligationStatusBusiness.BUSINESS_NAME})
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		return super.execute(source, target, args);
	}
}
