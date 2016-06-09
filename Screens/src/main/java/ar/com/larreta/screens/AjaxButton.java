package ar.com.larreta.screens;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "button")
@DiscriminatorValue(value = "ajaxButton")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class AjaxButton extends Button {

	private static Logger LOGGER = Logger.getLogger(AjaxButton.class);
	
	private String actionListenerObject;
	private String actionListenerMethod;
	private String update;
	private Set<Attribute> attributes;

	public AjaxButton() {
		super();
		ajax = Boolean.TRUE;
	}

	@Basic
	public String getActionListenerObject() {
		return actionListenerObject;
	}

	public void setActionListenerObject(String actionListenerObject) {
		this.actionListenerObject = actionListenerObject;
	}

	@Basic
	public String getActionListenerMethod() {
		return actionListenerMethod;
	}

	public void setActionListenerMethod(String actionListenerMethod) {
		this.actionListenerMethod = actionListenerMethod;
	}

	public void actionListener(ActionEvent actionEvent) {
		try{
			Object instance = ScreenUtils.evaluate(getActionListenerObject());
			if (instance!=null){
				MethodUtils.invokeExactMethod(instance, getActionListenerMethod(), actionEvent);
			}
		} catch (Exception e){
			LOGGER.error("Ocurrio un error ejecutando actionListener", e);
		}
	}
	
	@OneToMany (mappedBy="button", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=Attribute.class)
	@Where(clause="deleted IS NULL")
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public void add(Attribute attribute){
		if (attributes==null){
			attributes = new HashSet<Attribute>();
		}
		attribute.setButton(this);
		attributes.add(attribute);
	}

	@Basic @Column(name="updateValue")
	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	@Transient
	@Override
	public String getActionEvaluated() {
		return StringUtils.EMPTY;
	}
	
	@Transient
	@Override
	public Long getNextScreenId(){
		return null;
	}

	@Transient
	@Override
	public Collection getParams() {
		Collection params = super.getParams();
		Set attributes = getAttributes();
		if (attributes!=null){
			params.addAll(attributes);
		}
		return params;
	}
	

	
}
