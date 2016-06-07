package ar.com.larreta.screens.impl;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.SubmitButton;
import ar.com.larreta.screens.Table;


public class CountryMainScreen extends CommonsScreen {

	public CountryMainScreen(){
		super(ScreenImplementationsIds.COUNTRY_MAIN, Country.class);
	}

	@Override
	public ScreenElement getBody() {
		Form form = new Form();
		
		SubmitButton submitButton = new SubmitButton();
		submitButton.setAction("create");
		submitButton.setValue("app.create");
		submitButton.setIcon("ui-icon-plusthick");
		submitButton.setNextScreenId(ScreenImplementationsIds.COUNTRY_CREATE);
		form.add(submitButton);
		
		Table table = new Table();
		//table.setLazyProperties("country,language");
		form.add(table);
		
		table.addColumn(0, getColumnWithLabelProperty("abbreviation", 		ScreenUtils.generateMessage("app.abbreviation"), 	"tableElement.abbreviation",  	"40%"));
		table.addColumn(1, getColumnWithLabelProperty("description", 		ScreenUtils.generateMessage("app.description"), 	"tableElement.description", 	"40%"));
		table.addColumn(2, getColumnWithButtons());
		return form;
	}
	

}
