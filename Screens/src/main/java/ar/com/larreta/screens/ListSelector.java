package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.FacesEvent;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.faces.EntityConverter;
import ar.com.larreta.screens.impl.ListSelectorListener;

@Entity
@Table(name = "listSelector")
public abstract class ListSelector extends ContainerValued {
	
	private static Logger logger = Logger.getLogger(ListSelector.class);
	
	private String entityType;
	private String lazyProperties;

	private Set<ListSelectorItem> individualItems;

	private String changeListener;
	private ListSelectorListener changeListenerInstance;
	
	@Transient
	public Collection<ListSelectorItem> getOrdererIndividualItems(){
		List<ListSelectorItem> ordererElements = new ArrayList<ListSelectorItem>(getIndividualItems());
		Collections.sort(ordererElements, new Comparator<ListSelectorItem>() {
			public int compare(ListSelectorItem elementA, ListSelectorItem elementB) {
				return elementA.getOrder().compareTo(elementB.getOrder());
			}
		});
		return ordererElements;
	}

	
	@OneToMany (mappedBy="listSelector", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ListSelectorItem.class)
	public Set<ListSelectorItem> getIndividualItems() {
		return individualItems;
	}
	public void setIndividualItems(Set<ListSelectorItem> individualItems) {
		this.individualItems = individualItems;
	}
	
	public void addItem(ListSelectorItem comboBoxItem){
		if (individualItems==null){
			individualItems = new HashSet<ListSelectorItem>();
		}
		comboBoxItem.setListSelector(this);
		individualItems.add(comboBoxItem);
	}
	
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
			Object items = AppManager.getInstance().getStandardService().load(ScreenUtils.getClass(getEntityType()), null, null, null, null, getLazyPropertiesSplitted());
			return items;
		} catch (Exception e){
			logger.error("Ocurrio un error obteniendo items", e);
		}
		return null;
	}

	
	@Transient
	public Boolean getIsItemsValueExist(){
		return !StringUtils.isEmpty(getEntityType());
	}

	@Basic
	public String getChangeListener() {
		return changeListener;
	}


	public void setChangeListener(String changeListener) {
		this.changeListener = changeListener;
	}
	
	@Transient
	public ListSelectorListener getChangeListenerInstance() {
		if ((changeListenerInstance==null) && (!StringUtils.isEmpty(changeListener))){
			changeListenerInstance = (ListSelectorListener) ScreenUtils.getObject(changeListener);
		}
		return changeListenerInstance;
	}
	
	public void change(FacesEvent facesEvent, StandardController controller){
		getChangeListenerInstance().process(facesEvent, controller, this);
	}

	
}
