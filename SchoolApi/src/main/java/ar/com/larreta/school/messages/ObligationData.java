package ar.com.larreta.school.messages;

import java.util.Date;

import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.rest.messages.ParametricData;

public class ObligationData extends ParametricData {
	
	private Long 	productGroup;
	private Date 	dueDate;
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
