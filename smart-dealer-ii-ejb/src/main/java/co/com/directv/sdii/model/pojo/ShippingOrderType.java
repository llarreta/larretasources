package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * ShippingOrderType entity. @author MyEclipse Persistence Tools
 */

public class ShippingOrderType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1038792546544614363L;

	private Long id;

        @BusinessRequired
	private String shippingOrderCode;

        @BusinessRequired
        private String shippingOrderName;

	// Constructors

	/** default constructor */
	public ShippingOrderType() {
	}

	/** minimal constructor */
	public ShippingOrderType(String shippingOrderCode, String shippingOrderName) {
		this.shippingOrderCode = shippingOrderCode;
		this.shippingOrderName = shippingOrderName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShippingOrderCode() {
		return this.shippingOrderCode;
	}

	public void setShippingOrderCode(String shippingOrderCode) {
		this.shippingOrderCode = shippingOrderCode;
	}

	public String getShippingOrderName() {
		return this.shippingOrderName;
	}

	public void setShippingOrderName(String shippingOrderName) {
		this.shippingOrderName = shippingOrderName;
	}

	@Override
	public String toString() {
		return "ShippingOrderType [id=" + id + ", shippingOrderCode="
				+ shippingOrderCode + "]";
	}

}