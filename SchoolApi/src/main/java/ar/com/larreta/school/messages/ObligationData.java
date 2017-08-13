package ar.com.larreta.school.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.DocumentType;
import ar.com.larreta.school.persistence.ProductGroup;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.Format;
import ar.com.larreta.stepper.validators.annotations.NotNull;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class ObligationData extends JSONable {
	
	private Long 			id;
	private String 			description;
	
	@Exist(message="productGroup.inexistent", entityType=ProductGroup.class)
	private Long 	productGroup;
	
	@Format(formatType=Format.FormatType.DATE, message="dueDate.obligation.invalid")
	@NotNull(message="dueDate.required")
	private String 	dueDate;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/*public JSONableCollection<PriceData> getPrices() {
		return prices;
	}
	public void setPrices(JSONableCollection<PriceData> prices) {
		this.prices = prices;
	}*/
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


}
