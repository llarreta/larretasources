package ar.com.larreta.screens.impl;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.screens.AjaxButton;
import ar.com.larreta.screens.Attribute;
import ar.com.larreta.screens.Column;
import ar.com.larreta.screens.Confirm;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.PropertyActionListener;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.SubmitButton;
import ar.com.larreta.screens.Table;

@MappedSuperclass
public abstract class MainScreen extends CommonsScreen {

	private static final String DELETE = "delete";
	protected Table table;

	@Override
	public Long getId() {
		return screenConstantIds.getIdentifier(getEntityClassShortName() + "Main");
	}
	
	@Transient
	public Table getTable() {
		return table;
	}

	public MainScreen(Class entityClass){
		super(entityClass);
	}
	
	public MainScreen(Class entityClass, String listener){
		super(entityClass, listener);
	}

	@Override
	public ScreenElement getBody() {
		Form form = new Form();

		form.add(0, new Label(getEntityClassShortName() + "." + getTitleAction() + ".Title"));
		
		table = new Table();
		table.setId(screenConstantIds.getIdentifier(getEntityClassShortName() + "MainTable"));
		form.add(1, table);
		
		makeColumns();
	
		table.addColumn(9999, getColumnWithButtons(form.getIdValue()));

		putCreateButton(form);
		
		return form;
	}

	protected String getTitleAction() {
		return "Main";
	}
	
	protected void putCreateButton(Form form) {
		form.add(2, new SubmitButton("create", "ui-icon-plusthick", "app.create", getCreateScreenId()));
	}

	protected abstract void makeColumns();
	
	@Transient
	public Column getColumnWithLabelProperty(String property, String header, String sort, String width){
		Column column = new Column();
		column.setHeaderText(ScreenUtils.generateMessage(header));
		column.setSortBy(sort);
		column.setWidth(width);
		
		Label label = new Label(StringUtils.EMPTY);
		label.setBindingObject(ScreenElement.TABLE_ELEMENT);
		label.setBindingProperty(property);
		column.add(label);
		
		return column;
	}
	
	@Transient
	public Column getColumnWithContainsFilter(String property, String header, String sort, String width){
		Column column = getColumnWithLabelProperty(property, header, sort, width);
		column.setContainsFilter(property);
		return column;
	}
	
	@Transient
	public Column getColumnWithExactFilter(String property, String header, String sort, String width, String entityFilter){
		return getColumnWithExactFilter(property, header, sort, width, entityFilter, property);
	}

	@Transient
	public Column getColumnWithExactFilter(String property, String header, String sort, String width,
			String entityFilter, String filterProperty) {
		Column column = getColumnWithLabelProperty(property, header, sort, width);
		column.setExactFilter(filterProperty, entityFilter);
		return column;
	}

	@Transient
	public Column getColumnWithButtons(String update){
		Column column = new Column();
		
		PanelGrid panelGrid = new PanelGrid();
		panelGrid.setColumns("2");
		
		SubmitButton updateButton = new SubmitButton("update", "ui-icon-pencil", "app.modify", getUpdateScreenId());
		updateButton.setInmediate(Boolean.TRUE);
		updateButton.add(new PropertyActionListener(DATA_VIEW, SELECTED, TABLE_ELEMENT));
		
		AjaxButton deleteButton = new AjaxButton();
		deleteButton.setActionListenerObject(CONTROLLER);
		deleteButton.setActionListenerMethod(DELETE);
		deleteButton.setIcon("ui-icon-trash");
		deleteButton.setValue("app.delete");
		deleteButton.setInmediate(Boolean.TRUE);
		deleteButton.setUpdate(update);
		
		Attribute attribute = new Attribute();
		attribute.setBindingObject(TABLE_ELEMENT);
		attribute.setName(SELECTED);
		
		Confirm confirm = getConfirm();
		
		deleteButton.setConfirm(confirm);
		
		deleteButton.add(attribute);
		
		panelGrid.add(0, updateButton);
		addDeleteButton(panelGrid, deleteButton);
		column.add(panelGrid);
		
		return column;	
	}

	protected void addDeleteButton(PanelGrid panelGrid, AjaxButton deleteButton) {
		panelGrid.add(1, deleteButton);
	}

	protected Confirm getConfirm() {
		Confirm confirm = new Confirm();
		confirm.setHeader(ScreenUtils.generateMessage("app.confirm"));
		confirm.setMessage(ScreenUtils.generateMessage("app.confirmRemove"));
		confirm.setIcon("ui-icon-alert");
		
		SubmitButton yesButton = new SubmitButton();
		yesButton.setMessageValue("app.yes");
		yesButton.setStyleClass("ui-confirmdialog-yes");
		yesButton.setIcon("ui-icon-check");
		yesButton.setButtonType("button");
		
		SubmitButton noButton = new SubmitButton();
		noButton.setMessageValue("app.no");
		noButton.setStyleClass("ui-confirmdialog-no");
		noButton.setIcon("ui-icon-close");
		noButton.setButtonType("button");
		
		confirm.add(0, yesButton);
		confirm.add(1, noButton);
		return confirm;
	}

	public Long getCreateScreenId(){
		return screenConstantIds.getIdentifier(getEntityClassShortName() + "Create");
	}

	public Long getUpdateScreenId(){
		return screenConstantIds.getIdentifier(getEntityClassShortName() + "Update");
	}


}
