package co.com.directv.sdii.ejb.business.core;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * Interfaz que contiene las operaciones de la
 * maquina de estados, los cuales validan si es
 * posible el cambio de un estado actual de una
 * work order a otro estado.
 * El flujo de estados, o los posibles estados
 * por los cuales puede pasar una Work order se
 * encuentran realacionados en una tabla en la
 * Base de Datos.
 * 
 * Fecha de Creaci�n: 28/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface StateMachineWOBusinessLocal {
	
	
	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro y verifica si es posible 
	 * realizar el cambio de estado.
	 * @param WorkOrderVO workOrder
	 * @param Long statusChange
	 * @return boolean
	 * @throws BusinessException 
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
	 * @throws BusinessException 
	 * @author jalopez
	 */
	public boolean validateStatusChangeWorkOrderByCodes(WorkOrderVO workOrder,String statusChange) throws BusinessException;
}
