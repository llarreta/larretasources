package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * ShippingOrder entity. @author MyEclipse Persistence Tools
 */

public class ShippingOrder implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1840998851726546032L;
	private Long id;
	private ShippingOrderStatus shippingOrderStatus;
	private Long workOrderId;
	private ShippingOrderType shippingOrderType;
	private Date creationEventDate;
	private String soDescription;
	private String shippingMethodCode;
	private Date shippingDate;
	private Date realShippingDate;
	private String contractCode;
	private String contractNumber;
	private String vendorCode;
	private String shippingOrderCode;

	// Constructors

	/** default constructor */
	public ShippingOrder() {
	}

	/** minimal constructor */
	public ShippingOrder(String contractCode, String contractNumber,
			String vendorCode, String shippingOrderCode) {
		this.contractCode = contractCode;
		this.contractNumber = contractNumber;
		this.vendorCode = vendorCode;
		this.shippingOrderCode = shippingOrderCode;
	}

	/** full constructor */
	public ShippingOrder(ShippingOrderStatus shippingOrderStatus,
			Long workOrderId, ShippingOrderType shippingOrderType,
			Date creationEventDate, String soDescription,
			String shippingMethodCode, Date shippingDate,
			Date realShippingDate, String contractCode, String contractNumber,
			String vendorCode, String shippingOrderCode) {
		this.shippingOrderStatus = shippingOrderStatus;
		this.workOrderId = workOrderId;
		this.shippingOrderType = shippingOrderType;
		this.creationEventDate = creationEventDate;
		this.soDescription = soDescription;
		this.shippingMethodCode = shippingMethodCode;
		this.shippingDate = shippingDate;
		this.realShippingDate = realShippingDate;
		this.contractCode = contractCode;
		this.contractNumber = contractNumber;
		this.vendorCode = vendorCode;
		this.shippingOrderCode = shippingOrderCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShippingOrderStatus getShippingOrderStatus() {
		return this.shippingOrderStatus;
	}

	public void setShippingOrderStatus(ShippingOrderStatus shippingOrderStatus) {
		this.shippingOrderStatus = shippingOrderStatus;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public ShippingOrderType getShippingOrderType() {
		return this.shippingOrderType;
	}

	public void setShippingOrderType(ShippingOrderType shippingOrderType) {
		this.shippingOrderType = shippingOrderType;
	}

	public Date getCreationEventDate() {
		return this.creationEventDate;
	}

	public void setCreationEventDate(Date creationEventDate) {
		this.creationEventDate = creationEventDate;
	}

	public String getSoDescription() {
		return this.soDescription;
	}

	public void setSoDescription(String soDescription) {
		this.soDescription = soDescription;
	}

	public String getShippingMethodCode() {
		return this.shippingMethodCode;
	}

	public void setShippingMethodCode(String shippingMethodCode) {
		this.shippingMethodCode = shippingMethodCode;
	}

	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public Date getRealShippingDate() {
		return this.realShippingDate;
	}

	public void setRealShippingDate(Date realShippingDate) {
		this.realShippingDate = realShippingDate;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractNumber() {
		return this.contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getShippingOrderCode() {
		return this.shippingOrderCode;
	}

	public void setShippingOrderCode(String shippingOrderCode) {
		this.shippingOrderCode = shippingOrderCode;
	}

	@Override
	public String toString() {
		return "ShippingOrder [id=" + id + ", workOrderId=" + workOrderId + "]";
	}
	
	

}