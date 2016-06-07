package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.ScreenElement;

public class Header extends Form {
	public Header(){
		setId(ScreenImplementationsIds.HEADER);
		Div div = new Div();
		add(div);
		div.add(0, new GraphicImage("images", "logo-barra-menu.png", "logo-main-menu"));
		div.add(1, new MainMenu().getMe());
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(Form.class);
	}
}
