package ar.com.larreta.screens.impl;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuButton;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.ScreenUtils;

@Component(Header.HEADER)
public class Header extends Form {

	public static final String HEADER = "header";

	public Header(){
	}

	@Override
	public void initialize() {
		super.initialize();
		
		setId(screenConstantIds.getIdentifier("header"));
		Div header = new Div();
		header.setStyleClass("ui-g header-style");
		add(header);
		
		Div headerTop = new Div();
		
		Div logoContainer = new Div();
		logoContainer.setStyleClass("ui-g-4");
		
		GraphicImage logo = new GraphicImage("images", "LogoCommons.png", "LogoCommons");
		logo.setStyleClass("main-logo");
		
		logoContainer.add(0, logo);
		
		headerTop.add(0, logoContainer);
		
		Div searchSection = new Div();
		searchSection.setStyleClass("ui-g-4");
		
		Div searchInputContainer = new Div();
		searchInputContainer.setStyleClass("ui-g-10");
		
		Div searchInputInnerContainer = new Div();
		searchInputInnerContainer.setStyleClass("search-input-inner-container");
		
		Input searchInput = new Input();
		searchInput.setStyleClass("search-input");
		
		searchInputInnerContainer.add(0, searchInput);
		
		searchInputContainer.add(0, searchInputInnerContainer);
		
		Div iconSearch = new Div();
		iconSearch.setStyleClass("search-icon fa fa-search fa-2x ui-g-2");
		
		searchSection.add(0, searchInputContainer);
		searchSection.add(1, iconSearch);
		headerTop.add(1, searchSection);
		
		Div notificationSection = new Div();
		notificationSection.setStyleClass("ui-g-4");
		
		Div notificationMenuContainer = new Div();
		notificationMenuContainer.setStyleClass("ui-g-2 notification-section");
		
		Div notificationMenuInnerContainer = new Div();
		notificationMenuInnerContainer.setStyleClass("notification-menu-inner-container");
		
		Label notificationMenuIcon = new Label();
		notificationMenuIcon.setStyleClass("notification-menu-icon");
		notificationMenuIcon.setValue("3");
		
		notificationMenuInnerContainer.add(0, notificationMenuIcon);
		
		MenuButton notificationMenu = new MenuButton("fa fa-bell fa-2x notification-menu");
		
		for(Integer i = 0; i < 3; i++){
			MenuItem exampleNotification = new MenuItem();
			exampleNotification.setValue("Notificacion " + i);
			exampleNotification.setUrl("#");
			notificationMenu.add(i, exampleNotification);
		}
		
		notificationMenuInnerContainer.add(1, notificationMenu);
		
		notificationMenuContainer.add(0, notificationMenuInnerContainer);
		
		Div chatMenuContainer = new Div();
		chatMenuContainer.setStyleClass("ui-g-2 notification-section");

		Div notificationChatInnerContainer = new Div();
		notificationChatInnerContainer.setStyleClass("notification-chat-inner-container");
		
		MenuButton chatMenu = new MenuButton("fa fa-comments fa-2x chat-menu");
		
		Label chatMenuIcon = new Label();
		chatMenuIcon.setStyleClass("chat-menu-icon");
		chatMenuIcon.setValue("3");
		
		notificationChatInnerContainer.add(0, chatMenuIcon);
		
		for(Integer i = 0; i < 3; i++){
			MenuItem exampleChat = new MenuItem();
			exampleChat.setValue("Chat " + i);
			exampleChat.setUrl("#");
			chatMenu.add(i, exampleChat);
		}
		
		notificationChatInnerContainer.add(1, chatMenu);
		
		chatMenuContainer.add(0, notificationChatInnerContainer);
		
		Div configurationMenuContainer = new Div();
		configurationMenuContainer.setStyleClass("ui-g-2 notification-section");
		
		Div notificationConfigurationInnerContainer = new Div();
		notificationConfigurationInnerContainer.setStyleClass("notification-configuration-inner-container");
		
		MenuButton configurationMenu = new MenuButton("fa fa-cog fa-2x configuration-menu");
		
		Label configurationMenuIcon = new Label();
		configurationMenuIcon.setStyleClass("configuration-menu-icon");
		configurationMenuIcon.setValue("3");
		
		notificationConfigurationInnerContainer.add(0, configurationMenuIcon);
		
		for(Integer i = 0; i < 3; i++){
			MenuItem exampleConfiguration = new MenuItem();
			exampleConfiguration.setValue("Configuration " + i);
			exampleConfiguration.setUrl("#");
			configurationMenu.add(i, exampleConfiguration);
		}
		
		notificationConfigurationInnerContainer.add(1, configurationMenu);
		
		configurationMenuContainer.add(0, notificationConfigurationInnerContainer);
		
		notificationSection.add(2, notificationMenuContainer);
		notificationSection.add(1, chatMenuContainer);
		notificationSection.add(0, configurationMenuContainer);
		
		headerTop.add(2, notificationSection);
		headerTop.setStyleClass("header-top");	
		
		MenuBar menuBar = ScreenUtils.getMainMenu();
		menuBar.initialize();
		
		header.add(0, headerTop);
		header.add(1, menuBar);
	}

	@Override
	public String getPersistEntityName() {
		return Form.class.getName();
	}
}
