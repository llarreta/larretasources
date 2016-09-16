package co.com.directv.sdii.model.pojo;

import java.util.Date;

import org.hibernate.envers.Audited;

import co.com.directv.sdii.audit.Auditable;

/**
 * DealerCoverage entity. @author MyEclipse Persistence Tools
 */

public class DealerCoverage implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -699232640641629749L;
	private Long id;
	private User user;
	private CoverageType coverageType;
	private Dealer dealer;
	private Country country;
	private PostalCode postalCode;
	private Long dealerPriority;
	private Date dateLastChange;
	private String status;

	//Campos para realizar la auditoria
	private Long dealerId;
	private Long userId;
	private Long coverageTypeId;
	private Long countryId;
	private Long postalCodeIds;
	
	// Constructors

	/** default constructor */
	public DealerCoverage() {
	}

	public DealerCoverage(Long id) {
		this.id = id;
	}
	
	/** minimal constructor */
	public DealerCoverage(Dealer dealer, Country country, PostalCode postalCode) {
		this.dealer = dealer;
		this.country = country;
		this.postalCode = postalCode;
	}

	/** full constructor */
	public DealerCoverage(User user, CoverageType coverageType, Dealer dealer,
			Country country, PostalCode postalCode, Long dealerPriority,
			Date dateLastChange, String status) {
		this.user = user;
		this.coverageType = coverageType;
		this.dealer = dealer;
		this.country = country;
		this.postalCode = postalCode;
		this.dealerPriority = dealerPriority;
		this.dateLastChange = dateLastChange;
		this.status = status;
	}

	// Property accessors
	@Audited
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

	public CoverageType getCoverageType() {
		return this.coverageType;
	}

	public void setCoverageType(CoverageType coverageType) {
		this.coverageType = coverageType;
	}


	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public PostalCode getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}

	@Audited
	public Long getDealerPriority() {
		return this.dealerPriority;
	}

	public void setDealerPriority(Long dealerPriority) {
		this.dealerPriority = dealerPriority;
	}

	@Audited
	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	@Audited
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Audited
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Audited
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Audited
	public Long getCoverageTypeId() {
		return coverageTypeId;
	}

	public void setCoverageTypeId(Long coverageTypeId) {
		this.coverageTypeId = coverageTypeId;
	}

	@Audited
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Audited
	public Long getPostalCodeIds() {
		return postalCodeIds;
	}

	public void setPostalCodeIds(Long postalCodeIds) {
		this.postalCodeIds = postalCodeIds;
	}
	
}