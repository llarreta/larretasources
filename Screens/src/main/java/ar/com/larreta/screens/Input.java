package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "input")
@DiscriminatorValue(value = "input")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Input extends ScreenElement {
	private String value;

	@Basic
	public String getValue() {
		String bindingValue = (String) getBindingPropertyValue();
		if (!StringUtils.isEmpty(bindingValue)){
			value = bindingValue;
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setBindingPropertyValue(value);
	}
}
