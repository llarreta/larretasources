package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;

/**
 * 
 * Objeto para transportar los servicios paginados.
 * 
 * Fecha de Creación: 16/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WorkOrderServiceResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3465756116911940136L;

	private List<WorkOrderService> workOrderServices;
	private List<WorkOrderServiceVO> workOrderServicesVO;
	
	public List<WorkOrderService> getWorkOrderServices() {
		return workOrderServices;
	}
	public void setWorkOrderServices(List<WorkOrderService> workOrderServices) {
		this.workOrderServices = workOrderServices;
	}
	public List<WorkOrderServiceVO> getWorkOrderServicesVO() {
		return workOrderServicesVO;
	}
	public void setWorkOrderServicesVO(List<WorkOrderServiceVO> workOrderServicesVO) {
		this.workOrderServicesVO = workOrderServicesVO;
	}	
}
