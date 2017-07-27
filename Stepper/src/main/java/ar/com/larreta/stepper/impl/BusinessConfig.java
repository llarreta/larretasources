package ar.com.larreta.stepper.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ar.com.larreta.stepper.Step;

public abstract class BusinessConfig {
	
	public Set<Step> getSet(Step... steps){
		Set<Step> set = new HashSet<>();
		set.addAll(Arrays.asList(steps));
		set.remove(null);
		return set;
	}

}
