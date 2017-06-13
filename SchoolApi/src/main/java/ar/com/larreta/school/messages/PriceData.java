package ar.com.larreta.school.messages;

import java.util.Date;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;

public class PriceData extends JSONable {
	
	private Long 	id;
	private Date 	validityStartDate;
	private Double 	value;
	private JSONableCollection<DetailData> details;
	
	public JSONableCollection<DetailData> getDetails() {
		return details;
	}
	public void setDetails(JSONableCollection<DetailData> details) {
		this.details = details;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getValidityStartDate() {
		return validityStartDate;
	}
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

}
