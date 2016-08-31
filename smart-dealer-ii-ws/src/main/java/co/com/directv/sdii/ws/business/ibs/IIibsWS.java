package co.com.directv.sdii.ws.business.ibs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;


/**
 * Servicio web que expone las operaciones a ser consumidas por IBS
 * 
 * Fecha de Creación: 28/06/2010
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="IIibsWS",targetNamespace="http://ibs.business.ws.sdii.directv.com.co/")
public interface IIibsWS {

//	/**
//	 * Metodo: Notifica cambio de fecha de atencion de servicio
//	 * @param Long customerID id del cliente
//	 * @param Long workOrderNumer codigo de la workorder
//	 * @param String newRealizationDate nueva fecha de servicio
//	 * @param String workOrderCountryId codigo del pais
//	 * @param newRealizationDate
//	 * @throws BusinessException en caso de error al ejecutar la operación
//	 * a continuación los códigos de error:<br>
//     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
//     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
//     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
//     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
//     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
//     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
//     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
//	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
//	 * @author jnova
//	 */
//	@WebMethod(operationName="changeRealizationDateIBS", action="changeRealizationDateIBS")
//	public Long changeRealizationDateIBS(@WebParam(name="customerID")Long customerID,@WebParam(name="workOrderNumer")Long workOrderNumer,@WebParam(name="newRealizationDate")String newRealizationDate,@WebParam(name="workOrderCountryId")String workOrderCountryId) throws BusinessException;
	
	/**
	 * Permite adicionar un servicio a la WO. Retorna -1 si hubo error o el numero del ID del la workorder en caso de exito
	 * 
	 * @param workOrder
	 *            - WorkOrderVO
	 * @param service
	 *            - ServiceVO
	 * @param decoSerialNumber
	 *            - String
	 * @return String
	 * @throws BusinessException
	 * @author gfandino
	 */
	@WebMethod(operationName = "addServiceToWorkOrderFromIbs", action = "addServiceToWorkOrderFromIbs")
	public void addServiceToWorkOrderFromIbs(
			@WebParam(name = "workOrderId")Long workOrderId,
			@WebParam(name = "serviceId")Long serviceId,
			@WebParam(name = "decoSerialNumber")String decoSerialNumber,
			@WebParam(name = "countryCode")String countryCode,
			@WebParam(name = "userId")Long userId) throws BusinessException;

}