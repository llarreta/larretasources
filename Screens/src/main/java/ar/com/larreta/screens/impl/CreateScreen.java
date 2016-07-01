package ar.com.larreta.screens.impl;

import ar.com.larreta.screens.ComboBox;
import ar.com.larreta.screens.ComboBoxItem;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.MultiBox;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.Password;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.SubmitButton;

public abstract class CreateScreen extends CommonsScreen {

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
		addNewBody(2);
		
		makeBody();
		
		form.add(index++, new SubmitButton(getConfirmAction(), "ui-icon-check", "app.confirm", getNextScreenId()));
		SubmitButton backButton = new SubmitButton("starting", "ui-icon-check", "app.back", getNextScreenId());
		backButton.setInmediate(Boolean.TRUE);
		form.add(index++, backButton);

		return form;
	}

	protected void addNewBody(Integer columns) {
		try {
			body = new PanelGrid(columns);
			form.add(index++, body);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	protected  abstract void makeBody();

	protected String getConfirmAction() {
		return "preCreate";
	}

	public Integer addPassword(Integer index, String labelText, String dataViewSelectedProperty) {
		body.add(index++, new Label(labelText));
		body.add(index++, new Password(DATA_VIEW_SELECTED, dataViewSelectedProperty));
		return index;
	}
	
	public Integer addInput(Integer index, String labelText, String dataViewSelectedProperty) {
		body.add(index++, new Label(labelText));
		body.add(index++, new Input(DATA_VIEW_SELECTED, dataViewSelectedProperty));
		return index;
	}
	
	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType) {
		body.add(index++, new Label(labelText));
		ComboBox comboBox = new ComboBox();
		comboBox.setBindingObject(DATA_VIEW_SELECTED);
		comboBox.setBindingProperty(dataViewSelectedProperty);
		comboBox.setEntityType(entityType);
		
		comboBox.addComboBoxItem(getVoidItem());
		
		body.add(index++, comboBox);
		return index;
	}
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, String entityType, String propertyItemLabel){
		addNewBody(1);
		
		MultiBox multiBox = new MultiBox();
		multiBox.setSourceCaption(sourceCaption);
		multiBox.setTargetCaption(targetCaption);
		multiBox.setEntityType(entityType);
		multiBox.setBindingObject(DATA_VIEW_SELECTED);
		multiBox.setBindingProperty(dataViewSelectedProperty);
		multiBox.setPropertyItemLabel(propertyItemLabel);
		
		body.add(index++, multiBox);
		
		addNewBody(2);
		return index;
	}
	
	public Integer addInBody(Integer index, ScreenElement element){
		body.add(index++, element);
		return index;
	}
	

	protected ComboBoxItem getVoidItem() {
		ComboBoxItem comboBoxItem = new ComboBoxItem();
		comboBoxItem.setItemLabel(ScreenUtils.generateMessage("app.selection.void"));
		comboBoxItem.setNoSelectionOption(Boolean.TRUE.toString());
		return comboBoxItem;
	}
	
	
}
