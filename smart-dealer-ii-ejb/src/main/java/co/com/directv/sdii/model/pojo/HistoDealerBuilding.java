package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * HistoDealerBuilding entity. @author MyEclipse Persistence Tools
 */

public class HistoDealerBuilding implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8146713844241410060L;
	private Long id;
	private DealerBulding dealerBulding;
	private Long dealerCoverageId;
	private Long userIdLastChange;
	private Date dateLastChange;
	private String status;

	// Constructors

	/** default constructor */
	public HistoDealerBuilding() {
	}

	/** minimal constructor */
	public HistoDealerBuilding(DealerBulding dealerBulding) {
		this.dealerBulding = dealerBulding;
	}

	/** full constructor */
	public HistoDealerBuilding(DealerBulding dealerBulding,
			Long dealerCoverageId, Long userIdLastChange, Date dateLastChange,
			String status) {
		this.dealerBulding = dealerBulding;
		this.dealerCoverageId = dealerCoverageId;
		this.userIdLastChange = userIdLastChange;
		this.dateLastChange = dateLastChange;
		this.status = status;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DealerBulding getDealerBulding() {
		return this.dealerBulding;
	}

	public void setDealerBulding(DealerBulding dealerBulding) {
		this.dealerBulding = dealerBulding;
	}

	public Long getDealerCoverageId() {
		return this.dealerCoverageId;
	}

	public void setDealerCoverageId(Long dealerCoverageId) {
		this.dealerCoverageId = dealerCoverageId;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}