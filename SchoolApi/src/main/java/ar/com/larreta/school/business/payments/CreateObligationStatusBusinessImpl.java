package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.PayData;
import ar.com.larreta.school.persistence.ObligationStatus;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;

@Service(CreateObligationStatusBusiness.BUSINESS_NAME)
@Transactional
public class CreateObligationStatusBusinessImpl extends CreateBusinessImpl<PayData, ObligationStatus>
		implements CreateObligationStatusBusiness {

	@Override
	protected Serializable getSource(Serializable source, Serializable target, Object... args) {
		PayData payData = (PayData) args[0];
		return payData.getPaymentUnits().iterator().next();
	}

	
}
