package co.com.directv.sdii.ws.business.stock;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.model.vo.AdjustmentStatusVO;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.TransferReasonVO;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;

/**
 * Servicio web que expone las operaciones relacionadas con Adjustment
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="AdjustmentWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IAdjustmentWS {

	/**
	 * Metodo: persiste la información de un Adjustment
	 * @param obj objeto que encapsula la información necesaria para construir el Adjustment,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
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
	@WebMethod(operationName="createAdjustment", action="createAdjustment", exclude = false)
	public Long createAdjustment(@WebParam(name="objAdjustment")AdjustmentVO objAdjustment, @WebParam(name="userId")Long userId) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un Adjustment
	 * @param obj objeto que encapsula la información del Adjustment a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Adjustment
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
	@WebMethod(operationName="updateAdjustment", action="updateAdjustment", exclude = true)
	public void updateAdjustment(@WebParam(name="objAdjustment")AdjustmentVO objAdjustment,@WebParam(name="userId")Long userId) throws BusinessException;
	
	/**
	 * Metodo: borra un Adjustment de la persistencia
	 * @param obj objeto que encapsula la información del Adjustment, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteAdjustment", action="deleteAdjustment", exclude = true)
	public void deleteAdjustment(@WebParam(name="objAdjustment")AdjustmentVO objAdjustment) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un Adjustment dado el identificador del mismo
	 * @param id identificador del Adjustment a ser consultado
	 * @return Adjustment con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Adjustment con el id, ver códigos de excepción.
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
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAdjustmentByID", action="getAdjustmentByID", exclude = false)
	public AdjustmentVO getAdjustmentByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Adjustment almacenados en la persistencia
	 * @return lista con los Adjustment existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllAdjustments", action="getAllAdjustments", exclude = true)
	public List<AdjustmentVO> getAllAdjustments() throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los tipos de ajuste 
	 * @return lista con los Adjustment existentes, una lista vacia en caso que no exista ninguno.
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
	 * @author waguilera
	 */
	public List<AdjustmentTypeVO> getAllAdjustmentsTypes() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea un ajuste de entrada, salida o translado para un elemento serializado
	 * @param adjusment
	 * @param element
	 * @param serialized
	 * @return AdjustmentVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	@WebMethod(operationName="createAdjustmentSerializedElement", action="createAdjustmentSerializedElement")
	public AdjustmentVO createAdjustmentSerializedElement(@WebParam(name="listAdjustmentElementsVO")List<AdjustmentElementDTO> listAdjustmentElementsVO, @WebParam(name="adjustmentVO")AdjustmentVO adjustmentVO, @WebParam(name="userId")Long userId) throws BusinessException;

	
	/**
	 * Metodo:  Persiste la información de un objeto TransferReasonVO
	 * @param obj objeto que encapsula la información de un TransferReasonVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public void createTransferReason(TransferReasonVO obj) throws BusinessException, DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un TransferReasonVO
	 * @param obj objeto que encapsula la información de un TransferReasonVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateTransferReason(TransferReasonVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un TransferReasonVO
	 * @param obj información del TransferReasonVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public void deleteTransferReason(TransferReasonVO obj) throws BusinessException, DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los TransferReason almacenados en la persistencia
	 * @return Lista con los TransferReason existentes, una lista vacia en caso que no existan TransferReason en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los TransferReason
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los TransferReason
	 * @author mrugeles
	 */
	public List<TransferReasonVO> getAllTransferReasons() throws BusinessException;

	/**
	 * Método: Permite consultar todos los TransferReason en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param TransferReasonStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return TransferReasonResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @author gfandino
	 */
	public TransferReasonResponse getTransferReasonByFilter(String TransferReasonName,RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * Metodo: Obtiene la información de un TransferReason por su identificador
	 * @param id identificador del TransferReason a ser consultado
	 * @return objeto con la información del TransferReason dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason por ID
	 * @author mrugeles
	 */
	public TransferReasonVO getTransferReasonByID(Long id) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de un TransferReason dado su estado
	 * @param status - String estado del elemento
	 * @return List<TransferReason> correspondiente al estado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason por estado
	 * @author gfandino
	 */
	public List<TransferReasonVO> getTransferReasonsByIsActive(String status)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Método encargado de obtener los causales de ajuste a partir de el tipo de ajuste
	 * que se reciba como parametro.
	 * Si son causal de entrada o de calidad retorna los MovementType de entrada o de salida de 
	 * acuerdo a al tipo, si es un causal de translado consulta la tabla de TransferReason
	 * Un TransferReason esta compuesto por dos MovementType.
	 * @param adjustment
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	@WebMethod(operationName="getAdjustmentReasonbyAdjustmentType", action="getAdjustmentReasonbyAdjustmentType")
	public List<TransferReasonVO> getAdjustmentReasonbyAdjustmentType(@WebParam(name="adjustmentType")AdjustmentTypeVO adjustmentType)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Método encargado de la creación de ajustes de entrada, salida y translado para elementos no
	 * serializados
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="createAdjustmentNotSerializedElement", action="createAdjustmentNotSerializedElement")
	public AdjustmentVO createAdjustmentNotSerializedElement(
			@WebParam(name="listAdjustmentElementsVO")List<AdjustmentElementDTO> listAdjustmentElementsVO,
			@WebParam(name="adjustmentVO")AdjustmentVO adjustmentVO, @WebParam(name="userId")Long userId) throws BusinessException;

	/**
	 * 
	 * Metodo: Método encargado de consultar un elemento serializado y 
	 * verificar si posee ubicacion
	 * @param serialCode
	 * @param userId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="findSerializedElement", action="findSerializedElement")
	public SerializedVO findSerializedElement(
			@WebParam(name="serialCode")String serialCode,
			@WebParam(name="userId")Long userId) throws BusinessException;

	@WebMethod(operationName="searchAdjustmentsBySearchParameters", action="searchAdjustmentsBySearchParameters")
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(@WebParam(name="paramsRequestDTO")AdjustmentRequestDTO paramsRequestDTO, 
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;	
	
	/**
	 * 
	 * Metodo encargado de consultar los elementos correspondientes a un ajuste
	 * para su autorizacion.  Previamente se chequea que el ajuste no se encuentre 
	 * en estado 'Autorizando'.
	 * @param request
	 * @param requestCollInfo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	@WebMethod(operationName="getCheckAdjustmentElementsForAuthorization", action="getCheckAdjustmentElementsForAuthorization")
	public AdjustmentElementCollDTO getCheckAdjustmentElementsForAuthorization(
			@WebParam(name="request")AdjustmenElementsRequestDTO request,
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo encargado de consultar los elementos correspondientes a un ajuste
	 * para su autorizacion
	 * @param request
	 * @param requestCollInfo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	@WebMethod(operationName="getAdjustmentElementsForAuthorization", action="getAdjustmentElementsForAuthorization")
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(
			@WebParam(name="request")AdjustmenElementsRequestDTO request,
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo encargado de realizar el ajuste para los elementos indicados
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	@WebMethod(operationName="createAdjustmentElementsAuthorization", action="createAdjustmentElementsAuthorization")
	public void createAdjustmentElementsAuthorization(
			@WebParam(name="request")AdjustmenElementsRequestDTO request) throws BusinessException;
	
	@WebMethod(operationName="approvalAllElementsOfAdjustment", action="approvalAllElementsOfAdjustment")
	public void approvalAllElementsOfAdjustment(@WebParam(name="request")AdjustmenElementsRequestDTO request) throws BusinessException ;

	
	/**
	 * 
	 * Metodo encargado de consultar los estados para un ajuste
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	@WebMethod(operationName="getAllAdjustmentStatus", action="getAllAdjustmentStatus")
	public List<AdjustmentStatusVO> getAllAdjustmentStatus() throws BusinessException;
	
}
