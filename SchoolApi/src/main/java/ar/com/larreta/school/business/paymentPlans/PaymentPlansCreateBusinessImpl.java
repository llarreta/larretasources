package ar.com.larreta.school.business.paymentPlans;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.UpdatePaymentPlansBody;
import ar.com.larreta.school.persistence.PaymentPlan;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;

@Service(PaymentPlansCreateBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansCreateBusinessImpl extends CreateBusinessImpl<UpdatePaymentPlansBody, PaymentPlan> implements PaymentPlansCreateBusiness {

}
