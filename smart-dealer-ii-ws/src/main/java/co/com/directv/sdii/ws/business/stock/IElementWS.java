package co.com.directv.sdii.ws.business.stock;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.model.dto.ElementTypeAndModelDTO;
import co.com.directv.sdii.model.dto.LinkSerializedFilterVO;
import co.com.directv.sdii.model.dto.LinkSerializedVO;
import co.com.directv.sdii.model.dto.QAItemDTO;
import co.com.directv.sdii.model.dto.SearchElementTypesDTO;
import co.com.directv.sdii.model.dto.UpdateLinkedSerialsDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.NotSerializedAjustmentCollDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementBrandVO;
import co.com.directv.sdii.model.vo.ElementClassVO;
import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.model.vo.ElementStatusVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.SupplierVO;
import co.com.directv.sdii.model.vo.TechnologyVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Servicio web que expone las operaciones relacionadas con Element
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name = "ElementWS", targetNamespace = "http://stock.business.ws.sdii.directv.com.co/")
public interface IElementWS {

	/**
	 * Metodo: persiste la información de un Element
	 * @param obj objeto que encapsula la información necesaria para construir el Element,
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
	@WebMethod(operationName="createElement", action="createElement")
	public void createElement(@WebParam(name="objElement")ElementVO objElement) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un Element No Serializado
	 * @param objElement objeto que encapsula la información necesaria para construir el Element,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @param noSerializado objeto que encapsula la información necesaria para construir el NotSerialized,
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
	@WebMethod(operationName="createNotSerializedElement", action="createNotSerializedElement")
	public void createNotSerializedElement(@WebParam(name="objElement")ElementVO objElement,@WebParam(name="noSerializado")NotSerializedVO noSerializado, @WebParam(name="impLogID") Long impLogID ) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un Element Serializado
	 * @param objElement objeto que encapsula la información necesaria para construir el Element,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un erfror
	 * @param serializado objeto que encapsula la información necesaria para construir el Serialized,
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
	@WebMethod(operationName="createSerializedElement", action="createSerializedElement")
	public void createSerializedElement(@WebParam(name="objElement")ElementVO objElement,@WebParam(name="serializado")SerializedVO serializado) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un Element dado el identificador del mismo
	 * @param id identificador del Element a ser consultado
	 * @return Element con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Element con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementByID", action="getElementByID")
	public ElementVO getElementByID(@WebParam(name="id")Long id) throws BusinessException;
		
	/**
	 * Metodo: Obtiene todos los Element almacenados en la persistencia
	 * @return lista con los Element existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllElements", action="getAllElements")
	public List<ElementVO> getAllElements(@WebParam(name="countryId")Long countryId) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ElementType
	 * @param obj objeto que encapsula la información necesaria para construir el ElementType,
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
	@WebMethod(operationName="createElementType", action="createElementType")
	public void createElementType(@WebParam(name="objElementType")ElementTypeVO objElementType) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementType
	 * @param obj objeto que encapsula la información del ElementType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ElementType
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
	@WebMethod(operationName="updateElementType", action="updateElementType")
	public void updateElementType(@WebParam(name="objElementType")ElementTypeVO objElementType) throws BusinessException;
	
	/**
	 * Metodo: borra un ElementType de la persistencia
	 * @param obj objeto que encapsula la información del ElementType, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteElementType", action="deleteElementType")
	public void deleteElementType(@WebParam(name="objElementType")ElementTypeVO objElementType) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ElementType dado el identificador del mismo
	 * @param id identificador del ElementType a ser consultado
	 * @return ElementType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ElementType con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementTypeByID", action="getElementTypeByID")
	public ElementTypeVO getElementTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ElementType dado el código del mismo
	 * @param code código del ElementType a ser consultado
	 * @return ElementType con el código especificado, se lanza una exepción en caso que no se
	 * encuentre el ElementType con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementTypeByCode", action="getElementTypeByCode")
	public ElementTypeVO getElementTypeByCode(@WebParam(name="code")String code) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todos los ElementType almacenados en la persistencia
	 * @return lista con los ElementType existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllElementTypes", action="getAllElementTypes")
	public List<ElementTypeVO> getAllElementTypes() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ElementStatus
	 * @param obj objeto que encapsula la información necesaria para construir el ElementStatus,
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
	@WebMethod(operationName="createElementStatus", action="createElementStatus")
	public void createElementStatus(@WebParam(name="objElementStatus")ElementStatusVO objElementStatus) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementStatus
	 * @param obj objeto que encapsula la información del ElementStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ElementStatus
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
	@WebMethod(operationName="updateElementStatus", action="updateElementStatus")
	public void updateElementStatus(@WebParam(name="objElementStatus")ElementStatusVO objElementStatus) throws BusinessException;
	
	/**
	 * Metodo: borra un ElementStatus de la persistencia
	 * @param obj objeto que encapsula la información del ElementStatus, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteElementStatus", action="deleteElementStatus")
	public void deleteElementStatus(@WebParam(name="objElementStatus")ElementStatusVO objElementStatus) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ElementStatus dado el identificador del mismo
	 * @param id identificador del ElementStatus a ser consultado
	 * @return ElementStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ElementStatus con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementStatusByID", action="getElementStatusByID", exclude = true)
	public ElementStatusVO getElementStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ElementStatus almacenados en la persistencia
	 * @return lista con los ElementStatus existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllElementStatuss", action="getAllElementStatuss")
	public List<ElementStatusVO> getAllElementStatuss() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ElementModel
	 * @param obj objeto que encapsula la información necesaria para construir el ElementModel,
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
	@WebMethod(operationName="createElementModel", action="createElementModel")
	public void createElementModel(@WebParam(name="objElementModel")ElementModelVO objElementModel) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementModel
	 * @param obj objeto que encapsula la información del ElementModel a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ElementModel
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
	@WebMethod(operationName="updateElementModel", action="updateElementModel")
	public void updateElementModel(@WebParam(name="objElementModel")ElementModelVO objElementModel) throws BusinessException;
	
	/**
	 * Metodo: borra un ElementModel de la persistencia
	 * @param obj objeto que encapsula la información del ElementModel, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteElementModel", action="deleteElementModel")
	public void deleteElementModel(@WebParam(name="objElementModel")ElementModelVO objElementModel) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ElementModel dado el identificador del mismo
	 * @param id identificador del ElementModel a ser consultado
	 * @return ElementModel con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ElementModel con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementModelByID", action="getElementModelByID")
	public ElementModelVO getElementModelByID(@WebParam(name="id")Long id) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ElementModelVO por su código
	 * @param code - String código del ElementModelVO a ser consultado
	 * @return ElementModelVO objeto con la información del ElementModelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementModel por Código
	 * a continuación los códigos de error:<br>
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
	@WebMethod(operationName="getElementModelByCode", action="getElementModelByCode")
	public ElementModelVO getElementModelByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ElementClass
	 * @param obj objeto que encapsula la información necesaria para construir el ElementClass,
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
	@WebMethod(operationName="createElementClass", action="createElementClass")
	public void createElementClass(@WebParam(name="objElementClass")ElementClassVO objElementClass) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementClass
	 * @param obj objeto que encapsula la información del ElementClass a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ElementClass
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
	@WebMethod(operationName="updateElementClass", action="updateElementClass")
	public void updateElementClass(@WebParam(name="objElementClass")ElementClassVO objElementClass) throws BusinessException;
	
	/**
	 * Metodo: borra un ElementClass de la persistencia
	 * @param obj objeto que encapsula la información del ElementClass, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteElementClass", action="deleteElementClass")
	public void deleteElementClass(@WebParam(name="objElementClass")ElementClassVO objElementClass) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ElementClass dado el identificador del mismo
	 * @param id identificador del ElementClass a ser consultado
	 * @return ElementClass con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ElementClass con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementClassByID", action="getElementClassByID")
	public ElementClassVO getElementClassByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ElementClass almacenados en la persistencia
	 * @return lista con los ElementClass existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllElementClasss", action="getAllElementClasss")
	public List<ElementClassVO> getAllElementClasss() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ElementClassVO por su código
	 * @param code - String Código del ElementClassVO a ser consultado
	 * @return ElementClassVO objeto con la información del ElementClassVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementClass por código
	 * a continuación los códigos de error:<br>
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
	@WebMethod(operationName="getElementClassByCode", action="getElementClassByCode")
	public ElementClassVO getElementClassByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un ElementBrand
	 * @param obj objeto que encapsula la información necesaria para construir el ElementBrand,
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
	@WebMethod(operationName="createElementBrand", action="createElementBrand")
	public void createElementBrand(@WebParam(name="objElementBrand")ElementBrandVO objElementBrand) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementBrand
	 * @param obj objeto que encapsula la información del ElementBrand a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ElementBrand
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
	@WebMethod(operationName="updateElementBrand", action="updateElementBrand")
	public void updateElementBrand(@WebParam(name="objElementBrand")ElementBrandVO objElementBrand) throws BusinessException;
	
	/**
	 * Metodo: borra un ElementBrand de la persistencia
	 * @param obj objeto que encapsula la información del ElementBrand, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteElementBrand", action="deleteElementBrand")
	public void deleteElementBrand(@WebParam(name="objElementBrand")ElementBrandVO objElementBrand) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un ElementBrand dado el identificador del mismo
	 * @param id identificador del ElementBrand a ser consultado
	 * @return ElementBrand con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ElementBrand con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getElementBrandByID", action="getElementBrandByID")
	public ElementBrandVO getElementBrandByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ElementBrand almacenados en la persistencia
	 * @return lista con los ElementBrand existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllElementBrands", action="getAllElementBrands")
	public List<ElementBrandVO> getAllElementBrands() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ElementBrandVO por su código
	 * @param code - String código del ElementBrandVO a ser consultado
	 * @return ElementBrandVO objeto con la información del ElementBrandVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementBrand por código
	 * a continuación los códigos de error:<br>
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
	@WebMethod(operationName="getElementBrandByCode", action="getElementBrandByCode")
	public ElementBrandVO getElementBrandByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un NotSerialized
	 * @param obj objeto que encapsula la información necesaria para construir el NotSerialized,
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
	@WebMethod(operationName="createNotSerialized", action="createNotSerialized", exclude = true)
	public void createNotSerialized(@WebParam(name="objNotSerialized")NotSerializedVO objNotSerialized) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un NotSerialized
	 * @param obj objeto que encapsula la información del NotSerialized a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo NotSerialized
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
	@WebMethod(operationName="updateNotSerialized", action="updateNotSerialized", exclude = true)
	public void updateNotSerialized(@WebParam(name="objNotSerialized")NotSerializedVO objNotSerialized) throws BusinessException;
	
	/**
	 * Metodo: borra un NotSerialized de la persistencia
	 * @param obj objeto que encapsula la información del NotSerialized, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteNotSerialized", action="deleteNotSerialized", exclude = true)
	public void deleteNotSerialized(@WebParam(name="objNotSerialized")NotSerializedVO objNotSerialized) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un NotSerialized dado el identificador del mismo
	 * @param id identificador del NotSerialized a ser consultado
	 * @return NotSerialized con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el NotSerialized con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getNotSerializedByID", action="getNotSerializedByID")
	public NotSerializedVO getNotSerializedByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los NotSerialized almacenados en la persistencia
	 * @return lista con los NotSerialized existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllNotSerializeds", action="getAllNotSerializeds")
	public List<NotSerializedVO> getAllNotSerializeds() throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene una lista de los elementos no serializados que tiene un registro
	 * de importación, se agrega para el caso de uso INV - 03 confirmar los elementos
	 * no serializados de un registro de importación
	 * @param importLogId identificador del registro de importación
	 * @return Lista con los elementos no serializados encontrados en el
	 * registro de importación, una lista vacia en caso que el registro
	 * de importación no tenga items
	 * @throws BusinessException en caso de error al consultar los elementos
	 * no serializados
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
	@WebMethod(operationName="getNotSerializedByImportLogId", action="getNotSerializedByImportLogId")
	public List<NotSerializedVO> getNotSerializedByImportLogId(@WebParam(name="importLogId") Long importLogId)throws BusinessException;

	/**
	 * Metodo: persiste la información de un Serialized
	 * @param obj objeto que encapsula la información necesaria para construir el Serialized,
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
	@WebMethod(operationName="createSerialized", action="createSerialized", exclude = true)
	public void createSerialized(@WebParam(name="objSerialized")SerializedVO objSerialized) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un Serialized
	 * @param obj objeto que encapsula la información del Serialized a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Serialized
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
	@WebMethod(operationName="updateSerialized", action="updateSerialized")
	public void updateSerialized(@WebParam(name="objSerialized")SerializedVO objSerialized) throws BusinessException;
	
	/**
	 * Metodo: borra un Serialized de la persistencia
	 * @param obj objeto que encapsula la información del Serialized, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteSerialized", action="deleteSerialized", exclude = true)
	public void deleteSerialized(@WebParam(name="objSerialized")SerializedVO objSerialized) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un Serialized dado el identificador del mismo
	 * @param id identificador del Serialized a ser consultado
	 * @return Serialized con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Serialized con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getSerializedByID", action="getSerializedByID")
	public SerializedVO getSerializedByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Serialized almacenados en la persistencia
	 * @return lista con los Serialized existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllSerializeds", action="getAllSerializeds")
	public List<SerializedVO> getAllSerializeds() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un Supplier
	 * @param obj objeto que encapsula la información necesaria para construir el Supplier,
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
	@WebMethod(operationName="createSupplier", action="createSupplier")
	public void createSupplier(@WebParam(name="objSupplier")SupplierVO objSupplier) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un Supplier
	 * @param obj objeto que encapsula la información del Supplier a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Supplier
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
	@WebMethod(operationName="updateSupplier", action="updateSupplier")
	public void updateSupplier(@WebParam(name="objSupplier")SupplierVO objSupplier) throws BusinessException;
	
	/**
	 * Metodo: borra un Supplier de la persistencia
	 * @param obj objeto que encapsula la información del Supplier, solo se requiere la propiedad id
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
	@WebMethod(operationName="deleteSupplier", action="deleteSupplier")
	public void deleteSupplier(@WebParam(name="objSupplier")SupplierVO objSupplier) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un Supplier dado el identificador del mismo
	 * @param id identificador del Supplier a ser consultado
	 * @return Supplier con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Supplier con el id, ver códigos de excepción.
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
	@WebMethod(operationName="getSupplierByID", action="getSupplierByID")
	public SupplierVO getSupplierByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un SupplierVO por su código
	 * @param code - String Código del SupplierVO a ser consultado
	 * @return SupplierVO objeto con la información del SupplierVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por código
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
	@WebMethod(operationName="getSupplierByCode", action="getSupplierByCode")
	public SupplierVO getSupplierByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un SupplierVO por su nit
	 * @param nit - String Nit del SupplierVO a ser consultado
	 * @return SupplierVO objeto con la información del SupplierVO dado su nit, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por nit
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
	@WebMethod(operationName="getSupplierByNit", action="getSupplierByNit")
	public SupplierVO getSupplierByNit(@WebParam(name="nit")String nit) throws BusinessException;
	

	/**
	 * Metodo: Registrar control de calidad de elementos serializados desde archivo plano 'procesado'
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por nit
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param importLog
	 * @param logisticOperator
	 * @param qaItemDTOs
	 * @param country - Long identificador del país
	 */
	@WebMethod(operationName="registerSerializedElementQualityControlAndByCountry", action="registerSerializedElementQualityControlAndByCountry")
	public void registerSerializedElementQualityControlAndByCountry(@WebParam(name="importLog")ImportLogVO importLog, @WebParam(name="logisticOperator")DealerVO logisticOperator,@WebParam(name="qaItemDTOs") List<QAItemDTO> qaItemDTOs,@WebParam(name="user")UserVO user,@WebParam(name="country")Long country) throws BusinessException;
		
	
	/**
	 * Metodo: Permite consultar los elementos NO serializados de una bodega junto con sus movimientos parciales
	 * CU 26
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @return List<NotSerializedAjustmentDTO> Lista con los elementos que cumplen con el filtro junto con sus retiros parciales
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jnova
	 */
	@WebMethod(operationName="getWhElementsAndNotSerPartRetByWarehouseId", action="getWhElementsAndNotSerPartRetByWarehouseId")
	public NotSerializedAjustmentCollDTO getWhElementsAndNotSerPartRetByWarehouseId(@WebParam(name="idWh")Long warehouseId, @WebParam(name="requestCollInfo")RequestCollectionInfoDTO requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos NO serializados de una bodega junto con sus movimientos parciales
	 * CU INV - 04
	 * @param ElementId Long, Id del Elemento
	 * @return List<SerializedVO> Lista con la informacion del elemento serializado
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jalopez
	 */
	@WebMethod(operationName="getSerializedByElementId", action="getSerializedByElementId")
	public List<SerializedVO> getSerializedByElementId(@WebParam(name = "elementId") Long elementId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO activos
	 * @return List<SupplierVO> Lista de SupplierVO activos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier activos
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getSupplierByActiveStatus", action="getSupplierByActiveStatus",exclude=true)
	public List<SupplierVO> getSupplierByActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO activos por país
	 * @param countryId - Long Identificador del país a consultar
	 * @return List<SupplierVO> Lista de SupplierVO activos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier activos por país
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getSupplierByActiveStatus", action="getSupplierByActiveStatus")
	public List<SupplierVO> getSupplierByActiveStatus(@WebParam(name="countryId")Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO inactivos
	 * @return List<SupplierVO> Lista de SupplierVO inactivos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier inactivos
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getSupplierByInactiveStatus", action="getSupplierByInactiveStatus",exclude=true)
	public List<SupplierVO> getSupplierByInactiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO inactivos por país
	 * @param countryId - Long Identificador del país a consultar
	 * @return List<SupplierVO> Lista de SupplierVO inactivos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier inactivos por país
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getSupplierByInactiveStatus", action="getSupplierByInactiveStatus")
	public List<SupplierVO> getSupplierByInactiveStatus(@WebParam(name="countryId")Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia con estado activo
	 * @return Lista con los ElementBrandVO existentes con estado activo, una lista vacia en caso que no existan ElementBrandVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO con estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementBrandByActiveStatus", action="getElementBrandByActiveStatus")
	public List<ElementBrandVO> getElementBrandByActiveStatus() throws BusinessException;
	
	/** Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return ElementBrandResponse con la información de la consulta 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO con estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementBrandByActiveStatuPage", action="getElementBrandByActiveStatuPage")
	public ElementBrandResponse getElementBrandByActiveStatuPage(@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/** Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia en cualquier estado
	 * @param code - String Código de la marca del elemento
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return ElementBrandResponse con la información de la consulta 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO en cualquier estado
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementBrandByAllStatuPage", action="getElementBrandByAllStatuPage")
	public ElementBrandResponse getElementBrandByAllStatuPage(@WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los ElementBrandVO existentes con estado inactivo, una lista vacia en caso que no existan ElementBrandVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO con estado inactivo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementBrandByInActiveStatus", action="getElementBrandByInActiveStatus",exclude=true)
	public List<ElementBrandVO> getElementBrandByInActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModelVO almacenados en la persistencia con estado activo
	 * @return Lista con los ElementModelVO existentes con estado activo, una lista vacia en caso que no existan ElementModelVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel con estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByActiveStatus", action="getElementModelsByActiveStatus")
	public List<ElementModelVO> getElementModelsByActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModelVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los ElementModelVO existentes con estado inactivo, una lista vacia en caso que no existan ElementModelVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel con estado inactivo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByInActiveStatus", action="getElementModelsByInActiveStatus",exclude=true)
	public List<ElementModelVO> getElementModelsByInActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con estado activo
	 * @return Lista con los ElementClassVO existentes con estado activo, una lista vacia en caso que no existan ElementClassVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementClassByActiveStatus", action="getElementClassByActiveStatus")
	public List<ElementClassVO> getElementClassByActiveStatus() throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return ElementClassResponse con los datos asociados a la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementClassByActiveStatusPage", action="getElementClassByActiveStatusPage")
	public ElementClassResponse getElementClassByActiveStatusPage(@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con cualquier estado
	 * @param code - String Código de la clase de elemtno
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return ElementClassResponse con los datos asociados a la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con cualquier estado
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementClassByAllStatusPage", action="getElementClassByAllStatusPage")
	public ElementClassResponse getElementClassByAllStatusPage(@WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los ElementClassVO existentes con estado inactivo, una lista vacia en caso que no existan ElementClassVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con estado inactivo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementClassByInactiveStatus", action="getElementClassByInactiveStatus",exclude=true)
	public List<ElementClassVO> getElementClassByInactiveStatus() throws BusinessException;
		
	/**
	 * Caso de uso INV 50 Cargar elementos serializados a una Remisi�n desde archivo plano
	 * 
	 * Metodo: persiste la informacion de serialized
	 * @param List<Serialized> listSerialized Lista de los serialized que requieren
	 * ser insertados
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
	 * @author jforero 19/08/2010
	 */
	@WebMethod(operationName="saveSerializedList", action="saveSerializedList")
	@Deprecated
	public void saveSerializedList(@WebParam(name="listSerialized")List<SerializedVO> listSerialized) throws BusinessException;
	
	/**
	 * Metodo: Obtiene el tipo de elemento activos
	 * @return List<ElementTypeVO> que se encuentran activos; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de activos
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
	@WebMethod(operationName="getElementTypesByActive", action="getElementTypesByActive")
	public List<ElementTypeVO> getElementTypesByActive() throws BusinessException;
	
	/**
	 * Metodo: Obtiene el tipo de elemento activos
	 * @return List<ElementTypeVO> que se encuentran activos; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de activos
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
	@WebMethod(operationName="getElementTypesByActiveAndIsSerialized", action="getElementTypesByActiveAndIsSerialized")
	public List<ElementTypeVO> getElementTypesByActiveAndIsSerialized(@WebParam(name="isSerialized")boolean isSerialized) throws BusinessException;
	
	/**
	 * Metodo: Obtiene dos listas de elementos para que el usuario seleccione uno de la primera
	 * lista para vincularle uno de la segunda lista
	 * CU 44
	 * @param LinkSerializedFilterVO VO Encapsula la informacion para filtrar la consulta de elementos
	 * @return LinkSerializedVO VO que encapsula dos listas de elementos que cumplen con el filtro enviado
	 * por el usuario
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de elementos serializados
	 * de acuerdo al filtro
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
	@WebMethod(operationName="getElementsToLinkSerializedElements", action="getElementsToLinkSerializedElements")
	public LinkSerializedVO getElementsToLinkSerializedElements(@WebParam(name="linkSerializedFilter")LinkSerializedFilterVO criteria) throws BusinessException;
	
	/**
	 * Metodo: Vincula dos elementos si no tienen elementos vinculados
	 * CU 44
	 * @param elementOneId Long Id del elemento al que se le va a vincular el elemento
	 * @param elementTwoId Long Id del elemento que va a ser vinculado
	 * @throws BusinessException En caso que ocurra error al vincular los elementos
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
	@WebMethod(operationName="linkSerializedElements", action="linkSerializedElements")
	public void linkSerializedElements(@WebParam(name="elementOneId")Long elementOneId, @WebParam(name="elementTwoId")Long elementTwoId) throws BusinessException;
	
	/**
	 * Metodo: Retorna los elementos vinculados a un elemento
	 * @param serialized - Long identificador del elemento serializado
	 * @return List<SerializedVO> de elementos serializados
	 * @throws BusinessException en caso de error en la consulta de elementos vinculados
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
	@WebMethod(operationName="getLinkedSerializedBySerializedId", action="getLinkedSerializedBySerializedId")
	public List<SerializedVO> getLinkedSerializedBySerializedId (@WebParam(name="serialized")Long serialized)throws BusinessException;
	

	/**
	 * Metodo: Consulta un serializado por Numero de Serie
	 * @param serial Numero de Serie
	 * @return SerializedVO Datos del serializado obtenido
	 * @throws BusinessException 
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
	@WebMethod(operationName="getSerializedBySerial", action="getSerializedBySerial")
	public SerializedVO getSerializedBySerial(@WebParam(name="serial")String serial,@WebParam(name="countryId")Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ElementVO correspondientes a ElementType
	 * @param elementType - ElementTypeVO. Si typeElementCode es nulo, se debe especificar el id.
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ElementResponse, List<ElementVO> Lista de ElementVO cuyo ElementTypeVO (id o código es el especificado). En caso contrio vacio
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Element por ElementType
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
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementsByElementType", action="getElementsByElementType")
	public ElementResponse getElementsByElementType (@WebParam(name="elementType")ElementTypeVO elementType, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ElementModel almacenados en la persistencia
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginacion.
	 * @return ElementModelResponse, lista con los ElementModel existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllElementModels", action="getAllElementModels")
	public ElementModelResponse getAllElementModels(@WebParam(name="requestCollInfo")RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Supplier almacenados en la persistencia
	 * @param Long countryId, id del pais.
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return SuppliersResponse, lista con los Supplier existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllSuppliers", action="getAllSuppliers")
	public SuppliersResponse getAllSuppliers(@WebParam(name="countryId") Long countryId, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Consultar el historial de los elementos en un edificio	 
	 * Caso de Uso Inv_22 Consultar el historial de los elementos en un edificio
	 * @param ibsCode String Codigo del edificio del cual se va a consultar el historial
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return List<HistoryElementsOfCustomerDTO> Lista de consulta de historial de elementos en un edificio dado
	 * @throws BusinessException En caso de error en la consulta
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
	@WebMethod(operationName="getElementsHistoryOnBuildingCode", action="getElementsHistoryOnBuildingCode")
	public CustomerElementsResponse getElementsHistoryOnBuildingCode(@WebParam(name="ibsCode")String ibsCode,@WebParam(name="countryId")Long countryId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModelVO asociados al ElementClass con codeElementClass
	 * @param codeElementClass - String código del ElementClass
	 * @return List<ElementModelVO> asociados al ElementClass
	 *  @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel dado ElementClass
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByElementClass", action="getElementModelsByElementClass")
	public List<ElementModelVO> getElementModelsByElementClass(@WebParam(name="codeElementClass")String codeElementClass) throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con codeElementClass
	 * @param codeElementClass - String código del ElementClass
	 * @return List<ElementModelVO> activos asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByElementClassAndActiveStatus", action="getElementModelsByElementClassAndActiveStatus")
	public List<ElementModelVO> getElementModelsByElementClassAndActiveStatus(@WebParam(name="codeElementClass")String codeElementClass) throws BusinessException;
	
	/**
	 * getElementModelsByElementClass
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByElementClassAndInactiveStatus", action="getElementModelsByElementClassAndInactiveStatus")
	public List<ElementModelVO> getElementModelsByElementClassAndInactiveStatus(@WebParam(name="codeElementClass")String codeElementClass) throws BusinessException;
	
	
	
	
	
	/**
	 * Método: Obtiene la información de todos los ElementTypeVO asociados al ElementModel con codeElementModel
	 * @param codeElementModel - String código del ElementModel
	 * @return List<ElementTypeVO> asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType dado ElementModel
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByElementModel", action="getElementTypesByElementModel")
	public List<ElementTypeVO> getElementTypesByElementModel(@WebParam(name="codeElementModel")String codeElementModel) throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con codeElementModel
	 * @param codeElementModel - String código del ElementModel
	 * @return List<ElementModelVO> activos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByElementModelAndActiveStatus", action="getElementTypesByElementModelAndActiveStatus")
	public List<ElementTypeVO> getElementTypesByElementModelAndActiveStatus(@WebParam(name="codeElementModel")String codeElementModel) throws BusinessException;
	
	/**
	 *  Método: Obtiene la información de todos los ElementModelVO inactivos asociados al ElementClass con codeElementModel
	 * @param codeElementModel - String código del ElementModel
	 * @return List<ElementModelVO> inactivos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType inactivos dado ElementModel
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByElementModelAndInactiveStatus", action="getElementTypesByElementModelAndInactiveStatus")
	public List<ElementTypeVO> getElementTypesByElementModelAndInactiveStatus(@WebParam(name="codeElementModel")String codeElementModel) throws BusinessException;
	
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @return List<ElementModelVO> activos asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByElementClassIdAndActiveStatus", action="getElementModelsByElementClassIdAndActiveStatus")
	public List<ElementModelVO> getElementModelsByElementClassIdAndActiveStatus(@WebParam(name="idElementClass")Long idElementClass) throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @param requestCollInfo - RequestCollectionInfo Información de la paginación
	 * @return ElementModelResponse con los elementos de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByElementClassIdAndActiveStatusPage", action="getElementModelsByElementClassIdAndActiveStatusPage")
	public ElementModelResponse getElementModelsByElementClassIdAndActiveStatusPage (@WebParam(name="idElementClass")Long idElementClass,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;

	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @param code - Código del modelo de elemento
	 * @param requestCollInfo - RequestCollectionInfo Información de la paginación
	 * @return ElementModelResponse con los elementos de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementModelsByElementClassIdAndAllStatusPage", action="getElementModelsByElementClassIdAndAllStatusPage")
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage (@WebParam(name="idElementClass")Long idElementClass,@WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @return List<ElementModelVO> activos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByElementModelIdAndActiveStatus", action="getElementTypesByElementModelIdAndActiveStatus")
	public List<ElementTypeVO> getElementTypesByElementModelIdAndActiveStatus(@WebParam(name="idElementModel")Long idElementModel) throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementTypeVO activos asociados al ElementModel 
	 * @param idElementModel - Long id del ElementModel
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con la información de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByElementModelIdAndActiveStatusPage", action="getElementTypesByElementModelIdAndActiveStatusPage")
	public ElementTypeResponse getElementTypesByElementModelIdAndActiveStatusPage(@WebParam(name="idElementModel")Long idElementModel,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementTypeVO activos asociados al ElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @param code - String Código del tipo del elemento
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con la información de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByElementModelIdAndAllStatusPage", action="getElementTypesByElementModelIdAndAllStatusPage")
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(@WebParam(name="idElementModel")Long idElementModel,@WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Technology almacenados en la persistencia
	 * @return List<TechnologyVO> Lista con los TechnologyVO existentes, una lista vacia en caso que no existan TechnologyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexi�n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getActiveTechnologies", action="getActiveTechnologies")
	public List<TechnologyVO> getActiveTechnologies() throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: CU_INV_53 Modificación de Modelo de un Elemento.
	 * Este método permite cambiar el modelo de un elemento.
	 * @param idWarehouseElement
	 * @param codTypeElement
	 * @param idWarehouseElementLinkElement
	 * @param codTypeElementLinkElement
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="changeElementModel", action="changeElementModel")
	public void changeElementModel(@WebParam(name="idElement")Long idElement, @WebParam(name="codTypeElement")String codTypeElement, @WebParam(name="idElementLinkElement")Long idElementLinkElement, @WebParam(name="codTypeElementLinkElement")String codTypeElementLinkElement) throws BusinessException;
	
	
	
	/**
	 * Método: CU_INV30Permite consultar todos los ElementType en estado activo indicando si es prepago o no
	 * @param prepaid - String indica si se consultan serializados o no serializados
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con los resultados de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * @author gfandino
	 */
	@WebMethod(operationName="getElementTypesByPrepaidIdAndActiveStatusPage", action="getElementTypesByPrepaidIdAndActiveStatusPage")
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(@WebParam(name="prepaid")String prepaid, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Permite consultar un objeto serializado activo para la vinculacion
	 * @param elementSerializedLinkUnLinkFilterVO - Filtro que contiene los criterios de busqueda enviados por el usuario
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink(@WebParam(name = "elementSerializedLinkUnLinkFilterVO") ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Actualiza los seriales de los elementos vinculados
	 * @param updateLinkedSerialsDTO objeto que encapsula la informacion para realiza la actualizacion de seriales
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="updateLinkedSerials", action="updateLinkedSerials")
	public void updateLinkedSerials(@WebParam(name="updateLinkedSerialsDTO")UpdateLinkedSerialsDTO updateLinkedSerialsDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: 
	 * @param elementModelCode
	 * @param isSerialized
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	@WebMethod(operationName="getElementTypeByElementModelAndIsSerialized", action="getElementTypeByElementModelAndIsSerialized")
	public List<ElementTypeVO> getElementTypeByElementModelAndIsSerialized(
			@WebParam(name="elementModelCode")String elementModelCode, @WebParam(name="isSerialized")boolean isSerialized)
			throws BusinessException;
	
	
	
	
	/**
	 * 
	 * Metodo: Método encargado de consultar los modelos activos bajo el filtro de prepago o no prepago
	 * @param isPrepaid
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getElementModelsByActiveStatusAndIsPrepaid", action="getElementModelsByActiveStatusAndIsPrepaid")
	public List<ElementModelVO> getElementModelsByActiveStatusAndIsPrepaid(@WebParam(name="isPrepaid")String isPrepaid) throws BusinessException;
	
	/**
	 * Metodo: Metodo permite consultar los tipos y modelos de elementos filtrados por una bodega y un elemento
	 * @param warehouseId
	 * @param elementModelId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	@WebMethod(operationName="getElementModelsByWarehouseIdAndElementModelId", action="getElementModelsByWarehouseIdAndElementModelId")
	public ElementTypeAndModelDTO getElementModelsByWarehouseIdAndElementModelId(@WebParam(name="warehouseId")Long warehouseId,@WebParam(name="elementModelId")Long elementModelId)throws BusinessException;
	

	/**
	 * Método encargado de consultar los tipos del elementos activos con respecto al
	 * los filtros recibidos en al operación  
	 * @param isPrepaid
	 * @param isSerialized
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName="getElementTypeBySerorNotSerAndPrepaidorNotPrepaid", action="getElementTypeBySerorNotSerAndPrepaidorNotPrepaid")
	public List<ElementTypeVO> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			@WebParam(name="isPrepaid")String isPrepaid, @WebParam(name="isSerialized")String isSerialized)
			throws BusinessException;
	
	/**
	 * Metodo encargado de la ejecución del proceso
	 * encargado de reportar cambios de estado y movimientos
	 * de elementos a IBS
	 * @throws BusinessException
	 * @waguilera
	 */
	@WebMethod(operationName="sendStatusCmdToIBS", action="sendStatusCmdToIBS")
	public void sendStatusCmdToIBS(@WebParam(name="countryId")Long countryId) throws BusinessException;
	
	@WebMethod(operationName="getElementTypesByModelStatusAndIsSerializedForCombos", action="getElementTypesByModelStatusAndIsSerializedForCombos")
	public List<ElementTypeVO> getElementTypesByModelStatusAndIsSerializedForCombos(SearchElementTypesDTO params) throws BusinessException;
	
}
