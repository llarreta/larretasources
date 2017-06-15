package ar.com.larreta.rest.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.persistence.dao.StandardDAO;
import ar.com.larreta.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.tools.BeanUtils;

public abstract class BusinessListenerImpl implements BusinessListener {

	@Autowired
	protected BeanUtils beanUtils;
	
	@Autowired
	@Qualifier(value=StandardDAOImpl.STANDAR_DAO)
	protected StandardDAO standardDAO;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
}