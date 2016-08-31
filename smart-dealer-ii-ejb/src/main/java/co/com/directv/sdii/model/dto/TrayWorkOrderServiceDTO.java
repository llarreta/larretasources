package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;

public class TrayWorkOrderServiceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3238193650622598931L;

	private Long workOrderServiceId;
	private Boolean addService;
	private Boolean deleteService;
	private String serialNumber;
	private List<WorkOrderServiceElementVO> woServiceElements;
	private List<WorkOrderServiceElementVO> woServiceElementsRecovery;
	
	private TrayServiceDTO trayServiceDTO;
	
	public Boolean getAddService() {
		return addService;
	}
	public void setAddService(Boolean addService) {
		this.addService = addService;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public List<WorkOrderServiceElementVO> getWoServiceElements() {
		return woServiceElements;
	}
	public void setWoServiceElements(
			List<WorkOrderServiceElementVO> woServiceElements) {
		this.woServiceElements = woServiceElements;
	}
	public TrayServiceDTO getTrayServiceDTO() {
		return trayServiceDTO;
	}
	public void setTrayServiceDTO(TrayServiceDTO trayServiceDTO) {
		this.trayServiceDTO = trayServiceDTO;
	}
	public Long getWorkOrderServiceId() {
		return workOrderServiceId;
	}
	public void setWorkOrderServiceId(Long workOrderServiceId) {
		this.workOrderServiceId = workOrderServiceId;
	}
	public List<WorkOrderServiceElementVO> getWoServiceElementsRecovery() {
		return woServiceElementsRecovery;
	}
	public void setWoServiceElementsRecovery(
			List<WorkOrderServiceElementVO> woServiceElementsRecovery) {
		this.woServiceElementsRecovery = woServiceElementsRecovery;
	}
	public Boolean getDeleteService() {
		return deleteService;
	}
	public void setDeleteService(Boolean deleteService) {
		this.deleteService = deleteService;
	}		
}
