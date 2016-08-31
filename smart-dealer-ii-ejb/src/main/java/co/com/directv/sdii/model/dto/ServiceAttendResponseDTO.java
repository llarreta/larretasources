/**
 * 
 */
package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WorkOrderService;

/**
 * Clase que encapsula la respuesta de una atención de servicios.
 * 
 * @author jvelezmu
 *
 */
public class ServiceAttendResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6610140812819194100L;
	private WoAssignment lastAssignment;
	private Long workOrderServiceId;	
	private WorkOrderService woService;
	
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
	public WorkOrderService getWoService() {
		return woService;
	}
	public void setWoService(WorkOrderService woService) {
		this.woService = woService;
	}
}
