package ar.com.larreta.screens.impl;

import ar.com.larreta.screens.CheckBox;
import ar.com.larreta.screens.ComboBox;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.ListSelectorItem;
import ar.com.larreta.screens.MultiBox;
import ar.com.larreta.screens.MultiCheckBox;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.Password;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.SubmitButton;
import ar.com.larreta.screens.validators.Validator;

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
		form.add(index++, new Label(getEntityClassShortName() + "." + getTitleAction() + ".Title"));
		addNewBody(2);
		makeBody();
		form.add(index++, new SubmitButton(getConfirmAction(), "ui-icon-check", "app.confirm", getNextScreenId()));
		SubmitButton backButton = new SubmitButton("starting", "ui-icon-check", "app.back", getNextScreenId());
		backButton.setInmediate(Boolean.TRUE);
		form.add(index++, backButton);

		return form;
	}

	protected String getTitleAction() {
		return "Create";
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

	public Integer addPassword(Integer index, String labelText, String dataViewSelectedProperty, Validator validator) {
		Password password = new Password(DATA_VIEW_SELECTED, dataViewSelectedProperty);
		password.addValidator(validator);
		body.add(index++, new Label(labelText));
		body.add(index++, password);
		return index;
	}
	
	public Integer addPassword(Integer index, String labelText, String dataViewSelectedProperty) {
		return addPassword(index, labelText, dataViewSelectedProperty, null);
	}
	
	public Integer addInput(Integer index, String labelText, String dataViewSelectedProperty, Validator validator) {
		Input input = new Input(DATA_VIEW_SELECTED, dataViewSelectedProperty);
		input.addValidator(validator);
		body.add(index++, new Label(labelText));
		body.add(index++, input);
		return index;
	}
	
	public Integer addInput(Integer index, String labelText, String dataViewSelectedProperty) {
		return addInput(index, labelText, dataViewSelectedProperty, null);
	}
	
	public Integer addCheckBox(Integer index, String labelText, String dataViewSelectedProperty) {
		body.add(index++, new Label(labelText));
		body.add(index++, new CheckBox(DATA_VIEW_SELECTED, dataViewSelectedProperty));
		return index;
	}

	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType) {
		return addCombo(index, labelText, dataViewSelectedProperty, entityType, null, null);
	}

	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType, Validator validator) {
		return addCombo(index, labelText, dataViewSelectedProperty, entityType, null, validator);
	}

	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties) {
		return addCombo(index, labelText, dataViewSelectedProperty, entityType, lazyProperties, null);
	}
	
	public Integer addCombo(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties, Validator validator) {
		body.add(index++, new Label(labelText));
		ComboBox comboBox = new ComboBox();
		comboBox.addValidator(validator);
		comboBox.setBindingObject(DATA_VIEW_SELECTED);
		comboBox.setBindingProperty(dataViewSelectedProperty);
		comboBox.setEntityType(entityType);
		comboBox.setLazyProperties(lazyProperties);
		
		comboBox.addItem(getVoidItem());
		
		body.add(index++, comboBox);
		return index;
	}

	public Integer addMultiCheckBox(Integer index, String labelText, String dataViewSelectedProperty, String entityType) {
		return addMultiCheckBox(index, labelText, dataViewSelectedProperty, entityType, null, null);
	}
	
	public Integer addMultiCheckBox(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties){
		return addMultiCheckBox(index, labelText, dataViewSelectedProperty, entityType, lazyProperties, null) ;
	}
	
	public Integer addMultiCheckBox(Integer index, String labelText, String dataViewSelectedProperty, String entityType, String lazyProperties, Validator validator) {
		body.add(index++, new Label(labelText));
		MultiCheckBox multiCheckBox = new MultiCheckBox();
		multiCheckBox.addValidator(validator);
		multiCheckBox.setBindingObject(DATA_VIEW_SELECTED);
		multiCheckBox.setBindingProperty(dataViewSelectedProperty);
		multiCheckBox.setEntityType(entityType);
		multiCheckBox.setLazyProperties(lazyProperties);
		body.add(index++, multiCheckBox);
		return index;
	}
	
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
								String entityType, String propertyItemLabel){
		return addMultiBox(index, sourceCaption, targetCaption, dataViewSelectedProperty, entityType, propertyItemLabel, null, null);
	}
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
								String entityType, String propertyItemLabel, Validator validator){
		return addMultiBox(index, sourceCaption, targetCaption, dataViewSelectedProperty, entityType, propertyItemLabel, null, validator);
	}

	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
			String entityType, String propertyItemLabel, String lazyProperties) {
		return addMultiBox(index, sourceCaption, targetCaption, dataViewSelectedProperty, entityType, propertyItemLabel, lazyProperties, null);
	}
	
	public Integer addMultiBox(Integer index, String sourceCaption, String targetCaption, String dataViewSelectedProperty, 
								String entityType, String propertyItemLabel, String lazyProperties, Validator validator) {
		addNewBody(1);
		
		MultiBox multiBox = new MultiBox();
		multiBox.addValidator(validator);
		multiBox.setSourceCaption(sourceCaption);
		multiBox.setTargetCaption(targetCaption);
		multiBox.setEntityType(entityType);
		multiBox.setBindingObject(DATA_VIEW_SELECTED);
		multiBox.setBindingProperty(dataViewSelectedProperty);
		multiBox.setPropertyItemLabel(propertyItemLabel);
		multiBox.setLazyProperties(lazyProperties);
		
		body.add(index++, multiBox);
		
		addNewBody(2);
		return index;
	}
	
	public Integer addInBody(Integer index, ScreenElement element){
		body.add(index++, element);
		return index;
	}
	

	protected ListSelectorItem getVoidItem() {
		ListSelectorItem item = new ListSelectorItem();
		item.setItemLabel(ScreenUtils.generateMessage("app.selection.void"));
		item.setNoSelectionOption(Boolean.TRUE.toString());
		return item;
	}
	
	
}
