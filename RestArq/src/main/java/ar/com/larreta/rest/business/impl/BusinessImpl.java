package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.query.Persister;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.tools.BeanUtils;

public abstract class BusinessImpl implements Business {

	protected static @Log Logger LOG;
	
	@Autowired
	protected BeanUtils beanUtils;
	
	@Autowired
	protected Persister persister;
	
	@Autowired
	protected ApplicationContext applicationContext;

	
	public static void callListeners(Set<BusinessListener> listeners, Serializable source, Serializable target, Object... args) throws Exception {
		if (listeners!=null){
			Iterator<BusinessListener> it = listeners.iterator();
			while (it.hasNext()) {
				BusinessListener listener = (BusinessListener) it.next();
				listener.process(source, target, args);
			}
		}
	}
}
