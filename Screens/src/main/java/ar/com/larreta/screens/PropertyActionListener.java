package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

@Entity
@Table(name = "propertyActionListener")
@DiscriminatorValue(value = "propertyActionListener")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class PropertyActionListener extends ScreenElement {

	private static Logger LOGGER = Logger.getLogger(PropertyActionListener.class);
	
	private String value;
	private String forAttributes;
	private SubmitButton button;
	private String targetBindingObject;
	private String targetBindingProperty;
	
	public PropertyActionListener(){}
	
	public PropertyActionListener(String targetBindingObject, String targetBindingProperty, String bindingObject){
		super();
		setTargetBindingObject(targetBindingObject);
		setTargetBindingProperty(targetBindingProperty);
		setBindingObject(bindingObject);
	}
	
	@Basic
	public String getTargetBindingObject() {
		return targetBindingObject;
	}
	public void setTargetBindingObject(String targetBindingObject) {
		this.targetBindingObject = targetBindingObject;
	}
	
	@Basic
	public String getTargetBindingProperty() {
		return targetBindingProperty;
	}
	
	public void setTargetBindingProperty(String targetBindingProperty) {
		this.targetBindingProperty = targetBindingProperty;
	}
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=SubmitButton.class)
	@JoinColumn (name="idButton")
	public SubmitButton getButton() {
		return button;
	}
	public void setButton(SubmitButton button) {
		this.button = button;
	}
	
	@Basic
	public String getForAttributes() {
		return forAttributes;
	}
	public void setForAttributes(String forAttributes) {
		this.forAttributes = forAttributes;
	}
	
	public void setTarget(Object value) {
		try {
			Object targetBindingObject = ScreenUtils.evaluate(getTargetBindingObject());
			if (targetBindingObject!=null){
				PropertyUtils.setProperty(targetBindingObject, getTargetBindingProperty(), value);
			}
		} catch (Exception e){
			LOGGER.error("Ocurrio un error asignando target", e);
		}
	}
	
	@Transient
	public Object getValueEvaluated(){
		return ScreenUtils.evaluate(getValue());
	}
	
	@Basic
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
