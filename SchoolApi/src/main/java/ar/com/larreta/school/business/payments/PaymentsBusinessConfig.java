package ar.com.larreta.school.business.payments;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ar.com.larreta.persistence.model.Person;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.BusinessConfig;
import ar.com.larreta.rest.business.impl.EntityAsignBusinessListener;
import ar.com.larreta.rest.business.impl.IteratorListener;
import ar.com.larreta.school.business.paymentPlans.PaymentPlansBusinessConfig;
import ar.com.larreta.school.persistence.PaymentUnit;

@Configuration
public class PaymentsBusinessConfig extends BusinessConfig {

	public static final String PAYMENT_UNIT_BEFORE_PERIST_LISTENERS = "paymentUnitBeforePeristListeners";

	public static final String PAYMENT_BEFORE_PERSIT_LISTENERS = "PAYMENT_BEFORE_PERSIT_LISTENERS";

	public static final String PERSON_BENEFITING = "personBenefiting";

	public static final String ASIGN_PERSON_LISTENER = "asignPersonListener";

	public static final String PAY_UNITS = "payUnits";

	public static final String PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE = "paymentUnitsListenerFrontToService";

	private EntityAsignBusinessListener<Person> 	  asignPersonListener;
	
	private IteratorListener<PaymentUnit> paymentUnitsListenerServiceToFront;

	@Bean(name=PAYMENT_BEFORE_PERSIT_LISTENERS) @DependsOn(value={PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> paymentBeforePersistListeners(){
		return getSet(paymentUnitsListenerServiceToFront);
	}
	
	@Bean(name=PAYMENT_UNIT_BEFORE_PERIST_LISTENERS) @DependsOn(value={ASIGN_PERSON_LISTENER})
	public Set<BusinessListener> paymentUnitBeforePeristListeners(){
		return getSet(asignPersonListener);
	}
	
	@Bean(name=ASIGN_PERSON_LISTENER)
	public EntityAsignBusinessListener<Person> asignPersonListener(){
		asignPersonListener = new EntityAsignBusinessListener<Person>() {
			@Override
			public String getSourceProperty() {
				return PERSON_BENEFITING;
			}

			@Override
			public String getTargetProperty() {
				return PERSON_BENEFITING;
			}
		};
		return asignPersonListener;
	}
	
	@Bean(name=PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<PaymentUnit> paymentUnitsListenerFrontToService(){
		paymentUnitsListenerServiceToFront =  new IteratorListener<PaymentUnit>(PAY_UNITS) {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.OBLIGATIONS_BEFORE_PERSIST_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}
		};
		return paymentUnitsListenerServiceToFront;
	}
	
}
