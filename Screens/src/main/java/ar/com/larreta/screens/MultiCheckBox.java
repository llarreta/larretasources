package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.faces.EntityConverter;

@Entity
@Table(name = "multiCheckBox")
@DiscriminatorValue(value = "multiCheckBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class MultiCheckBox extends ContainerValued {

	private static Logger logger = Logger.getLogger(MultiCheckBox.class);
	
	private Set<CheckBoxItem> individualItems;
	private String entityType;
	private String lazyProperties;
	
	@Basic
	public String getLazyProperties() {
		return lazyProperties;
	}


	public void setLazyProperties(String lazyProperties) {
		this.lazyProperties = lazyProperties;
	}

	@Transient
	public Collection<String> getLazyPropertiesSplitted(){
		return ScreenUtils.split(lazyProperties);
	}

	@Transient
	public Collection<CheckBoxItem> getOrdererIndividualItems(){
		List<CheckBoxItem> ordererElements = new ArrayList<CheckBoxItem>(getIndividualItems());
		Collections.sort(ordererElements, new Comparator<CheckBoxItem>() {
			public int compare(CheckBoxItem elementA, CheckBoxItem elementB) {
				return elementA.getOrder().compareTo(elementB.getOrder());
			}
		});
		return ordererElements;
	}

	
	@OneToMany (mappedBy="multiCheckBox", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=CheckBoxItem.class)
	public Set<CheckBoxItem> getIndividualItems() {
		return individualItems;
	}
	public void setIndividualItems(Set<CheckBoxItem> individualItems) {
		this.individualItems = individualItems;
	}
	
	public void addComboBoxItem(CheckBoxItem checkBoxItem){
		if (individualItems==null){
			individualItems = new HashSet<CheckBoxItem>();
		}
		checkBoxItem.setMultiCheckBox(this);
		individualItems.add(checkBoxItem);
	}
	
	@Basic
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	@Transient
	public EntityConverter getConverter(){
		EntityConverter converter = new EntityConverter();
		if (!StringUtils.isEmpty(getEntityType())){
			converter.setEntityClass(ScreenUtils.getClass(getEntityType()));
		}
		return converter;
	}
	
	@Transient
	public Object getItems(){
		try {
			return AppManager.getInstance().getStandardService().load(ScreenUtils.getClass(getEntityType()), null, null, null, null, getLazyPropertiesSplitted());
		} catch (Exception e){
			logger.error("Ocurrio un error obteniendo items", e);
		}
		return null;
	}

	
	@Transient
	public Boolean getIsItemsValueExist(){
		return !StringUtils.isEmpty(getEntityType());
	}
	
}
