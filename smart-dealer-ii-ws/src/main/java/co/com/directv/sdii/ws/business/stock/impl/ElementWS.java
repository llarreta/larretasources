package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ElementStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.NotSerializedFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.TechnologyFacadeBeanLocal;
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
import co.com.directv.sdii.ws.business.stock.IElementWS;

/**
 * Servicio web que expone las operaciones relacionadas con Element
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a @href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal
 */
/**
 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
 *
 */
@MTOM
@WebService(serviceName="ElementService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IElementWS",		
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="ElementPort")
@Stateless()
public class ElementWS implements IElementWS {

	@EJB
    private ElementFacadeBeanLocal ejbRef;
	@EJB
    private ElementTypeFacadeBeanLocal ejbRefType;
	@EJB
    private ElementStatusFacadeBeanLocal ejbRefStatus;
	@EJB
    private ElementModelFacadeBeanLocal ejbRefModel;
	@EJB
    private ElementClassFacadeBeanLocal ejbRefClass;
	@EJB
    private ElementBrandFacadeBeanLocal ejbRefBrand;
	@EJB
    private NotSerializedFacadeBeanLocal ejbRefNotSer;
	@EJB
    private SerializedFacadeBeanLocal ejbRefSer;
	@EJB
    private SupplierFacadeBeanLocal ejbRefSup;
	@EJB
    private SerializedFacadeBeanLocal serializedFacadeBean;	
	@EJB
	private TechnologyFacadeBeanLocal technologyFacadeBean;
	
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
	public void createElement(ElementVO objElement) throws BusinessException{
		ejbRef.createElement(objElement);
	}
	
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
	public void createNotSerializedElement(ElementVO objElement,
			NotSerializedVO noSerializado, Long impLogID) throws BusinessException {
		ejbRef.createNotSerializedElement(objElement,noSerializado, impLogID);
		
	}

