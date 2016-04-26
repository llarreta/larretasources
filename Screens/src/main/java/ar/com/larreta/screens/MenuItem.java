package ar.com.larreta.screens;

import org.primefaces.model.menu.DefaultMenuItem;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;

public class MenuItem extends ScreenElement implements MenuElement{

	private DefaultMenuItem menuItem;
	private String value;
	private String url;

	public org.primefaces.model.menu.MenuItem getMenuItem() {
		if (menuItem==null){
			menuItem = new DefaultMenuItem();
			menuItem.setValue(getValue());
			menuItem.setUrl(url);
			menuItem.setStyleClass(getStyleClass());
		}
		return menuItem;
	}
	public void setMenuItem(DefaultMenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public String getValue() {
		return StandardControllerImpl.getMessage(value);
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public org.primefaces.model.menu.MenuElement getElement() {
		return getMenuItem();
	}
	
	
	
}
