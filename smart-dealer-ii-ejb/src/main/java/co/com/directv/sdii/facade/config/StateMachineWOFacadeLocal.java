package co.com.directv.sdii.facade.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * Interfaz para la invocacion de las
 * operaciones de negocio para la maquina de
 * estados de work orders.
 * 
 * Fecha de Creación: 29/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface StateMachineWOFacadeLocal {

	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro y verifica si es posible 
	 * realizar el cambio de estado.
	 * Retorna true si es posible realizar el cambio, de 
	 * lo contrario genera un BusinessException.
	 * Este metodo sera cambiando por validateStatusChangeWorkOrderByCodes
	 * @param WorkOrderVO workOrder
	 * @param Long statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@Deprecated
	public boolean validateStatusChangeWorkOrder(WorkOrderVO workOrder,Long statusChange) throws BusinessException;
	
	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro por medio de los codigos 
	 * de estado y verifica si es posible realizar 
	 * el cambio de estado.
	 * Retorna true si es posible realizar el cambio, de 
	 * lo contrario genera un BusinessException.
	 * @param WorkOrderVO workOrder
	 * @param String statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public boolean validateStatusChangeWorkOrderByCodes(WorkOrderVO workOrder,String statusChange) throws BusinessException;
}
