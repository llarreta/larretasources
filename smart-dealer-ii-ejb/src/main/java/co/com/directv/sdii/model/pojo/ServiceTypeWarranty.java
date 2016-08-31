package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * ServiceTypeWarranty entity. @author MyEclipse Persistence Tools
 */

public class ServiceTypeWarranty implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349522023498689998L;
	private Long id;
	private User user;
	private ServiceType serviceType;
	private Country country;
	private Long warrrantyPeriod;
	private Date dateLastChange;

	// Constructors

	/** default constructor */
	public ServiceTypeWarranty() {
	}

	public ServiceTypeWarranty(Long id) {
		this.id = id;
	}
	
	public ServiceTypeWarranty(ServiceType serviceType, Long countryId) {
		this.serviceType = serviceType;
		this.country = new Country(countryId);
	}
	
	/** minimal constructor */
	public ServiceTypeWarranty(User user, ServiceType serviceType,
			Country country, Long warrrantyPeriod) {
		this.user = user;
		this.serviceType = serviceType;
		this.country = country;
		this.warrrantyPeriod = warrrantyPeriod;
	}

	/** full constructor */
	public ServiceTypeWarranty(User user, ServiceType serviceType,
			Country country, Long warrrantyPeriod, Date dateLastChange) {
		this.user = user;
		this.serviceType = serviceType;
		this.country = country;
		this.warrrantyPeriod = warrrantyPeriod;
		this.dateLastChange = dateLastChange;
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

	public ServiceType getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Long getWarrrantyPeriod() {
		return this.warrrantyPeriod;
	}

	public void setWarrrantyPeriod(Long warrrantyPeriod) {
		this.warrrantyPeriod = warrrantyPeriod;
	}

	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}
}