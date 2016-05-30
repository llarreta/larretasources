package ar.com.larreta.screens;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.primefaces.model.SelectableDataModel;

@Entity
@javax.persistence.Table(name = "columntag")
@DiscriminatorValue(value = "column")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Column extends Container implements SelectableDataModel {
	
	private String headerText;
	private String sortBy;
	private String width;
	
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
