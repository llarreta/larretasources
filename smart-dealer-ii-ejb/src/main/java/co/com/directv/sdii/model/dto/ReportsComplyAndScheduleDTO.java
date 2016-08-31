package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * 
 * @author Aharker
 *
 */
public class ReportsComplyAndScheduleDTO implements Serializable{

	private String clientNum;
	private String woNum;
	private String serviceCat;
	private String serviceTypeName;
	private Date creationDate;
	private Date allocationDate;
	private Date schedulerDate;
	private Date attentionDate;
	private String city;
	private String employeeName;
	private String scheduled;
	private String rescheduled;
	
	public ReportsComplyAndScheduleDTO() {
		super();
	}

	public ReportsComplyAndScheduleDTO(String clientNum, String woNum,
			String serviceCat, String serviceTypeName, Date creationDate,
			Date allocationDate, Date schedulerDate, Date attentionDate,
			String city, String employeeName, String scheduled,
			String rescheduled) {
		super();
		this.clientNum = clientNum;
		this.woNum = woNum;
		this.serviceCat = serviceCat;
		this.serviceTypeName = serviceTypeName;
		this.creationDate = creationDate;
		this.allocationDate = allocationDate;
		this.schedulerDate = schedulerDate;
		this.attentionDate = attentionDate;
		this.city = city;
		this.employeeName = employeeName;
		this.scheduled = scheduled;
		this.rescheduled = rescheduled;
	}
	public String getClientNum() {
		return clientNum;
	}
	public void setClientNum(String clientNum) {
		this.clientNum = clientNum;
	}
	public String getWoNum() {
		return woNum;
	}
	public void setWoNum(String woNum) {
		this.woNum = woNum;
	}
	public String getServiceCat() {
		return serviceCat;
	}
	public void setServiceCat(String serviceCat) {
		this.serviceCat = serviceCat;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getSchedulerDate() {
		return schedulerDate;
	}
	public void setSchedulerDate(Date schedulerDate) {
		this.schedulerDate = schedulerDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAttentionDate() {
		return attentionDate;
	}
	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getScheduled() {
		return scheduled;
	}
	public void setScheduled(String scheduled) {
		this.scheduled = scheduled;
	}
	public String getRescheduled() {
		return rescheduled;
	}
	public void setRescheduled(String rescheduled) {
		this.rescheduled = rescheduled;
	}
	
}
