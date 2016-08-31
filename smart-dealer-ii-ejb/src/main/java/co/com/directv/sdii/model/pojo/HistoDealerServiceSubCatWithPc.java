package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * HistoDealerServiceSubCatWithPc entity. @author MyEclipse Persistence Tools
 */

public class HistoDealerServiceSubCatWithPc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5463443162649495517L;
	private Long id;
	private DealerServiceSubCategoryWithPc dealerServiceSubCategoryWithPc;
	private Long userIdLastChange;
	private Date dateLastChange;
	private String state;

	// Constructors

	/** default constructor */
	public HistoDealerServiceSubCatWithPc() {
	}

	/** minimal constructor */
	public HistoDealerServiceSubCatWithPc(
			DealerServiceSubCategoryWithPc dealerServiceSubCategoryWithPc,
			Long userIdLastChange) {
		this.dealerServiceSubCategoryWithPc = dealerServiceSubCategoryWithPc;
		this.userIdLastChange = userIdLastChange;
	}

	/** full constructor */
	public HistoDealerServiceSubCatWithPc(
			DealerServiceSubCategoryWithPc dealerServiceSubCategoryWithPc,
			Long userIdLastChange, Date dateLastChange, String state) {
		this.dealerServiceSubCategoryWithPc = dealerServiceSubCategoryWithPc;
		this.userIdLastChange = userIdLastChange;
		this.dateLastChange = dateLastChange;
		this.state = state;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DealerServiceSubCategoryWithPc getDealerServiceSubCategoryWithPc() {
		return this.dealerServiceSubCategoryWithPc;
	}

	public void setDealerServiceSubCategoryWithPc(
			DealerServiceSubCategoryWithPc dealerServiceSubCategoryWithPc) {
		this.dealerServiceSubCategoryWithPc = dealerServiceSubCategoryWithPc;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}