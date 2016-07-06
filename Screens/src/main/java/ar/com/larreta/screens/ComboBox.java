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
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.faces.EntityConverter;
import ar.com.larreta.screens.impl.ComboBoxListener;

@Entity
@Table(name = "comboBox")
@DiscriminatorValue(value = "comboBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class ComboBox extends ContainerValued {

	private static Logger logger = Logger.getLogger(ComboBox.class);
	
	private Set<ComboBoxItem> individualItems;
	private String entityType;
	private String changeListener;
	private ComboBoxListener changeListenerInstance;
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

	@Basic
	public String getChangeListener() {
		return changeListener;
	}


	public void setChangeListener(String changeListener) {
		this.changeListener = changeListener;
	}
	
	@Transient
	public ComboBoxListener getChangeListenerInstance() {
		if ((changeListenerInstance==null) && (!StringUtils.isEmpty(changeListener))){
			changeListenerInstance = (ComboBoxListener) ScreenUtils.getObject(changeListener);
		}
		return changeListenerInstance;
	}


	@Transient
	public Collection<ComboBoxItem> getOrdererIndividualItems(){
		List<ComboBoxItem> ordererElements = new ArrayList<ComboBoxItem>(getIndividualItems());
		Collections.sort(ordererElements, new Comparator<ComboBoxItem>() {
			public int compare(ComboBoxItem elementA, ComboBoxItem elementB) {
				return elementA.getOrder().compareTo(elementB.getOrder());
			}
		});
		return ordererElements;
	}

	
	@OneToMany (mappedBy="comboBox", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ComboBoxItem.class)
	public Set<ComboBoxItem> getIndividualItems() {
		return individualItems;
	}
	public void setIndividualItems(Set<ComboBoxItem> individualItems) {
		this.individualItems = individualItems;
	}
	
	public void addComboBoxItem(ComboBoxItem comboBoxItem){
		if (individualItems==null){
			individualItems = new HashSet<ComboBoxItem>();
		}
		comboBoxItem.setComboBox(this);
		individualItems.add(comboBoxItem);
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
	
	public void change(FacesEvent facesEvent, StandardController controller){
		getChangeListenerInstance().process(facesEvent, controller, this);
	}
	
}
