package ar.com.larreta.screens.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

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
		styleSheets.add(new StyleSheet("css", 				"main.css"));
		styleSheets.add(new StyleSheet("smarttrace/css", 	"bootstrap.min.css"));
		styleSheets.add(new StyleSheet("css", 				"font-awesome.css"));
		styleSheets.add(new StyleSheet("css", 				"animate.css"));
		styleSheets.add(new StyleSheet("css", 				"socicon.css"));
		styleSheets.add(new StyleSheet("css", 				"font-awesome-animation.min.css"));
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
		add(0, getHeader());
		add(1, getBody());
		add(2, getFooter());
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
