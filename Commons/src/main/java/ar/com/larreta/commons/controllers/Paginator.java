package ar.com.larreta.commons.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.views.DataView;

public class Paginator extends LazyDataModel<Entity> implements AppObject {

	protected List<Entity> datasource;

	private Integer count = 0;

	private AppObject appObject = new AppObjectImpl(getClass());
	
	protected DataView dataView;
	
	public DataView getDataView() {
		return dataView;
	}

	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}

	public Entity getRowData(String rowKey) {
		for (Entity entity : datasource) {
			if (entity.equals(rowKey)) {
				return entity;
			}
		}

		return null;
	}

	public Object getRowKey(Entity entity) {
		return entity.getId();
	}

	public Order getOrder(SortOrder sortOrder, String field) {
		if (field != null) {
			if (SortOrder.ASCENDING.equals(sortOrder)) {
				return Order.asc(field);
			}
			if (SortOrder.DESCENDING.equals(sortOrder)) {
				return Order.desc(field);
			}
		}
		return null;
	}

	public void refresh() throws NotServiceAssignedException {
		try {
			// FIXME: Evaluar como se comporta el count con filtros, posible issue
			count = getService().count(getEntityClass()).intValue();
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	@Override
	public int getRowCount() {
		return count;
	}

	@Override
	public List<Entity> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		try {
			datasource = new ArrayList<Entity>(getService().load(getEntityClass(), first, pageSize, null, filters));
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));		
		}
		return datasource;
	}

	@Override
	public List<Entity> load(int first, int pageSize, String sortField,	SortOrder sortOrder, Map<String, Object> filters) {
		try {
			datasource = new ArrayList<Entity>(getService().load(getEntityClass(), first,	pageSize, getOrder(sortOrder, sortField), filters));
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return datasource;
	}

	public Logger getLog() {
		return appObject.getLog();
	}

	public void setLog(Logger log) {
		appObject.setLog(log);
	}

	/**
	 * Arranca a contabilizar estadisticas
	 * 
	 * @param mark
	 * @return
	 */
	public Long statisticsStart(String mark) {
		return appObject.statisticsStart(mark);
	}

	/**
	 * Finaliza de contabilizar estadisticas
	 * 
	 * @param id
	 */
	public void statisticsStop(Long id) {
		appObject.statisticsStop(id);
	}
	
	public StandardService getService() throws NotServiceAssignedException{
		return getDataView().getController().getService();
	}
	
	public Class getEntityClass(){
		return getDataView().getController().getEntityClass();
	}
}
