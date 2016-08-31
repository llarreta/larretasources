package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * ShippingOrderStatus entity. @author MyEclipse Persistence Tools
 */

public class ShippingOrderStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7589639905511029658L;

	private Long id;
	
        @BusinessRequired
        private String soStatusCode;
	
        @BusinessRequired
        private String soStatusName;

	// Constructors

	/** default constructor */
	public ShippingOrderStatus() {
	}

	/** minimal constructor */
	public ShippingOrderStatus(String soStatusCode, String soStatusName) {
		this.soStatusCode = soStatusCode;
		this.soStatusName = soStatusName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoStatusCode() {
		return this.soStatusCode;
	}

	public void setSoStatusCode(String soStatusCode) {
		this.soStatusCode = soStatusCode;
	}

	public String getSoStatusName() {
		return this.soStatusName;
	}

	public void setSoStatusName(String soStatusName) {
		this.soStatusName = soStatusName;
	}

	@Override
	public String toString() {
		return "ShippingOrderStatus [id=" + id + ", soStatusCode="
				+ soStatusCode + "]";
	}
}