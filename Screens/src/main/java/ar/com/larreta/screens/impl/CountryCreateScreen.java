package ar.com.larreta.screens.impl;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.SubmitButton;

public class CountryCreateScreen extends CommonsScreen {

	public CountryCreateScreen(){
		super(ScreenImplementationsIds.COUNTRY_CREATE, Country.class);	
	}
	
	@Override
	public ScreenElement getBody() {
		Form form = new Form();
		
		PanelGrid panelGrid = new PanelGrid(2);
		form.add(panelGrid);
		
		panelGrid.add(0, new Label("app.nick"));
		Input inputNick = new Input();
		inputNick.setBindingObject("#{dataView.selected}");
		inputNick.setBindingProperty("nick");
		panelGrid.add(1, inputNick);
		
		panelGrid.add(2, new Label("app.email"));
		Input inputEmail = new Input();
		inputEmail.setBindingObject("#{dataView.selected}");
		inputEmail.setBindingProperty("email");
		panelGrid.add(3, inputEmail);

		SubmitButton submitButton = new SubmitButton();
		submitButton.setAction("preCreate");
		submitButton.setValue("app.confirm");
		submitButton.setIcon("ui-icon-check");
		submitButton.setNextScreenId(new Long(2));
		form.add(submitButton);
		
		SubmitButton returnButton = new SubmitButton();
		returnButton.setAction("starting");
		returnButton.setValue("app.back");
		returnButton.setIcon("ui-icon-check");
		returnButton.setNextScreenId(ScreenImplementationsIds.COUNTRY_MAIN);
		form.add(returnButton);

		return form;
	}

}
