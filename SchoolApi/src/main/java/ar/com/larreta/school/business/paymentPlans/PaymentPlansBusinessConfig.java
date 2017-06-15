package ar.com.larreta.school.business.paymentPlans;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.rest.business.BusinessListener;

@Configuration
public class PaymentPlansBusinessConfig {

	public static final String PAYMENT_PLANS_BEFORE_CREATE = "PaymentPlansBeforeCreate";
	public static final String OBLIGATIONS_BEFORE_CREATE = "ObligationsBeforeCreate";
	public static final String PRICES_BEFORE_CREATE = "PricesBeforeCreate";
	public static final String DETAILS_BEFORE_CREATE = "DetailsBeforeCreate";
	
	@Autowired
	private PaymentPlansBeforePersistListener paymentPlansBeforePersistListener;
	@Autowired
	private ObligationsBeforePersistListener obligationsBeforePersistListener;
	@Autowired
	private PricesBeforePersistListener pricesBeforePersistListener;
	@Autowired
	private DetailPersistBusinessListener detailPersistBusinessListener;
	
	@Bean(name=PAYMENT_PLANS_BEFORE_CREATE)
	public Set<BusinessListener> getPaymentPlansBeforeCreate(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(paymentPlansBeforePersistListener);
		return businessListeners;
	}
	
	@Bean(name=OBLIGATIONS_BEFORE_CREATE)
	public Set<BusinessListener> getObligationsBeforeCreate(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(paymentPlansBeforePersistListener);
		return businessListeners;
	}
	
	@Bean(name=PRICES_BEFORE_CREATE)
	public Set<BusinessListener> getPricesBeforeCreate(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(paymentPlansBeforePersistListener);
		return businessListeners;
	}
	
	@Bean(name=DETAILS_BEFORE_CREATE)
	public Set<BusinessListener> getDetailsBeforeCreate(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(paymentPlansBeforePersistListener);
		return businessListeners;
	}

}
