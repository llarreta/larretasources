package ar.com.larreta.screens.impl;

import org.dom4j.rule.Stylesheet;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.commons.domain.Language;
import ar.com.larreta.commons.domain.Message;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.StyleSheet;
import ar.com.larreta.screens.SubMenu;

@Component(MainMenu.MAIN_MENU)
public class MainMenu extends MenuBar {
	public static final String MAIN_MENU = "mainMenu";
	protected static final String MENU_ITEM_DEFAULT_STYLE = "";
	protected static final String APP_SCREEN_URL = "/app/screen?screenId=";

	protected Integer index = 0;
	
	public MainMenu(){
		super("main-bar-1");
		setId(screenConstantIds.getIdentifier(MAIN_MENU));
	}

	public Long getMainScreenClass(Class entity){
		return screenConstantIds.getIdentifier(entity.getSimpleName() + "Main");
	}
	
	public void initialize() {
		setId(screenConstantIds.getIdentifier("mainMenu"));
		
		SubMenu general = new SubMenu("app.generalMenu", "");
		general.add(0, new MenuItem("app.language", APP_SCREEN_URL + getMainScreenClass(Language.class), MENU_ITEM_DEFAULT_STYLE));
		general.add(1, new MenuItem("app.country", APP_SCREEN_URL + getMainScreenClass(Country.class), MENU_ITEM_DEFAULT_STYLE));
		general.add(2, new MenuItem("app.resourceMessage", APP_SCREEN_URL + getMainScreenClass(ResourceMessage.class), MENU_ITEM_DEFAULT_STYLE));

		SubMenu users = new SubMenu("app.usersMenu", "");
		users.add(0, new MenuItem("app.role", APP_SCREEN_URL + getMainScreenClass(Role.class), MENU_ITEM_DEFAULT_STYLE));
		users.add(1, new MenuItem("app.profile", APP_SCREEN_URL + getMainScreenClass(Profile.class), MENU_ITEM_DEFAULT_STYLE));
		users.add(2, new MenuItem("app.user", APP_SCREEN_URL + getMainScreenClass(User.class), MENU_ITEM_DEFAULT_STYLE));
		
		SubMenu security = new SubMenu("app.securityMenu", "");
		security.add(0, new MenuItem("app.security", APP_SCREEN_URL + screenConstantIds.getIdentifier(Security.class.getSimpleName() + "Update"), MENU_ITEM_DEFAULT_STYLE));
		security.add(1, new MenuItem("app.securityMatcher", APP_SCREEN_URL + getMainScreenClass(SecurityMatcher.class), MENU_ITEM_DEFAULT_STYLE));
		
		//SubMenu root = new SubMenu("app.root", "");
		//root.add(0, new MenuItem("app.stylesheet", APP_SCREEN_URL + screenConstantIds.getIdentifier(StyleSheet.class.getSimpleName() + "Update"), MENU_ITEM_DEFAULT_STYLE));
		
		SubMenu preferences = new SubMenu("app.preferences", "");
		preferences.add(0, general);
		preferences.add(1, users);
		preferences.add(2, security);
		//preferences.add(3, root);
		
		add(index++, preferences);
		//add(index++, new MenuItem("app.message", APP_SCREEN_URL + getMainScreenClass(Message.class), MENU_ITEM_DEFAULT_STYLE));
	}

	@Override
	public String getPersistEntityName() {
		return MenuBar.class.getName();
	}
}
