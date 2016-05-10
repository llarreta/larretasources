package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "screen")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Screen extends Container {

	private static final String DEFAULT_EXPIRES = "0";
	private static final String NO_CACHE = "no-cache";
	private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=UTF-8";
	
	private String contentType = TEXT_HTML_CHARSET_UTF_8;
	private String pragma = NO_CACHE;
	private String cacheControl = NO_CACHE;
	private String expires = DEFAULT_EXPIRES;
	private Collection<StyleSheet> styleSheets;
	private ScreenElement header;
	private ScreenElement footer;
	private String title;

	@Basic
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Basic
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Basic
	public String getPragma() {
		return pragma;
	}

	public void setPragma(String pragma) {
		this.pragma = pragma;
	}

	@Basic
	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	@Basic
	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=ScreenElement.class)
	@JoinColumn (name="idFooter")
	public ScreenElement getFooter() {
		return footer;
	}

	public void setFooter(ScreenElement footer) {
		this.footer = footer;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=ScreenElement.class)
	@JoinColumn (name="idHeader")
	public ScreenElement getHeader() {
		return header;
	}

	public void setHeader(ScreenElement header) {
		this.header = header;
	}

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=StyleSheet.class)
	@JoinTable(name = "hasStyleSheets", joinColumns = { @JoinColumn(name = "idScreen") }, 
		inverseJoinColumns = { @JoinColumn(name = "idStyleSheet") })
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
