package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.DeliveryFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.RefConfirmationFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.model.dto.collection.ReferenceElementItemsDTO;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS;

/**
 * Servicio web que expone las operaciones relacionadas con ReferenceElementItem
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="ReferenceElementItemService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="ReferenceElementItemPort")
@Stateless()
public class ReferenceElementItemWS implements IReferenceElementItemWS{

	@EJB
    private RefConfirmationFacadeBeanLocal ejbRefConf;
	
	@EJB
    private ReferenceElementItemFacadeBeanLocal ejbRefElement;
	
	@EJB
    private ReferenceFacadeBeanLocal ejbReference;
	
	@EJB
    private DeliveryFacadeBeanLocal ejbRefDev;
	
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
	public void createRefConfirmation(RefConfirmationVO objRefConfirmation) throws BusinessException{
		ejbRefConf.createRefConfirmation(objRefConfirmation);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS#createReferenceByFile(java.util.List, java.lang.Long)
	 */
	public Long createReferenceByFile(List<ReferenceElementItemVO> refElements, Long userId ) throws BusinessException
	{
		return ejbReference.createReferenceByFile(refElements, userId);
	}
	
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
	public void updateRefConfirmation(RefConfirmationVO objRefConfirmation) throws BusinessException{
		ejbRefConf.updateRefConfirmation(objRefConfirmation);
	}
	
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
	public void deleteRefConfirmation(RefConfirmationVO objRefConfirmation) throws BusinessException{
		ejbRefConf.deleteRefConfirmation(objRefConfirmation);
	}
	
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
	public RefConfirmationVO getRefConfirmationByID(Long id) throws BusinessException{
		return ejbRefConf.getRefConfirmationByID(id);
	}
	
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
	public List<RefConfirmationVO> getAllRefConfirmations() throws BusinessException{
		return ejbRefConf.getAllRefConfirmations();
	}
	
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
	public void createReferenceElementItem(ReferenceElementItemVO objReferenceElementItem) throws BusinessException{
		ejbRefElement.createReferenceElementItem(objReferenceElementItem);
	}
	
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
	public void updateReferenceElementItem(ReferenceElementItemVO objReferenceElementItem) throws BusinessException{
		ejbRefElement.updateReferenceElementItem(objReferenceElementItem);
	}
	
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
	public void deleteReferenceElementItem(ReferenceElementItemVO objReferenceElementItem) throws BusinessException{
		ejbRefElement.deleteReferenceElementItem(objReferenceElementItem);
	}
	
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
	public ReferenceElementItemVO getReferenceElementItemByID(Long id) throws BusinessException{
		return ejbRefElement.getReferenceElementItemByID(id);
	}
	
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
	public List<ReferenceElementItemVO> getAllReferenceElementItems() throws BusinessException{
		return ejbRefElement.getAllReferenceElementItems();
	}
	
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
	public void createDelivery(DeliveryVO objDelivery) throws BusinessException{
		ejbRefDev.createDelivery(objDelivery);
	}
	
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
	public void updateDelivery(DeliveryVO objDelivery) throws BusinessException{
		ejbRefDev.updateDelivery(objDelivery);
	}
	
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
	public void deleteDelivery(DeliveryVO objDelivery) throws BusinessException{
		ejbRefDev.deleteDelivery(objDelivery);
	}
	
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
	public DeliveryVO getDeliveryByID(Long id) throws BusinessException{
		return ejbRefDev.getDeliveryByID(id);
	}
	
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
	public List<DeliveryVO> getAllDeliverys() throws BusinessException{
		return ejbRefDev.getAllDeliverys();
	}

	/**
	 * Metodo: Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * @param refID - Long Identificador de la remisión
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
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
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefElement.getReferenceElementItemsByReferenceID(refID, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS#getNotSerializedReferenceElementItemByReferenceId(java.lang.Long)
	 */
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException{
		return ejbRefElement.getNotSerializedReferenceElementItemByReferenceId(referenceId);
	}

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
	public void receptRefSerialElementItem(
			List<ReferenceElementItemVO> refElements) throws BusinessException {
		ejbRefElement.receptRefSerialElementItem(refElements);
		
	}

	@Override
	public ReferenceElementItemsDTO getReferenceElementSerializedNotSerialized(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {		
		return ejbRefElement.getReferenceElementSerializedNotSerialized(refID, requestCollInfo);
	}

//	/*
//	 * (non-Javadoc)
//	 * @see co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS#addElementToReference(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
//	 */
//	@Override
//	public void addElementToReference(ReferenceElementItemVO refElementVO) throws BusinessException {
//		ejbRefElement.addElementToReference(refElementVO);
//		
//	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#removeElementOfReference(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	@Override
	public void removeElementOfReference(ReferenceElementItemVO item, Long userId) throws BusinessException {
		this.ejbRefElement.removeElementOfReference(item, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS#getReferenceElementsByReferenceIdAndSerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long, boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementMixedResponse getReferenceElementsByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId, boolean isSerialized, RequestCollectionInfo requestCollInfo)
			throws BusinessException {		
		return ejbRefElement.getReferenceElementsByReferenceIdAndSerialOrElementType(refID,
				serial, elementTypeId, isSerialized, requestCollInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS#partialReceptionOfReferenceElementItem(java.util.List, java.lang.Long)
	 */
	@Override
	public void partialReceptionOfReferenceElementItem(List<ReferenceElementItemVO> elements, Long userId)throws BusinessException {
		this.ejbRefElement.partialReceptionOfReferenceElementItem(elements, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceElementItemWS#getReferenceElementItemsByReferenceIDAndSerOrNotSer(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo, boolean)
	 */
	@Override
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDAndSerOrNotSer(Long refID, RequestCollectionInfo requestCollInfo,boolean isSerialized) throws BusinessException {
		return this.ejbRefElement.getReferenceElementItemsByReferenceIDAndSerOrNotSer(refID,requestCollInfo,isSerialized) ;
	}

	@Override
	public void addElementSerialized(String serialCode, 
			Long referenceID, boolean isPrepaid, Long userId) throws BusinessException {
		 this.ejbRefElement.addElementSerialized(serialCode, referenceID, isPrepaid, userId);
		
	}

	

	@Override
	public void addElementNotSerialized(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd, Long referenceID, Long userId) throws BusinessException {
		this.ejbRefElement.addElementNotSerialized(listElementNotSerializedToAdd, referenceID, userId);
		
	}

	@Override
	public ReferenceElementItemsResponse getElementNotSerializedFromWarehouse(RequestCollectionInfo requestCollInfo,
			Long referenceID, boolean isPrepaid, Long elementTypeId, Long modelId)
			throws BusinessException {
		return this.ejbRefElement.getElementNotSerializedFromWarehouse(requestCollInfo,referenceID, isPrepaid, elementTypeId, modelId);
	}

	@Override
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(
			Long refID, RequestCollectionInfo requestCollInfo, String serialCode, boolean isSerialized)
			throws BusinessException {
		return this.ejbRefElement.getReferenceElementItemsByReferenceIDSerAndNotSerPending(refID, requestCollInfo, serialCode, isSerialized);
	}

}
