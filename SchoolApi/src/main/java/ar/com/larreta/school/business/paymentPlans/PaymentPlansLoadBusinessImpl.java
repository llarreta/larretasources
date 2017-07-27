package ar.com.larreta.school.business.paymentPlans;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.PaymentPlansData;
import ar.com.larreta.school.persistence.PaymentPlan;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;

@Service(PaymentPlansLoadBusiness.BUSINESS_NAME)
@Transactional
public class PaymentPlansLoadBusinessImpl extends LoadBusinessImpl<PaymentPlansData, PaymentPlan> implements PaymentPlansLoadBusiness {

}
