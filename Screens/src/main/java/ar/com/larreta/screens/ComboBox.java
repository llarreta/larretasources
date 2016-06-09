package ar.com.larreta.screens;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "comboBox")
@DiscriminatorValue(value = "comboBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class ComboBox extends ValuedElement {

	private Set<ComboBoxItem> individualItems;
	private String itemsValue;
	
	@OneToMany (mappedBy="comboBox", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ComboBoxItem.class)
	public Set<ComboBoxItem> getIndividualItems() {
		return individualItems;
	}
	public void setIndividualItems(Set<ComboBoxItem> individualItems) {
		this.individualItems = individualItems;
	}
	
	public void add(ComboBoxItem comboBoxItem){
		if (individualItems==null){
			individualItems = new HashSet<ComboBoxItem>();
		}
		comboBoxItem.setComboBox(this);
		individualItems.add(comboBoxItem);
	}
	
	@Basic
	public String getItemsValue() {
		return itemsValue;
	}
	public void setItemsValue(String itemsValue) {
		this.itemsValue = itemsValue;
	}
	
	@Transient
	public Object getItemsValueEvaluated(){
		return ScreenUtils.evaluate(getItemsValue());
	}

	
	@Transient
	public Boolean getIsItemsValueExist(){
		return !StringUtils.isEmpty(getItemsValue());
	}
	
}
