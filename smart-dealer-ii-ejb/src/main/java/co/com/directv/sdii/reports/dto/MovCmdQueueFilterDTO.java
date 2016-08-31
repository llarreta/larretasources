package co.com.directv.sdii.reports.dto;

import java.util.Date;

import co.com.directv.sdii.model.pojo.User;


public class MovCmdQueueFilterDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300370510673117471L;
	
	private Long idWarehouseSource;
	private Long idWarehouseTarget;
	private String processType;
	private String serialCode;
	private String status;
	private String isManagment;
	private String documentType;
	private Long documentNumber;
	private Long customerCodeIbs;
	private Date initialDateCreation;
	private Date finalDateCreation; 
	private Long userId;
	
	public MovCmdQueueFilterDTO(){}

	public Long getIdWarehouseSource() {
		return idWarehouseSource;
	}

	public void setIdWarehouseSource(Long idWarehouseSource) {
		this.idWarehouseSource = idWarehouseSource;
	}

	public Long getIdWarehouseTarget() {
		return idWarehouseTarget;
	}

	public void setIdWarehouseTarget(Long idWarehouseTarget) {
		this.idWarehouseTarget = idWarehouseTarget;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsManagment() {
		return isManagment;
	}

	public void setIsManagment(String isManagment) {
		this.isManagment = isManagment;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Long getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Long getCustomerCodeIbs() {
		return customerCodeIbs;
	}

	public void setCustomerCodeIbs(Long customerCodeIbs) {
		this.customerCodeIbs = customerCodeIbs;
	}

	public Date getInitialDateCreation() {
		return initialDateCreation;
	}

	public void setInitialDateCreation(Date initialDateCreation) {
		this.initialDateCreation = initialDateCreation;
	}

	public Date getFinalDateCreation() {
		return finalDateCreation;
	}

	public void setFinalDateCreation(Date finalDateCreation) {
		this.finalDateCreation = finalDateCreation;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
