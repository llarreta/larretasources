package ar.com.larreta.commons.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "resourceMessage")
public class ResourceMessage extends ar.com.larreta.commons.domain.Entity {

	private String country;
	private String language;
	private String key;
	private byte[] text;
	
	@Basic
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Basic
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Basic @Column(name="keyValue")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Lob @Basic
	public byte[] getText() {
		return text;
	}
	
	public void setText(byte[] text) {
		this.text = text;
	}
	
	@Transient
	public String getTextMessage() {
		if ((text!=null) && (text.length>0)){
			return new String(text);
		} 
		return StringUtils.EMPTY;
	}
	
	
	
}
