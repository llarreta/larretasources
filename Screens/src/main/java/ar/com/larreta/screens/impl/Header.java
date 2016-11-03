package ar.com.larreta.screens.impl;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.MenuBar;
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
		Div div = new Div();
		div.setStyleClass("header-style");
		add(div);
		
		Div notificationSection = new Div();
		
		GraphicImage logo = new GraphicImage("images", "LogoCommons.png", "LogoCommons");
		logo.setStyleClass("main-logo");
		
		notificationSection.add(0, logo);
		notificationSection.setStyleClass("notification-section");	
		
		MenuBar menuBar = ScreenUtils.getMainMenu();
		menuBar.initialize();
		notificationSection.add(1, menuBar);
		
		div.add(notificationSection);
	}

	@Override
	public String getPersistEntityName() {
		return Form.class.getName();
	}
}
