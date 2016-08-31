package co.com.directv.sdii.ws.business.stock;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.ReferenceShipmentDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.RefIncStatusVO;
import co.com.directv.sdii.model.vo.RefIncTypeVO;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.RefRecieveDataVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceModTypeVO;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.model.vo.TransportCompanyVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.ws.model.dto.ReportedElementForValidationDTO;

/**
 * Servicio web que expone las operaciones relacionadas con Reference
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="ReferenceWS",targetNamespace="http://reference.stock.business.ws.sdii.directv.com.co/")
public interface IReferenceWS {

	/**
	 * Metodo: persiste la información de un Reference
	 * @param obj objeto que encapsula la información necesaria para construir el Reference,
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
	@WebMethod(operationName="createReference", action="createReference", exclude = true)
	public void createReference(@WebParam(name="objReference")ReferenceVO objReference) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un Reference
	 * @param obj objeto que encapsula la información del Reference a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Reference
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
	@WebMethod(operationName="updateReference", action="updateReference", exclude = true)
	public void updateReference(@WebParam(name="objReference")ReferenceVO objReference) throws BusinessException;
	
	/**
	 * Metodo: borra un Reference de la persistencia
	 * @param obj objeto que encapsula la información del Reference, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteReference", action="deleteReference")
	public void deleteReference(@WebParam(name="objReference")ReferenceVO objReference) throws BusinessException;
	
	/**
	 * Elimina una remision de manera lógica
	 * @param referenceID
	 * @param userDelete
	 * @throws BusinessException
	 */
	@WebMethod(operationName="deleteReferenceLogic", action="deleteReferenceLogic")
	public Long deleteReferenceLogic( @WebParam(name="referenceID") Long referenceID,  @WebParam(name="userDelete") Long userDelete ) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un Reference dado el identificador del mismo
	 * @param id identificador del Reference a ser consultado
	 * @return Reference con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Reference con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getReferenceByID", action="getReferenceByID")
	public ReferenceVO getReferenceByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Reference almacenados en la persistencia
	 * @param country - Long identificador del país
	 * @return lista con los Reference existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllReferencesAndByCountry", action="getAllReferencesAndByCountry")
	public List<ReferenceVO> getAllReferencesAndByCountry(@WebParam(name="country")Long country) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Reference almacenados en la persistencia
	 * @return lista con los Reference existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllReferences", action="getAllReferences")
	public List<ReferenceVO> getAllReferences() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de la Reference para imprimir
	 * @param referenceId id de la reference a imprimir
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
	 * @author cduarte
	 */
	@WebMethod(operationName="getInfoToPrintReferencesById", action="getInfoToPrintReferencesById")
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(@WebParam(name="referenceId") Long referenceId)throws BusinessException;

	/**
	 * Metodo: Obtiene los Reference almacenados en la persistencia dado un conjunto de filtros
	 * @param referenceId identificador de la remisión
	 * @param sourceWhId identificador de la bodega origen
	 * @param targetWhId identificador de la bodega destino
	 * @return lista con las remisiones que coincidan con los filtros especificados, una lista vacia en caso que no se encuentren
	 * remisiones con los criterios de búsqueda especificados.
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
	@WebMethod(operationName="getReferencesByIdOrSourceWhOrTargetWh", action="getReferencesByIdOrSourceWhOrTargetWh")
	public List<ReferenceVO> getReferencesByIdOrSourceWhOrTargetWh(@WebParam(name="referenceId") Long referenceId, @WebParam(name="sourceWhId") Long sourceWhId, @WebParam(name="targetWhId") Long targetWhId, @WebParam(name="referenceCreationDate") Date referenceCreationDate, @WebParam(name="recorderUserName") String recorderUserName) throws BusinessException;
	
	/**
	 * Metodo: Actualiza la información de una remisión y los elementos asociados a ella,
	 * Caso de uso INV 31 Consulta o modificación de una remisión
	 * @param reference Información completa de la remisión a ser actualizada, se validará
	 * que tenga la información completa para su actualización
	 * @param referenceElements lista con los elementos de asociados a la remisión, se verificará que estén completos los datos
	 * ya que este caso de uso permite agregar elementos no registrados con anterioridad, en caso que el id del elemento venga
	 * especificado, se asumirá como una actualización, de lo contrario, se creará un nuevo registro de elemento.
	 * @param isFinished Determina si la remisión fué finalizada o nó, de acuerdo con el caso de uso
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
	@WebMethod(operationName="updateReferenceAndElements", action="updateReferenceAndElements")
	public void updateReferenceAndElements(@WebParam(name="reference") ReferenceVO reference,@WebParam(name="referenceElements") List<ReferenceElementItemVO> referenceElements,@WebParam(name="isFinished") boolean isFinished,@WebParam(name="userId") Long userId)throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un RefIncType
	 * @param obj objeto que encapsula la información necesaria para construir el RefIncType,
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
	@WebMethod(operationName="createRefIncType", action="createRefIncType", exclude = true)
	public void createRefIncType(@WebParam(name="objRefIncType")RefIncTypeVO objRefIncType) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefIncType
	 * @param obj objeto que encapsula la información del RefIncType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefIncType
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
	@WebMethod(operationName="updateRefIncType", action="updateRefIncType", exclude = true)
	public void updateRefIncType(@WebParam(name="objRefIncType")RefIncTypeVO objRefIncType) throws BusinessException;
	
	/**
	 * Metodo: borra un RefIncType de la persistencia
	 * @param obj objeto que encapsula la información del RefIncType, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteRefIncType", action="deleteRefIncType", exclude = true)
	public void deleteRefIncType(@WebParam(name="objRefIncType")RefIncTypeVO objRefIncType) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un RefIncType dado el identificador del mismo
	 * @param id identificador del RefIncType a ser consultado
	 * @return RefIncType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefIncType con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getRefIncTypeByID", action="getRefIncTypeByID", exclude = true)
	public RefIncTypeVO getRefIncTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los RefIncType almacenados en la persistencia
	 * @return lista con los RefIncType existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllRefIncTypes", action="getAllRefIncTypes")
	public List<RefIncTypeVO> getAllRefIncTypes() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un RefIncStatus
	 * @param obj objeto que encapsula la información necesaria para construir el RefIncStatus,
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
	@WebMethod(operationName="createRefIncStatus", action="createRefIncStatus", exclude = true)
	public void createRefIncStatus(@WebParam(name="objRefIncStatus")RefIncStatusVO objRefIncStatus) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefIncStatus
	 * @param obj objeto que encapsula la información del RefIncStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefIncStatus
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
	@WebMethod(operationName="updateRefIncStatus", action="updateRefIncStatus", exclude = true)
	public void updateRefIncStatus(@WebParam(name="objRefIncStatus")RefIncStatusVO objRefIncStatus) throws BusinessException;
	
	/**
	 * Metodo: borra un RefIncStatus de la persistencia
	 * @param obj objeto que encapsula la información del RefIncStatus, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteRefIncStatus", action="deleteRefIncStatus", exclude = true)
	public void deleteRefIncStatus(@WebParam(name="objRefIncStatus")RefIncStatusVO objRefIncStatus) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un RefIncStatus dado el identificador del mismo
	 * @param id identificador del RefIncStatus a ser consultado
	 * @return RefIncStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefIncStatus con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getRefIncStatusByID", action="getRefIncStatusByID", exclude = true)
	public RefIncStatusVO getRefIncStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los RefIncStatus almacenados en la persistencia
	 * @return lista con los RefIncStatus existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllRefIncStatuss", action="getAllRefIncStatuss")
	public List<RefIncStatusVO> getAllRefIncStatuss() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un RefInconsistency incluyendo los elementos reportados
	 * @param refInconsistency objeto que encapsula la información necesaria para construir el RefInconsistency
	 * @param reportedElements listado completo de los elementos reportados para la inconsistencia, independientemente de
	 * si ya habían sido guardados anteriormente o son nuevos.
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
	 * @author wjimenez
	 */
	@WebMethod(operationName="saveRefInconsistency", action="saveRefInconsistency")
	public void saveRefInconsistency(@WebParam(name="refInconsistency")RefInconsistencyVO refInconsistency, @WebParam(name="reportedElements")List<ReportedElementVO> reportedElements, @WebParam(name="userId")Long userId) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefInconsistency
	 * @param obj objeto que encapsula la información del RefInconsistency a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefInconsistency
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
	@WebMethod(operationName="updateRefInconsistency", action="updateRefInconsistency")
	public void updateRefInconsistency(@WebParam(name="objRefInconsistency")RefInconsistencyVO objRefInconsistency) throws BusinessException;
	
	/**
	 * Metodo: borra un RefInconsistency de la persistencia
	 * @param obj objeto que encapsula la información del RefInconsistency, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteRefInconsistency", action="deleteRefInconsistency", exclude = true)
	public void deleteRefInconsistency(@WebParam(name="objRefInconsistency")RefInconsistencyVO objRefInconsistency) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un RefInconsistency dado el identificador del mismo
	 * @param id identificador del RefInconsistency a ser consultado
	 * @return RefInconsistency con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefInconsistency con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getRefInconsistencyByID", action="getRefInconsistencyByID", exclude = true)
	public RefInconsistencyVO getRefInconsistencyByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los RefInconsistency almacenados en la persistencia
	 * @return lista con los RefInconsistency existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllRefInconsistencys", action="getAllRefInconsistencys")
	public List<RefInconsistencyVO> getAllRefInconsistencys() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ReferenceStatus
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceStatus,
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
	@WebMethod(operationName="createReferenceStatus", action="createReferenceStatus", exclude = true)
	public void createReferenceStatus(@WebParam(name="objReferenceStatus")ReferenceStatusVO objReferenceStatus) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceStatus
	 * @param obj objeto que encapsula la información del ReferenceStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceStatus
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
	@WebMethod(operationName="updateReferenceStatus", action="updateReferenceStatus", exclude = true)
	public void updateReferenceStatus(@WebParam(name="objReferenceStatus")ReferenceStatusVO objReferenceStatus) throws BusinessException;
	
	/**
	 * Metodo: borra un ReferenceStatus de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceStatus, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteReferenceStatus", action="deleteReferenceStatus", exclude = true)
	public void deleteReferenceStatus(@WebParam(name="objReferenceStatus")ReferenceStatusVO objReferenceStatus) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ReferenceStatus dado el identificador del mismo
	 * @param id identificador del ReferenceStatus a ser consultado
	 * @return ReferenceStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceStatus con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getReferenceStatusByID", action="getReferenceStatusByID", exclude = true)
	public ReferenceStatusVO getReferenceStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ReferenceStatus almacenados en la persistencia
	 * @return lista con los ReferenceStatus existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllReferenceStatuss", action="getAllReferenceStatuss")
	public List<ReferenceStatusVO> getAllReferenceStatuss() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ReferenceModType
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceModType,
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
	@WebMethod(operationName="createReferenceModType", action="createReferenceModType", exclude = true)
	public void createReferenceModType(@WebParam(name="objReferenceModType")ReferenceModTypeVO objReferenceModType) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceModType
	 * @param obj objeto que encapsula la información del ReferenceModType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceModType
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
	@WebMethod(operationName="updateReferenceModType", action="updateReferenceModType", exclude = true)
	public void updateReferenceModType(@WebParam(name="objReferenceModType")ReferenceModTypeVO objReferenceModType) throws BusinessException;
	
	/**
	 * Metodo: borra un ReferenceModType de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceModType, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteReferenceModType", action="deleteReferenceModType", exclude = true)
	public void deleteReferenceModType(@WebParam(name="objReferenceModType")ReferenceModTypeVO objReferenceModType) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ReferenceModType dado el identificador del mismo
	 * @param id identificador del ReferenceModType a ser consultado
	 * @return ReferenceModType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceModType con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getReferenceModTypeByID", action="getReferenceModTypeByID", exclude = true)
	public ReferenceModTypeVO getReferenceModTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ReferenceModType almacenados en la persistencia
	 * @return lista con los ReferenceModType existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllReferenceModTypes", action="getAllReferenceModTypes")
	public List<ReferenceModTypeVO> getAllReferenceModTypes() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ReferenceModification
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceModification,
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
	@WebMethod(operationName="createReferenceModification", action="createReferenceModification", exclude = true)
	public void createReferenceModification(@WebParam(name="objReferenceModification")ReferenceModificationVO objReferenceModification) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceModification
	 * @param obj objeto que encapsula la información del ReferenceModification a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceModification
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
	@WebMethod(operationName="updateReferenceModification", action="updateReferenceModification", exclude = true)
	public void updateReferenceModification(@WebParam(name="objReferenceModification")ReferenceModificationVO objReferenceModification) throws BusinessException;
	
	/**
	 * Metodo: borra un ReferenceModification de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceModification, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteReferenceModification", action="deleteReferenceModification", exclude = true)
	public void deleteReferenceModification(@WebParam(name="objReferenceModification")ReferenceModificationVO objReferenceModification) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ReferenceModification dado el identificador del mismo
	 * @param id identificador del ReferenceModification a ser consultado
	 * @return ReferenceModification con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceModification con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getReferenceModificationByID", action="getReferenceModificationByID", exclude = true)
	public ReferenceModificationVO getReferenceModificationByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ReferenceModification almacenados en la persistencia
	 * @return lista con los ReferenceModification existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllReferenceModifications", action="getAllReferenceModifications")
	public List<ReferenceModificationVO> getAllReferenceModifications() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un TransportCompany
	 * @param obj objeto que encapsula la información necesaria para construir el TransportCompany,
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
	@WebMethod(operationName="createTransportCompany", action="createTransportCompany")
	public void createTransportCompany(@WebParam(name="objTransportCompany")TransportCompanyVO objTransportCompany) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un TransportCompany
	 * @param obj objeto que encapsula la información del TransportCompany a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo TransportCompany
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
	@WebMethod(operationName="updateTransportCompany", action="updateTransportCompany")
	public void updateTransportCompany(@WebParam(name="objTransportCompany")TransportCompanyVO objTransportCompany) throws BusinessException;
	
	/**
	 * Metodo: borra un TransportCompany de la persistencia
	 * @param obj objeto que encapsula la información del TransportCompany, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteTransportCompany", action="deleteTransportCompany")
	public void deleteTransportCompany(@WebParam(name="objTransportCompany")TransportCompanyVO objTransportCompany) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un TransportCompany dado el identificador del mismo
	 * @param id identificador del TransportCompany a ser consultado
	 * @return TransportCompany con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el TransportCompany con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getTransportCompanyByID", action="getTransportCompanyByID")
	public TransportCompanyVO getTransportCompanyByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un TransportCompany dado el código de la misma
	 * @param code código de la TransportCompany a ser consultada
	 * @return TransportCompany con el código especificado, nulo en caso que no
	 * se encuentre compañía transportadora con ese código
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
	@WebMethod(operationName="getTransportCompanyByCode", action="getTransportCompanyByCode")
	public TransportCompanyVO getTransportCompanyByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un TransportCompany dado el código de la misma
	 * @param code código de la TransportCompany a ser consultada
	 * @param countryId identificador del país
	 * @return TransportCompany con el código especificado, nulo en caso que no
	 * se encuentre compañía transportadora con ese código
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
	@WebMethod(operationName="getTransportCompanyByCodeAndCountryId", action="getTransportCompanyByCodeAndCountryId")
	public TransportCompanyVO getTransportCompanyByCodeAndCountryId(@WebParam(name="code")String code, @WebParam(name="countryId")Long countryId) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todos los TransportCompany almacenados en la persistencia
	 * @return lista con los TransportCompany existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getTransportCompaniesByCountryId", action="getTransportCompaniesByCountryId")
	public List<TransportCompanyVO> getTransportCompaniesByCountryId(@WebParam(name = "countryId") Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los TransportCompany almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginacion.
	 * @return TransportCompanyDTO, lista con los TransportCompany existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getActiveTransportCompaniesByCountryId", action="getActiveTransportCompaniesByCountryId")
	public TransportCompanyDTO getActiveTransportCompaniesByCountryId(@WebParam(name = "countryId") Long countryId, @WebParam(name = "requestCollInfo") RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todos los TransportCompany almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginacion.
	 * @return TransportCompanyDTO, lista con los TransportCompany existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllTransportCompaniesByCountryId", action="getAllTransportCompaniesByCountryId")
	public TransportCompanyDTO getAllTransportCompaniesByCountryId(@WebParam(name = "countryId") Long countryId, @WebParam(name = "requestCollInfo") RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un estado dado el código o identificado de este
	 * @param refStatus - ReferenceStatusVO Debe contener el ID o el código del estado
	 * @param country - Long identificador del país
	 * @return  List<ReferenceVO> correspondiente al estado de remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su estado
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesByReferenceStatusAndByCountry", action="getReferencesByReferenceStatusAndByCountry")
	public List<ReferenceVO> getReferencesByReferenceStatusAndByCountry (@WebParam(name="refStatus")ReferenceStatusVO refStatus,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un estado y unas bodegas de entrada y salida
	 * @param refStatus - ReferenceStatusVO Debe contener el ID o el código del estado
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente al estado de remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su estado y bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesByReferenceStatusAndWhAndByCountry", action="getReferencesByReferenceStatusAndWhAndByCountry")
	public List<ReferenceVO> getReferencesByReferenceStatusAndWhAndByCountry (@WebParam(name="refStatus")ReferenceStatusVO refStatus,@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada y salida
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesBySourceAndTargetWareHouseAndByCountry", action="getReferencesBySourceAndTargetWareHouseAndByCountry")
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountry (@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO almacenados en la persistencia por remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> correspondiente a la remisión especificada. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de RefInconsistency por remisión
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getRefInconsistencysByReferenceID", action="getRefInconsistencysByReferenceID")
	public List<RefInconsistencyVO> getRefInconsistencysByReferenceID(@WebParam(name="refID")Long refID)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceModificationVO almacenados en la persistencia asosiada a la remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<ReferenceModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los ReferenceModification por remisión
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getReferenceModificationsByReferenceID", action="getReferenceModificationsByReferenceID")
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(@WebParam(name="refID")Long refID)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesBySourceWareHouseAndByCountry", action="getReferencesBySourceWareHouseAndByCountry")
	public List<ReferenceVO> getReferencesBySourceWareHouseAndByCountry (@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de salida
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesByTargetWareHouseAndByCountry", action="getReferencesByTargetWareHouseAndByCountry")
	public List<ReferenceVO> getReferencesByTargetWareHouseAndByCountry (@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada y su estado es inconsistente
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry", action="getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry")
	public List<ReferenceVO> getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry (@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="country")Long country)throws BusinessException;

	/**
	 * Metodo: Permite consultar las remisiones segun el filtro ingresado
	 * @param referencesFilterToPrintDTO - ReferencesFilterToPrintDTO Contiene los filtros de 
	 * idReferences: El numero de Remision
	 * whSource: La bodega de origen
	 * whTarget: La bodega Destino
	 * referenceStatusId: El estado de la Remision
	 * @return  NotSerializedElementInSelectReferenceToPrintPaginationResponse correspondiente a las remisiones segun el filtro. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author cduarte
	 */
	@WebMethod(operationName="getAllReferencesByIdSourceTargetWareHouseStatus", action="getAllReferencesByIdSourceTargetWareHouseStatus")
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			@WebParam(name="referencesFilterToPrintDTO")FilterReferencesToPrintDTO referencesFilterToPrintDTO, @WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;
	
	/**
	 * Metodo: Registra el envio de una remision creada
	 * @param referencesShipment ReferenceShipmentDTO - El objeto DTO que encapsula la data del envio
	 * de una remision dada. 
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author garciniegas 
	 */
	@WebMethod(operationName="registerReferenceShipment", action="registerReferenceShipment")
	public void registerReferenceShipment( @WebParam(name="referencesShipment")ReferenceShipmentDTO referencesShipment,
			@WebParam(name="user")UserVO user ) throws BusinessException;
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y el codigo de pais
	 * @param referenceId Long - El identificador de la remision
	 * @param countryCode Long - El codigo del pais
	 * @throws BusinessException en caso de error al tratar de listar las remisiones	 
	 * @author garciniegas 
	 */
	@WebMethod(operationName="getReferenceByIdAndCountryCode", action="getReferenceByIdAndCountryCode")
    public List<ReferenceVO>getReferenceByIdAndCountryCode( @WebParam(name="referenceId")Long referenceId,
    	   @WebParam(name="countryCode")Long countryCode )throws BusinessException;
	
	/**
	 * Metodo: Valida que la cantidad de elementos que se desea mover de una bodega este disponible
	 * CU INV 30
	 * @param referencesElementItems items de la remision
	 * @param sourceWareHouseId Id de la bodega de origen de la remision
	 * @throws BusinessException en caso de error al tratar de confirmar la remision
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_mouvement_elements_actual_quantity_lower</code> La cantidad de elementos a trasladar es superior a la disponible<br>
	 * @author jnova
	 */
	@WebMethod(operationName="validateElementsInWHQuantities", action="validateElementsInWHQuantities")
	public void validateElementsInWHQuantities(@WebParam(name="referencesElementItems") List<ReferenceElementItemVO> referencesElementItems ,@WebParam(name="sourceWareHouseId")  Long sourceWareHouseId) throws BusinessException;
	
	/**
	 * Metodo: Registra la entrega de una remisión y sus elementos
	 * Caso de uso INV 35 Registrar  la entrega  y  movimiento de los elementos de  una remisión entre  Bodegas de una misma compañía
	 * @param referenceId identificador de la remisión
	 * @param deliveryDate fecha de entrega de la remisión
	 * @param targetEmployeeId empleado que recibe los elementos en la bodega destino
	 * @param comments Comentarios sobre la entrega de la remisión
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="registerReferenceDeliveryAndElementMovement", action="registerReferenceDeliveryAndElementMovement")
	public void registerReferenceDeliveryAndElementMovement(
			@WebParam(name="referenceId") Long referenceId, 
			@WebParam(name="deliveryDate") Date deliveryDate, 
			@WebParam(name="targetEmployeeId") Long targetEmployeeId, 
			@WebParam(name="comments") String comments,
			@WebParam(name="sentUnits") Double sentUnits,
			@WebParam(name="userId") Long userId)throws BusinessException;
	
	/**
	 * Metodo: Consulta las remisiones por id de la remision y destino de la remision
	 * Caso de uso INV 34
	 * @param referenceId identificador de la remisión
	 * @param warehouseId Identificador de  la bodega de destino
	 * @return List<ReferenceVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="getReferenceByIdAndWarhouseTarget", action="getReferenceByIdAndWarhouseTarget")
    public List<ReferenceVO> getReferenceByIdAndWarhouseTarget( @WebParam(name="referenceId")Long referenceId,
    	   @WebParam(name="warehouseId")Long warehouseId )throws BusinessException;
	
	/**
	 * Metodo: Consulta las remisiones filtradas por la bodega de origen,bodega destino, 
	 * id de la remision y con estado inconsistente 
	 * Caso de uso INV 37
	 * @param whSource identificador de la bodega de origen
	 * @param whTarget Identificador de la bodega de destino
	 * @param reference Identificador de remision
	 * @param country Identificador del pais
	 * @return List<ReferenceVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	@WebMethod(operationName="getReferenceByComplexFilterToFindInconsistenceReferences", action="getReferenceByComplexFilterToFindInconsistenceReferences")
	public List<ReferenceVO> getReferenceByComplexFilterToFindInconsistenceReferences( 
			@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,
			@WebParam(name="reference")ReferenceVO reference,@WebParam(name="country")Long country )throws BusinessException;
	
	/**
	 * Metodo: Consulta los elementos serializados asociados a una remision 
	 * Caso de uso INV 37
	 * @param reference Identificador de remision
	 * @param requestCollectionInfo
	 * @return SerializedElementInReferencePaginationResponse Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	@WebMethod(operationName="getSerializedElementInReference", action="getSerializedElementInReference")
	public SerializedElementInReferencePaginationResponse getSerializedElementInReference( @WebParam(name="reference")ReferenceVO reference, @WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo )throws BusinessException;
	
	/**
	 * Metodo: Consulta los elementos no serializados asociados a una remision 
	 * Caso de uso INV 37
	 * @param reference Identificador de remision
	 * @param requestCollectionInfo
	 * @return NotSerializedElementInReferencePaginationResponse Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	
	@WebMethod(operationName="getNotSerializedElementInReference", action="getNotSerializedElementInReference")
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference( @WebParam(name="reference")ReferenceVO reference, @WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo )throws BusinessException;
	
	/**
	 * Metodo: Consulta objetos ReferenceModificationVO asociados a un objeto Reference
	 * Caso de uso INV 37
	 * @param refID Identificador de remision
	 * @return List<ReferenceModificationVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	@WebMethod(operationName="getReferenceModificationsInReference", action="getReferenceModificationsInReference")
	public List<ReferenceModificationVO> getReferenceModificationsInReference( @WebParam(name="refID")ReferenceVO refID)throws BusinessException;
	
	
	/**
	 * Metodo: Consulta las detalles de una remision
	 * Caso de uso INV 34
	 * @param referenceId identificador de la remisión
	 * @return ReferenceVO Referencia con todos los detalles necesarios para realizar actualizaciones
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="getReferenceDetailsById", action="getReferenceDetailsById")
    public ReferenceVO getReferenceDetailsById( @WebParam(name="referenceId")Long referenceId)throws BusinessException;
	
	/**
	 * Metodo: Consulta objetos RefInconsistencyVO asociados a un Reference
	 * Caso de uso INV 37
	 * @param refID Identificador de remision
	 * @return List<RefInconsistency> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	@WebMethod(operationName="getReferenceInconsistencyByReferenceID", action="getReferenceInconsistencyByReferenceID")
	public List<RefInconsistencyVO>getReferenceInconsistencyByReferenceID( @WebParam(name="id")Long id )throws BusinessException;
	
	
	/**
	 * Metodo: Guarda las entregas de una remision dada
	 * Caso de uso INV 34 - Opcion 2
	 * @param referenceId identificador de la remisión
	 * @param deliveriesList List<DeliveryVO> Lista de entregas ingresadas por el usuario
	 * @param specialCommentsList List<SpecialCommentVO> Lista de comentarios especial ingresados por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="saveReferenceDeliveries", action="saveReferenceDeliveries")
    public void saveReferenceDeliveries( @WebParam(name="referenceId")Long referenceId , @WebParam(name="deliveriesList")List<DeliveryVO> deliveriesList , @WebParam(name="specialCommentsList")List<SpecialCommentVO> specialCommentsList)throws BusinessException;
	
	
	/**
	 * Metodo: Actualiza el estado de las inconsistencias asociados a un Reference
	 * Caso de uso INV 37
	 * @param inconsistenciesListIds Una lista con los identificadores de los objetos RefInconsistency a cambiar a estado cerrado
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	@WebMethod(operationName="closeReferenceInconsistencyStatus", action="closeReferenceInconsistencyStatus")
	public void closeReferenceInconsistencyStatus( @WebParam(name="inconsistenciesListIds")List<Long>inconsistenciesListIds )throws BusinessException;
	
	/**
	 * Metodo: Actualiza las cantidades de los objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param reference - Reference el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @param items - los objetos ReferenceElementItem que seran actualizados
	 * @param finished - Indica el flujo alternativo para terminar o guardar en la modificacion de elementos en la remision
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	@WebMethod(operationName="modifyReferenceElementItemCuantityByReferenceAndItemList", action="modifyReferenceElementItemCuantityByReferenceAndItemList")
	public void modifyReferenceElementItemCuantityByReferenceAndItemList( 
			@WebParam(name="items")List<ReferenceElementItemVO>items,
			@WebParam(name="reference")ReferenceVO reference,@WebParam(name="finished")Boolean finished,@WebParam(name="user")UserVO user )throws BusinessException;
	
	/**
	 * Metodo: adiciona objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param reference - Reference el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @param items - los objetos ReferenceElementItem que seran adicionados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	@WebMethod(operationName="addReferenceElementItemToReference", action="addReferenceElementItemToReference")
	public void addReferenceElementItemToReference( @WebParam(name="items")List<ReferenceElementItemVO>items,
			@WebParam(name="reference")ReferenceVO reference )throws BusinessException;
	
	/**
	 * Metodo: elimina objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param itemsId - los id de los objetos ReferenceElementItem que seran eliminados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	@WebMethod(operationName="deleteReferenceElementItemInReference", action="deleteReferenceElementItemInReference")
	public void deleteReferenceElementItemInReference(@WebParam(name="itemsId")List<Long>itemsId )throws BusinessException;
	
	/**
	 * Metodo: Confirma el recibido de todos los elementos de una remision poniendo el estado de la remision y sus elementos
	 * en recepcionado
	 * Caso de uso INV 34 - Opcion 2
	 * LLama a CU 12
	 * LLama a CU 14
	 * LLama a CU 16
	 * LLama a CU 08
	 * @param referenceId Long identificador de la remisión
	 * @param user UserVO Usuario que realiza la confirmacion
	 * @throws BusinessException En caso de error al modificar el estado de la remision o sus elementos y/o al llamado
	 * de los casos de uso de los cuales es dependiente
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="confirmReferenceElementsReception", action="confirmReferenceElementsReception")
    public void confirmReferenceElementsReception( @WebParam(name="referenceId")Long referenceId , @WebParam(name="user")UserVO user)throws BusinessException;
	
	/**
	 * Metodo: Confirma el recibido de los elementos NO serializados de una remision poniendo el estado de la remision en recepcionado
	 * si todos sus elementos quedan confirmados o en cofirmado parcial en caso contrario y los elementos en recepcionados
	 * Caso de uso INV 34 - Opcion 4
	 * LLama a CU 12
	 * LLama a CU 08
	 * @param referenceId Long identificador de la remisión
	 * @param user UserVO Usuario que realiza la confirmacion
	 * @throws BusinessException En caso de error al modificar el estado de la remision o sus elementos y/o al llamado
	 * de los casos de uso de los cuales es dependiente
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="confirmNotSerializeReferenceElementsReception", action="confirmNotSerializeReferenceElementsReception")
    public void confirmNotSerializeReferenceElementsReception( @WebParam(name="referenceId")Long referenceId , @WebParam(name="user")UserVO user)throws BusinessException;
	
	/**
	 * Metodo: Confirma el recibido de los elementos serializados de una remision poniendo el estado de la remision en recepcionado
	 * si todos sus elementos quedan confirmados o en cofirmado parcial en caso contrario y los elementos en recepcionados
	 * Caso de uso INV 34 - Opcion 5
	 * LLama a CU 14
	 * LLama a CU 16
	 * LLama a CU 08
	 * @param referenceId Long identificador de la remisión
	 * @param user UserVO Usuario que realiza la confirmacion
	 * @throws BusinessException En caso de error al modificar el estado de la remision o sus elementos y/o al llamado
	 * de los casos de uso de los cuales es dependiente
	 * a continuación la lista de errorer que puede lanzar la operación
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
	@WebMethod(operationName="confirmSerializeReferenceElementsReception", action="confirmSerializeReferenceElementsReception")
    public void confirmSerializeReferenceElementsReception( @WebParam(name="referenceId")Long referenceId , @WebParam(name="user")UserVO user)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesByReferenceAndWhAndByCountry", action="getReferencesByReferenceAndWhAndByCountry")
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountry (@WebParam(name="ref")Long ref,@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-33
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID de la bodega; nulo si no hace parte de la consulta
	 * @para refStatus - ReferenceStatusVO Debe contener el ID del estadoo; nulo si no hace parte de la consulta
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 *  <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesByReferenceAndWhAndRefStatusRefStatus", action="getReferencesByReferenceAndWhAndRefStatusRefStatus")
	public ReferenceResponse getReferencesByReferenceAndWhAndRefStatusRefStatus(@WebParam(name="ref")Long ref,
			 @WebParam(name="whSource")WarehouseVO whSource,  @WebParam(name="whTarget")WarehouseVO whTarget,  @WebParam(name="refStatus")ReferenceStatusVO refStatus, @WebParam(name="userId")Long userId, @WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo )
			throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones por fecha de confirmacion de remision: remisiones que tienen la fecha
	 * esperada de entrega menor a la actual y el estado esta en "Enviada" o "recepcionada parcial"
	 * CU Inv-49
	 * @param userId Long ID usuario que ingresa a la aplicacion y solicita las remisiones por fecha de confirmacion
	 * @param country - Long identificador del país
	 * @return  List<ReferenceVO> Lista que contiene las remisiones que tienen como fecha esperada de entrega menor
	 * a la actual y el estado en "enviado" o "recepcionado parcial"
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference
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
	@WebMethod(operationName="getReferencesByConfirmationDateAndByCountry", action="getReferencesByConfirmationDateAndByCountry")
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(@WebParam(name="dealerId")Long dealerId,@WebParam(name="country")Long country,@WebParam(name="requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega destino
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas 
	 */
	@WebMethod(operationName="getReferenceByIdAndWarehouseTargetToAddInconsistence", action="getReferenceByIdAndWarehouseTargetToAddInconsistence")
    public List<ReferenceVO>getReferenceByIdAndWarehouseTargetToAddInconsistence(@WebParam(name="referenceId")Long referenceId,@WebParam(name="warehouseId")Long warehouseId )throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a una remision
	 * @param referenceId Long ID de la remision
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas
	 */
	@WebMethod(operationName="getConfirmationsByReferenceId", action="getConfirmationsByReferenceId")
    public List<RefConfirmationVO> getConfirmationsByReferenceId( @WebParam(name="referenceId")Long referenceId ) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a una remision y a un elemento en especifico
	 * @param referenceId Long ID de la remision
	 * @param elementId Long ID del elemento
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	  * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas
	 */
	@WebMethod(operationName="getConfirmationsByReferenceIdAndElementId", action="getConfirmationsByReferenceIdAndElementId")
	public List<RefConfirmationVO> getConfirmationsByReferenceIdAndElementId( 
			@WebParam(name="referenceId")Long referenceId,@WebParam(name="elementId")Long elementId ) throws BusinessException;
	
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega de origen
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas 
	 */
	@WebMethod(operationName="getReferenceByIdAndWarehouseSourceAndWareHouseTarget", action="getReferenceByIdAndWarehouseSourceAndWareHouseTarget")
    public List<ReferenceVO>getReferenceByIdAndWarehouseSourceAndWareHouseTarget( 
    		@WebParam(name="referenceId")Long referenceId,@WebParam(name="whSourceId")Long whSourceId,
    		@WebParam(name="whTargetId")Long whTargetId )throws BusinessException;
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega de origen
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas 
	 */
	@WebMethod(operationName="getReferenceByIdAndWarehouseSource", action="getReferenceByIdAndWarehouseSource")
    public List<ReferenceVO>getReferenceByIdAndWarehouseSource( 
    		@WebParam(name="referenceId")Long referenceId,@WebParam(name="warehouseId")Long warehouseId )throws BusinessException;
	
	
	
	 /**
	 * Caso de uso INV 20 Regresa las remisiones a las que esta asociado un elemento
	 * 
	 * Metodo: persiste la informacion de serialized
	 * @param elementId el id del elemento
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author garciniegas
	 */
	
	@WebMethod(operationName="getReferencesByElementId", action="getReferencesByElementId")
    public List<ReferenceVO> getReferencesByElementId(@WebParam(name="elementId")Long elementId) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas en estado creada
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
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
	@WebMethod(operationName="getReferencesByReferenceAndWhAndByCountryAndCreatedStatus", action="getReferencesByReferenceAndWhAndByCountryAndCreatedStatus")
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatus (@WebParam(name="ref")Long ref,@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas en estado creada y en creacion
	 * CU Inv-31
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
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
	@WebMethod(operationName="getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus", action="getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus")
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus (@WebParam(name="ref")Long ref,@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas en estado enviado o confirmado parcial
	 * CU Inv-34
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
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
	@WebMethod(operationName="getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed", action="getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed")
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed (@WebParam(name="ref")Long ref,@WebParam(name="whSource")WarehouseVO whSource,@WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las remisiones dados los criterios de filtro y además cuyas bodegas 
	 * origen y destino correspondan al mismo dealer
	 * @param ref identificador de la remisión
	 * @param whSource información de la bodega origen
	 * @param whTarget información de la bodega destino
	 * @param country identificador del país
	 * @return Lista con las remisiones que coinciden con los filtros seleccionados cuyas bodegas origen y destino 
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner", action="getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner")
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(@WebParam(name="ref")Long ref, @WebParam(name="whSource")WarehouseVO whSource, @WebParam(name="whTarget")WarehouseVO whTarget,@WebParam(name="country")Long country)throws BusinessException;
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega de origen Solo traerá el listado de remisiones si y solo si
	 * la bodega origen y la bodega destino de las remisiones son de diferentes compañías
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author jjimenezh 
	 */
	@WebMethod(operationName="getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs", action="getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs")
    public List<ReferenceVO> getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(@WebParam(name="referenceId") Long referenceId,@WebParam(name="sourceWarehouseId") Long sourceWarehouseId )throws BusinessException;
    
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author gfadnino
	 */
	@WebMethod(operationName="getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs", action="getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs")
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(@WebParam(name="ref") Long ref, @WebParam(name="whSource") WarehouseVO whSource,@WebParam(name="whTarget") WarehouseVO whTarget,@WebParam(name = "country") Long country)throws BusinessException;
	
	/**
     * Método: Consulta las remisiones en estado creado o en creación  por un filtro establecido
     * CU INV-32
     * @param referenceDTO - ReferencesFilterDTO filtro de remisiones
     * @param requestCollInfo - RequestCollectionInfo Paginación
     * @return ReferenceResponse - Datos de Remisión paginados
     * @throws BusinessException en caso de error al ejecutar la consulta de las Reference en estado creado o en creación  por un filtro establecido
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
	@WebMethod(operationName="getCreatedReferencesByFilter", action="getCreatedReferencesByFilter")
    public ReferenceResponse getCreatedReferencesByFilter (@WebParam(name="referenceDTO")ReferencesFilterDTO referenceDTO,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	 /**
     * Método: Genera los datos básicos de la remisión
     * INV CU-30
     * @param reference - ReferenceVO datos de la remisión
     * @return ReferenceVO - retorna los datos de la remisión con el ID asignado
     * @throws BusinessException en caso de error al generar los datos de la remisión
     * @author gfandino
     */
	@WebMethod(operationName="generateReference", action="generateReference")
    public ReferenceVO generateReference(@WebParam(name="reference")ReferenceVO reference)throws BusinessException;
	
	
	/**
	 * Método: Permite guardar el control de cantidades
	 * @param reference -  Remisión a la que pertenece la lista de control de cantidades
	 * @param qtyCtrlItems - List<RefQuantityControlItemVO>  Lista de control de cantidades
	 * @throws BusinessException en caso de error guardando el control de cantidades
	 * @author gfandino
	 */
	@WebMethod(operationName="saveQtyRefCtrlItems", action="saveQtyRefCtrlItems")
	public void saveQtyRefCtrlItems(@WebParam(name="reference")ReferenceVO reference, @WebParam(name="qtyCtrlItems")List<RefQuantityControlItemVO> qtyCtrlItems)throws BusinessException;
	
	/**
     * Método: UC30 (sección 21 prototipo) Se consultan las remisiones por la bodega de origen
     * @param idWhSource - Long identificador de la bodega de origen
     * @param requestCollInfo - RequestCollectionInfo datos de la paginación
     * @return RefQuantityControlItemsResponse con los datos de las remisiones precargadas paginadas
     * @throws BusinessException en caso de error en la consulta
     * @author gfandino
     */
	@WebMethod(operationName="getReferencesPreloadByCreationProcess", action="getReferencesPreloadByCreationProcess")
    public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(@WebParam(name="idWhSource")Long idWhSource,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
     * Método: UC30 
     * @param idWhSource - Long identificador de la bodega de origen
     * @param requestCollInfo - RequestCollectionInfo datos de la paginación
     * @return RefQuantityControlItemsResponse con los datos de las remisiones precargadas paginadas
     * @throws BusinessException en caso de error en la consulta
     * @author gfandino
     */
	@WebMethod(operationName="getReferencesPreloadByID", action="getReferencesPreloadByID")
    public RefQuantityControlItemVO getReferencesPreloadByID(@WebParam(name="idRefQtyCtrl")Long idRefQtyCtrl)throws BusinessException;
	
	  /**
	 * Metodo: INV 129 marca como eliminado un ReferenceVO
	 * @param ref información del ReferenceVO a ser marcado como eliminado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de Reference
	 * @author gfandino
	 */
	@WebMethod(operationName="logicDeleteReference", action="logicDeleteReference")
	public void logicDeleteReference(@WebParam(name="ref")ReferenceVO ref) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las remisiones en precarga a partir de un identificador de bodega de origen
	 * @param sourceWhId Identificador de la bodega de origen
	 * @param RequestCollectionInfo RequestCollectionInfo Paginación
	 * @return List<ReferenceVO> Lista de remisiones en precarga que tiene como bodega de origen el parametro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="getPreloadedReferences", action="getPreloadedReferences")
	public ReferenceResponse getPreloadedReferences(@WebParam(name="sourceWhId")Long sourceWhId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Método: Obtiene la información del contrl de cantidades para uan remisión
	 * @param refId - Long identificador de la remisión
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return RefQuantityControlItemsResponse datos paginados
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	@WebMethod(operationName="getRefQtyCtrlItemsByReference", action="getRefQtyCtrlItemsByReference")
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(@WebParam(name="refId")RefQuantityControlItemVO refId,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Consulta los elementos reportados como inconsistencia de remisión para una inconsistencia en particular
	 * @param refInconsistencyId identificador de la inconsistencia de remisión
	 * @param incluirSobrantes indica si los resultados deben incluir inconsistencias con cantidades positivas
	 * @param incluirFaltantes indica si los resultados deben incluir inconsistencias con cantidades negativas
	 * @return List<ReportedElementVO> listado de los elementos reportados como inconsistencia de remisión para la inconsistencia
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getReportedElementsByRefInconsistencyId", action="getReportedElementsByRefInconsistencyId")
	public List<ReportedElementVO> getReportedElementsByRefInconsistencyId(@WebParam(name="refInconsistencyId")Long refInconsistencyId, @WebParam(name="incluirSobrantes")boolean incluirSobrantes, @WebParam(name="incluirFaltantes")boolean incluirFaltantes) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO con estado abierto almacenados en la persistencia por remisión
	 * @param referenceId - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> con estado abierto correspondiente a la remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	@WebMethod(operationName="getReferenceInconsistenciesOpenedByReferenceId", action="getReferenceInconsistenciesOpenedByReferenceId")
	public RefInconsistencyResponse getReferenceInconsistenciesOpenedByReferenceId(
			@WebParam(name="referenceId")Long referenceId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO con estado cerrado almacenados en la persistencia por remisión
	 * @param referenceId - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> con estado cerrado correspondiente a la remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */	
	@WebMethod(operationName="getReferenceInconsistenciesClosedByReferenceId", action="getReferenceInconsistenciesClosedByReferenceId")
	public RefInconsistencyResponse getReferenceInconsistenciesClosedByReferenceId(
			@WebParam(name="referenceId")Long referenceId,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite validar si una remision es de la misma compania o de diferente compania para realizar el envio de una remision
	 * @param referenceId identificador de la remision
	 * @return true si la remision es entre la misma compania, false si no lo es
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="isReferenceFromSameDealer", action="isReferenceFromSameDealer")
	public boolean isReferenceFromSameDealer(@WebParam(name="referenceId")Long referenceId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza la accion de enviar la remision en el CU-30 y CU-32
	 * @param reference remision con datos necesarios para el envio de la remision
	 * @param isBetweenDifDealers indica si es una remision entre diferentes companias: true si es de la misma, false si no
	 * @param validateQuantityControl indica si se deben validar los controles de cantidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	*/
	@WebMethod(operationName="sendReference", action="sendReference")
	public void sendReference(@WebParam(name="referenceVO")ReferenceVO referenceVO,@WebParam(name="isBetweenDifDealers")boolean isBetweenDifDealers,@WebParam(name="validateQuantityControl")boolean validateQuantityControl, @WebParam(name="userId")Long userId) throws BusinessException;

	 /**
	 * Metodo: consulta las remisiones generadas para una inconsistencia de menos elementos físicos
	 * @param refIncId identificador de la inconsistencia
	 * @return List<ReferenceVO> listado de objetos Reference que contiene solo los identificadores
	 * @throws BusinessException En caso de error en la consulta de referencia por filtro
	 * @author wjimenez
	 */
	@WebMethod(operationName="getDependentReferences", action="getDependentReferences")
	public List<ReferenceVO> getDependentReferences(@WebParam(name="refIncId")Long refIncId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las remisiones por el filtro enviado por parametro
	 * @param referenceDTO Filtro para la consulta de remisiones
	 * @param requestCollInfo objeto para manejo de paginacion
	 * @return ReferenceResponse Objeto que contiene la respuesta a la consulta y objeto de paginacion
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="getReferencesByFilter", action="getReferencesByFilter")
    public ReferenceResponse getReferencesByFilter (@WebParam(name="referenceDTO")ReferencesFilterDTO referenceDTO,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Valida si una remision no se le han ingresado los datos de ingreso 
	 * @param referenceId Long identificador de la remision a ser validada
	 * @return boolean true si ya tiene los datos de ingreso, false si no los tiene
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="validateReferenceReceiveData", action="validateReferenceReceiveData")
	public boolean validateReferenceReceiveData(@WebParam(name="referenceId")Long referenceId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea los datos de ingreso de una remision
	 * @param refRecieveDataVO datos de ingreso de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="createRefRecieveData", action="createRefRecieveData")
	public void createRefRecieveData(@WebParam(name="refRecieveDataVO")RefRecieveDataVO refRecieveDataVO, 
			@WebParam(name = "userId") Long userId) throws BusinessException;

	/**
	 * Metodo: cierra una inconsistencia generando una nueva remisión de devolución y realizando los
	 * correspondientes movimientos entre bodegas
	 * @param refInconsistencyVO objeto con id, answer, answerUserId, reference.id, reference.warehouses, reference.warehouses.dealer, reference.country.id
	 * @return en caso que la inconsistencia sea por menos elementos físicos, retorna el identificador de la remisión
	 * asociada. De lo contrario retorna cero.
	 * @throws BusinessException en caso de error en el cierre de la inconsistencia
	 * @author wjimenez
	 */
	@WebMethod(operationName="closeRefInconsistency", action="closeRefInconsistency")
	public Long closeRefInconsistency(@WebParam(name="refInconsistencyVO")RefInconsistencyVO refInconsistencyVO, @WebParam(name = "userId") Long userId ) throws BusinessException;

	/**
	 * Metodo: Busca los elementos de la remisión que fueron agregados como resultado del cierre
	 * de una inconsistencia de mas elementos físicos
	 * @param refInconsistencyId identificador de la inconsistencia cerrada
	 * @return List<ReferenceElementItemVO> listado de elementos de remisión que fueron agregados
	 * @throws BusinessException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	@WebMethod(operationName="getAddedElements", action="getAddedElements")
	public List<ReferenceElementItemVO> getAddedElements(@WebParam(name="refInconsistencyId")Long refInconsistencyId) throws BusinessException;

	/**
	 * Metodo: valida la existencia de diferencias entre las cantidades solicitadas
	 * y cantidades existentes en una remisión
	 * @param referenceId identificador de la referencia
	 * @return verdadero si no se encuentran diferencias en las cantidades 
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="validateExistenceOfReferenceQuantityDifferences", action="validateExistenceOfReferenceQuantityDifferences")
	public boolean validateExistenceOfReferenceQuantityDifferences(@WebParam(name="referenceId")Long referenceId) throws BusinessException;

	/**
	 * Metodo: de acuerdo a las bodegas de la remisión, determina si la remisión requiere un proceso estricto
	 * de confirmación de la remisión y registro de inconsistencias
	 * @param sourceWhId identificador de la bodega de origen
	 * @param targetWhId identificador de la bodega destino
	 * @return verdadero si se requiere realizar el proceso
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="isReferenceRequiresValidationProcess", action="isReferenceRequiresValidationProcess")
	public boolean isReferenceRequiresValidationProcess(@WebParam(name="sourceWhId")Long sourceWhId, @WebParam(name="targetWhId")Long targetWhId)
			throws BusinessException;

	/**
	 * Metodo: valida que los elementos de una inconsistencia de remisión con menos elementos físicos, tengan una cantidad
	 * adecuada de acuerdo a las existencias en la remisión
	 * @param elementsToValidate
	 * @throws BusinessException
	 * @author wjimenez
	 */
	@WebMethod(operationName="validateReportedElementsForLessElementsInRefInc", action="validateReportedElementsForLessElementsInRefInc")
	public void validateReportedElementsForLessElementsInRefInc(
			@WebParam(name="elementsToValidate")List<ReportedElementForValidationDTO> elementsToValidate)
			throws BusinessException;
	
}
