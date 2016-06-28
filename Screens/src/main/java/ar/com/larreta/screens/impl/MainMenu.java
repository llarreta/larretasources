package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.SubMenu;

@Component(MainMenu.MAIN_MENU)
public class MainMenu extends MenuBar {
	public static final String MAIN_MENU = "mainMenu";
	protected static final String MENU_ITEM_DEFAULT_STYLE = "menu-item-default";
	protected static final String APP_SCREEN_URL = "/app/screen?screenId=";

	protected Integer index = 0;
	
	
	
	public MainMenu(){
		super("main-bar-1");
	}

	public void initialize() {
		setId(ScreenImplementationsIds.MAIN_MENU);
		
		SubMenu general = new SubMenu("app.generalMenu", "");
		general.add(0, new MenuItem("app.language", APP_SCREEN_URL + ScreenImplementationsIds.LANGUAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		general.add(1, new MenuItem("app.country", APP_SCREEN_URL + ScreenImplementationsIds.COUNTRY_MAIN, MENU_ITEM_DEFAULT_STYLE));
		general.add(2, new MenuItem("app.resourceMessage", APP_SCREEN_URL + ScreenImplementationsIds.RESOURCE_MESSAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));

		SubMenu users = new SubMenu("app.usersMenu", "");
		users.add(0, new MenuItem("app.role", APP_SCREEN_URL + ScreenImplementationsIds.ROLE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		users.add(1, new MenuItem("app.profile", APP_SCREEN_URL + ScreenImplementationsIds.PROFILE_MAIN, MENU_ITEM_DEFAULT_STYLE));
		users.add(2, new MenuItem("app.user", APP_SCREEN_URL + ScreenImplementationsIds.USER_MAIN, MENU_ITEM_DEFAULT_STYLE));
		
		SubMenu security = new SubMenu("app.securityMenu", "");
		security.add(0, new MenuItem("app.security", APP_SCREEN_URL + ScreenImplementationsIds.SECURITY_UPDATE, MENU_ITEM_DEFAULT_STYLE));
		security.add(1, new MenuItem("app.securityMatcher", APP_SCREEN_URL + ScreenImplementationsIds.SECURITY_MATCHER_MAIN, MENU_ITEM_DEFAULT_STYLE));
		
		SubMenu preferences = new SubMenu("app.preferences", "");
		preferences.add(0, general);
		preferences.add(1, users);
		preferences.add(2, security);
		
		add(index++, preferences);
		add(index++, new MenuItem("app.message", APP_SCREEN_URL + ScreenImplementationsIds.MESSAGE_MAIN, MENU_ITEM_DEFAULT_STYLE));
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(MenuBar.class);
	}
}
