package ar.com.larreta.commons.domain;

import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@MappedSuperclass
public class BigParametricEntity extends Entity {
	private byte[] text;

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
