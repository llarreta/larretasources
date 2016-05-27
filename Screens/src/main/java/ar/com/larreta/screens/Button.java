package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;

@MappedSuperclass
public abstract class Button extends ScreenElement {

	private String value;
	private String icon;
	protected Boolean ajax;
	private Boolean inmediate = Boolean.FALSE;
	private Confirm confirm;
	
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=Confirm.class)
	@JoinColumn (name="idConfirm")
	public Confirm getConfirm() {
		return confirm;
	}
	public void setConfirm(Confirm confirm) {
		this.confirm = confirm;
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
		return value;
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
