package ar.com.larreta.screens;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SelectableDataModel;

@Entity
@javax.persistence.Table(name = "columntag")
@DiscriminatorValue(value = "column")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Column extends StandardContainer implements SelectableDataModel {
	
	private String headerText;
	private String sortBy;
	private String width;
	private FilterMatchMode filterMatchMode;

	@Transient
	public void setContainsFilter(String filterProperty){
		filterMatchMode = new Contains();
		filterMatchMode.setDescription(filterProperty);
	}

	@Transient
	public void setExactFilter(String filterProperty, String entityClass){
		filterMatchMode = new Exact();
		filterMatchMode.setDescription(filterProperty);
		((Exact)filterMatchMode).setEntityClass(entityClass);
	}

	
	@Transient
	public String getFilter(){
		if (filterMatchMode==null){
			return StringUtils.EMPTY;
		}
		return filterMatchMode.getClass().getSimpleName();
	}
	
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=FilterMatchMode.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idFilterMatchMode")
	public FilterMatchMode getFilterMatchMode() {
		return filterMatchMode;
	}

	public void setFilterMatchMode(FilterMatchMode filterMatchMode) {
		this.filterMatchMode = filterMatchMode;
	}

	@Transient
	public String getHeaderTextEvaluated() {
		return (String) ScreenUtils.evaluate(getHeaderText());
	}
	
	@Basic
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	
	@Basic
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	@Basic
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public Object getRowKey(Object object) {
		//FIXME: Implementar correctamente
		return null;
	}
	public Object getRowData(String rowKey) {
		//FIXME: Implementar correctamente
		return null;
	}

	@Override
	@Transient
	public Collection<ScreenElement> getOrdererElements() {
		return super.getOrdererElements();
	}
}
