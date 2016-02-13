package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import ar.com.larreta.commons.controllers.Paginator;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;

/**
 * @author ignacio.m.larreta
 *
 */
public class MaterialTypePaginator extends Paginator {
	
	@Override
	public List<Entity> load(int first, int pageSize,
			List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		try {
			List<String> properties = new ArrayList<String>();
			properties.add("unitType");
			properties.add("provider");
			properties.add("classification");
			datasource = new ArrayList<Entity>(getService().load(getEntityClass(), first, pageSize, null, filters, properties));
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));		
		}
		return datasource;
	}
	
	@Override
	public List<Entity> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		try {
			List<String> properties = new ArrayList<String>();
			properties.add("unitType");
			properties.add("provider");
			properties.add("classification");
			datasource = new ArrayList<Entity>(getService().load(getEntityClass(), first,	pageSize, getOrder(sortOrder, sortField), filters, properties));
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return datasource;
	}

}
