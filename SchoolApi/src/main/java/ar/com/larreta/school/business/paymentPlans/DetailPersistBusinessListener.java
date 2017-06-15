package ar.com.larreta.school.business.paymentPlans;

import java.io.Serializable;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.persistence.LittleDetail;

@Component
public class DetailPersistBusinessListener extends PaymentPlansBeforePersistListenerTemplate {

	private static @Log Logger LOG;	

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException {
		return processGeneral("littleDetails", LittleDetail.class, json, entity, args);
	}
}
