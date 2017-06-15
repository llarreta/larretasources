package ar.com.larreta.school.messages;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.validators.annotations.NotNull;

@Component @Scope("prototype")
public class ObligationData extends ParametricData {
	
	@NotNull(message="productGroup.required")
	private Long 	productGroup;
	
	@NotNull(message="dueDate.required")
	private Date 	dueDate;
	
	@NotNull(message="prices.required")
	@ar.com.larreta.validators.annotations.Size(message="prices.mayorOrEqual", mayorOrEqual=1)
	private JSONableCollection<PriceData> prices;
	
	public JSONableCollection<PriceData> getPrices() {
		return prices;
	}
	public void setPrices(JSONableCollection<PriceData> prices) {
		this.prices = prices;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Long getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(Long productGroup) {
		this.productGroup = productGroup;
	}


}
