package ar.com.larreta.screens;

import java.util.Collection;
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

@Entity
@Table(name = "button")
@DiscriminatorValue(value = "submitButton")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class SubmitButton extends Button {

	private String action;
	private Set<PropertyActionListener> properties;
	private Long nextScreenId;

	public SubmitButton() {
		super();
		ajax = Boolean.FALSE;
	}
	
	public SubmitButton(String action, String icon, String value) {
		this();
		setAction(action);
		setIcon(icon);
		setValue(value);
	}
	
	public SubmitButton(String action, String icon, String value, Boolean inmediate) {
		this(action, icon, value);
		setInmediate(inmediate);
	}
	
	public SubmitButton(String action, String icon, String value, Long nextScreenId) {
		this(action, icon, value);
		setNextScreenId(nextScreenId);
	}

	public SubmitButton(String action, String icon, String value, Long nextScreenId, Boolean inmediate) {
		this(action, icon, value);
		setNextScreenId(nextScreenId);
		setInmediate(inmediate);
	}
	
	
	@Basic
	public Long getNextScreenId() {
		return nextScreenId;
	}

	public void setNextScreenId(Long nextScreenId) {
		this.nextScreenId = nextScreenId;
	}

	@OneToMany (mappedBy="button", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=PropertyActionListener.class)
	//FIXME: Descomentar esta linea provoca un error en hibernate para crear la query que trae la info
	//@Where(clause="deleted IS NULL")
	public Set<PropertyActionListener> getProperties() {
		return properties;
	}

	public void setProperties(Set<PropertyActionListener> properties) {
		this.properties = properties;
	}
	
	public void add(PropertyActionListener propertyActionListener){
		if (properties==null){
			properties = new HashSet<PropertyActionListener>();
		}
		propertyActionListener.setButton(this);
		properties.add(propertyActionListener);
	}

	@Transient
	public String getActionEvaluated() {
		return (String) ScreenUtils.evaluate(getAction());
	}
	
	@Basic
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Transient
	@Override
	public Collection getParams() {
		Collection params = super.getParams();
		Set attributes = getProperties();
		if (attributes!=null){
			params.addAll(attributes);
		}
		return params;
	}

	
	
}
