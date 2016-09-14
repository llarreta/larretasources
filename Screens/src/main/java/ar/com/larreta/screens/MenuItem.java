package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.menu.DefaultMenuItem;

@Entity
@Table(name = "menuItem")
@DiscriminatorValue(value = "menuItem")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class MenuItem extends ScreenElement implements MenuElement{

	private DefaultMenuItem menuItem;
	private String value;
	private String url;
	private String onclick = "PF('blockUI').block()";

	public MenuItem(){}
	
	public MenuItem(String value, String url, String styleClass){
		this();
		setValueMessage(value);
		setUrl(url);
		setStyleClass(styleClass);
	}
	
	@Transient
	public org.primefaces.model.menu.MenuItem getMenuItem() {
		if (menuItem==null){
			menuItem = new DefaultMenuItem();
			menuItem.setValue(getValueEvaluated());
			menuItem.setUrl(url);
			menuItem.setStyleClass(getStyleClass());
			menuItem.setOnclick(onclick);
		}
		return menuItem;
	}
	
	@Basic
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setMenuItem(DefaultMenuItem menuItem) {
		this.menuItem = menuItem;
	}
	
	@Transient
	public String getValueEvaluated() {
		return (String) ScreenUtils.evaluate(getValue());
	}
	
	@Basic
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValueMessage(String value) {
		this.value = ScreenUtils.generateMessage(value);
	}
	
	
	@Basic
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Transient
	public org.primefaces.model.menu.MenuElement getElement() {
		return getMenuItem();
	}
	
	
	
}
