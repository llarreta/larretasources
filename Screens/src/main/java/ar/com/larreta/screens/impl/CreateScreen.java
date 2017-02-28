package ar.com.larreta.screens.impl;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.screens.CSSGrid;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.StandardContainer;
import ar.com.larreta.screens.SubmitButton;

public abstract class CreateScreen extends CommonsScreen {

	private static final Logger LOGGER = Logger.getLogger(CreateScreen.class);
	
	private CSSGrid body;
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
		form.setStyleClass("form-create-custom");
		index = 0;
		
		CSSGrid cssGridTitle = new CSSGrid(12);
		cssGridTitle.setStyleClass("createScreen-title");
		Label title = new Label((getEntityClassShortName() + "." + getTitleAction() + ".Title"));
		title.setStyleClass("createScreen-title");
		cssGridTitle.add(0, title);
		form.add(index++, cssGridTitle);
		
		CSSGrid cssGridBackButton = new CSSGrid(12);
		SubmitButton backButton = new SubmitButton();
		backButton.setStyleClass("back-button-custom");
		backButton.setAction("starting");
		backButton.setIcon("fa fa-arrow-left");
		backButton.setNextScreenId(getNextScreenId());
		backButton.setInmediate(Boolean.TRUE);
		cssGridBackButton.add(backButton);
		form.add(index++, cssGridBackButton);
		
		addNewBody();
		
		makeBody();
		
		SubmitButton confirmButton = new SubmitButton(getConfirmAction(), "fa fa-check", "app.confirm", getNextScreenId());
		confirmButton.setStyleClass("custom-confirm-button");
		CSSGrid confirmButtonCss = new CSSGrid(12);
		confirmButtonCss.setStyleClass("confirm-button-custom");
		confirmButtonCss.add(confirmButton);
		form.add(index++, confirmButtonCss);
		
		return form;
	}

	protected abstract void makeBody();
	
	protected void makeButtons() {
		
	}

	protected String getTitleAction() {
		return "Create";
	}

	public void addNewBody() {
		try {
			body = new CSSGrid(12);
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
		addNewBody();
	}

	@Override
	public void postAddMultibox() {
		super.postAddMultibox();
		addNewBody();
	}

	@Transient
	@Override
	public StandardContainer getTargetObject() {
		return body;
	}
}
