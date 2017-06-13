package ar.com.larreta.school.messages;

import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.rest.messages.ParametricData;

public class DetailData extends ParametricData {

	private Double value;
	private JSONableCollection<LittleDetailData> littleDetails;

	public JSONableCollection<LittleDetailData> getLittleDetails() {
		return littleDetails;
	}

	public void setLittleDetails(JSONableCollection<LittleDetailData> littleDetails) {
		this.littleDetails = littleDetails;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
