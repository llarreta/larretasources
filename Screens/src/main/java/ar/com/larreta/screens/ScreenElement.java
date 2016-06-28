package ar.com.larreta.screens;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "screenElement")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ScreenElement extends ar.com.larreta.commons.domain.Entity {

	public static final String TABLE_ELEMENT = ScreenUtils.generateExpression("tableElement");
	public static final String CONTROLLER = ScreenUtils.generateExpression("controller");

	private static final Logger LOGGER = Logger.getLogger(ScreenElement.class);
	
	@Transient
	private ScreenElement me;
	
	private String styleClass;
	private String tooltip;
	private String watermark;
	
	private String bindingObject;
	private String bindingProperty;
	
	private Boolean rendered = Boolean.TRUE;
	
	protected Set<ContainedElement> parents = new HashSet<ContainedElement>();
	
	@Transient
	private Boolean initialized = Boolean.FALSE;
	
	@OneToMany (mappedBy="element", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=ContainedElement.class)
	@Where(clause="deleted IS NULL")
	public Set<ContainedElement> getParents() {
		return parents;
	}

	public void setParents(Set<ContainedElement> parents) {
		this.parents = parents;
	}
	public void add(ContainedElement parent){
		if (parents==null){
			parents=new HashSet<ContainedElement>();
		}
		parents.add(parent);
	}

	@Basic
	public Boolean getRendered() {
		return rendered;
	}

	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}

	@Transient
	public Object getBindingObjectInstance() {
		return ScreenUtils.evaluate(getBindingObject());
	}
	
	@Transient
	public Boolean getIsBindingTableElementWithProperty(){
		return TABLE_ELEMENT.equals(getBindingObject()) && !StringUtils.isEmpty(getBindingProperty());
	}
	
	@Transient
	public Boolean getIsBindingTableElementWithoutProperty(){
		return TABLE_ELEMENT.equals(getBindingObject()) && StringUtils.isEmpty(getBindingProperty());
	}
	
	@Transient
	public Boolean getIsBindingTableElement(){
		return TABLE_ELEMENT.equals(getBindingObject());
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
//					return ScreenUtils.fixValue(PropertyUtils.getProperty(toBinding, getBindingProperty()));

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
	
	@Transient
	protected ScreenElement getMe(Class toInstance) {
		if (!initialized){
			initialized=!initialized;
			initialize();
		}
		try {
			if (me==null){
				me = (ScreenElement) toInstance.newInstance();
			}
			PropertyUtils.copyProperties(me, this);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error copiando propiedades", e);
		}
		return me;
	}
	
	@Transient
	public ScreenElement getMe(){
		return this;
	}
	
	public void initialize(){}
	
}
