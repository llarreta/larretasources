package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class OrderHandlingServiceRequestDTO implements Serializable {

	private String shippingOrderCode;
	private String countryCode;
	public OrderHandlingServiceRequestDTO(String shippingOrderCode,
			String countryCode) {
		super();
		this.shippingOrderCode = shippingOrderCode;
		this.countryCode = countryCode;
	}
	public OrderHandlingServiceRequestDTO(OrderHandlingServiceRequestDTO copy) {
		super();
		this.shippingOrderCode = copy.shippingOrderCode;
		this.countryCode = copy.countryCode;
	}
	public OrderHandlingServiceRequestDTO() {
		super();
	}
	public String getShippingOrderCode() {
		return shippingOrderCode;
	}
	public void setShippingOrderCode(String shippingOrderCode) {
		this.shippingOrderCode = shippingOrderCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
