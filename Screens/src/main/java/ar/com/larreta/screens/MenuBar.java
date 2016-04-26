package ar.com.larreta.screens;

import java.util.Iterator;

import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

public class MenuBar extends Container {
	
	private MenuModel menuModel;
	
	public MenuModel getMenuModel() {
		if (menuModel==null){
			menuModel = new DefaultMenuModel();
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
