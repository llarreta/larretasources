package ar.com.larreta.mystic.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ExtendedParametricEntity extends ParametricEntity {

	private String value;

	@Basic @Column (name="value", columnDefinition="TEXT")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
