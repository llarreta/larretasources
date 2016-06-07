package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import ar.com.larreta.screens.CommandLink;
import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.ScreenElement;

public class Footer extends Div {

	public Footer(){
		super("ui-grid-col-12");
		setId(ScreenImplementationsIds.FOOTER);
		Div interior = new Div("footer-standar");
		add(0, interior);
		interior.add(0, new GraphicImage("images", "logosmarttrace-blanco.png", "logo-footer"));
		interior.add(1, new CommandLink("socicon socicon-twitter sc-2x social-networks-icons twitter-position"));
		interior.add(2, new CommandLink("socicon socicon-facebook sc-2x social-networks-icons facebook-position"));
		interior.add(4, new CommandLink("socicon socicon-google sc-2x social-networks-icons google-position"));
		interior.add(5, new Label("loggin.labelRights", "label-rights"));
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(Div.class);
	}
}
