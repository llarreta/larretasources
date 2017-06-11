package ar.com.larreta.rest.business.impl;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.persistence.dao.StandardDAO;
import ar.com.larreta.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.tools.BeanUtils;

public abstract class BusinessImpl implements Business {

	@Autowired
	protected BeanUtils beanUtils;
	
	@Autowired
	@Qualifier(value=StandardDAOImpl.STANDAR_DAO)
	protected StandardDAO standardDAO;
	
	@Autowired
	protected ApplicationContext applicationContext;

	
	protected void callListeners(Set<BusinessListener> listeners, JSONable json, Entity entity, Object... args) throws Exception {
		if (listeners!=null){
			Iterator<BusinessListener> it = listeners.iterator();
			while (it.hasNext()) {
				BusinessListener listener = (BusinessListener) it.next();
				listener.process(json, entity, args);;
			}
		}
	}
}
