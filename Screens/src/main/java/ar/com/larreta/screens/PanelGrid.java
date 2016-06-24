package ar.com.larreta.screens;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "panelGrid")
@DiscriminatorValue(value = "panelGrid")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class PanelGrid extends StandardContainer {

	private String columns;

	public PanelGrid(){}
	
	public PanelGrid(Integer columns){
		setColumns(columns.toString());
	}
	
	@Basic
	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	@Transient
	@Override
	public Collection<ScreenElement> getOrdererElements() {
		return super.getOrdererElements();
	}

}
