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
		add(0, new MenuItem("app.language", APP_SCREEN_URL + ScreenImplementationsIds.LANGUAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(1, new MenuItem("app.country", APP_SCREEN_URL + ScreenImplementationsIds.COUNTRY_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(2, new MenuItem("app.resourceMessage", APP_SCREEN_URL + ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(3, new MenuItem("app.role", APP_SCREEN_URL + ScreenImplementationsIds.ROLE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(4, new MenuItem("app.profile", APP_SCREEN_URL + ScreenImplementationsIds.PROFILE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(5, new MenuItem("app.user", APP_SCREEN_URL + ScreenImplementationsIds.USER_MAIN, MENU_ITEM_DEFAULT_STYLE));
		add(6, new MenuItem("app.security", APP_SCREEN_URL + ScreenImplementationsIds.SECURITY_UPDATE, MENU_ITEM_DEFAULT_STYLE));
		add(7, new MenuItem("app.securityMatcher", APP_SCREEN_URL + ScreenImplementationsIds.SECURITY_MATCHER_MAIN, MENU_ITEM_DEFAULT_STYLE));
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(MenuBar.class);
	}
}
