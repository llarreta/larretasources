package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * DTO para encapsular la informacion
 * que expone el servicio de Contacts. 
 * 
 * Fecha de Creación: 8/11/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ContactDTO extends BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4417604695783527872L;
	
	private Long woId;
	private Long dealerId;
	private String woCode;
	private Long woStatusHistoryId;
	private Long countryId;
	private Long woReasonId;
	private String description;
	private WorkOrderVO workorder;
	private String countryCode;
	private String customerCode;
	private Long categoryId;
	
	
	
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public Long getWoId() {
		return woId;
	}
	public void setWoId(Long woId) {
		this.woId = woId;
	}
	public Long getWoStatusHistoryId() {
		return woStatusHistoryId;
	}
	public void setWoStatusHistoryId(Long woStatusHistoryId) {
		this.woStatusHistoryId = woStatusHistoryId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public WorkOrderVO getWorkorder() {
		return workorder;
	}
	public void setWorkorder(WorkOrderVO workorder) {
		this.workorder = workorder;
	}	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getWoReasonId() {
		return woReasonId;
	}
	public void setWoReasonId(Long woReasonId) {
		this.woReasonId = woReasonId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}	
}
