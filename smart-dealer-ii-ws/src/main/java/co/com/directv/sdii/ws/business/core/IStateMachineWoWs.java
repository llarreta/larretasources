package co.com.directv.sdii.ws.business.core;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * Interaz que define las operaciones de la maquina de estados.
 * 
 * Fecha de Creación: 25/06/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="StateMachine",targetNamespace="http://directvla.com/contract/sdii/")
public interface IStateMachineWoWs {
	
	@WebMethod(operationName = "validateStatusChangeWorkOrder", action = "validateStatusChangeWorkOrder", exclude = true)
	@RequestWrapper(localName = "validateStatusChangeRequest", className = "co.com.directv.sdii.ws.business.core.ValidateStatusChangeWorkOrderByCodesRequest", targetNamespace = "http://directvla.com/contract/sdii/")
	@ResponseWrapper(localName = "validateStatusChangeResponse", className = "co.com.directv.sdii.ws.business.core.ValidateStatusChangeWorkOrderByCodesResponse", targetNamespace = "http://directvla.com/contract/sdii/")
	@Deprecated
	public @WebResult(name = "validateStatusChangeResult", targetNamespace = "http://directvla.com/contract/sdii/")
	boolean validateStatusChangeWorkOrder(
			@WebParam(name = "workOrder", targetNamespace = "http://directvla.com/contract/sdii/") WorkOrderVO workOrder,
			@WebParam(name = "statusChange", targetNamespace = "http://directvla.com/contract/sdii/") Long statusChange)
			throws BusinessException;

	@WebMethod(operationName = "validateStatusChangeWorkOrderByCodes", action = "validateStatusChangeWorkOrderByCodes")
	@RequestWrapper(localName = "validateStatusRequest", className = "co.com.directv.sdii.ws.business.core.ValidateStatusChangeWorkOrderByCodesRequest", targetNamespace = "http://directvla.com/contract/sdii/")
	@ResponseWrapper(localName = "validateStatusResponse", className = "co.com.directv.sdii.ws.business.core.ValidateStatusChangeWorkOrderByCodesResponse", targetNamespace = "http://directvla.com/contract/sdii/")
	public @WebResult(name = "validateStatusResult", targetNamespace = "http://directvla.com/contract/sdii/")
	Boolean validateStatusChangeWorkOrderByCodes(
			@WebParam(name = "workOrder", targetNamespace = "http://directvla.com/contract/sdii/") WorkOrderVO workOrder,
			@WebParam(name = "statusChange", targetNamespace = "http://directvla.com/contract/sdii/") String statusChange)
			throws BusinessException;

}
