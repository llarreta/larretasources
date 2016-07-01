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
		add(div);
		div.add(0, new GraphicImage("images", "logo-barra-menu.png", "logo-main-menu"));
		MenuBar menuBar = ScreenUtils.getMainMenu();
		menuBar.initialize();
		div.add(1, menuBar);
	}

	@Override
	public String getPersistEntityName() {
		return Form.class.getName();
	}
}
