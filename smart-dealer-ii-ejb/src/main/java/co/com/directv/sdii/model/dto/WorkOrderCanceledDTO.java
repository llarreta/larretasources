package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

public class WorkOrderCanceledDTO implements Serializable {

	private Long woId;
	private String woCode;
	private String woDescription;
	private Date woCreationDate;
	private Date woCancelationDate;
	private String serviceHourName;
	private String dealerCode;
	private String dealerName;
	private String customerCode;
	private String customerName;
	private String crewResponsibleName;
	private String cancelationDescription;
	public Long getWoId() {
		return woId;
	}
	public void setWoId(Long woId) {
		this.woId = woId;
	}
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public String getWoDescription() {
		return woDescription;
	}
	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getWoCreationDate() {
		return woCreationDate;
	}
	public void setWoCreationDate(Date woCreationDate) {
		this.woCreationDate = woCreationDate;
	}
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getWoCancelationDate() {
		return woCancelationDate;
	}
	public void setWoCancelationDate(Date woCancelationDate) {
		this.woCancelationDate = woCancelationDate;
	}
	public String getServiceHourName() {
		return serviceHourName;
	}
	public void setServiceHourName(String serviceHourName) {
		this.serviceHourName = serviceHourName;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCrewResponsibleName() {
		return crewResponsibleName;
	}
	public void setCrewResponsibleName(String crewResponsibleName) {
		this.crewResponsibleName = crewResponsibleName;
	}
	public String getCancelationDescription() {
		return cancelationDescription;
	}
	public void setCancelationDescription(String cancelationDescription) {
		this.cancelationDescription = cancelationDescription;
	}
	public WorkOrderCanceledDTO() {
		super();
	}
	public WorkOrderCanceledDTO(Long woId, String woCode, String woDescription,
			Date woCreationDate, Date woCancelationDate,
			String serviceHourName, String dealerCode, String dealerName,
			String customerCode, String customerName,
			String crewResponsibleName, String cancelationDescription) {
		super();
		this.woId = woId;
		this.woCode = woCode;
		this.woDescription = woDescription;
		this.woCreationDate = woCreationDate;
		this.woCancelationDate = woCancelationDate;
		this.serviceHourName = serviceHourName;
		this.dealerCode = dealerCode;
		this.dealerName = dealerName;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.crewResponsibleName = crewResponsibleName;
		this.cancelationDescription = cancelationDescription;
	}
	
}
