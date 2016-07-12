package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "checkBoxItem")
@DiscriminatorValue(value = "checkBoxItem")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class CheckBoxItem extends ValuedElement {
	
	private Long order;
	private String itemLabel;
	private String noSelectionOption;
	private MultiCheckBox multiCheckBox;

	@Basic @Column(name="orderIndex")
	public Long getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = new Long(order);
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=MultiCheckBox.class)
	@JoinColumn (name="idMultiCheckBox")
	public MultiCheckBox getMultiCheckBox() {
		return multiCheckBox;
	}
	public void setMultiCheckBox(MultiCheckBox multiCheckBox) {
		this.multiCheckBox = multiCheckBox;
	}
	
	@Basic
	public String getItemLabel() {
		return itemLabel;
	}
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	
	@Transient
	public String getItemLabelEvaluated() {
		return (String) ScreenUtils.evaluate(itemLabel);
	}
	
	@Basic
	public String getNoSelectionOption() {
		return noSelectionOption;
	}
	public void setNoSelectionOption(String noSelectionOption) {
		this.noSelectionOption = noSelectionOption;
	}
	
	
}
