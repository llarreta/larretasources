package co.com.directv.sdii.model.pojo;

import java.util.Date;

import org.jboss.envers.Versioned;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * DealerServiceSubCategory entity. @author MyEclipse Persistence Tools
 */

public class DealerServiceSubCategory implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1999865334289621564L;
	private Long id;
	private User user;
	private ServiceCategory serviceCategory;
	private Dealer dealer;
	private Country country;
	private Date dateLastChange;
	private String status;

	//Campos para realizar la auditoria
	private Long dealerId;
	private Long userId;
	private Long serviceCategoryId;
	private Long countryId;
	
	
	// Constructors

	/** default constructor */
	public DealerServiceSubCategory() {
	}

	public DealerServiceSubCategory(ServiceCategory serviceCategory,
			Long countryId) throws PropertiesException {
		this.serviceCategory = serviceCategory;
		this.country = new Country(countryId);
		this.status = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
	}
	
	/** minimal constructor */
	public DealerServiceSubCategory(User user, ServiceCategory serviceCategory,
			Dealer dealer, Country country) {
		this.user = user;
		this.serviceCategory = serviceCategory;
		this.dealer = dealer;
		this.country = country;
	}

	/** full constructor */
	public DealerServiceSubCategory(User user, ServiceCategory serviceCategory,
			Dealer dealer, Country country, Date dateLastChange, String status) {
		this.user = user;
		this.serviceCategory = serviceCategory;
		this.dealer = dealer;
		this.country = country;
		this.dateLastChange = dateLastChange;
		this.status = status;
	}

	// Property accessors

	@Versioned
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

	public ServiceCategory getServiceCategory() {
		return this.serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
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

	@Versioned
	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	@Versioned
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Versioned
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Versioned
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Versioned
	public Long getServiceCategoryId() {
		return serviceCategoryId;
	}

	public void setServiceCategoryId(Long serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}

	@Versioned
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	
	
}