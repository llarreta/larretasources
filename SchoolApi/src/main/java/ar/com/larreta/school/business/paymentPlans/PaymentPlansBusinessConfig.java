package ar.com.larreta.school.business.paymentPlans;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.BusinessConfig;
import ar.com.larreta.rest.business.impl.IteratorListener;
import ar.com.larreta.rest.business.impl.LoadArgsAddProjectedCollectionLeftJoinBusinessListener;
import ar.com.larreta.school.messages.DetailData;
import ar.com.larreta.school.messages.LittleDetailData;
import ar.com.larreta.school.messages.ObligationData;
import ar.com.larreta.school.messages.PriceData;
import ar.com.larreta.school.persistence.Detail;
import ar.com.larreta.school.persistence.LittleDetail;
import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.school.persistence.Price;

@Configuration
public class PaymentPlansBusinessConfig extends BusinessConfig{

	public static final String PAYMENT_PLANS_BEFORE_LOAD_LISTENERS 			= "paymentPlansBeforeLoadListeners";

	public static final String OBLIGATIONS_PROJECTED 						= "obligationsProjected";
	
	public static final String DETAILS_BEFORE_LOAD_LISTENERS 				= "detailsBeforeLoadListeners";
	public static final String PRICES_BEFORE_LOAD_LISTENERS 				= "pricesBeforeLoadListeners";
	public static final String OBLIGATIONS_BEFORE_LOAD_LISTENERS 			= "obligationsBeforeLoadListeners";
	public static final String PAYMENT_PLANS_AFTER_LOAD_LISTENERS 			= "paymentPlansAfterLoadListeners";
	
	public static final String LITTLE_DETAILS_LISTENER_SERVICE_TO_FRONT 	= "littleDetailsListenerServiceToFront";
	public static final String DETAILS_LISTENER_SERVICE_TO_FRONT 			= "detailsListenerServiceToFront";
	public static final String PRICES_LISTENER_SERVICE_TO_FRONT 			= "pricesListenerServiceToFront";
	public static final String OBLIGATIONS_LISTENER_SERVICE_TO_FRONT 		= "obligationsListenerServiceToFront";
	
	public static final String DETAILS_BEFORE_PERSIST_LISTENERS 			= "detailsBeforePersistListeners";
	public static final String PRICES_BEFORE_PERSIST_LISTENERS 				= "pricesBeforePersistListeners";
	public static final String OBLIGATIONS_BEFORE_PERSIST_LISTENERS 		= "obligationsBeforePersistListeners";
	public static final String PAYMENT_PLANS_BEFORE_PERSIST_LISTENERS 		= "paymentPlansBeforePersistListeners";
	
	public static final String LITTLE_DETAILS_LISTENER_FRONT_TO_SERVICE 	= "littleDetailsListenerFrontToService";
	public static final String DETAILS_LISTENER_FRONT_TO_SERVICE 			= "detailsListenerFrontToService";
	public static final String PRICES_LISTENER_FRONT_TO_SERVICE 			= "pricesListenerFrontToService";
	public static final String OBLIGATIONS_LISTENER_FRONT_TO_SERVICE 		= "obligationsListenerFrontToService";
	
	public static final String OBLIGATIONS 		= "obligations";
	public static final String PRICES 			= "prices";
	public static final String DETAILS 			= "details";
	public static final String LITTLE_DETAILS 	= "littleDetails";
	
	private IteratorListener<Obligation> obligationsListenerFrontToService;
	private IteratorListener<Price> pricesListenerFrontToService;
	private IteratorListener<Detail> detailsListenerFrontToService;
	private IteratorListener<LittleDetail> littleDetailsListenerFrontToService;

	private IteratorListener<ObligationData> obligationsListenerServiceToFront;
	private IteratorListener<PriceData> pricesListenerServiceToFront;
	private IteratorListener<DetailData> detailsListenerServiceToFront;
	private IteratorListener<LittleDetailData> littleDetailsListenerServiceToFront;
	
	private LoadArgsAddProjectedCollectionLeftJoinBusinessListener obligationsProjected;

	// Services Listeners
	
	@Bean(name=OBLIGATIONS_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<Obligation> obligationsListenerFrontToService(){
		obligationsListenerFrontToService =  new IteratorListener<Obligation>() {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.OBLIGATIONS_BEFORE_PERSIST_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return OBLIGATIONS;
			}

			@Override
			public String getTargetProperty() {
				return OBLIGATIONS;
			}
		};
		return obligationsListenerFrontToService;
	}

	@Bean(name=PRICES_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<Price> pricesListenerFrontToService(){
		pricesListenerFrontToService = new IteratorListener<Price>() {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.PRICES_BEFORE_PERSIST_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return PRICES;
			}

			@Override
			public String getTargetProperty() {
				return PRICES;
			}
		};
		return pricesListenerFrontToService;
	}

	@Bean(name=DETAILS_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<Detail> detailsListenerFrontToService(){
		detailsListenerFrontToService = new IteratorListener<Detail>() {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.DETAILS_BEFORE_PERSIST_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return DETAILS;
			}

			@Override
			public String getTargetProperty() {
				return DETAILS;
			}			
		};
		return detailsListenerFrontToService;
	}

