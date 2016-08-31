package co.com.directv.sdii.ws.business.core.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.StateMachineWOFacadeLocal;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.business.core.IStateMachineWoWs;

/**
 * Web Service que expone los servicios de la
 * maquina de estados. Verifica si un 
 * cambio de estado de una work order es permitido.
 * Consume operaciones de Negocio para realizar las
 * validaciones respectivas.
 * Fecha de Creacion: 21/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@MTOM(threshold=3072)
@WebService(serviceName="StateMachineService",
		endpointInterface="co.com.directv.sdii.ws.business.core.IStateMachineWoWs",
		targetNamespace="http://directvla.com/contract/sdii/",
		portName="StateMachinePort")		
@Stateless()
public class StateMachineWoWs implements IStateMachineWoWs{
	
	@EJB
	private StateMachineWOFacadeLocal stateMachine;
	
	
	/**
	 * Metodo: Valida el cambio de estado de una work order por medio
	 * del id de un WorkorderStatus.
	 * @param workOrder
	 * @param statusChange
	 * @return boolean
	 * @throws BusinessException en caso de validar el estado de una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_wo_state_change_not_allowed</code> En caso de que el Cambio de estado no es permitido.<br>
	 */
	@Deprecated
	public boolean validateStatusChangeWorkOrder(WorkOrderVO workOrder,Long statusChange) throws BusinessException{
		return stateMachine.validateStatusChangeWorkOrder(workOrder,statusChange);
	}
	
	
	/**
	 * Metodo: Valida el cambio de estado de una work order por medio
	 * del codigo de un WorkorderStatus.
	 * @param workOrder
	 * @param statusChange
	 * @return boolean
	 * @throws BusinessException en caso de validar el estado de una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_wo_state_change_not_allowed</code> En caso de que el Cambio de estado no es permitido.<br>
	 */
	public Boolean validateStatusChangeWorkOrderByCodes(WorkOrderVO workOrder,String statusChange) throws BusinessException{
		Boolean response;
		response =  stateMachine.validateStatusChangeWorkOrderByCodes(workOrder,statusChange);
		return response;
	}
}
