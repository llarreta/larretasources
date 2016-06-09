package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@MappedSuperclass
public class ValuedElement extends ScreenElement {
	
	private String value;
	
	@Basic
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void setMessageValue(String value) {
		this.value = ScreenUtils.generateMessage(value);
	}
	
	public void setExpressionValue(String value) {
		this.value = ScreenUtils.generateExpression(value);
	}
	
	@Transient
	public Object getBindingPropertyValue(){
		if (StringUtils.isEmpty(value)){
			return super.getBindingPropertyValue();
		}
		return ScreenUtils.evaluate(value);
	}

}
