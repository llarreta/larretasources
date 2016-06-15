package ar.com.larreta.screens.impl;

import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.ScreenElement;

public class HomeScreen extends CommonsScreen {

	public HomeScreen(){
		super(ScreenImplementationsIds.HOME, null);
		
	}
	
	@Override
	public ScreenElement getBody() {
		return new Form();
	}

}
