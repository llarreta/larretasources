package ar.com.larreta.screens;

import java.util.Iterator;

import org.primefaces.model.menu.DefaultSubMenu;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;

public class SubMenu extends Container implements MenuElement{

	private String label;
	private DefaultSubMenu submenu;
	
	public DefaultSubMenu getSubmenu() {
		if (submenu==null){
			submenu = new DefaultSubMenu(getLabel());
			if (elements!=null){
				Iterator it = elements.iterator();
				while (it.hasNext()) {
					MenuElement element = (MenuElement) it.next();
					submenu.addElement(element.getElement());
				}
			}
		}
		return submenu;
	}

	public void setSubmenu(DefaultSubMenu submenu) {
		this.submenu = submenu;
	}
	
	public String getLabel() {
		return StandardControllerImpl.getMessage(label);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void add(ScreenElement element) {
		if ((element instanceof MenuItem) || (element instanceof SubMenu)) {
			super.add(element);
		}
	}

	public org.primefaces.model.menu.MenuElement getElement() {
		return getSubmenu();
	}

}
