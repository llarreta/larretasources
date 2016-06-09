package ar.com.larreta.screens.impl;

import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.SubmitButton;

public abstract class CreateScreen extends CommonsScreen {

	private PanelGrid body;

	public abstract Long getNextScreenId();

	public CreateScreen(Long id, Class entityClass) {
		super(id, entityClass);
	}
	
	@Override
	public ScreenElement getBody() {
		Form form = new Form();
		
		body = new PanelGrid(2);
		form.add(0, body);
		
		makeBody();
		
		form.add(1, new SubmitButton(getConfirmAction(), "ui-icon-check", "app.confirm", getNextScreenId()));
		form.add(2, new SubmitButton("starting", "ui-icon-check", "app.back", getNextScreenId()));

		return form;
	}

	protected  abstract void makeBody();

	protected String getConfirmAction() {
		return "preCreate";
	}

	public Integer addInput(Integer index, String labelText, String dataViewSelectedProperty) {
		body.add(index++, new Label(labelText));
		body.add(index++, new Input(DATA_VIEW_SELECTED, dataViewSelectedProperty));
		return index;
	}
	
}
