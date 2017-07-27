package ar.com.larreta.school.business.payments;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.ObligationStatusData;
import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;

@Service(ObligationsStatusBusiness.BUSINESS_NAME)
@Transactional
public class ObligationsStatusBusinessImpl extends LoadBusinessImpl<ObligationStatusData, Obligation> implements ObligationsStatusBusiness {
	
/*	@Override
	@Autowired @Qualifier(PaymentsBusinessConfig.OBLIGATION_STATUS_AFTER_LOAD_LISTENERS)
	public void setAfterLoadListeners(Set<BusinessListener> afterLoadListeners) {
		super.setAfterLoadListeners(afterLoadListeners);
	}

	@Override
	@Autowired @Qualifier(PaymentsBusinessConfig.OBLIGATION_STATUS_BEFORE_LOAD_LISTENERS)
	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		super.setBeforeLoadListeners(beforeLoadListeners);
	}
*/
}
