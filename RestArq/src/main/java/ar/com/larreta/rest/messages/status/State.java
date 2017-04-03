package ar.com.larreta.rest.messages.status;

import ar.com.larreta.prototypes.JSONableCollection;
import ar.com.larreta.rest.messages.Message;

public abstract class State extends Message {
	private String code;
	private String description;
	private JSONableCollection<Detail> details = new JSONableCollection<Detail>();
	
	public State(String code, String description){
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JSONableCollection<Detail> getDetails() {
		return details;
	}

	public void setDetails(JSONableCollection<Detail> details) {
		this.details = details;
	}
	public void addDetail(Detail detail){
		details.add(detail);
	}
	public void addDetail(Long index, String detailDescription){
		details.add(new Detail(index, detailDescription));
	}
}
