package ar.com.larreta.school.business.paymentPlans;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.school.messages.PaymentPlansData;
import ar.com.larreta.school.persistence.PaymentPlan;

@Service(PaymentPlansLoadBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansLoadBusinessImpl extends LoadBusinessImpl<PaymentPlansData, PaymentPlan> implements PaymentPlansLoadBusiness {

	
	
	@Override
	@Autowired @Qualifier(PaymentPlansBusinessConfig.PAYMENT_PLANS_BEFORE_LOAD_LISTENERS)
	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		super.setBeforeLoadListeners(beforeLoadListeners);
	}

	@Override
	@Autowired @Qualifier(PaymentPlansBusinessConfig.PAYMENT_PLANS_AFTER_LOAD_LISTENERS)
	public void setAfterLoadListeners(Set<BusinessListener> afterLoadListeners) {
		super.setAfterLoadListeners(afterLoadListeners);
	}
}
