package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

public class WoManagmentElement implements Serializable, Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4632684899985791952L;

	private Long id;
	private Long elementId;
	private String elementCode;
	private String isSerialized;
	private String userLastChange;
	private Date dateLastChange;
	private String serialCode;
	private Double quantity;
	private Date managmentDate;
	private String linkedSerialCode;
	private Long woId;
	private Long serviceId;
	private String codeTypeMovement;
	private String codeElementClassType;
	
	private WorkOrderService workOrderService;

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}

	public WoManagmentElement(){
		
	}
	
	public WoManagmentElement(Long id, 
 			                  Long elementId,
                    		  String elementCode, 
                    		  String isSerialized, 
                    		  String userLastChange,
                    		  Date dateLastChange, 
                    		  String serialCode, 
                    		  Double quantity,
			                  Date managmentDate, 
			                  String linkedSerialCode, 
			                  Long woId, 
			                  Long serviceId, 
			                  String codeTypeMovement, 
			                  String codeElementClassType,
			                  WorkOrderService workOrderServiceParam) {
		this.workOrderService=workOrderServiceParam;
		
		this.id = id;
		this.elementId = elementId;
		this.elementCode = elementCode;
		this.isSerialized = isSerialized;
		this.userLastChange = userLastChange;
		this.dateLastChange = dateLastChange;
		this.serialCode = serialCode;
		this.quantity = quantity;
		this.managmentDate = managmentDate;
		this.linkedSerialCode = linkedSerialCode;
		this.woId = woId;
		this.serviceId = serviceId;
		this.codeTypeMovement  = codeTypeMovement;
		this.codeElementClassType = codeElementClassType;
	}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getElementId() {
		return elementId;
	}
	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}
	public String getElementCode() {
		return elementCode;
	}
	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
	}
	public String getIsSerialized() {
		return isSerialized;
	}
	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}
	public String getUserLastChange() {
		return userLastChange;
	}
	public void setUserLastChange(String userLastChange) {
		this.userLastChange = userLastChange;
	}
	public Date getDateLastChange() {
		return dateLastChange;
	}
	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Date getManagmentDate() {
		return managmentDate;
	}
	public void setManagmentDate(Date managmentDate) {
		this.managmentDate = managmentDate;
	}

	public String getLinkedSerialCode() {
		return linkedSerialCode;
	}

	public void setLinkedSerialCode(String linkedSerialCode) {
		this.linkedSerialCode = linkedSerialCode;
	}

	public Long getWoId() {
		return woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getCodeTypeMovement() {
		return codeTypeMovement;
	}

	public void setCodeTypeMovement(String codeTypeMovement) {
		this.codeTypeMovement = codeTypeMovement;
	}
	
	public String getCodeElementClassType() {
		return codeElementClassType;
	}

	public void setCodeElementClassType(String codeElementClassType) {
		this.codeElementClassType = codeElementClassType;
	}

	@Override
	public String toString() {
		return "WoManagmentElement [dateLastChange=" + dateLastChange
				+ ", elementCode=" + elementCode + ", elementId=" + elementId
				+ ", id=" + id + ", isSerialized=" + isSerialized
				+ ", linkedSerialCode=" + linkedSerialCode + ", managmentDate="
				+ managmentDate + ", quantity=" + quantity + ", serialCode="
				+ serialCode + ", serviceId=" + serviceId + ", userLastChange="
				+ userLastChange + ", woId=" + woId + "]";
	}	
	
}
