package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * WorkOrderService entity. 
 * @author MyEclipse Persistence Tools
 */

public class WorkOrderService implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5108133057628645905L;
	private Long id;
	private Service service;
	private WoServiceStatus woServiceStatus;
	private Long woId;
	private Date serviceInclusionDate;
	private String serialNumber;
	private String ibsServiceKey;
	private String linkedSerialNumber;
	private boolean isShippingOrderRequired = false;


	// Constructors

	/** default constructor */
	public WorkOrderService() {
	}

	/** minimal constructor */
	public WorkOrderService(Service service) {
		this.service = service;		
	}
	/** minimal constructor */
	public WorkOrderService(Service service, Long woId,
			Date serviceInclusionDate) {
		this.service = service;
		this.woId = woId;
		this.serviceInclusionDate = serviceInclusionDate;
	}

	/** full constructor */
	public WorkOrderService(Service service, Long woId,
			Date serviceInclusionDate, String serialNumber) {
		this.service = service;
		this.woId = woId;
		this.serviceInclusionDate = serviceInclusionDate;
		this.serialNumber = serialNumber;
		
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	public WoServiceStatus getWoServiceStatus() {
		return woServiceStatus;
	}

	public void setWoServiceStatus(WoServiceStatus woServiceStatus) {
		this.woServiceStatus = woServiceStatus;
	}

	public Long getWoId() {
		return this.woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getServiceInclusionDate() {
		return this.serviceInclusionDate;
	}

	public void setServiceInclusionDate(Date serviceInclusionDate) {
		this.serviceInclusionDate = serviceInclusionDate;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIbsServiceKey() {
		return ibsServiceKey;
	}

	public void setIbsServiceKey(String ibsServiceKey) {
		this.ibsServiceKey = ibsServiceKey;
	}

	public String getLinkedSerialNumber() {
		return linkedSerialNumber;
	}

	public void setLinkedSerialNumber(String linkedSerialNumber) {
		this.linkedSerialNumber = linkedSerialNumber;
	}

	@Override
	public String toString() {
		return "WorkOrderService [id=" + id + ", service=" + service
				+ ", woId=" + woId + "]";
	}

	public boolean isShippingOrderRequired() {
		return isShippingOrderRequired;
	}

	public void setShippingOrderRequired(boolean isShippingOrderRequired) {
		this.isShippingOrderRequired = isShippingOrderRequired;
	}
	

}