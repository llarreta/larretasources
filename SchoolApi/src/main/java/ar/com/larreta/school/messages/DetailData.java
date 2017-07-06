package ar.com.larreta.school.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.tools.Const;
import ar.com.larreta.validators.annotations.NotNull;

@Component @Scope(Const.PROTOTYPE)
public class DetailData extends JSONable {

	private Long 			id;
	
	@NotNull(message="detail.value.required")
	private Double value;

	@NotNull(message="detail.description.required")
	private String 			description;

	@Valid
	private JSONableCollection<LittleDetailData> littleDetails;

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
