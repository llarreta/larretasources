package ar.com.larreta.school.business.payments;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.messages.PayData;
import ar.com.larreta.school.persistence.Payment;

@Service(PayObligationBusiness.BUSINESS_NAME)
@Transactional
public class PayObligationBusinessImpl extends CreateBusinessImpl<PayData, Payment> implements PayObligationBusiness {

}
