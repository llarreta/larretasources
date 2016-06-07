package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "panelGrid")
@DiscriminatorValue(value = "panelGrid")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class PanelGrid extends Container {

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
	
}
