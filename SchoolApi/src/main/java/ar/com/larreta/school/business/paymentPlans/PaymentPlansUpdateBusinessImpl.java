package ar.com.larreta.school.business.paymentPlans;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.UpdateBusinessImpl;
import ar.com.larreta.school.messages.UpdatePaymentPlansBody;
import ar.com.larreta.school.persistence.PaymentPlan;

@Service(PaymentPlansUpdateBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansUpdateBusinessImpl extends UpdateBusinessImpl<UpdatePaymentPlansBody, PaymentPlan> implements PaymentPlansUpdateBusiness {

	@Override
	@Autowired @Qualifier(PaymentPlansBusinessConfig.PAYMENT_PLANS_BEFORE_CREATE)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}
	
}
