package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Entity
@Table(name = "screenElement")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ScreenElement extends ar.com.larreta.commons.domain.Entity {

	public static final String TABLE_ELEMENT = "#{tableElement}";

	private static final Logger LOGGER = Logger.getLogger(ScreenElement.class);
	
	private String styleClass;
	private String tooltip;
	private String watermark;
	
	private String bindingObject;
	private String bindingProperty;

	@Transient
	public Object getBindingObjectInstance() {
		return ScreenUtils.evaluate(getBindingObject());
	}
	
	/**
	 * Asigna el valor a la propiedad del objeto enlazado
	 * @param value
	 */
	public void setBindingPropertyValue(Object value){
		if (!StringUtils.isEmpty(getBindingObject()) && !StringUtils.isEmpty(getBindingProperty())){
			Object toBinding = getBindingObjectInstance();
			if (toBinding!=null){
				try {
					PropertyUtils.setProperty(toBinding, getBindingProperty(), value);
				} catch (Exception e){
					LOGGER.error("Ocurrio un error en el set binding", e);
				}
			}
		}
	}

	@Transient
	public Boolean getIsBindingTableElement(){
		return TABLE_ELEMENT.equals(getBindingObject());
	}
	
	/**
	 * Retorna el valor de la propiedad en el objeto enlazado
	 * @return
	 */
	@Transient
	public Object getBindingPropertyValue(){
		if (!StringUtils.isEmpty(getBindingObject()) && !StringUtils.isEmpty(getBindingProperty())){
			Object toBinding = getBindingObjectInstance();
			if (toBinding!=null){
				try {
					return PropertyUtils.getProperty(toBinding, getBindingProperty());
				} catch (Exception e){
					LOGGER.error("Ocurrio un error en el get binding", e);
				}
			}
		}
		return null;
	}

	@Basic
	public String getBindingObject() {
		return bindingObject;
	}

	public void setBindingObject(String bindingObject) {
		this.bindingObject = bindingObject;
	}

	@Basic
	public String getBindingProperty() {
		return bindingProperty;
	}

	public void setBindingProperty(String bindingProperty) {
		this.bindingProperty = bindingProperty;
	}

	@Transient
	public String getIdValue(){
		return getSimpleName() + getId();
	}
	
	@Transient
	public Boolean getIsWatermark(){
		return !StringUtils.isEmpty(watermark);
	}
	
	@Basic
	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	@Transient
	public Boolean getIsTooltip(){
		return !StringUtils.isEmpty(tooltip);
	}
	
	@Basic
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@Transient
	public String getSimpleName(){
		return getClass().getSimpleName();
	}

	@Basic
	public String getStyleClass() {
		return styleClass;
	}
	
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
}
