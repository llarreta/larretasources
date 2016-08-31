package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Clase que encapsula la petición del reporte de los
 * elementos utilizados y recuperados en la prestación
 * de servicios.
 * 
 * @author jvelezmu
 *
 */
public class WOAttentionElementsRequestDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197502169689569941L;
	
	private Long userId;
	private Long workOrderServiceId;
	private WorkOrderService woService;
	private List<WorkOrderServiceElementVO> installationNotSerializedElements;
	private List<WorkOrderServiceElementVO> installationSerializedElements;
	private List<WorkOrderServiceElementVO> recoverySerializedElements;
	private WorkOrderVO workorderVo;
	private Long dealerId;
	private Long dealerCode;
	private WoAssignment lastAssignment;
	private WorkOrderServiceElementVO woServiceElementVO;
	private String swapFallaTecnicaOld;
	private String swapFallaTecnicaNew;
	private String attentionComment;
	private String crewResponsibleName;
	private String customerCode;
	
	public WorkOrderVO getWorkorderVo() {
		return workorderVo;
	}
	public void setWorkorderVo(WorkOrderVO workorderVo) {
		this.workorderVo = workorderVo;
	}	
	public List<WorkOrderServiceElementVO> getInstallationNotSerializedElements() {
		return installationNotSerializedElements;
	}
	public void setInstallationNotSerializedElements(
			List<WorkOrderServiceElementVO> installationNotSerializedElements) {
		this.installationNotSerializedElements = installationNotSerializedElements;
	}
	public List<WorkOrderServiceElementVO> getInstallationSerializedElements() {
		return installationSerializedElements;
	}
	public void setInstallationSerializedElements(
			List<WorkOrderServiceElementVO> installationSerializedElements) {
		this.installationSerializedElements = installationSerializedElements;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public WoAssignment getLastAssignment() {
		return lastAssignment;
	}
	public void setLastAssignment(WoAssignment lastAssignment) {
		this.lastAssignment = lastAssignment;
	}
	public Long getWorkOrderServiceId() {
		return workOrderServiceId;
	}
	public void setWorkOrderServiceId(Long workOrderServiceId) {
		this.workOrderServiceId = workOrderServiceId;
	}
	public WorkOrderServiceElementVO getWoServiceElementVO() {
		return woServiceElementVO;
	}
	public void setWoServiceElementVO(WorkOrderServiceElementVO woServiceElementVO) {
		this.woServiceElementVO = woServiceElementVO;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<WorkOrderServiceElementVO> getRecoverySerializedElements() {
		return recoverySerializedElements;
	}
	public void setRecoverySerializedElements(
			List<WorkOrderServiceElementVO> recoverySerializedElements) {
		this.recoverySerializedElements = recoverySerializedElements;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getSwapFallaTecnicaOld() {
		return swapFallaTecnicaOld;
	}
	public void setSwapFallaTecnicaOld(String swapFallaTecnicaOld) {
		this.swapFallaTecnicaOld = swapFallaTecnicaOld;
	}
	public String getSwapFallaTecnicaNew() {
		return swapFallaTecnicaNew;
	}
	public void setSwapFallaTecnicaNew(String swapFallaTecnicaNew) {
		this.swapFallaTecnicaNew = swapFallaTecnicaNew;
	}
	public String getAttentionComment() {
		return attentionComment;
	}
	public void setAttentionComment(String attentionComment) {
		this.attentionComment = attentionComment;
	}
	public String getCrewResponsibleName() {
		return crewResponsibleName;
	}
	public void setCrewResponsibleName(String crewResponsibleName) {
		this.crewResponsibleName = crewResponsibleName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public WorkOrderService getWoService() {
		return woService;
	}
	public void setWoService(WorkOrderService woService) {
		this.woService = woService;
	}	
}
