package ar.com.larreta.screens;

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

import org.hibernate.annotations.Where;

@Entity
@Table(name = "confirm")
@DiscriminatorValue(value = "confirm")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Confirm extends ScreenElement {

	private String header;
	private String message;
	private String icon;
	private Boolean global;
	private String showEffect;
	private String hideEffect;
	private Set<SubmitButton> submitButtons;
	private Set<AjaxButton> ajaxButtons;

	@OneToMany (mappedBy="confirm", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=AjaxButton.class)
	@Where(clause="deleted IS NULL")
	public Set<AjaxButton> getAjaxButtons() {
		return ajaxButtons;
	}
	public void setAjaxButtons(Set<AjaxButton> ajaxButtons) {
		this.ajaxButtons = ajaxButtons;
	}
	@OneToMany (mappedBy="confirm", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=SubmitButton.class)
	@Where(clause="deleted IS NULL")
	public Set<SubmitButton> getSubmitButtons() {
		return submitButtons;
	}
	public void setSubmitButtons(Set<SubmitButton> buttons) {
		this.submitButtons = buttons;
	}
	
	@Transient
	public Set<Button> getButtons(){
		Set<Button> buttons = new HashSet<Button>();
		buttons.addAll(getAjaxButtons());
		buttons.addAll(getSubmitButtons());
		return buttons;
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
