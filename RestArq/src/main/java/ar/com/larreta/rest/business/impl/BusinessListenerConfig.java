package ar.com.larreta.rest.business.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.rest.business.BusinessListener;

@Configuration
public class BusinessListenerConfig {

	public static final String PARAMETRIC_DATA_BEFORE_LOAD_LISTENER = "parametricDataBeforeLoadListener";

	public static final String FIRST_AND_MAX_RESULTS = "firstAndMaxResults";
	
	@Autowired
	private LoadArgsFirstResultBusinessListener firstResultListener;

	@Autowired
	private LoadArgsMaxResultsBusinessListener maxResultListener;
	
	private LoadArgsWhereEqualDescriptionBusinessListener loadArgsWhereEqualDescriptionBusinessListener;
	
	@Bean(name=FIRST_AND_MAX_RESULTS)
	public Set<BusinessListener> getFirstAndaMaxResults(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(firstResultListener);
		businessListeners.add(maxResultListener);
		return businessListeners;
	}
	
	@Bean(name=PARAMETRIC_DATA_BEFORE_LOAD_LISTENER)
	public Set<BusinessListener> getParametricDataBeforeLoadListener(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(firstResultListener);
		businessListeners.add(maxResultListener);
		return businessListeners;
	}
}
