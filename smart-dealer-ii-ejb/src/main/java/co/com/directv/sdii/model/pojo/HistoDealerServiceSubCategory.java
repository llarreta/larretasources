package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * HistoDealerServiceSubCategory entity. @author MyEclipse Persistence Tools
 */

public class HistoDealerServiceSubCategory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8217504374511849292L;
	private Long id;
	private DealerServiceSubCategory dealerServiceSubCategory;
	private String status;
	private Long userIdLastChange;
	private Date dateLastChange;

	// Constructors

	/** default constructor */
	public HistoDealerServiceSubCategory() {
	}

	/** full constructor */
	public HistoDealerServiceSubCategory(
			DealerServiceSubCategory dealerServiceSubCategory, String status,
			Long userIdLastChange, Date dateLastChange) {
		this.dealerServiceSubCategory = dealerServiceSubCategory;
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

	public DealerServiceSubCategory getDealerServiceSubCategory() {
		return this.dealerServiceSubCategory;
	}

	public void setDealerServiceSubCategory(
			DealerServiceSubCategory dealerServiceSubCategory) {
		this.dealerServiceSubCategory = dealerServiceSubCategory;
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