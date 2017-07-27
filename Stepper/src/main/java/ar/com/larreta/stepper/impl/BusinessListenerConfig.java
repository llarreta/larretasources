package ar.com.larreta.stepper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Deprecated //FIXME: Evaluar necesidad de esta clase
public class BusinessListenerConfig {

	public static final String PARAMETRIC_DATA_BEFORE_LOAD_LISTENER = "parametricDataBeforeLoadListener";

	public static final String FIRST_AND_MAX_RESULTS = "firstAndMaxResults";
	
	//@Autowired
	private FirstResultBusinessListener firstResultListener;

	//@Autowired
	private MaxResultsBusinessListener maxResultListener;
	
	/*@Bean(name=FIRST_AND_MAX_RESULTS) 
	@DependsOn(value={FirstResultBusinessListener.BUSINESS_LISTENER_NAME, MaxResultsBusinessListener.BUSINESS_LISTENER_NAME})
	public Set<BusinessListener> getFirstAndaMaxResults(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(firstResultListener);
		businessListeners.add(maxResultListener);
		return businessListeners;
	}
	
	@Bean(name=PARAMETRIC_DATA_BEFORE_LOAD_LISTENER) 
	@DependsOn(value={FirstResultBusinessListener.BUSINESS_LISTENER_NAME, MaxResultsBusinessListener.BUSINESS_LISTENER_NAME})
	public Set<BusinessListener> getParametricDataBeforeLoadListener(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(firstResultListener);
		businessListeners.add(maxResultListener);
		return businessListeners;
	}*/
}
