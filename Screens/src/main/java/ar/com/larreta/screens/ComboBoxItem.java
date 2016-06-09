package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "comboBoxItem")
@DiscriminatorValue(value = "comboBoxItem")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class ComboBoxItem extends ValuedElement {
	
	private String itemLabel;
	private String noSelectionOption;
	private ComboBox comboBox;
	
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=ComboBox.class)
	@JoinColumn (name="idComboBox")
	public ComboBox getComboBox() {
		return comboBox;
	}
	public void setComboBox(ComboBox comboBox) {
		this.comboBox = comboBox;
	}
	
	@Basic
	public String getItemLabel() {
		return itemLabel;
	}
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	
	@Basic
	public String getNoSelectionOption() {
		return noSelectionOption;
	}
	public void setNoSelectionOption(String noSelectionOption) {
		this.noSelectionOption = noSelectionOption;
	}
	
	
}
