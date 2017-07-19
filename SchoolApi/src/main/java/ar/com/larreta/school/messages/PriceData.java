package ar.com.larreta.school.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.tools.Const;
import ar.com.larreta.validators.annotations.Format;
import ar.com.larreta.validators.annotations.NotNull;

@Component @Scope(Const.PROTOTYPE)
@Deprecated
public class PriceData extends JSONable {
	
	private Long 	id;
	
	@Format(formatType=Format.FormatType.DATE, message="validityStartDate.price.invalid")
	@NotNull(message="validityStartDate.required")
	private String 	validityStartDate;
	
	@NotNull(message="price.value.required")
	private Double 	value;

	@Valid
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
	public String getValidityStartDate() {
		return validityStartDate;
	}
	public void setValidityStartDate(String validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

}
