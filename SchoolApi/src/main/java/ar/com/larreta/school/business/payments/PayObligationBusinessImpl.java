package ar.com.larreta.school.business.payments;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.messages.PayData;
import ar.com.larreta.school.persistence.Payment;

@Service(PayObligationBusiness.BUSINESS_NAME)
@Transactional
public class PayObligationBusinessImpl extends CreateBusinessImpl<PayData, Payment> implements PayObligationBusiness {

	@Override
	@Autowired @Qualifier(PaymentsBusinessConfig.PAYMENTS_AFTER_PERSIST_LISTENER)
	public void setAfterPersistListeners(Set<BusinessListener> afterPersistListeners) {
		super.setAfterPersistListeners(afterPersistListeners);
	}

	@Override
	@Autowired @Qualifier(PaymentsBusinessConfig.PAYMENT_BEFORE_PERSIST_LISTENERS)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}

}
