package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import ar.com.larreta.commons.controllers.Paginator;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.smarttrace.domain.Container;

/**
 * @author ignacio.m.larreta
 *
 */
public class ContainerPaginator extends Paginator {
	@Override
	public List<Entity> load(int first, int pageSize,
			List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		try {
			List<String> properties = new ArrayList<String>();
			properties.add("materialType");
			properties.add("parentContainer");
			datasource = new ArrayList<Entity>(getService().load(getEntityClass(), first, pageSize, null, filters, properties));
			for(int i = 0; i < datasource.size(); i++){
				if(((Container)datasource.get(i)).getParentContainer() != null){
					datasource.remove(i);
				}
			}
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
			properties.add("materialType");
			properties.add("parentContainer");
			datasource = new ArrayList<Entity>(getService().load(getEntityClass(), first,	pageSize, getOrder(sortOrder, sortField), filters, properties));
			for(int i = 0; i < datasource.size(); i++){
				if(((Container)datasource.get(i)).getParentContainer() != null){
					datasource.remove(i);
				}
			}
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return datasource;
	}
}
