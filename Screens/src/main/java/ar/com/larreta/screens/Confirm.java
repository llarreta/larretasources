package ar.com.larreta.screens;

import java.util.Set;

public class Confirm extends ScreenElement {

	private String header;
	private String message;
	private String icon;
	private Boolean global;
	private String showEffect;
	private String hideEffect;
	private Set<Button> buttons;
	
	public Set<Button> getButtons() {
		return buttons;
	}
	public void setButtons(Set<Button> buttons) {
		this.buttons = buttons;
	}
	public Boolean getGlobal() {
		return global;
	}
	public void setGlobal(Boolean global) {
		this.global = global;
	}
	public String getShowEffect() {
		return showEffect;
	}
	public void setShowEffect(String showEffect) {
		this.showEffect = showEffect;
	}
	public String getHideEffect() {
		return hideEffect;
	}
	public void setHideEffect(String hideEffect) {
		this.hideEffect = hideEffect;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
}
