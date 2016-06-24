package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.screens.impl.ScreenListener;

@Entity
@Table(name = "screen")
@DiscriminatorValue(value = "screen")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Screen extends StandardContainer {

	private static final String DEFAULT_EXPIRES = "0";
	private static final String NO_CACHE = "no-cache";
	private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=UTF-8";
	
	private String contentType = TEXT_HTML_CHARSET_UTF_8;
	private String pragma = NO_CACHE;
	private String cacheControl = NO_CACHE;
	private String expires = DEFAULT_EXPIRES;
	private Collection<StyleSheet> styleSheets;
	private String title;
	
	private String entityClass;
	
	private ScreenListener preActionListener;
	private ScreenListener postActionListener;
	private ScreenListener initActionListener;

	private String preActionListenerName;
	private String postActionListenerName;
	private String initActionListenerName;
	
	private String lazyProperties;
	private String lazyCollections;

	@Basic
	public String getInitActionListenerName() {
		return initActionListenerName;
	}

	public void setInitActionListenerName(String initActionListenerName) {
		this.initActionListenerName = initActionListenerName;
	}

	@Transient
	public ScreenListener getInitActionListener() {
		if ((initActionListener==null) && (!StringUtils.isEmpty(initActionListenerName))){
			initActionListener = (ScreenListener) ScreenUtils.getObject(initActionListenerName);
		}
		return initActionListener;
	}

	public void setInitActionListener(ScreenListener initActionListener) {
		this.initActionListener = initActionListener;
	}
	
	@Basic
	public String getPreActionListenerName() {
		return preActionListenerName;
	}

	public void setPreActionListenerName(String preActionListenerName) {
		this.preActionListenerName = preActionListenerName;
	}

	@Transient
	public ScreenListener getPreActionListener() {
		if ((preActionListener==null) && (!StringUtils.isEmpty(preActionListenerName))){
			preActionListener = (ScreenListener) ScreenUtils.getObject(preActionListenerName);
		}
		return preActionListener;
	}

	public void setPreActionListener(ScreenListener preActionListener) {
		this.preActionListener = preActionListener;
	}
	
	@Basic
	public String getPostActionListenerName() {
		return postActionListenerName;
	}

	public void setPostActionListenerName(String postActionListenerName) {
		this.postActionListenerName = postActionListenerName;
	}

	@Transient
	public ScreenListener getPostActionListener() {
		if ((postActionListener==null) && (!StringUtils.isEmpty(postActionListenerName))){
			postActionListener = (ScreenListener) ScreenUtils.getObject(postActionListenerName);
		}
		return postActionListener;
	}

	public void setPostActionListener(ScreenListener postActionListener) {
		this.postActionListener = postActionListener;
	}
	
	
	@Transient
	public Boolean getIsLazyProperties(){
		return !StringUtils.isEmpty(lazyProperties) || !StringUtils.isEmpty(lazyCollections);
	}
	
	@Basic
	public String getLazyProperties() {
		return lazyProperties;
	}

	public void setLazyProperties(String lazyProperties) {
		this.lazyProperties = lazyProperties;
	}
	
	@Transient
	public Collection<String> getLazyPropertiesSplitted(){
		return ScreenUtils.split(lazyProperties);
	}

	@Basic
	public String getLazyCollections() {
		return lazyCollections;
	}

	public void setLazyCollections(String lazyCollections) {
		this.lazyCollections = lazyCollections;
	}

	@Transient
	public Collection<String> getLazyCollectionsSplitted(){
		return ScreenUtils.split(lazyCollections);
	}
	
	public Screen(){}
	
	public Screen(Class entityClass){
		this();
		if (entityClass!=null){
			String entityClassName = entityClass.getName();
			setEntityClass(entityClassName);
		}
	}
	
	public Screen(Long id, Class entityClass){
		this(entityClass);
		setId(id);
	}
	
	@Basic
	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	@Transient
	public String getTitleEvaluated() {
		return (String) ScreenUtils.evaluate(getTitle());
	}
	
	@Basic
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTitleMessage(String title) {
		this.title = ScreenUtils.generateMessage(title);
	}

	@Basic
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		if (!StringUtils.isEmpty(contentType)){
			this.contentType = contentType;
		}
	}

	@Basic
	public String getPragma() {
		return pragma;
	}

	public void setPragma(String pragma) {
		if (!StringUtils.isEmpty(pragma)){
			this.pragma = pragma;
		}
	}

	@Basic
	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		if (!StringUtils.isEmpty(cacheControl)){
			this.cacheControl = cacheControl;
		}
	}

	@Basic
	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		if (!StringUtils.isEmpty(expires)){
			this.expires = expires;
		}
	}

	@ManyToMany (fetch=FetchType.EAGER,  cascade=CascadeType.ALL, targetEntity=StyleSheet.class)
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
