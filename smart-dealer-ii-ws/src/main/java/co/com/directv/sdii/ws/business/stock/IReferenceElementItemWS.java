package co.com.directv.sdii.ws.business.stock;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.ReferenceElementItemsDTO;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * Servicio web que expone las operaciones relacionadas con ReferenceElementItem
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="ReferenceElementItemWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IReferenceElementItemWS {

	/**
	 * Metodo: persiste la información de un ReferenceElementItem
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceElementItem,
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
	@WebMethod(operationName="createReferenceElementItem", action="createReferenceElementItem", exclude = true)
	public void createReferenceElementItem(@WebParam(name="objReferenceElementItem")ReferenceElementItemVO objReferenceElementItem) throws BusinessException;
	
	
	/**
	 * Caso de uso INV 50 Cargar elementos serializados a una Remisi�n desde archivo plano
	 * 
	 * Metodo: persiste la informacion de serialized
	 * @param referenceID Long id de la remision a la que se le van a adicionar elementos 
	 * @param List<Serialized> listSerialized Lista de los serialized que van a asociarse a una remision
	 * @param sourceWareHouseId Long Id de la bodega de origen
	 * @param targetWareHouseId Long Id de la bodega de destino
	 * @param userId UserVO usuario que crea la remision
	 * @return Long Id de la remision creada
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
	@WebMethod(operationName="createReferenceByFile", action="createReferenceByFile")
	public Long createReferenceByFile(@WebParam(name="refElements") List<ReferenceElementItemVO> refElements, @WebParam(name = "userId") Long userId ) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceElementItem
	 * @param obj objeto que encapsula la información del ReferenceElementItem a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceElementItem
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
	@WebMethod(operationName="updateReferenceElementItem", action="updateReferenceElementItem", exclude = true)
	public void updateReferenceElementItem(@WebParam(name="objReferenceElementItem")ReferenceElementItemVO objReferenceElementItem) throws BusinessException;
	
	/**
	 * Metodo: borra un ReferenceElementItem de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceElementItem, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteReferenceElementItem", action="deleteReferenceElementItem", exclude = true)
	public void deleteReferenceElementItem(@WebParam(name="objReferenceElementItem")ReferenceElementItemVO objReferenceElementItem) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ReferenceElementItem dado el identificador del mismo
	 * @param id identificador del ReferenceElementItem a ser consultado
	 * @return ReferenceElementItem con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceElementItem con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getReferenceElementItemByID", action="getReferenceElementItemByID", exclude = true)
	public ReferenceElementItemVO getReferenceElementItemByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ReferenceElementItem almacenados en la persistencia
	 * @return lista con los ReferenceElementItem existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllReferenceElementItems", action="getAllReferenceElementItems")
	public List<ReferenceElementItemVO> getAllReferenceElementItems() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un RefConfirmation
	 * @param obj objeto que encapsula la información necesaria para construir el RefConfirmation,
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
	@WebMethod(operationName="createRefConfirmation", action="createRefConfirmation", exclude = true)
	public void createRefConfirmation(@WebParam(name="objRefConfirmation")RefConfirmationVO objRefConfirmation) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefConfirmation
	 * @param obj objeto que encapsula la información del RefConfirmation a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefConfirmation
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
	@WebMethod(operationName="updateRefConfirmation", action="updateRefConfirmation", exclude = true)
	public void updateRefConfirmation(@WebParam(name="objRefConfirmation")RefConfirmationVO objRefConfirmation) throws BusinessException;
	
	/**
	 * Metodo: borra un RefConfirmation de la persistencia
	 * @param obj objeto que encapsula la información del RefConfirmation, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteRefConfirmation", action="deleteRefConfirmation", exclude = true)
	public void deleteRefConfirmation(@WebParam(name="objRefConfirmation")RefConfirmationVO objRefConfirmation) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un RefConfirmation dado el identificador del mismo
	 * @param id identificador del RefConfirmation a ser consultado
	 * @return RefConfirmation con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefConfirmation con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getRefConfirmationByID", action="getRefConfirmationByID", exclude = true)
	public RefConfirmationVO getRefConfirmationByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los RefConfirmation almacenados en la persistencia
	 * @return lista con los RefConfirmation existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllRefConfirmations", action="getAllRefConfirmations")
	public List<RefConfirmationVO> getAllRefConfirmations() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un Delivery
	 * @param obj objeto que encapsula la información necesaria para construir el Delivery,
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
	@WebMethod(operationName="createDelivery", action="createDelivery", exclude = true)
	public void createDelivery(@WebParam(name="objDelivery")DeliveryVO objDelivery) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un Delivery
	 * @param obj objeto que encapsula la información del Delivery a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Delivery
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
	@WebMethod(operationName="updateDelivery", action="updateDelivery", exclude = true)
	public void updateDelivery(@WebParam(name="objDelivery")DeliveryVO objDelivery) throws BusinessException;
	
	/**
	 * Metodo: borra un Delivery de la persistencia
	 * @param obj objeto que encapsula la información del Delivery, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteDelivery", action="deleteDelivery", exclude = true)
	public void deleteDelivery(@WebParam(name="objDelivery")DeliveryVO objDelivery) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un Delivery dado el identificador del mismo
	 * @param id identificador del Delivery a ser consultado
	 * @return Delivery con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Delivery con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getDeliveryByID", action="getDeliveryByID", exclude = true)
	public DeliveryVO getDeliveryByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Delivery almacenados en la persistencia
	 * @return lista con los Delivery existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllDeliverys", action="getAllDeliverys")
	public List<DeliveryVO> getAllDeliverys() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * @param refID - Long Identificador de la remisión
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ReferenceElementItemsResponse, List<ReferenceElementItemVO> correspondiente a la remisión especificada; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por remisión
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
	@WebMethod(operationName="getReferenceElementItemsByReferenceID", action="getReferenceElementItemsByReferenceID")
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(@WebParam(name="refID")Long refID, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la lista de elementos no serializados de una remision
	 * @param referenceId Long ID de la remision de la que se desean obtener los elementos
	 * @return List<ReferenceElementItemVO> lista de elementos no serializados junto con su lista de confirmaciones parciales
	 * @throws BusinessException En caso de error en la consulta de elementos no serializados
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
	@WebMethod(operationName="getNotSerializedReferenceElementItemByReferenceId", action="getNotSerializedReferenceElementItemByReferenceId")
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceId(@WebParam(name="refID")Long referenceId) throws BusinessException;
	
	
	
	/**
	 * Metodo: Permite generar la recepción de elementos serializados de una remisión
	 * @param refElements - Elementos de la remisión
	 * @throws BusinessException En caso de error generanlo la recepción de elementos para la remisión
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que una de las entidades requeridas no sea encontrada durante el proceso<br>
	 * <code>sdii_references_not_exist</code> En caso que la remisión no exista<br>
	 * <code>sdii_CODE_element_not_exist</code> En caso que algun elemento de la remisión no exista<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="receptRefSerialElementItem", action="receptRefSerialElementItem")
	public void receptRefSerialElementItem(@WebParam(name="refElements")List<ReferenceElementItemVO> refElements) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene la información de los ReferenceElementItemVO 
	 * almacenados en la persistencia correspondientes a una remisión.
	 * @param refID Long, id de la referencia
	 * @param requestCollInfo RequestCollectionInfo, parametros para la paginacion
	 * @return ReferenceElementItemsDTO
	 * @throws BusinessException 
	 * @author jalopez
	 */
	@WebMethod(operationName="getReferenceElementSerializedNotSerialized", action="getReferenceElementSerializedNotSerialized")
	public ReferenceElementItemsDTO getReferenceElementSerializedNotSerialized(@WebParam(name="refID")Long refID, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
//	/**
//     * Método: INV30 permite adicionar un elemento a una remisión
//     * @param refElementVO - ReferenceElementItemVO item a ingresar
//     * @throws BusinessException en caso de error adicionando el elemento a la remisión
//     * @author gfandino
//     */
//	@WebMethod(operationName="addElementToReference", action="addElementToReference")
//    public void addElementToReference (@WebParam(name="refElementVO")ReferenceElementItemVO refElementVO)throws BusinessException;
//   
	/**
	 * 
	 * Metodo: Elimina un elemento de una remision y lo devuelve a la bodega de origen de la remision desde la bodega de 
	 * transito origen
	 * @param refId Identificador de la remision
	 * @param serialCode Serial del elemento que se va a eliminar de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="removeElementOfReference", action="removeElementOfReference")
	public void removeElementOfReference(@WebParam(name="item")ReferenceElementItemVO item, @WebParam(name="userId")Long userId)throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los ElementDTO almacenados en la persistencia correspondientes a una remisión
	 * filtrados por serial o por tipo de elemento en caso que se busquen elementos no serializados
	 * @param refID - Long Identificador de la remisión
	 * @param serial - String serial del elemento
	 * @param elementTypeId - Long Identificador del tipo de elemento
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ElementMixedResponse
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por remisión
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
	@WebMethod(operationName="getReferenceElementsByReferenceIdAndSerialOrElementType", action="getReferenceElementsByReferenceIdAndSerialOrElementType")
	public ElementMixedResponse getReferenceElementsByReferenceIdAndSerialOrElementType(
			@WebParam(name="refID")Long refID, @WebParam(name="serial")String serial, @WebParam(name="elementTypeId")Long elementTypeId,@WebParam(name="isSerialized")boolean isSerialized,
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)
			throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite confirmar parcialmente elementos asociados a una remision
	 * @param elements Elementos a confirmar
	 * @param userId usuario que realiza la confirmacion parcial
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="partialReceptionOfReferenceElementItem", action="partialReceptionOfReferenceElementItem")
	public void partialReceptionOfReferenceElementItem(@WebParam(name="elements")List<ReferenceElementItemVO> elements,@WebParam(name="userId")Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los elementos asociados a una remision
	 * @param refID identificador de la remision
	 * @param requestCollInfo objeto de paginacion
	 * @param isSerialized true si son serializados, false si no lo son
	 * @return ReferenceElementItemsResponse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="getReferenceElementItemsByReferenceIDAndSerOrNotSer", action="getReferenceElementItemsByReferenceIDAndSerOrNotSer")
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDAndSerOrNotSer(@WebParam(name="refID")Long refID, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo,@WebParam(name="isSerialized") boolean isSerialized)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los elementos asociados a una remision
	 * @param refID identificador de la remision
	 * @param requestCollInfo objeto de paginacion
	 * @param isSerialized true si son serializados, false si no lo son
	 * @return ReferenceElementItemsResponse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="getReferenceElementItemsByReferenceIDSerAndNotSerPending", action="getReferenceElementItemsByReferenceIDSerAndNotSerPending")
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(@WebParam(name="refID")Long refID, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo,@WebParam(name="serialCode") String serialCode, @WebParam(name="isSerialized")boolean isSerialized)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Operación encargada de agregar elementos serializados a partir  del serial
	 * @param serialCode
	 * @param isSerialized
	 * @param referenceID <tipo> <descripcion>
	 * @author waguilera
	 */
	@WebMethod(operationName="addElementSerialized", action="addElementSerialized")
	public void addElementSerialized(@WebParam(name="serialCode")String serialCode, @WebParam(name="referenceID")Long referenceID, @WebParam(name="isPrepaid")boolean isPrepaid, @WebParam(name="userId")Long userId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Método encargado de agregar elementos no serializados a una remision a partir del serial
	 * El elemento debe existir en la bodega origen de la remision
	 * @param listElementNotSerializedToAdd
	 * @param referenceID
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	@WebMethod(operationName="addElementNotSerialized", action="addElementNotSerialized")
	public void addElementNotSerialized(@WebParam(name="refElements") List<ReferenceElementItemVO> listElementNotSerializedToAdd, @WebParam(name="referenceID")Long referenceID, @WebParam(name="userId")Long userId)throws BusinessException;
	

	/**
	 * 
	 * Metodo: operación encargada de retornar los elmentos no serializados agrupados
	 * que se encuentren en la ubicación origen de la remisión  
	 * @param listElementNotSerializedToAdd
	 * @param referenceID
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	@WebMethod(operationName="getElementNotSerializedFromWarehouse", action="getElementNotSerializedFromWarehouse")
	public ReferenceElementItemsResponse getElementNotSerializedFromWarehouse(@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo, @WebParam(name="referenceID")Long referenceID, @WebParam(name="isPrepaid")boolean isPrepaid, @WebParam(name="elementTypeId")Long elementTypeId, @WebParam(name="modelId")Long modelId)throws BusinessException;

}
