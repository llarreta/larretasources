package ar.com.larreta.school.business.paymentPlans;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.DeleteBusinessImpl;
import ar.com.larreta.school.persistence.PaymentPlan;

@Service(PaymentPlansDeleteBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansDeleteBusinessImpl extends DeleteBusinessImpl<PaymentPlan> implements PaymentPlansDeleteBusiness {

}
