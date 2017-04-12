package ar.com.larreta.rest.messages.status;

import ar.com.larreta.rest.messages.JSONableCollectionBody;
import ar.com.larreta.rest.messages.Message;

public abstract class State extends Message {
	private String code;
	private String description;
	private JSONableCollectionBody<Detail> details = new JSONableCollectionBody<Detail>();
	
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
	public JSONableCollectionBody<Detail> getDetails() {
		return details;
	}

	public void setDetails(JSONableCollectionBody<Detail> details) {
		this.details = details;
	}
	public void addDetail(Detail detail){
		details.add(detail);
	}
	public void addDetail(Long index, String detailDescription){
		details.add(new Detail(index, detailDescription));
	}
}
