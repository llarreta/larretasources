/**
 * 
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Clase que encapsula los datos para la creación
 * de una nueva WorkOrder cuando existen servicios
 * que no han sido atendidos.
 * 
 * @author jvelezmu
 *
 */
public class CreateNewWORequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6983084499819539108L;
	private WorkOrderVO workorderVo;
	private WoServicesList rejectedWoServices;
	
	public WorkOrderVO getWorkorderVo() {
		return workorderVo;
	}
	public void setWorkorderVo(WorkOrderVO workorderVo) {
		this.workorderVo = workorderVo;
	}
	public WoServicesList getRejectedWoServices() {
		return rejectedWoServices;
	}
	public void setRejectedWoServices(WoServicesList rejectedWoServices) {
		this.rejectedWoServices = rejectedWoServices;
	}
	
}
