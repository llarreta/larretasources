package ar.com.larreta.rest.business.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ar.com.larreta.rest.business.BusinessListener;

public abstract class BusinessConfig {
	
	public Set<BusinessListener> getSet(BusinessListener... listeners){
		Set<BusinessListener> set = new HashSet<>();
		set.addAll(Arrays.asList(listeners));
		set.remove(null);
		return set;
	}

}
