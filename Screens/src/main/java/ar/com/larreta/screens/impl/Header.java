package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;

@Component(Header.HEADER)
public class Header extends Form {

	public static final String HEADER = "header";

	public Header(){
	}

	@Override
	public void initialize() {
		super.initialize();
		setId(ScreenImplementationsIds.HEADER);
		Div div = new Div();
		add(div);
		div.add(0, new GraphicImage("images", "logo-barra-menu.png", "logo-main-menu"));
		div.add(1, getMenu());
	}

	
	protected ScreenElement getMenu() {
		return ScreenUtils.getMainMenu().getMe();
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(Form.class);
	}
}
