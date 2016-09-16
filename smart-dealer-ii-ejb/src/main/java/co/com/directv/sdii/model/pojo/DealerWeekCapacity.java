package co.com.directv.sdii.model.pojo;

import java.util.Date;

import org.jboss.envers.Versioned;

import co.com.directv.sdii.audit.Auditable;

/**
 * DealerWeekCapacity entity. @author MyEclipse Persistence Tools
 */

public class DealerWeekCapacity implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5818247595945146771L;
	private Long id;
	private User user;
	private ServiceSuperCategory serviceSuperCategory;
	private Dealer dealer;
	private Country country;
	private ServiceHour serviceHour;
	private Double monCapacity;
	private Double tueCapacity;
	private Double wedCapacity;
	private Double thuCapacity;
	private Double friCapacity;
	private Double satCapacity;
	private Double sunCapacity;
	private Date dateLastChange;
	
	//Campos para realizar la auditoria
	private Long dealerId;
	private Long userId;
	private Long serviceSuperCategoryId;
	private Long countryId;
	private Long serviceHourId;

	// Constructors

	/** default constructor */
	public DealerWeekCapacity() {
	}

	/** minimal constructor */
	public DealerWeekCapacity(Dealer dealer) {
		this.dealer = dealer;
	}

	public DealerWeekCapacity(ServiceSuperCategory serviceSuperCategory,
			Dealer dealer, Country country, ServiceHour serviceHour) {
		this.serviceSuperCategory = serviceSuperCategory;
		this.dealer = dealer;
		this.country = country;
		this.serviceHour = serviceHour;
	}
	
	/** full constructor */
	public DealerWeekCapacity(User user,
			ServiceSuperCategory serviceSuperCategory, Dealer dealer,
			Country country, ServiceHour serviceHour, Double monCapacity,
			Double tueCapacity, Double wedCapacity, Double thuCapacity,
			Double friCapacity, Double satCapacity, Double sunCapacity,
			Date dateLastChange) {
		this.user = user;
		this.serviceSuperCategory = serviceSuperCategory;
		this.dealer = dealer;
		this.country = country;
		this.serviceHour = serviceHour;
		this.monCapacity = monCapacity;
		this.tueCapacity = tueCapacity;
		this.wedCapacity = wedCapacity;
		this.thuCapacity = thuCapacity;
		this.friCapacity = friCapacity;
		this.satCapacity = satCapacity;
		this.sunCapacity = sunCapacity;
		this.dateLastChange = dateLastChange;
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

	public ServiceSuperCategory getServiceSuperCategory() {
		return this.serviceSuperCategory;
	}

	public void setServiceSuperCategory(
			ServiceSuperCategory serviceSuperCategory) {
		this.serviceSuperCategory = serviceSuperCategory;
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

	public ServiceHour getServiceHour() {
		return this.serviceHour;
	}

	public void setServiceHour(ServiceHour serviceHour) {
		this.serviceHour = serviceHour;
	}

	@Versioned
	public Double getMonCapacity() {
		return this.monCapacity;
	}

	public void setMonCapacity(Double monCapacity) {
		this.monCapacity = monCapacity;
	}

	@Versioned
	public Double getTueCapacity() {
		return this.tueCapacity;
	}

	public void setTueCapacity(Double tueCapacity) {
		this.tueCapacity = tueCapacity;
	}

	@Versioned
	public Double getWedCapacity() {
		return this.wedCapacity;
	}

	public void setWedCapacity(Double wedCapacity) {
		this.wedCapacity = wedCapacity;
	}

	@Versioned
	public Double getThuCapacity() {
		return this.thuCapacity;
	}

	public void setThuCapacity(Double thuCapacity) {
		this.thuCapacity = thuCapacity;
	}

	@Versioned
	public Double getFriCapacity() {
		return this.friCapacity;
	}

	public void setFriCapacity(Double friCapacity) {
		this.friCapacity = friCapacity;
	}

	@Versioned
	public Double getSatCapacity() {
		return this.satCapacity;
	}

	public void setSatCapacity(Double satCapacity) {
		this.satCapacity = satCapacity;
	}

	@Versioned
	public Double getSunCapacity() {
		return this.sunCapacity;
	}

	public void setSunCapacity(Double sunCapacity) {
		this.sunCapacity = sunCapacity;
	}

	@Versioned
	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
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
	public Long getServiceSuperCategoryId() {
		return serviceSuperCategoryId;
	}

	public void setServiceSuperCategoryId(Long serviceSuperCategoryId) {
		this.serviceSuperCategoryId = serviceSuperCategoryId;
	}

	@Versioned
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Versioned
	public Long getServiceHourId() {
		return serviceHourId;
	}

	public void setServiceHourId(Long serviceHourId) {
		this.serviceHourId = serviceHourId;
	}
	
}