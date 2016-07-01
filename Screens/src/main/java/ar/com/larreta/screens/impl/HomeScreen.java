package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.ScreenElement;

@Component
public class HomeScreen extends CommonsScreen {

	private static final Long HOME_ID = new Long(-1);

	public HomeScreen(){
		super(null);
		
	}
	
	@Transient
	@Override
	public Long getId() {
		return HOME_ID;
	}
	
	@Override
	public ScreenElement getBody() {
		return new Form();
	}

}
