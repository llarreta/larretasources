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
import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.faces.EntityConverter;

@Entity
@Table(name = "comboBox")
@DiscriminatorValue(value = "comboBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class ComboBox extends ValuedElement {

	private static Logger logger = Logger.getLogger(ComboBox.class);
	
	private Set<ComboBoxItem> individualItems;
	private String entityType;
	
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
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	@Transient
	public EntityConverter getConverter(){
			EntityConverter converter = new EntityConverter();
			converter.setEntityClass(ScreenUtils.getClass(getEntityType()));
			return converter;
	}
	
	@Transient
	public Object getItems(){
		try {
			return AppManager.getInstance().getStandardService().load(ScreenUtils.getClass(getEntityType()));
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
