package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "confirm")
@DiscriminatorValue(value = "confirm")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Confirm extends Container {

	private String header;
	private String message;
	private String icon;
	private Boolean global = Boolean.TRUE;
	private String showEffect;
	private String hideEffect;

	@Basic
	public Boolean getGlobal() {
		return global;
	}
	public void setGlobal(Boolean global) {
		this.global = global;
	}
	
	@Basic
	public String getShowEffect() {
		return showEffect;
	}
	public void setShowEffect(String showEffect) {
		this.showEffect = showEffect;
	}
	
	@Basic
	public String getHideEffect() {
		return hideEffect;
	}
	public void setHideEffect(String hideEffect) {
		this.hideEffect = hideEffect;
	}
	
	@Transient
	public String getHeaderEvaluated(){
		return (String) ScreenUtils.evaluate(getHeader());
	}
	
	@Basic
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	@Transient
	public String getMessageEvaluated() {
		return  (String) ScreenUtils.evaluate(getMessage());
	}
	
	@Basic
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Basic
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
}
