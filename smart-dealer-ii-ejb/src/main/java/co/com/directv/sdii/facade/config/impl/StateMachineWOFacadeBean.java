package co.com.directv.sdii.facade.config.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.StateMachineWOFacadeLocal;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * Invoca las operaciones de negocio para
 * validar el cambio de estado de una
 * work order.
 * 
 * Fecha de Creación: 29/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="StateMachineWOFacadeLocal",mappedName="ejb/StateMachineWOFacadeLocal")
public class StateMachineWOFacadeBean implements StateMachineWOFacadeLocal {
	
	@EJB(name="StateMachineWOBusinessLocal",beanInterface=StateMachineWOBusinessLocal.class)
	private StateMachineWOBusinessLocal stateMachine;

	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro y verifica si es posible 
	 * realizar el cambio de estado.
	 * Retorna true si es posible realizar el cambio, de 
	 * lo contrario genera un BusinessException.
	 * @param WorkOrderVO workOrder
	 * @param Long statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@Deprecated
	public boolean validateStatusChangeWorkOrder(WorkOrderVO workOrder,Long statusChange) throws BusinessException {		
		return stateMachine.validateStatusChangeWorkOrder(workOrder,statusChange);
	}
	
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
	public boolean validateStatusChangeWorkOrderByCodes(WorkOrderVO workOrder,String statusChange) throws BusinessException {
		return stateMachine.validateStatusChangeWorkOrderByCodes(workOrder, statusChange);
	}
}
