package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

public class ReportsPendingServicesByDateDTO implements Serializable {
	private String clientNumber;
	private String orderNumber;
	private String clientName;
	private String city;
	private Date creationDate;
	private String jobCard;
	private String jobCardType;
	private String jobCardStatus;
	private String technicalName;
	private Long dayQty;
	private Date appointment;
	private String address;
	private String telephone;
	private String problemDescription;
	private String model;
	private String product;
	private Date allocationDate;

	public ReportsPendingServicesByDateDTO(String clientNumber,
			String orderNumber, String clientName, String city,
			Date creationDate, String jobCard, String jobCardType,
			String jobCardStatus, String technicalName, Long dayQty,
			Date appointment, String address, String telephone,
			String problemDescription, String model, String product,
			Date allocationDate) {
		super();
		this.clientNumber = clientNumber;
		this.orderNumber = orderNumber;
		this.clientName = clientName;
		this.city = city;
		this.creationDate = creationDate;
		this.jobCard = jobCard;
		this.jobCardType = jobCardType;
		this.jobCardStatus = jobCardStatus;
		this.technicalName = technicalName;
		this.dayQty = dayQty;
		this.appointment = appointment;
		this.address = address;
		this.telephone = telephone;
		this.problemDescription = problemDescription;
		this.model = model;
		this.product = product;
		this.allocationDate = allocationDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}
	public ReportsPendingServicesByDateDTO(String clientNumber,
			String orderNumber, String clientName, String city,
			Date creationDate, String jobCard, String jobCardType,
			String jobCardStatus, String technicalName, Long dayQty,
			Date appointment, String address, String telephone,
			String problemDescription, String model, String product) {
		super();
		this.clientNumber = clientNumber;
		this.orderNumber = orderNumber;
		this.clientName = clientName;
		this.city = city;
		this.creationDate = creationDate;
		this.jobCard = jobCard;
		this.jobCardType = jobCardType;
		this.jobCardStatus = jobCardStatus;
		this.technicalName = technicalName;
		this.dayQty = dayQty;
		this.appointment = appointment;
		this.address = address;
		this.telephone = telephone;
		this.problemDescription = problemDescription;
		this.model = model;
		this.product = product;
	}
	public ReportsPendingServicesByDateDTO() {
		super();
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getJobCard() {
		return jobCard;
	}
	public void setJobCard(String jobCard) {
		this.jobCard = jobCard;
	}
	public String getJobCardType() {
		return jobCardType;
	}
	public void setJobCardType(String jobCardType) {
		this.jobCardType = jobCardType;
	}
	public String getJobCardStatus() {
		return jobCardStatus;
	}
	public void setJobCardStatus(String jobCardStatus) {
		this.jobCardStatus = jobCardStatus;
	}
	public String getTechnicalName() {
		return technicalName;
	}
	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}
	public Long getDayQty() {
		return dayQty;
	}
	public void setDayQty(Long dayQty) {
		this.dayQty = dayQty;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAppointment() {
		return appointment;
	}
	public void setAppointment(Date appointment) {
		this.appointment = appointment;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
}
