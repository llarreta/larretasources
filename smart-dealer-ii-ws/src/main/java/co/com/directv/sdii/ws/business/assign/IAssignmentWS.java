/**
 * Creado 14/06/2011 09:25:28
 */
package co.com.directv.sdii.ws.business.assign;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;

/**
 * Define las operaciones del WS para asignación 
 * 
 * Fecha de Creación: 14/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@WebService(name="AssignmentWS",targetNamespace="http://assign.business.ws.sdii.directv.com.co/")
public interface IAssignmentWS {

	/**
	 * Metodo: Realiza el proceso de asignación
	 * @param request petición con los parámetros para la evaluación de la asignación
	 * @return respuesta con la información de la compañía seleccionada
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="assignDealer", action="assignDealer", exclude = false)
	public AssignResponseDTO assignDealer(@WebParam(name="request") AssignRequestDTO request)throws BusinessException;
	
	
}
