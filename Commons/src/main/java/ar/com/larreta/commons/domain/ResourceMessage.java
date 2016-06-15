package ar.com.larreta.commons.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "resourceMessage")
public class ResourceMessage extends ar.com.larreta.commons.domain.Entity {

	private Country country;
	private Language language;
	private String key;
	private byte[] text;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Country.class)
	@JoinColumn (name="idCountry")
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Language.class)
	@JoinColumn (name="idLanguage")
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
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
	public String getTextString() {
		if (text==null){
			return StringUtils.EMPTY;
		}
		return new String(text);
	}
	
	public void setTextString(String text) {
		if (text!=null){
			this.text = text.getBytes();
		}
	}
	
}
