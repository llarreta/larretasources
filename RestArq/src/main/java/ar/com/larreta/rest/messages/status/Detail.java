package ar.com.larreta.rest.messages.status;

import ar.com.larreta.prototypes.JSONable;

public class Detail extends JSONable {

	private Long index;
	private String description;
	
	public Detail(Long index, String description){
		this.index = index;
		this.description = description;
	}
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
