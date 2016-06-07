package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.SubMenu;

public class MainMenu extends MenuBar {
	public MainMenu(){
		super("main-bar-1");
		setId(ScreenImplementationsIds.MAIN_MENU);
		add(new MenuItem("main-bar.init", "/app/home", "menu-item-default"));
		SubMenu subMenu = new SubMenu("main-bar.basicConfigurations", "sub-menu-main");
		subMenu.add(new MenuItem("main-bar.init", "/app/home", "menu-item-default"));
		add(subMenu);
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(MenuBar.class);
	}
}
