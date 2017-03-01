package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Where;

import ar.com.larreta.screens.validators.Validator;

@MappedSuperclass
public abstract class StandardContainer extends ScreenElement implements Container {

	private static final Logger LOGGER = Logger.getLogger(StandardContainer.class);
	
	protected Set<ContainedElement> containedElements = new HashSet<ContainedElement>();
	private SearchMap searchMap = new SearchMap();

	@Transient
	public SearchMap getSearchMap() {
		return searchMap;
	}

	@OneToMany (mappedBy="container", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ContainedElement.class)
	@Where(clause="deleted IS NULL")
	public Set<ContainedElement> getContainedElements() {
		return containedElements;
	}

	public void setContainedElements(Set<ContainedElement> containedElements) {
		try {
			this.containedElements = containedElements;
			searchMap.add(containedElements);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error", e);
		}
	}

	@Transient
	public Collection<ScreenElement> getOrdererElements(){
		List<ContainedElement> ordererElements = new ArrayList<ContainedElement>(getContainedElements());
		Collections.sort(ordererElements, new Comparator<ContainedElement>() {
			public int compare(ContainedElement elementA, ContainedElement elementB) {
				return elementA.getOrder().compareTo(elementB.getOrder());
			}
		});
		List<ScreenElement> elements = new ArrayList<ScreenElement>();
		java.util.Iterator<ContainedElement> it = ordererElements.iterator();
		while (it.hasNext()) {
			ContainedElement containedElement = (ContainedElement) it.next();
			ScreenElement screenElement = containedElement.getElement();
			elements.add(screenElement);
		}
		return elements;
	}

	public void add(Integer orderIndex, ScreenElement element){
		try {
			if (element!=this){
				containedElements.add(new ContainedElement(orderIndex, this, element));
				searchMap.add(element);
			} else {
				LOGGER.info("No puede agregarse un elemento sobre si mismo:" + element.getClass().getName());
			}
		} catch (Exception e){
			LOGGER.error("Ocurrio un error", e);
		}
	}
	
	public void add(ScreenElement element){
			add(0, element);
	}
	
	@Transient
	public StandardContainer getTargetObject(){
		return this;
	}
	
	public Integer addPassword(Integer index, String labelText, String dataViewSelectedProperty, Validator validator) {
		Password password = new Password(DATA_VIEW_SELECTED, dataViewSelectedProperty);
		password.addValidator(validator);
		index = addLabel(index, labelText);
		getTargetObject().add(index++, password);
		return index;
	}
	
	public Integer addPassword(Integer index, String labelText, String dataViewSelectedProperty) {
		return addPassword(index, labelText, dataViewSelectedProperty, null);
	}
	
	public Integer addInput(Integer index, String labelText, String dataViewSelectedProperty, Validator validator) {
		Input input = new Input(DATA_VIEW_SELECTED, dataViewSelectedProperty);
		input.addValidator(validator);
		input.setPlaceHolder(labelText);
		getTargetObject().add(index++, input);
		return index;
	}
	
	public Integer addCalendar(Integer index, String labelText, String dataViewSelectedProperty) {
		return addCalendar(index, labelText, dataViewSelectedProperty, null);
	}
	
	public Integer addCalendar(Integer index, String labelText, String dataViewSelectedProperty, Validator validator) {
		Calendar calendar = new Calendar(DATA_VIEW_SELECTED, dataViewSelectedProperty);
		calendar.addValidator(validator);
		index = addLabel(index, labelText);
		getTargetObject().add(index++, calendar);
		return index;
	}

	public Integer addLabel(Integer index, String labelText) {
		getTargetObject().add(index++, new Label(labelText));
		return index;
	}
	
	public Integer addInput(Integer index, String labelText, String dataViewSelectedProperty) {
		return addInput(index, labelText, dataViewSelectedProperty, null);
	}
	
	public Integer addCheckBox(Integer index, String labelText, String dataViewSelectedProperty) {
		index = addLabel(index, labelText);
		getTargetObject().add(index++, new CheckBox(DATA_VIEW_SELECTED, dataViewSelectedProperty));
		return index;
	}

	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType) {
		return addCombo(index, labelText, dataViewSelectedProperty, entityType, null, null);
	}

	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType, Validator validator) {
		return addCombo(index, labelText, dataViewSelectedProperty, entityType, null, validator);
	}

	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties) {
		return addCombo(index, labelText, dataViewSelectedProperty, entityType, lazyProperties, null);
	}
	
	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties, Validator validator) {
		ComboBox comboBox = new ComboBox();
		comboBox.addValidator(validator);
		comboBox.setBindingObject(DATA_VIEW_SELECTED);
		comboBox.setBindingProperty(dataViewSelectedProperty);
		comboBox.setEntityType(entityType);
		comboBox.setLazyProperties(lazyProperties);
		comboBox.setStyleClass("combo-box-custom");
		
		comboBox.addVoidItem(labelText);
		
		getTargetObject().add(index++, comboBox);
		return index;
	}

	public Integer addMultiCheckBox(Integer index, String labelText, String dataViewSelectedProperty, String entityType) {
		return addMultiCheckBox(index, labelText, dataViewSelectedProperty, entityType, null, null);
	}
	
	public Integer addMultiCheckBox(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties){
		return addMultiCheckBox(index, labelText, dataViewSelectedProperty, entityType, lazyProperties, null) ;
	}
	
	public Integer addMultiCheckBox(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties, Validator validator) {
		index = addLabel(index, labelText);
		MultiCheckBox multiCheckBox = new MultiCheckBox();
		multiCheckBox.addValidator(validator);
		multiCheckBox.setBindingObject(DATA_VIEW_SELECTED);
		multiCheckBox.setBindingProperty(dataViewSelectedProperty);
		multiCheckBox.setEntityType(entityType);
		multiCheckBox.setLazyProperties(lazyProperties);
		getTargetObject().add(index++, multiCheckBox);
		return index;
	}
	
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
								String entityType, String propertyItemLabel){
		return addMultiBox(index, sourceCaption, targetCaption, dataViewSelectedProperty, entityType, propertyItemLabel, null, null);
	}
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
								String entityType, String propertyItemLabel, Validator validator){
		return addMultiBox(index, sourceCaption, targetCaption, dataViewSelectedProperty, entityType, propertyItemLabel, null, validator);
	}

	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
			String entityType, String propertyItemLabel, String lazyProperties) {
		return addMultiBox(index, sourceCaption, targetCaption, dataViewSelectedProperty, entityType, propertyItemLabel, lazyProperties, null);
	}
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
								String entityType, String propertyItemLabel, String lazyProperties, Validator validator) {
		preAddMultibox();
		
		MultiBox multiBox = new MultiBox();
		multiBox.addValidator(validator);
		multiBox.setSourceCaption(sourceCaption);
		multiBox.setTargetCaption(targetCaption);
		multiBox.setEntityType(entityType);
		multiBox.setBindingObject(DATA_VIEW_SELECTED);
		multiBox.setBindingProperty(dataViewSelectedProperty);
		multiBox.setPropertyItemLabel(propertyItemLabel);
		multiBox.setLazyProperties(lazyProperties);
		
		getTargetObject().add(index++, multiBox);
		
		postAddMultibox();
		return index;
	}
	
	public void preAddMultibox(){}
	public void postAddMultibox(){}
	
}
