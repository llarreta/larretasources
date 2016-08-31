package co.com.directv.sdii.ws.business.core.contact;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.vo.ContactVO;

/**
 * Servicio web que expone las operaciones relacionadas con Contact
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="IContactWS",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
public interface IContactWS {

	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * persiste la información de un Contact
	 * @param obj objeto que encapsula la información necesaria para construir el Contact,
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
	 * @author jalopez
	 */
	@WebMethod(operationName="createContact", action="createContact", exclude = true)
	//@RequestWrapper(localName="createContactRequest",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	public void createContact(@WebParam(name="objContact")ContactVO objContact) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un inteto de contacto
	 * @param contactDTO - objeto que encapsula la información necesaria para construir el Contact,
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
	 * @author ssanabri
	 */
	@WebMethod(operationName="createTryContact", action="createTryContact")
	public void createTryContact(@WebParam(name="contactDTO") ContactDTO contactDTO) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * actualiza la información de un Contact
	 * @param obj objeto que encapsula la información del Contact a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Contact
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
	 * @author jalopez
	 */
	@WebMethod(operationName="updateContact", action="updateContact", exclude = true)
	//@RequestWrapper(localName="updateContactRequest",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	public void updateContact(@WebParam(name="objContact")ContactVO objContact) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * borra un Contact de la persistencia
	 * @param obj objeto que encapsula la información del Contact, solo se requiere la propiedad id
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
	 * @author jalopez
	 */
	@WebMethod(operationName="deleteContact", action="deleteContact", exclude = true)
	//@RequestWrapper(localName="deleteContactRequest",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	public void deleteContact(@WebParam(name="objContact")ContactVO objContact) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Obtiene un Contact dado el identificador del mismo
	 * @param id identificador del Contact a ser consultado
	 * @return Contact con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Contact con el id, ver códigos de excepción.
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
	 * @author jalopez
	 */
	@WebMethod(operationName="getContactByID", action="getContactByID")
	//@WebResult(name="responseContacts",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts/")
	//@RequestWrapper(localName="getContactByIDRequest",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	//@ResponseWrapper(localName="getContactByIDResponse",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	public ContactVO getContactByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna los Contacts generados por el cambio de estdo
	 * de la WorkOrder.
	 * @param ContactDTO contactDTO - dealerId Long(Id del dealer), woCode String(Codigo de la WO)
	 * @return List<ContactVO> - Retorna el Listado de Contacts
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
	 * @author jalopez
	 */
	@WebMethod(operationName="getContactsByWorkOrder", action="getContactsByWorkOrder")
	//@WebResult(name="responseContacts",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts/")
	//@RequestWrapper(localName="getContactsByWorkOrderRequest",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	//@ResponseWrapper(localName="getContactsByWorkOrderResponse",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	public List<ContactVO> getContactsByWorkOrder(@WebParam(name="contactDTO")ContactDTO contactDTO) throws BusinessException;
	
	/**
	 * Metodo: Retorna los Contacts generados por el cambio de estdo de la WorkOrder.
	 * @param woCode String(Codigo de la WO)
	 * @return List<ContactVO> - Retorna el Listado de Contacts
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
	 * @author ssanabri
	 */
	@WebMethod(operationName="getContactsTriesByWorkOrder", action="getContactsTriesByWorkOrder")	
	public List<ContactVO> getContactsTriesByWorkOrder(@WebParam(name="woCode")String woCode) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Realiza la creacion del Contact en SDII, realiza
	 * las consultas respectivas para obtener los
	 * datos necesarios para su creacion
	 * @param contactDTO ContactDTO workOrderId Long - id de la WO, 
	 * woStatusHistoryId Long - id de la woStatusHistory
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
	 * @author jalopez
	 */
	@WebMethod(operationName="createContactCore", action="createContactCore")
	//@RequestWrapper(localName="createContactCoreRequest",targetNamespace="http://directvla.com.contract/ws/sdii/Contacts")
	public void createContactCore(@WebParam(name="contactDTO") ContactDTO contactDTO ) throws BusinessException;
	
}
