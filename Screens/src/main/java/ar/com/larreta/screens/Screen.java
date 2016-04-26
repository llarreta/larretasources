package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class Screen extends Container {

	private Collection<StyleSheet> styleSheets;
	private ScreenElement header;
	private ScreenElement footer;
	
	public ScreenElement getFooter() {
		return footer;
	}

	public void setFooter(ScreenElement footer) {
		this.footer = footer;
	}

	public ScreenElement getHeader() {
		return header;
	}

	public void setHeader(ScreenElement header) {
		this.header = header;
	}

	public Collection<StyleSheet> getStyleSheets() {
		return styleSheets;
	}

	public void setStyleSheets(Collection<StyleSheet> styleSheets) {
		this.styleSheets = styleSheets;
	}
	
	public void addStyleSheet(StyleSheet styleSheet){
		if (styleSheet!=null){
			if (styleSheets==null){
				styleSheets = new ArrayList<StyleSheet>();
			}
			styleSheets.add(styleSheet);
		}
	}
	
	public void addStyleSheet(String library, String name){
		if (!StringUtils.isEmpty(library) && !StringUtils.isEmpty(name)){
			addStyleSheet(new StyleSheet(library, name));
		}
	}
}
