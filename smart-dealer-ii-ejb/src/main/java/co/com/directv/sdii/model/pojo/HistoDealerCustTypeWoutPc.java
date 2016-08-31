package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * HistoDealerCustTypeWoutPc entity. @author MyEclipse Persistence Tools
 */

public class HistoDealerCustTypeWoutPc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1682856057025628351L;
	private Long id;
	private DealerCustomerTypesWoutPc dealerCustomerTypesWoutPc;
	private String status;
	private Long userIdLastChange;
	private Date dateLastChange;

	// Constructors

	/** default constructor */
	public HistoDealerCustTypeWoutPc() {
	}

	/** full constructor */
	public HistoDealerCustTypeWoutPc(
			DealerCustomerTypesWoutPc dealerCustomerTypesWoutPc, String status,
			Long userIdLastChange, Date dateLastChange) {
		this.dealerCustomerTypesWoutPc = dealerCustomerTypesWoutPc;
		this.status = status;
		this.userIdLastChange = userIdLastChange;
		this.dateLastChange = dateLastChange;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DealerCustomerTypesWoutPc getDealerCustomerTypesWoutPc() {
		return this.dealerCustomerTypesWoutPc;
	}

	public void setDealerCustomerTypesWoutPc(
			DealerCustomerTypesWoutPc dealerCustomerTypesWoutPc) {
		this.dealerCustomerTypesWoutPc = dealerCustomerTypesWoutPc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserIdLastChange() {
		return this.userIdLastChange;
	}

	public void setUserIdLastChange(Long userIdLastChange) {
		this.userIdLastChange = userIdLastChange;
	}

	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

}