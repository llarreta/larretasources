package ar.com.larreta.school.messages;

import ar.com.larreta.rest.messages.ParametricData;

public class LittleDetailData extends ParametricData {
	
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
