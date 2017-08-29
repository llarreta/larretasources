package ar.com.larreta.school.messages;

import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.JSONable;

@Component
public class ScholarshipData extends JSONable {

	private Long id;
	private String description;
	private Double value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
