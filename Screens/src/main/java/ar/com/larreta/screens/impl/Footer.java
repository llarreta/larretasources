package ar.com.larreta.screens.impl;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.CommandLink;
import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.Label;

@Component(Footer.FOOTER)
public class Footer extends Div {

	public static final String FOOTER = "footer";

	public Footer(){
		super("ui-grid-col-12");
	}
	
	@Override
	public void initialize() {
		super.initialize();
		setId(screenConstantIds.getIdentifier("footer"));
		Div interior = new Div("footer-standar");
		add(0, interior);
		interior.add(0, new GraphicImage("images", "logosmarttrace-blanco.png", "logo-footer"));
		interior.add(1, new CommandLink("socicon socicon-twitter sc-2x social-networks-icons twitter-position"));
		interior.add(2, new CommandLink("socicon socicon-facebook sc-2x social-networks-icons facebook-position"));
		interior.add(4, new CommandLink("socicon socicon-google sc-2x social-networks-icons google-position"));
		interior.add(5, new Label("loggin.labelRights", "label-rights"));	
	}

	@Override
	public String getPersistEntityName() {
		return Div.class.getName();
	}
}
