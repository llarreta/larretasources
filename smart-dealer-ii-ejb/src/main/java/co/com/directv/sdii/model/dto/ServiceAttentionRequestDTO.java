/**
 * 
 */
package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Clase que encapsula los datos para realizar la atención
 * de un servicio de una WO.
 * 
 * @author jvelezmu
 *
 */
public class ServiceAttentionRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5530727258268006628L;
	
	private Boolean workOrderAttendFinished;
	private WorkOrderVO workorderVo;
	private WorkOrderServiceVO workorderService;
	private Long userId;
	
	public WorkOrderVO getWorkorderVo() {
		return workorderVo;
	}
	public void setWorkorderVo(WorkOrderVO workorderVo) {
		this.workorderVo = workorderVo;
	}
	public WorkOrderServiceVO getWorkorderService() {
		return workorderService;
	}
	public void setWorkorderService(WorkOrderServiceVO workorderService) {
		this.workorderService = workorderService;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Boolean getWorkOrderAttendFinished() {
		return workOrderAttendFinished;
	}
	public void setWorkOrderAttendFinished(Boolean workOrderAttendFinished) {
		this.workOrderAttendFinished = workOrderAttendFinished;
	}
	
}
