package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.ScreenElement;

public class MainMenu extends MenuBar {
	private static final String MENU_ITEM_DEFAULT_STYLE = "menu-item-default";
	private static final String APP_SCREEN_URL = "/app/screen?screenId=";

	public MainMenu(){
		super("main-bar-1");
		setId(ScreenImplementationsIds.MAIN_MENU);
		add(new MenuItem("Language", APP_SCREEN_URL + ScreenImplementationsIds.LANGUAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(new MenuItem("Country", APP_SCREEN_URL + ScreenImplementationsIds.COUNTRY_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(new MenuItem("Resource Message", APP_SCREEN_URL + ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(MenuBar.class);
	}
}
