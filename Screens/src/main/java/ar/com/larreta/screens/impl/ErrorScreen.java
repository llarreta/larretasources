package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.ScreenElement;

@Component(ErrorScreen.ERROR_SCREEN)
public class ErrorScreen extends CommonsScreen {

	public static final String ERROR_SCREEN = "errorScreen";
	private static final Long HOME_ID = new Long(-2);

	public ErrorScreen(){
		super(null);
	}
	
	@Transient
	@Override
	public Long getId() {
		return HOME_ID;
	}
	
	@Override
	public ScreenElement getBody() {
		Label label = new Label("Ocurrio un error inesperado");
		return label;
	}

}
