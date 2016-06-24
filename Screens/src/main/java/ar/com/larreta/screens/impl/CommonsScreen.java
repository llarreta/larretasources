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
	protected static Header header = new Header();
	protected static Footer footer = new Footer();
	
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
	
	public CommonsScreen(Long id, Class entityClass){
		super(id, entityClass);
		setCommons();
		initialize();
	}

	public CommonsScreen(Long id, Class entityClass, String listener){
		super(id, entityClass);
		setPostActionListenerName(listener);
		setCommons();
	}

	protected void setCommons() {
		setTitleMessage("app.titleApp");
		setStyleSheets(styleSheets);
		add(0, header.getMe());
		add(1, getBody());
		add(2, footer.getMe());
	}
	
	public void initialize(){}
	
	@Transient
	public abstract ScreenElement getBody();

	@Transient
	@Override
	public ScreenElement getMe(){
		return (ScreenElement) getMe(Screen.class);
	}

}
