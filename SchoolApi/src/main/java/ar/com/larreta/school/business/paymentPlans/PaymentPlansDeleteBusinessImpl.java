package ar.com.larreta.school.business.paymentPlans;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.PaymentPlan;
import ar.com.larreta.stepper.impl.DeleteBusinessImpl;

@Service(PaymentPlansDeleteBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansDeleteBusinessImpl extends DeleteBusinessImpl<PaymentPlan> implements PaymentPlansDeleteBusiness {

}
