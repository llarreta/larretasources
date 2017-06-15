package ar.com.larreta.school.business.paymentPlans;

import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.persistence.Detail;

@Component
public class PricesBeforePersistListener extends PaymentPlansBeforePersistListenerTemplate {
	
	private static @Log Logger LOG;	
	
	@Autowired @Qualifier(PaymentPlansBusinessConfig.DETAILS_BEFORE_CREATE)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);;
	}
	
	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException {
		return processGeneral("details", Detail.class, json, entity, args);
	}
}
