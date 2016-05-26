package ar.com.larreta.screens;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.menu.DefaultSubMenu;

@Entity
@Table(name = "subMenu")
@DiscriminatorValue(value = "subMenu")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class SubMenu extends Container implements MenuElement{

	private String label;
	private DefaultSubMenu submenu;
	
	@Transient
	public DefaultSubMenu getSubmenu() {
		if (submenu==null){
			submenu = new DefaultSubMenu(getLabel());
			Collection<ScreenElement> elements = getOrdererElements();
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
	
	@Basic
	public String getLabel() {
		return label;
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

	@Transient
	public org.primefaces.model.menu.MenuElement getElement() {
		return getSubmenu();
	}

}
