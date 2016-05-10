package ar.com.larreta.screens;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "confirm")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Confirm extends ScreenElement {

	private String header;
	private String message;
	private String icon;
	private Boolean global;
	private String showEffect;
	private String hideEffect;
	private Set<Button> buttons;
	
	@OneToMany (mappedBy="confirm", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Button.class)
	@Where(clause="deleted IS NULL")
	public Set<Button> getButtons() {
		return buttons;
	}
	public void setButtons(Set<Button> buttons) {
		this.buttons = buttons;
	}
	
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
	
	@Basic
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
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
