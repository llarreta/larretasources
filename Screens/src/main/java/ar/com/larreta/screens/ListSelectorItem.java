package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "listSelectorItem")
@DiscriminatorValue(value = "listSelectorItem")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class ListSelectorItem extends ValuedElement {

	private Long order;
	private String itemLabel;
	private String noSelectionOption;
	private ListSelector listSelector;

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
	
	@ManyToOne (targetEntity=ListSelector.class, fetch=FetchType.EAGER,  cascade=CascadeType.ALL)
	@JoinColumn (name="idListSelector")
	public ListSelector getListSelector() {
		return listSelector;
	}
	public void setListSelector(ListSelector listSelector) {
		this.listSelector = listSelector;
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
