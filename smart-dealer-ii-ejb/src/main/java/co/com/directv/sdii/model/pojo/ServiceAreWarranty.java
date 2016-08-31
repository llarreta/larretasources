package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * ServiceAreWarranty entity. @author MyEclipse Persistence Tools
 */

public class ServiceAreWarranty implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6821139023097003749L;
	private Long id;
	private User user;
	private ServiceTypeWarranty serviceTypeWarranty;
	private Service service;
	private Date inclusionDate;

	// Constructors

	/** default constructor */
	public ServiceAreWarranty() {
	}

	public ServiceAreWarranty(Long serviceTypeWarrantyId, Long serviceId, Long userId) {
		this.serviceTypeWarranty = new ServiceTypeWarranty(serviceTypeWarrantyId);
		this.service = new Service(serviceId);
		this.user = new User(userId);
	}
	
	/** minimal constructor */
	public ServiceAreWarranty(User user,
			ServiceTypeWarranty serviceTypeWarranty, Service service) {
		this.user = user;
		this.serviceTypeWarranty = serviceTypeWarranty;
		this.service = service;
	}

	/** full constructor */
	public ServiceAreWarranty(User user,
			ServiceTypeWarranty serviceTypeWarranty, Service service,
			Date inclusionDate) {
		this.user = user;
		this.serviceTypeWarranty = serviceTypeWarranty;
		this.service = service;
		this.inclusionDate = inclusionDate;
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

	public ServiceTypeWarranty getServiceTypeWarranty() {
		return this.serviceTypeWarranty;
	}

	public void setServiceTypeWarranty(ServiceTypeWarranty serviceTypeWarranty) {
		this.serviceTypeWarranty = serviceTypeWarranty;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Date getInclusionDate() {
		return this.inclusionDate;
	}

	public void setInclusionDate(Date inclusionDate) {
		this.inclusionDate = inclusionDate;
	}

}