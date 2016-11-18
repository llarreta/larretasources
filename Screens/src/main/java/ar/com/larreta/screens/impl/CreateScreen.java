package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.StandardContainer;
import ar.com.larreta.screens.SubmitButton;

public abstract class CreateScreen extends CommonsScreen {

	private static final Logger LOGGER = Logger.getLogger(CreateScreen.class);
	
	private PanelGrid body;
	private Integer index;
	private Form form;
	
	@Override
	public Long getId() {
		return screenConstantIds.getIdentifier(getEntityClassShortName() + "Create");
	}
	
	public Form getForm() {
		return form;
	}

	public Long getNextScreenId(){
		return screenConstantIds.getIdentifier(getEntityClassShortName() + "Main");
	}

	public CreateScreen(Class entityClass) {
		super(entityClass);
	}
	
	public CreateScreen(Class entityClass, String listener){
		super(entityClass, listener);
	}
	
	@Override
	public ScreenElement getBody() {
		form = new Form();
		
		index = 0;
		form.add(index++, new Label(getEntityClassShortName() + "." + getTitleAction() + ".Title"));
		addNewBody(2);
		
		makeBody();
		makeButtons();

		return form;
	}

	protected abstract void makeBody();
	
	protected void makeButtons() {
		form.add(index++, new SubmitButton(getConfirmAction(), "ui-icon-check", "app.confirm", getNextScreenId()));
		SubmitButton backButton = new SubmitButton("starting", "ui-icon-check", "app.back", getNextScreenId());
		backButton.setInmediate(Boolean.TRUE);
		form.add(index++, backButton);
	}

	protected String getTitleAction() {
		return "Create";
	}

	public void addNewBody(Integer columns) {
		try {
			body = new PanelGrid(columns);
			form.add(index++, body);
		} catch (Exception e){
			LOGGER.error(AppException.getStackTrace(e));
		}
	}

	protected String getConfirmAction() {
		return "preCreate";
	}
	
	public Integer addInBody(Integer index, ScreenElement element){
		body.add(index++, element);
		return index;
	}
	
	@Override
	public void preAddMultibox() {
		super.preAddMultibox();
		addNewBody(1);
	}

	@Override
	public void postAddMultibox() {
		super.postAddMultibox();
		addNewBody(2);
	}

	@Transient
	@Override
	public StandardContainer getTargetObject() {
		return body;
	}
}
