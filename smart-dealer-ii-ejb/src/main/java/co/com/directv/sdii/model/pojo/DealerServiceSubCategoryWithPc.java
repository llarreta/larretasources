package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * DealerServiceSubCategoryWithPc entity. @author MyEclipse Persistence Tools
 */

public class DealerServiceSubCategoryWithPc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5504479527775308773L;
	private Long id;
	private User user;
	private DealerCoverage dealerCoverage;
	private ServiceCategory serviceCategory;
	private Date dateLastChange;
	private String status;
	// Constructors

	/** default constructor */
	public DealerServiceSubCategoryWithPc() {
	}

	public DealerServiceSubCategoryWithPc(ServiceCategory serviceCategory,
			Long dealerCoverageId) throws PropertiesException {
		this.dealerCoverage = new DealerCoverage(dealerCoverageId);
		this.serviceCategory = serviceCategory;
		this.status = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
	}
	
	/** minimal constructor */
	public DealerServiceSubCategoryWithPc(User user,
			DealerCoverage dealerCoverage, ServiceCategory serviceCategory,
			Date dateLastChange, String status) {
		this.user = user;
		this.dealerCoverage = dealerCoverage;
		this.serviceCategory = serviceCategory;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DealerCoverage getDealerCoverage() {
		return this.dealerCoverage;
	}

	public void setDealerCoverage(DealerCoverage dealerCoverage) {
		this.dealerCoverage = dealerCoverage;
	}

	public ServiceCategory getServiceCategory() {
		return this.serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
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