	/**
	 * Metodo: persiste la información de un Element Serializado
	 * @param objElement objeto que encapsula la información necesaria para construir el Element,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
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
	public void createSerializedElement(ElementVO objElement,
			SerializedVO serializado) throws BusinessException {
		ejbRef.createSerializedElement(objElement,serializado);
		
	}
	
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
	public ElementVO getElementByID(Long id) throws BusinessException{
		return ejbRef.getElementByID(id);
	}
	
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
	public List<ElementVO> getAllElements(Long countryId) throws BusinessException{
		return ejbRef.getAllElements(countryId);
	}
	
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
	public void createElementType(ElementTypeVO objElementType) throws BusinessException{
		ejbRefType.createElementType(objElementType);
	}
	
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
	public void updateElementType(ElementTypeVO objElementType) throws BusinessException{
		ejbRefType.updateElementType(objElementType);
	}
	
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
	public void deleteElementType(ElementTypeVO objElementType) throws BusinessException{
		ejbRefType.deleteElementType(objElementType);
	}
	
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
	public ElementTypeVO getElementTypeByID(Long id) throws BusinessException{
		return ejbRefType.getElementTypeByID(id);
	}
	
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
	public List<ElementTypeVO> getAllElementTypes() throws BusinessException{
		return ejbRefType.getAllElementTypes();
	}

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
	public ElementTypeVO getElementTypeByCode(String code)
			throws BusinessException {
		return ejbRefType.getElementTypeByCode(code);
	}
	
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
	public void createElementStatus(ElementStatusVO objElementStatus) throws BusinessException{
		ejbRefStatus.createElementStatus(objElementStatus);
	}
	
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
	public void updateElementStatus(ElementStatusVO objElementStatus) throws BusinessException{
		ejbRefStatus.updateElementStatus(objElementStatus);
	}
	
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
	public void deleteElementStatus(ElementStatusVO objElementStatus) throws BusinessException{
		ejbRefStatus.deleteElementStatus(objElementStatus);
	}
	
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
	public ElementStatusVO getElementStatusByID(Long id) throws BusinessException{
		return ejbRefStatus.getElementStatusByID(id);
	}
	
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
	public List<ElementStatusVO> getAllElementStatuss() throws BusinessException{
		return ejbRefStatus.getAllElementStatuss();
	}
	
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
	public void createElementModel(ElementModelVO objElementModel) throws BusinessException{
		ejbRefModel.createElementModel(objElementModel);
	}
	
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
	public void updateElementModel(ElementModelVO objElementModel) throws BusinessException{
		ejbRefModel.updateElementModel(objElementModel);
	}
	
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
	public void deleteElementModel(ElementModelVO objElementModel) throws BusinessException{
		ejbRefModel.deleteElementModel(objElementModel);
	}
	
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
	public ElementModelVO getElementModelByID(Long id) throws BusinessException{
		return ejbRefModel.getElementModelByID(id);
	}
	
	

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
	public ElementModelVO getElementModelByCode(String code)
			throws BusinessException {
		return ejbRefModel.getElementModelByCode(code);
	}
	
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
	public void createElementClass(ElementClassVO objElementClass) throws BusinessException{
		ejbRefClass.createElementClass(objElementClass);
	}
	
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
	public void updateElementClass(ElementClassVO objElementClass) throws BusinessException{
		ejbRefClass.updateElementClass(objElementClass);
	}
	
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
	public void deleteElementClass(ElementClassVO objElementClass) throws BusinessException{
		ejbRefClass.deleteElementClass(objElementClass);
	}
	
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
	public ElementClassVO getElementClassByID(Long id) throws BusinessException{
		return ejbRefClass.getElementClassByID(id);
	}
	
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
	public List<ElementClassVO> getAllElementClasss() throws BusinessException{
		return ejbRefClass.getAllElementClasss();
	}

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
	public ElementClassVO getElementClassByCode(String code)
			throws BusinessException {		
		return ejbRefClass.getElementClassByCode(code);
	}
	
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
	public void createElementBrand(ElementBrandVO objElementBrand) throws BusinessException{
		ejbRefBrand.createElementBrand(objElementBrand);
	}
	
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
	public void updateElementBrand(ElementBrandVO objElementBrand) throws BusinessException{
		ejbRefBrand.updateElementBrand(objElementBrand);
	}
	
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
	public void deleteElementBrand(ElementBrandVO objElementBrand) throws BusinessException{
		ejbRefBrand.deleteElementBrand(objElementBrand);
	}
	
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
	public ElementBrandVO getElementBrandByID(Long id) throws BusinessException{
		return ejbRefBrand.getElementBrandByID(id);
	}
	
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
	public List<ElementBrandVO> getAllElementBrands() throws BusinessException{
		return ejbRefBrand.getAllElementBrands();
	}

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
	public ElementBrandVO getElementBrandByCode(String code)
			throws BusinessException {
		return ejbRefBrand.getElementBrandByCode(code);
	}
	
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
	public void createNotSerialized(NotSerializedVO objNotSerialized) throws BusinessException{
		ejbRefNotSer.createNotSerialized(objNotSerialized);
	}
	
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
	public void updateNotSerialized(NotSerializedVO objNotSerialized) throws BusinessException{
		ejbRefNotSer.updateNotSerialized(objNotSerialized);
	}
	
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
	public void deleteNotSerialized(NotSerializedVO objNotSerialized) throws BusinessException{
		ejbRefNotSer.deleteNotSerialized(objNotSerialized);
	}
	
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
	public NotSerializedVO getNotSerializedByID(Long id) throws BusinessException{
		return ejbRefNotSer.getNotSerializedByID(id);
	}
	
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
	public List<NotSerializedVO> getAllNotSerializeds() throws BusinessException{
		return ejbRefNotSer.getAllNotSerializeds();
	}
	
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
	 * @author jjimenezh
	 */
	public List<NotSerializedVO> getNotSerializedByImportLogId(Long importLogId)throws BusinessException{
		return ejbRefNotSer.getNotSerializedByImportLogId(importLogId);
	}
	
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
	public void createSerialized(SerializedVO objSerialized) throws BusinessException{
		ejbRefSer.createSerialized(objSerialized);
	}
	
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
	public void updateSerialized(SerializedVO objSerialized) throws BusinessException{
		ejbRefSer.updateSerialized(objSerialized);
	}
	
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
	public void deleteSerialized(SerializedVO objSerialized) throws BusinessException{
		ejbRefSer.deleteSerialized(objSerialized);
	}
	
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
	public SerializedVO getSerializedByID(Long id) throws BusinessException{
		return ejbRefSer.getSerializedByID(id);
	}
	
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
	public List<SerializedVO> getAllSerializeds() throws BusinessException{
		return ejbRefSer.getAllSerializeds();
	}	
	
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
	public void createSupplier(SupplierVO objSupplier) throws BusinessException{
		ejbRefSup.createSupplier(objSupplier);
	}
	
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
	public void updateSupplier(SupplierVO objSupplier) throws BusinessException{
		ejbRefSup.updateSupplier(objSupplier);
	}
	
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
	public void deleteSupplier(SupplierVO objSupplier) throws BusinessException{
		ejbRefSup.deleteSupplier(objSupplier);
	}
	
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
	public SupplierVO getSupplierByID(Long id) throws BusinessException{
		return ejbRefSup.getSupplierByID(id);
	}
	
	
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
	public SupplierVO getSupplierByCode(String code) throws BusinessException {
		return ejbRefSup.getSupplierByCode(code);
	}

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
	public SupplierVO getSupplierByNit(String nit) throws BusinessException {
		return ejbRefSup.getSupplierByNit(nit);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#registerSerializedElementQualityControlAndByCountry(co.com.directv.sdii.model.vo.ImportLogVO, co.com.directv.sdii.model.vo.DealerVO, java.util.List, co.com.directv.sdii.model.vo.UserVO, java.lang.Long)
	 */
	@Override
	public void registerSerializedElementQualityControlAndByCountry(ImportLogVO importLog,
			DealerVO logisticOperator, List<QAItemDTO> qaItemDTOs,UserVO user,Long country)
			throws BusinessException {
		ejbRef.registerSerializedElementQualityControlAndByCountry(importLog, logisticOperator, qaItemDTOs,user,country);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getWhElementsAndNotSerPartRetByWarehouseId(java.lang.Long)
	 */
	@Override
	public NotSerializedAjustmentCollDTO getWhElementsAndNotSerPartRetByWarehouseId(Long warehouseId, RequestCollectionInfoDTO requestCollInfo)throws BusinessException{
		return ejbRef.getWhElementsAndNotSerPartRetByWarehouseId(warehouseId, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getSerializedByElementId(java.lang.Long)
	 */
	@Override
	public List<SerializedVO> getSerializedByElementId(Long elementId)throws BusinessException {
		return ejbRefSer.getSerializedByElementId(elementId);
	}
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
	public List<SupplierVO> getSupplierByActiveStatus()
			throws BusinessException {
		return ejbRefSup.getSupplierByActiveStatus();
	}

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
	public List<SupplierVO> getSupplierByActiveStatus(Long countryId)
			throws BusinessException {
		return ejbRefSup.getSupplierByActiveStatus(countryId);
	}

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
	public List<SupplierVO> getSupplierByInactiveStatus()
			throws BusinessException {
		return ejbRefSup.getSupplierByInactiveStatus();
	}

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
	public List<SupplierVO> getSupplierByInactiveStatus(Long countryId)
			throws BusinessException {
		return ejbRefSup.getSupplierByInactiveStatus(countryId);
	}
	
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
	public List<ElementBrandVO> getElementBrandByActiveStatus()
			throws BusinessException {
		return ejbRefBrand. getElementBrandByActiveStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementBrandByActiveStatuPage(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ElementBrandResponse getElementBrandByActiveStatuPage(RequestCollectionInfo requestCollInfo)
		throws BusinessException{
		return ejbRefBrand.getElementBrandByActiveStatus(requestCollInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementBrandByAllStatuPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ElementBrandResponse getElementBrandByAllStatuPage(String code,RequestCollectionInfo requestCollInfo)
		throws BusinessException{
		return ejbRefBrand.getElementBrandByAllStatuPage(code,requestCollInfo);
	}

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
	public List<ElementBrandVO> getElementBrandByInActiveStatus()
			throws BusinessException {
		return ejbRefBrand.getElementBrandByInActiveStatus();
	}

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
	public List<ElementModelVO> getElementModelsByActiveStatus()
			throws BusinessException {
		return ejbRefModel.getElementModelsByActiveStatus();
	}

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
	public List<ElementModelVO> getElementModelsByInActiveStatus()
			throws BusinessException {
		return ejbRefModel.getElementModelsByInActiveStatus();
	}

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
	public List<ElementClassVO> getElementClassByActiveStatus()
			throws BusinessException {
		return ejbRefClass.getElementClassByActiveStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementClassByActiveStatusPage(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ElementClassResponse getElementClassByActiveStatusPage(RequestCollectionInfo requestCollInfo)
		throws BusinessException {
		return ejbRefClass.getElementClassByActiveStatus(requestCollInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementClassByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ElementClassResponse getElementClassByAllStatusPage(String code,RequestCollectionInfo requestCollInfo)
		throws BusinessException {
	return ejbRefClass.getElementClassByAllStatusPage(code,requestCollInfo);
}

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
	public List<ElementClassVO> getElementClassByInactiveStatus()
			throws BusinessException {
		return ejbRefClass.getElementClassByInactiveStatus();
	}
	
		
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#saveSerializedList(java.util.List)
	 */
	@Override
	public void saveSerializedList(List<SerializedVO> listSerialized) throws BusinessException{
		serializedFacadeBean.createSerialized(null);
	}

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
	public List<ElementTypeVO> getElementTypesByActive()
			throws BusinessException {
		return ejbRefType.getElementTypesByActive();
	}
	
	public List<ElementTypeVO> getElementTypesByParameters(Boolean isActive, Boolean isSerialized, String elementModelCode)
	throws BusinessException {
		return ejbRefType.getElementTypesByActive();
	}
	
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
	public List<ElementTypeVO> getElementTypesByActiveAndIsSerialized(boolean isSerialized)
			throws BusinessException {
		return ejbRefType.getElementTypesByActiveAndIsSerialized(isSerialized);
	}
	
		
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementsToLinkSerializedElements(co.com.directv.sdii.model.dto.LinkSerializedFilterVO)
	 */
	@Override
	public LinkSerializedVO getElementsToLinkSerializedElements(LinkSerializedFilterVO criteria) throws BusinessException{
		return ejbRef.getElementsToLinkSerializedElements(criteria);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#linkSerializedElements(java.lang.Long,java.lang.Long)
	 */
	@Override
	public void linkSerializedElements(Long elementOneId, Long elementTwoId) throws BusinessException{
		ejbRef.linkSerializedElements(elementOneId, elementTwoId);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getLinkedSerializedBySerializedId(java.lang.Long)
	 */
	public List<SerializedVO> getLinkedSerializedBySerializedId(Long serialized)
			throws BusinessException {
		return ejbRefSer.getLinkedSerializedBySerializedId(serialized);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getSerializedBySerial(java.lang.String)
	 */
	public SerializedVO getSerializedBySerial(String serial,Long countryId)
			throws BusinessException {
		return serializedFacadeBean.getSerializedBySerial(serial,countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementsByElementType(co.com.directv.sdii.model.vo.ElementTypeVO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override	
	public ElementResponse getElementsByElementType(ElementTypeVO elementType, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef. getElementsByElementType(elementType, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getAllElementModels(co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override	
	public ElementModelResponse getAllElementModels(RequestCollectionInfoDTO requestCollInfo) throws BusinessException{
		return ejbRefModel.getAllElementModels(requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getAllSuppliers(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public SuppliersResponse getAllSuppliers(Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefSup.getAllSuppliers(countryId,requestCollInfo);
	}	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementsHistoryOnBuildingCode(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public CustomerElementsResponse getElementsHistoryOnBuildingCode(String ibsCode,Long countryId, RequestCollectionInfo requestCollInfo)	throws BusinessException {
		return ejbRef.getElementsHistoryOnBuildingCode(ibsCode,countryId, requestCollInfo);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByElementClassIdAndActiveStatus(java.lang.Long)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassIdAndActiveStatus(
			Long idElementClass) throws BusinessException {
		return ejbRefModel.getElementModelsByElementClassIdAndActiveStatus(idElementClass);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByElementClassIdAndActiveStatusPage(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementModelResponse getElementModelsByElementClassIdAndActiveStatusPage(
			Long idElementClass,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefModel.getElementModelsByElementClassIdAndActiveStatus(idElementClass,requestCollInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByElementClassIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage(Long idElementClass,String code,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefModel.getElementModelsByElementClassIdAndAllStatusPage(idElementClass,code,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByElementModelIdAndActiveStatus(java.lang.Long)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModelIdAndActiveStatus(
			Long idElementModel) throws BusinessException {
		return ejbRefType.getElementTypesByElementModelIdAndActiveStatus(idElementModel);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByElementModelIdAndActiveStatusPage(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByElementModelIdAndActiveStatusPage(
			Long idElementModel,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefType.getElementTypesByElementModelIdAndActiveStatus(idElementModel,requestCollInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByElementModelIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(
			Long idElementModel,String code,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefType.getElementTypesByElementModelIdAndAllStatusPage(idElementModel,code,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getActiveTechnologies()
	 */
	@Override
	public List<TechnologyVO> getActiveTechnologies() throws BusinessException {
		return technologyFacadeBean.getActiveTechnologies();
	}
	



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByPrepaidIdAndActiveStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(
			String prepaid, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRefType.getElementTypesByPrepaidIdAndActiveStatusPage(prepaid,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByElementClass(java.lang.String)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClass(
			String codeElementClass) throws BusinessException {
		 return ejbRefModel.getElementModelsByElementClass(codeElementClass);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByElementClassAndActiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassAndActiveStatus(
			String codeElementClass) throws BusinessException {
		return ejbRefModel.getElementModelsByElementClassAndActiveStatus(codeElementClass);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByElementClassAndInactiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementModelVO> getElementModelsByElementClassAndInactiveStatus(
			String codeElementClass) throws BusinessException {
		return ejbRefModel.getElementModelsByElementClassAndInactiveStatus(codeElementClass);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByElementModel(java.lang.String)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModel(
			String codeElementModel) throws BusinessException {
		return ejbRefType.getElementTypesByElementModel(codeElementModel);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByElementModelAndActiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModelAndActiveStatus(
			String codeElementModel) throws BusinessException {
		return ejbRefType.getElementTypesByElementModelAndActiveStatus(codeElementModel);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementTypesByElementModelAndInactiveStatus(java.lang.String)
	 */
	@Override
	public List<ElementTypeVO> getElementTypesByElementModelAndInactiveStatus(
			String codeElementModel) throws BusinessException {
		return ejbRefType.getElementTypesByElementModelAndInactiveStatus(codeElementModel);
	}
	
	@Override
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink(
			ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO)
			throws BusinessException {
		return ejbRef.getElementSerializedToLinkUnLink(elementSerializedLinkUnLinkFilterVO);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#updateLinkedSerials(co.com.directv.sdii.model.dto.UpdateLinkedSerialsDTO)
	 */
	@Override
	public void updateLinkedSerials(UpdateLinkedSerialsDTO updateLinkedSerialsDTO)throws BusinessException {
		this.ejbRef.updateLinkedSerials(updateLinkedSerialsDTO);
	}

	@Override
	public void changeElementModel(Long idElement,
			String codTypeElement, Long idElementLinkElement,
			String codTypeElementLinkElement) throws BusinessException {
		this.ejbRef.changeElementModel(idElement, codTypeElement, idElementLinkElement, codTypeElementLinkElement);
	}
	
	@Override
	public List<ElementTypeVO> getElementTypeByElementModelAndIsSerialized(
			String elementModelCode, boolean isSerialized)
			throws BusinessException {
		return ejbRefType.getElementTypeByElementModelAndIsSerialized(elementModelCode,isSerialized);
	}

	@Override
	public List<ElementModelVO> getElementModelsByActiveStatusAndIsPrepaid(
			String isPrepaid) throws BusinessException {
		return ejbRefModel.getElementModelsByActiveStatusAndIsPrepaid(isPrepaid);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IElementWS#getElementModelsByWarehouseIdAndElementModelId(java.lang.Long, java.lang.Long)
	 */
	public ElementTypeAndModelDTO getElementModelsByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId)throws BusinessException {
		return ejbRefModel.getElementModelsByWarehouseIdAndElementModelId(warehouseId,elementModelId);
	}

	@Override
	public List<ElementTypeVO> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			String isPrepaid, String isSerialized) throws BusinessException {
		return ejbRefType.getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(isPrepaid, isSerialized);
	}
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal#sendStatusCmdToIBS(java.lang.Long)
	 * 
	 * Servicio utilizado para lanzar la ejecución del proceso 'Comandos de ibs' manualmente.
	 */
	@Override
	public void sendStatusCmdToIBS(Long countryId) throws BusinessException {
		this.ejbRef.sendStatusCmdToIBS(countryId);
	}
	
	@Override
	public List<ElementTypeVO> getElementTypesByModelStatusAndIsSerializedForCombos(SearchElementTypesDTO params) throws BusinessException{
		return ejbRefType.getElementTypesByModelStatusAndIsSerialized( params.getIsSerialized(),  params.getElementModelCode(), params.getElementTypeStatus());
	}
	
}
