package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * DealerCapacity entity. @author MyEclipse Persistence Tools
 */

public class DealerCapacity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -626538119236803043L;
	private Long id;
	private ServiceSuperCategory serviceSuperCategory;
	private Dealer dealer;
	private Country country;
	private ServiceHour serviceHour;
	private Date capacityDate;
	private Long maxCapacity;
	private Long usedCapacity;

	// Constructors

	/** default constructor */
	public DealerCapacity() {
	}

	/** full constructor */
	public DealerCapacity(ServiceSuperCategory serviceSuperCategory,
			Dealer dealer, Country country, ServiceHour serviceHour,
			Date capacityDate, Long maxCapacity, Long usedCapacity) {
		this.serviceSuperCategory = serviceSuperCategory;
		this.dealer = dealer;
		this.country = country;
		this.serviceHour = serviceHour;
		this.capacityDate = capacityDate;
		this.maxCapacity = maxCapacity;
		this.usedCapacity = usedCapacity;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCapacityDate() {
		return this.capacityDate;
	}

	public void setCapacityDate(Date capacityDate) {
		this.capacityDate = capacityDate;
	}

	public Long getMaxCapacity() {
		return this.maxCapacity;
	}

	public void setMaxCapacity(Long maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Long getUsedCapacity() {
		return this.usedCapacity;
	}

	public void setUsedCapacity(Long usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

}