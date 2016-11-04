package ar.com.larreta.screens;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@MappedSuperclass
public abstract class Menu extends StandardContainer {
	private MenuModel menuModel;
	
	public Menu(){}
	
	public Menu(String styleClass){
		this();
		setStyleClass(styleClass);
	}
	
	@Transient
	public MenuModel getMenuModel() {
		if (menuModel==null){
			menuModel = new DefaultMenuModel();
			Collection elements = getOrdererElements();
			if (elements!=null){
				Iterator it = elements.iterator();
				while (it.hasNext()) {
					MenuElement element = (MenuElement) it.next();
					menuModel.addElement(element.getElement());
				}
			}
		}
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	@Override
	public void add(ScreenElement element) {
		if ((element instanceof MenuItem) || (element instanceof SubMenu)) {
			super.add(element);
		}
	}

}
