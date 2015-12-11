package ar.com.larreta.commons.services.impl;

import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.audit.UserAccessHistory;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.persistence.AccessDAO;
import ar.com.larreta.commons.persistence.dao.impl.LoadArguments;
import ar.com.larreta.commons.services.AccessService;

@Service
@Transactional
public class AccessServiceImpl extends StandardServiceImpl implements AccessService {
	

	@Override
	public AccessDAO getDao() {
		return (AccessDAO) super.getDao();
	}

	@Autowired
	public void setDao(AccessDAO dao) {
		super.setDao(dao);
	}

	
	
	public Collection load(Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters) throws NotImplementedException{
		return load(UserAccessHistory.class, firstResult, maxResults, order, filters);
	}

	@Override
	public Collection load(Class entityType, Integer firstResult,
			Integer maxResults, Order order, Map<String, Object> filters) {
		LoadArguments args = new LoadArguments(entityType);
		args.addProjectedProperties("user");
		args.addDescOrder("userAccessDate");
		args.setFirstResult(firstResult);
		args.setMaxResults(maxResults);
		return getDao().load(args);
	}

	
}