	@Bean(name=LITTLE_DETAILS_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<LittleDetail> littleDetailsListenerFrontToService(){
		littleDetailsListenerFrontToService = new IteratorListener<LittleDetail>() {

			@Override
			public String getSourceProperty() {
				return LITTLE_DETAILS;
			}

			@Override
			public String getTargetProperty() {
				return LITTLE_DETAILS;
			}};
		return littleDetailsListenerFrontToService;
	}
	
	@Bean(name=OBLIGATIONS_LISTENER_SERVICE_TO_FRONT)
	public IteratorListener<ObligationData> obligationsListenerServiceToFront(){
		obligationsListenerServiceToFront = new IteratorListener<ObligationData>() {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.OBLIGATIONS_BEFORE_LOAD_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return OBLIGATIONS;
			}

			@Override
			public String getTargetProperty() {
				return OBLIGATIONS;
			}
		};
		return obligationsListenerServiceToFront;
	}

	@Bean(name=PRICES_LISTENER_SERVICE_TO_FRONT)
	public IteratorListener<PriceData> pricesListenerServiceToFront(){
		pricesListenerServiceToFront = new IteratorListener<PriceData>() {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.PRICES_BEFORE_LOAD_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return PRICES;
			}

			@Override
			public String getTargetProperty() {
				return PRICES;
			}			
		};
		return pricesListenerServiceToFront;
	}
	
	@Bean(name=DETAILS_LISTENER_SERVICE_TO_FRONT)
	public IteratorListener<DetailData> detailsListenerServiceToFront(){
		detailsListenerServiceToFront = new IteratorListener<DetailData>() {
			@Override
			@Autowired @Qualifier(PaymentPlansBusinessConfig.DETAILS_BEFORE_LOAD_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return DETAILS;
			}

			@Override
			public String getTargetProperty() {
				return DETAILS;
			}			
		};
		return detailsListenerServiceToFront;
	}
	
	@Bean(name=LITTLE_DETAILS_LISTENER_SERVICE_TO_FRONT)
	public IteratorListener<LittleDetailData> littleDetailsListenerServiceToFront(){
		littleDetailsListenerServiceToFront = new IteratorListener<LittleDetailData>() {

			@Override
			public String getSourceProperty() {
				return LITTLE_DETAILS;
			}

			@Override
			public String getTargetProperty() {
				return LITTLE_DETAILS;
			}};
		return littleDetailsListenerServiceToFront;
	}

	@Bean(name=OBLIGATIONS_PROJECTED)
	public LoadArgsAddProjectedCollectionLeftJoinBusinessListener obligationsProjected(){
		obligationsProjected = new LoadArgsAddProjectedCollectionLeftJoinBusinessListener() {
			@Override
			public String getProjectedCollection() {
				return OBLIGATIONS;
			}
		};
		return obligationsProjected;
	}
	
	// Persist Listeners
	
	@Bean(name=PAYMENT_PLANS_BEFORE_PERSIST_LISTENERS) @DependsOn(value={OBLIGATIONS_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> paymentPlansBeforePersistListeners(){
		return getSet(obligationsListenerFrontToService);
	}
	
	@Bean(name=OBLIGATIONS_BEFORE_PERSIST_LISTENERS) @DependsOn(value={PRICES_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> obligationsBeforePersistListeners(){
		return getSet(pricesListenerFrontToService);
	}
	
	@Bean(name=PRICES_BEFORE_PERSIST_LISTENERS) @DependsOn(value={DETAILS_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> pricesBeforePersistListeners(){
		return getSet(detailsListenerFrontToService);
	}
	
	@Bean(name=DETAILS_BEFORE_PERSIST_LISTENERS) @DependsOn(value={LITTLE_DETAILS_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> detailsBeforePersistListeners(){
		return getSet(littleDetailsListenerFrontToService);
	}

	// Load Listeners
	
	@Bean(name=PAYMENT_PLANS_AFTER_LOAD_LISTENERS) @DependsOn(value={OBLIGATIONS_LISTENER_SERVICE_TO_FRONT})
	public Set<BusinessListener> paymentPlansAfterLoadListeners(){
		return getSet(obligationsListenerServiceToFront);
	}
	
	@Bean(name=OBLIGATIONS_BEFORE_LOAD_LISTENERS) @DependsOn(value={PRICES_LISTENER_SERVICE_TO_FRONT})
	public Set<BusinessListener> obligationsBeforeLoadListeners(){
		return getSet(pricesListenerServiceToFront);
	}

	@Bean(name=PRICES_BEFORE_LOAD_LISTENERS) @DependsOn(value={DETAILS_LISTENER_SERVICE_TO_FRONT})
	public Set<BusinessListener> pricesBeforeLoadListeners(){
		return getSet(detailsListenerServiceToFront);
	}
	
	@Bean(name=DETAILS_BEFORE_LOAD_LISTENERS) @DependsOn(value={LITTLE_DETAILS_LISTENER_SERVICE_TO_FRONT})
	public Set<BusinessListener> detailsBeforeLoadListeners(){
		return getSet(littleDetailsListenerServiceToFront);
	}

	@Bean(name=PAYMENT_PLANS_BEFORE_LOAD_LISTENERS) @DependsOn(value={OBLIGATIONS_PROJECTED})
	public Set<BusinessListener> paymentPlansBeforeLoadListeners(){
		return getSet(obligationsProjected);
	}
	
}
