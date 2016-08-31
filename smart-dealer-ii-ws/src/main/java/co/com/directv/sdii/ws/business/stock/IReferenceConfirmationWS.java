package co.com.directv.sdii.ws.business.stock;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Servicio web que expone las operaciones relacionadas con ReferenceConfirmation
 * 
 * Fecha de Creación: 19/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="ReferenceConfirmationWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IReferenceConfirmationWS {

	/**
	 * Metodo: Persiste la confirmacion de un elemento NO serializado
	 * CU 34 opcion 7
	 * @param confirmation List<RefConfirmationVO> Objetos que encapsulan la informacion de las confirmaciones
	 * @param user UserVO Objeto que encapsula la informacion del usuario que ingresa la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
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
	 * <code>sdii_CODE_reference_confirmation_greater_than_mov_quantity</code> En caso que la confirmacion que se va a ingresar junto con las ya existentes superen la cantidad de la remision<br>
	 * @author jnova
	 */
	@WebMethod(operationName="saveAllNotSerializedReferenceElementItemConfirmation", action="saveAllNotSerializedReferenceElementItemConfirmation")
	public void saveAllNotSerializedReferenceElementItemConfirmation(@WebParam(name="refConfirmations")List<RefConfirmationVO> confirmations , @WebParam(name="user")UserVO user,@WebParam(name="refId")Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Persiste la confirmacion de varios elemento serializado
	 * CU 34 opcion 8
	 * @param confirmation List<RefConfirmationVO> Lista de objetos que encapsulan la informacion de las confirmaciones
	 * @param user UserVO Objeto que encapsula la informacion del usuario que ingresa la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
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
	 * @author jnova
	 */
	@WebMethod(operationName="saveSerializedReferenceElementItemConfirmation", action="saveSerializedReferenceElementItemConfirmation")
	public void saveSerializedReferenceElementItemConfirmation(@WebParam(name="refConfirmations")List<RefConfirmationVO> confirmation , @WebParam(name="user")UserVO user,@WebParam(name="refId")Long referenceId) throws BusinessException;
}