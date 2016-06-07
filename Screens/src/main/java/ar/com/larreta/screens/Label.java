package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "label")
@DiscriminatorValue(value = "label")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Label extends ScreenElement {
	private String value;

	public Label(){
		setBindingProperty("value");
	}
	
	public Label(String value, String styleClass){
		this();
		setValue(value);
		setStyleClass(styleClass);
	}
	
	public Label(String value){
		this();
		setValue(value);
	}
	
	@Transient
	public Object getValueEvaluated() {
		
		String bindingValue = (String) getBindingPropertyValue();
		if (!StringUtils.isEmpty(bindingValue)){
			value = bindingValue;
		}
		return value;
	}
	
	@Basic
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setBindingPropertyValue(value);
	}
}