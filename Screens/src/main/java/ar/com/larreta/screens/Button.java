package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;

@Entity
@Table(name = "button")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="buttonType",
    discriminatorType=DiscriminatorType.STRING
)
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public abstract class Button extends ScreenElement {

	private String value;
	private String icon;
	protected Boolean ajax;
	private Boolean inmediate = Boolean.FALSE;
	private String type = "button";
	private Confirm confirm;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Confirm.class)
	@JoinColumn (name="idConfirm")
	public Confirm getConfirm() {
		return confirm;
	}
	public void setConfirm(Confirm confirm) {
		this.confirm = confirm;
	}
	
	@Basic
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Basic
	public Boolean getInmediate() {
		return inmediate;
	}
	public void setInmediate(Boolean inmediate) {
		this.inmediate = inmediate;
	}
	
	@Basic
	public String getValue() {
		return StandardControllerImpl.getMessage(value);
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Basic
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Transient
	public Boolean getAjax() {
		return ajax;
	}
	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
	}
	
}
