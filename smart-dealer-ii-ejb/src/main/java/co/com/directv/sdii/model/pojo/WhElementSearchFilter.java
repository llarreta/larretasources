/**
 * Creado 20/08/2010 20:28:48
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * Encapsula la información de un filtro de búsqueda para elementos en bodega
 * 
 * Fecha de Creación: 20/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WhElementSearchFilter implements Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3474099997733307957L;
	
	private Long wareHouseId; 
	private Long dealerId;
	private Long branchDealerId; 
	private Long crewId;
	private Long warehouseTypeId; 
	private Long elementModelId;
	private Long elementTypeId;
	private String elementTypeCode;
	private String serialElement;
	private Date initialDate;
	private Date finalDate;
	private Long userId;
	
	
	public Long getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public Long getBranchDealerId() {
		return branchDealerId;
	}
	public void setBranchDealerId(Long branchDealerId) {
		this.branchDealerId = branchDealerId;
	}
	public Long getCrewId() {
		return crewId;
	}
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}
	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}
	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}
	public Long getElementModelId() {
		return elementModelId;
	}
	public void setElementModelId(Long elementModelId) {
		this.elementModelId = elementModelId;
	}
	public Long getElementTypeId() {
		return elementTypeId;
	}
	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}
	public String getElementTypeCode() {
		return elementTypeCode;
	}
	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}
	public Date getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}
	public Date getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
	public String getSerialElement() {
		return serialElement;
	}
	public void setSerialElement(String serialElement) {
		this.serialElement = serialElement;
	}
	
	@Override
	public String toString() {
		return "WhElementSearchFilter [branchDealerId=" + branchDealerId
				+ ", crewId=" + crewId + ", dealerId=" + dealerId
				+ ", elementModelId=" + elementModelId + ", elementTypeCode="
				+ elementTypeCode + ", elementTypeId=" + elementTypeId
				+ ", finalDate=" + finalDate + ", initialDate=" + initialDate
				+ ", serialElement=" + serialElement + ", wareHouseId="
				+ wareHouseId + ", warehouseTypeId=" + warehouseTypeId + "]";
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}	
}
