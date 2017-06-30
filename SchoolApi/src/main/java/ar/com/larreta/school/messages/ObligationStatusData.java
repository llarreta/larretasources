package ar.com.larreta.school.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.validators.annotations.Format;
import ar.com.larreta.validators.annotations.NotNull;

@Component @Scope("prototype")
public class ObligationStatusData extends JSONable {

	private Long 			student;
	private Boolean 		paidOff;
	private Long 			id;
	private String 			description;
	private Long 			productGroup;
	
	@Format(formatType=Format.FormatType.DATE, message="dueDate.obligation.invalid")
	@NotNull(message="dueDate.required")
	private String 	dueDate;
	
	@Valid
	@NotNull(message="prices.required")
	@ar.com.larreta.validators.annotations.Size(message="prices.mayorOrEqual", mayorOrEqual=1)
	private JSONableCollection<PriceData> prices;


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
	
	public JSONableCollection<PriceData> getPrices() {
		return prices;
	}
	public void setPrices(JSONableCollection<PriceData> prices) {
		this.prices = prices;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public Long getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(Long productGroup) {
		this.productGroup = productGroup;
	}
	
	public Long getStudent() {
		return student;
	}
	public void setStudent(Long student) {
		this.student = student;
	}
	public Boolean getPaidOff() {
		return paidOff;
	}
	public void setPaidOff(Boolean paidOff) {
		this.paidOff = paidOff;
	}
	
	
}
