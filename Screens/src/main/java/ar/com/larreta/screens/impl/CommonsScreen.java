package ar.com.larreta.screens.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ar.com.larreta.screens.AjaxButton;
import ar.com.larreta.screens.Column;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.StyleSheet;
import ar.com.larreta.screens.SubmitButton;

public abstract class CommonsScreen extends Screen{
	
	private static final Logger LOGGER = Logger.getLogger(CommonsScreen.class);
	
	protected static Collection<StyleSheet> styleSheets = getStyles();
	protected static Header header = new Header();
	protected static Footer footer = new Footer();
	
	private static Collection<StyleSheet> getStyles(){
		Collection<StyleSheet> styleSheets = new ArrayList<StyleSheet>();
		styleSheets.add(new StyleSheet("css", 			"main.css"));
		styleSheets.add(new StyleSheet("smarttrace/css", 	"bootstrap.min.css"));
		styleSheets.add(new StyleSheet("css", 			"font-awesome.css"));
		styleSheets.add(new StyleSheet("css", 			"animate.css"));
		styleSheets.add(new StyleSheet("css", 			"socicon.css"));
		styleSheets.add(new StyleSheet("css", 			"font-awesome-animation.min.css"));
		return styleSheets;
	}
	
	public CommonsScreen(Long id, Class entityClass){
		super(id, entityClass);
		setTitleMessage("app.titleApp");
		setStyleSheets(styleSheets);
		add(0, header.getMe());
		add(1, getBody());
		add(2, footer.getMe());
	}

	@Transient
	public abstract ScreenElement getBody();

	@Transient
	public Column getColumnWithLabelProperty(String property, String header, String sort, String width){
		Column column = new Column();
		column.setHeaderText(header);
		column.setSortBy(sort);
		column.setWidth(width);
		
		Label label = new Label(StringUtils.EMPTY);
		label.setBindingObject(ScreenElement.TABLE_ELEMENT);
		label.setBindingProperty(property);
		column.add(label);
		
		return column;
	}
	
	@Transient
	public Column getColumnWithButtons(){
		Column column = new Column();
		
		PanelGrid panelGrid = new PanelGrid();
		panelGrid.setColumns("2");
		
		SubmitButton updateButton = new SubmitButton();
		updateButton.setAction("update");
		updateButton.setIcon("ui-icon-pencil");
		updateButton.setValue("app.modify");
		updateButton.setInmediate(Boolean.TRUE);
		
		AjaxButton deleteButton = new AjaxButton();
		deleteButton.setActionListener("#{controller.delete}");
		deleteButton.setIcon("ui-icon-trash");
		deleteButton.setValue("app.delete");
		deleteButton.setInmediate(Boolean.TRUE);
		
		panelGrid.add(updateButton);
		panelGrid.add(deleteButton);
		column.add(panelGrid);
		
		return column;	
	}
	
	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(Screen.class);
	}

}
