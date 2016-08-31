package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * ServiceHour entity. @author MyEclipse Persistence Tools
 */

public class ServiceHour implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2106878429348318767L;

	private Long id;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ServiceHourStatus serviceHourStatus;
    @BusinessRequired
	private String serviceHoursName;
    @BusinessRequired
	private Date initTime;
    @BusinessRequired
	private Date endTime;
    @BusinessRequired
    private Country country;

	// Constructors

	/** default constructor */
	public ServiceHour() {
	}
	
	public ServiceHour(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public ServiceHour(ServiceHourStatus serviceHourStatus,
			String serviceHoursName, Date initTime, Date endTime) {
		this.serviceHourStatus = serviceHourStatus;
		this.serviceHoursName = serviceHoursName;
		this.initTime = initTime;
		this.endTime = endTime;
	}
	
	/** full constructor */
	public ServiceHour(ServiceHourStatus serviceHourStatus,
			String serviceHoursName, Date initTime, Date endTime,Country country) {
		this.serviceHourStatus = serviceHourStatus;
		this.serviceHoursName = serviceHoursName;
		this.initTime = initTime;
		this.endTime = endTime;
		this.country = country;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceHourStatus getServiceHourStatus() {
		return this.serviceHourStatus;
	}

	public void setServiceHourStatus(ServiceHourStatus serviceHourStatus) {
		this.serviceHourStatus = serviceHourStatus;
	}

	public String getServiceHoursName() {
		return this.serviceHoursName;
	}

	public void setServiceHoursName(String serviceHoursName) {
		this.serviceHoursName = serviceHoursName;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getInitTime() {
		return this.initTime;
	}

	public void setInitTime(Date initTime) {
		this.initTime = initTime;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ServiceHour [id=" + id + ", serviceHoursName="
				+ serviceHoursName + "]";
	}
	
}