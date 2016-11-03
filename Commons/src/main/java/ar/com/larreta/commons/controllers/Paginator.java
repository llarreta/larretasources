package ar.com.larreta.commons.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.logger.AppLogger;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.services.args.ServiceInfo;
import ar.com.larreta.commons.views.DataView;

public class Paginator extends LazyDataModel<Entity> implements AppObject {

	protected List<Entity> datasource;

	private Integer lastFirst = 0;

	private Integer count = 0;

	private AppObject appObject = new AppObjectImpl(getClass());
	
	protected DataView dataView;
	
	private Collection<LoadListener> loadListeners = new ArrayList<LoadListener>();
	
	private LoadArguments arguments;
	private List<String> lazyProperties = new ArrayList<String>();
	
	private String sortField;
	
	private Map<String, Object> filters = new HashMap<String, Object>();
	
	public Paginator(){
		super();
	}

	public Integer getLastFirst() {
		return lastFirst;
	}
	
	public void putFilter(String key, Object filter){
		filters.put(key, filter);
	}
	
	public void removeFilter(String key){
		filters.remove(key);
	}
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public void setLazyProperties(List<String> lazyProperties) {
		this.lazyProperties = lazyProperties;
	}

	public void addLoadListener(LoadListener loadListener){
		loadListeners.add(loadListener);
	}
	
	public DataView getDataView() {
		return dataView;
	}

	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}

	public Entity getRowData(String rowKey) {
		if (datasource!=null){
			for (Entity entity : datasource) {
				if (entity.equals(rowKey)) {
					return entity;
				}
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
			if (arguments!=null){
				setCount(getService().count(arguments.toCountArguments()));
			} else {
				if (getEntityClass()!=null){
					setCount(getService().count(getEntityClass()));
				}
			}
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	protected void setCount(Long value) {
		if (value!=null){
			count = value.intValue();
		}
	}

	@Override
	public int getRowCount() {
		return count;
	}

	private void alertLoadListeners(List<Entity> entities){
		Iterator<LoadListener> iterator = loadListeners.iterator();
		while (iterator.hasNext()) {
			LoadListener loadListener = (LoadListener) iterator.next();
			loadListener.alert(entities);
		}
	}
	
	@Override
	public List<Entity> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		try {
			filters = putFilters(filters);
			ServiceInfo serviceInfo = getService().loadServiceInfo(getEntityClass(), first, pageSize, null, filters, lazyProperties);
			lastFirst = first;
			datasource = new ArrayList<Entity>(serviceInfo.getData());
			arguments = serviceInfo.getArguments();
			refresh();
			alertLoadListeners(datasource);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));		
		}
		return datasource;
	}

	@Override
	public List<Entity> load(int first, int pageSize, String sortField,	SortOrder sortOrder, Map<String, Object> filters) {
		try {
			if (this.sortField!=null){
				sortField = this.sortField;
			}
			filters = putFilters(filters);
			ServiceInfo serviceInfo = getService().loadServiceInfo(getEntityClass(), first, pageSize, getOrder(sortOrder, sortField), filters, lazyProperties);
			lastFirst = first;
			datasource = new ArrayList<Entity>(serviceInfo.getData());
			arguments = serviceInfo.getArguments();
			refresh();
			alertLoadListeners(datasource);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return datasource;
	}

	private Map<String, Object> putFilters(Map<String, Object> filters) {
		if (filters==null){
			filters = new HashMap<>();
		}
		filters.putAll(this.filters);
		filters.remove("bindingProperty");
		return filters;
	}

	public AppLogger getLog() {
		return appObject.getLog();
	}

	public void setLog(AppLogger log) {
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
