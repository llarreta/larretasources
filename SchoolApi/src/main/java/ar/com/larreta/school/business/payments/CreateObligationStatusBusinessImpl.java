package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.messages.PayData;
import ar.com.larreta.school.persistence.ObligationStatus;

@Service(CreateObligationStatusBusiness.BUSINESS_NAME)
@Transactional
public class CreateObligationStatusBusinessImpl extends CreateBusinessImpl<PayData, ObligationStatus>
		implements CreateObligationStatusBusiness {

	@Override
	public Serializable execute(Serializable input) throws Exception {
		return super.execute(input);
	}


}
