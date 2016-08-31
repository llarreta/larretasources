package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ShippingOrderElement entity. @author MyEclipse Persistence Tools
 */

public class ShippingOrderDetail implements java.io.Serializable,Auditable {

	private static final long serialVersionUID = -8349298900261779299L;
	
	private Long id;
	
	private ShippingOrder shippingOrder;
	
	private Technology technology;
	
	private String ibsModelCode;
	
	private String serialCode;

	
	/** default constructor */
	public ShippingOrderDetail() {
	}

	/** minimal constructor */
	public ShippingOrderDetail(ShippingOrder shippingOrder , Technology technology) {
		this.shippingOrder = shippingOrder;
		this.technology = technology;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public ShippingOrder getShippingOrder() {
		return shippingOrder;
	}

	public void setShippingOrder(ShippingOrder shippingOrder) {
		this.shippingOrder = shippingOrder;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public String getIbsModelCode() {
		return ibsModelCode;
	}

	public void setIbsModelCode(String ibsModelCode) {
		this.ibsModelCode = ibsModelCode;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	@Override
	public String toString() {
		return "ShippingOrderDetail [id=" + id + "]";
	}
}