package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.validators.annotations.NotNull;

@Component @Scope("prototype")
public class LittleDetailData extends JSONable {
	
	private Long 			id;
	
	@NotNull(message="value.required")
	private Double value;
	
	@NotNull(message="description.required")
	private String 			description;

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