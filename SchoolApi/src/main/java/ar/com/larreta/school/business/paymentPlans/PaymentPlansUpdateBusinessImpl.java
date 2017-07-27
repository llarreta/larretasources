package ar.com.larreta.school.business.paymentPlans;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.UpdatePaymentPlansBody;
import ar.com.larreta.school.persistence.PaymentPlan;
import ar.com.larreta.stepper.impl.UpdateBusinessImpl;

@Service(PaymentPlansUpdateBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansUpdateBusinessImpl extends UpdateBusinessImpl<UpdatePaymentPlansBody, PaymentPlan> implements PaymentPlansUpdateBusiness {
	/*@Override
	@Autowired @Qualifier(PaymentPlansBusinessConfig.PAYMENT_PLANS_BEFORE_PERSIST_LISTENERS)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}*/
}
