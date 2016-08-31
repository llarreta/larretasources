package co.com.directv.sdii.ejb.business.optimus;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.WorkOrder;


/**
 * Interfaz que define los métodos de negocio para el módulo de desmarca .
 * @since 08/05/2015
 * @author 
 *
 */
@Local
public interface WorkOrderMarkOptimusBusinessBeanLocal {

	
	/**
	 * Método que permite eliminar el OptimusStatus y OptimusStatusDate de una Work Order.
	 * 
	 * @param workOrder
	 * @throws BusinessException
	 */
	public void deleteWorkOrderOptimusStatus(WorkOrder workOrder) throws BusinessException;
	
	
	/**
	 * Método que permite eliminar el optimusDeclineCode y optimusDeclineDescription de una Work Order.
	 * 
	 * @param workOrder
	 * @throws BusinessException
	 */
	public void deleteWorkOrderOptimusStatusDecline(WorkOrder workOrder, Long userId) throws BusinessException;
	
	
}
