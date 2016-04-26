package ar.com.larreta.screens;

public abstract class Button extends ScreenElement {

	private String value;
	private String icon;
	protected Boolean ajax;
	private Boolean inmediate = Boolean.FALSE;
	private String type = "button";
	private Confirm confirm;
	
	public Confirm getConfirm() {
		return confirm;
	}
	public void setConfirm(Confirm confirm) {
		this.confirm = confirm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getInmediate() {
		return inmediate;
	}
	public void setInmediate(Boolean inmediate) {
		this.inmediate = inmediate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getAjax() {
		return ajax;
	}
	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
	}
	
}
