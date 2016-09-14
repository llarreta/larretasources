package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Button extends ValuedElement {

	private String icon;
	protected Boolean ajax;
	private Boolean inmediate = Boolean.FALSE;
	private Confirm confirm;
	private String buttonType;
	protected String onstart;
	protected String oncomplete;
	protected String onclick;
	
	@Basic
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
	@Basic
	public String getOnstart() {
		return onstart;
	}
	public void setOnstart(String onstart) {
		this.onstart = onstart;
	}
	
	@Basic
	public String getOncomplete() {
		return oncomplete;
	}
	public void setOncomplete(String oncomplete) {
		this.oncomplete = oncomplete;
	}
	@Basic
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	
	@OneToOne (fetch=FetchType.EAGER, targetEntity=Confirm.class, cascade=CascadeType.ALL)
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
	
	@Transient
	public abstract String getActionEvaluated();

	@Transient
	public abstract Long getNextScreenId();

	@Transient
	public Collection getParams(){
		Collection params = new ArrayList();
		if (getConfirm()!=null){
			params.add(getConfirm());
		}
		return params;
	}
}
