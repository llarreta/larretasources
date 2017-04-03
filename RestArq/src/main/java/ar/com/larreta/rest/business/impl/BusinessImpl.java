package ar.com.larreta.rest.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.larreta.persistence.dao.StandardDAO;
import ar.com.larreta.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.tools.BeanUtils;

public abstract class BusinessImpl implements Business {

	@Autowired
	protected BeanUtils beanUtils;
	
	@Autowired
	@Qualifier(value=StandardDAOImpl.STANDAR_DAO)
	protected StandardDAO standardDAO;

}
