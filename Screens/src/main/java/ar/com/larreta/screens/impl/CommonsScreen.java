package ar.com.larreta.screens.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import ar.com.larreta.screens.AccordionPanel;
import ar.com.larreta.screens.AjaxButton;
import ar.com.larreta.screens.CSSGrid;
import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.StyleSheet;

public abstract class CommonsScreen extends Screen{

	private static final Logger LOGGER = Logger.getLogger(CommonsScreen.class);
	
	public static final String DATA_VIEW_SELECTED = ScreenUtils.generateExpression("dataView.selected");
	protected static final String DATA_VIEW = ScreenUtils.generateExpression("dataView");
	protected static final String SELECTED = "selected";
	
	protected static Collection<StyleSheet> styleSheets = getStyles();
	
	private static Header header;
	private static Footer footer;
	
	private static Collection<StyleSheet> getStyles(){
		Collection<StyleSheet> styleSheets = new ArrayList<StyleSheet>();
		styleSheets.add(new StyleSheet("css", 				"font-awesome.css"));
		styleSheets.add(new StyleSheet("css", 				"animate.css"));
		styleSheets.add(new StyleSheet("css", 				"font-awesome-animation.min.css"));
		styleSheets.add(new StyleSheet("css", 				"index-style.css"));
		return styleSheets;
	}
	
	public CommonsScreen(Class entityClass){
		super(entityClass);
	}

	public CommonsScreen(Class entityClass, String listener){
		super(entityClass);
		setPostActionListenerName(listener);
	}

	protected void setCommons() {
		setTitleMessage("app.titleApp");
		setStyleSheets(styleSheets);
		
		CSSGrid menuLeft = new CSSGrid(12, 2);
		menuLeft.addExtraClass("menu-left");
		
//		AccordionPanel menuAccordionPanel = new AccordionPanel();
//		menuLeft.add(menuAccordionPanel);
		
		CSSGrid bodyComplete = new CSSGrid(12, 10, true);
		bodyComplete.addExtraClass("body-complete body-complete-full");
		
		CSSGrid header = new CSSGrid(12);
		header.addExtraClass("header-container");
		header.add(getHeader());
		
		bodyComplete.add(0, header);
		
		CSSGrid bodyContainer = new CSSGrid(12, true);
		bodyContainer.addExtraClass("body-container");
		
		CSSGrid bodyRow = new CSSGrid(true);
		bodyRow.add(1, getBody());
		
		AjaxButton showHideMenuLeft = new AjaxButton();
		
		/**Este tipo de cosas podrian ser reemplazadas por
			
			showHideMenuLeft.setIcon(Icon.CHEVRON_RIGHT); 
			
			Inclusive podemos agregarle que tiene incorporados tamaños por default tiene por ejemplo estos 
			<i class="fa fa-camera-retro fa-lg"></i> fa-lg
			<i class="fa fa-camera-retro fa-2x"></i> fa-2x
			<i class="fa fa-camera-retro fa-3x"></i> fa-3x
			<i class="fa fa-camera-retro fa-4x"></i> fa-4x
			<i class="fa fa-camera-retro fa-5x"></i> fa-5x
			
			Nosotros podriamos tenerlo asi: 
			showHideMenuLeft.setIcon(Icon.CHEVRON_RIGHT, Icon.SIZE_LARGE);
			showHideMenuLeft.setIcon(Icon.CHEVRON_RIGHT, Icon.SIZE_2);
			showHideMenuLeft.setIcon(Icon.CHEVRON_RIGHT, Icon.SIZE_3);
			showHideMenuLeft.setIcon(Icon.CHEVRON_RIGHT, Icon.SIZE_4);
			showHideMenuLeft.setIcon(Icon.CHEVRON_RIGHT, Icon.SIZE_5); 
		*/
		showHideMenuLeft.setIcon("fa fa-bars");
		showHideMenuLeft.setStyleClass("button-menu-left");
		showHideMenuLeft.setOnclick("showHideMenuLeft()");
		bodyRow.add(0, showHideMenuLeft);
		
		bodyContainer.add(0, bodyRow);
		
		bodyComplete.add(1, bodyContainer);
		
		CSSGrid footer = new CSSGrid(12);
		footer.add(getFooter());
		
		//UML Y ETAPAS TOMA EN EL FINAL CLAUDIO
		add(0, menuLeft);
		add(1, bodyComplete);
		add(2, footer);
		
	}

	protected ScreenElement getFooter() {
		if (footer==null){
			footer = ScreenUtils.getFooter();
			footer.initialize();
		}
		return footer;
	}

	protected ScreenElement getHeader() {
		if (header==null){
			header = ScreenUtils.getHeader();
			header.initialize();
		}
		return header;
	}
	
	public void initialize(){
		setCommons();
	}
	
	@Transient
	public abstract ScreenElement getBody();

	@Override
	public String getPersistEntityName() {
		return Screen.class.getName();
	}

}
