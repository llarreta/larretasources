package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * HistoDealerCoverage entity. @author MyEclipse Persistence Tools
 */

public class HistoDealerCoverage implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3842461080932960367L;
	private Long id;
	private DealerCoverage dealerCoverage;
	private Long coverageTypeId;
	private Long dealerPriority;
	private Long userIdLastChange;
	private Date dateLastChange;
	private String status;

	// Constructors

	/** default constructor */
	public HistoDealerCoverage() {
	}

	/** minimal constructor */
	public HistoDealerCoverage(DealerCoverage dealerCoverage,
			Long coverageTypeId, Long userIdLastChange, Date dateLastChange) {
		this.dealerCoverage = dealerCoverage;
		this.coverageTypeId = coverageTypeId;
		this.userIdLastChange = userIdLastChange;
		this.dateLastChange = dateLastChange;
	}

	/** full constructor */
	public HistoDealerCoverage(DealerCoverage dealerCoverage,
			Long coverageTypeId, Long dealerPriority, Long userIdLastChange,
			Date dateLastChange, String status) {
		this.dealerCoverage = dealerCoverage;
		this.coverageTypeId = coverageTypeId;
		this.dealerPriority = dealerPriority;
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

	public DealerCoverage getDealerCoverage() {
		return this.dealerCoverage;
	}

	public void setDealerCoverage(DealerCoverage dealerCoverage) {
		this.dealerCoverage = dealerCoverage;
	}

	public Long getCoverageTypeId() {
		return this.coverageTypeId;
	}

	public void setCoverageTypeId(Long coverageTypeId) {
		this.coverageTypeId = coverageTypeId;
	}

	public Long getDealerPriority() {
		return this.dealerPriority;
	}

	public void setDealerPriority(Long dealerPriority) {
		this.dealerPriority = dealerPriority;
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