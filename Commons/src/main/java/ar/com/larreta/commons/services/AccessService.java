package ar.com.larreta.commons.services;

import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.Order;

import ar.com.larreta.commons.exceptions.NotImplementedException;

public interface AccessService extends StandardService {
	public Collection load(Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters) throws NotImplementedException;
}
