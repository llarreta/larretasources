package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Contact;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Contact
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactDAOLocal {

	/**
	 * Metodo: Control Cambios Generacion de Contacts.  
	 * persiste la información de un Contact
	 * @param obj objeto que encapsula la información de un Contact
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createContact(Contact obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * actualiza la información de un Contact
	 * @param obj objeto que encapsula la información de un Contact
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateContact(Contact obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Borra de la persistencia la información de un Contact
	 * @param obj información del Contact a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteContact(Contact obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Obtiene la información de un Contact por su identificador
	 * @param id identificador del Contact a ser consultado
	 * @return objeto con la información del Contact dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public Contact getContactByID(Long id) throws DAOServiceException, DAOSQLException;
	
		
	/**
	 * 
	 * Metodo: Contol Cambios Generacion de Contacts.
	 * Retorna los Contacts generados por el cambio de estdo
	 * de la WorkOrder.
	 * Los Contacts que son generados estan realcionados con la 
	 * historia de la Work Order ya que aqui se almacenan los 
	 * cambios de estado.
	 * @param woStatusHistoryId Long - id de WoStatusHistory
	 * @return Contact - Retorna un Contact segun el filtro aplicado
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jalopez
	 */
	public Contact getContactsByWoStatusHistory(Long woStatusHistoryId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Contol Cambios Generacion de Contacts.
	 * Retorna los Contacts generados por el cambio de estdo
	 * de la WorkOrder.
	 * Los Contacts que son generados estan realcionados con la 
	 * historia de la Work Order ya que aqui se almacenan los 
	 * cambios de estado.
	 * @param woCode String - Codigo de la WO
	 * @return List<Contact> - Retorna el Listado de Contacts
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<Contact> getContactsByWorkOrder(String woCode) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna los intentos de contactos realizados para una WO ordenados por fecha descendentemente.
	 * @param woCode String - Codigo de la WO
	 * @return List<Contact> - Retorna el Listado de intentos de Contacts realizados en el agendamiento.
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author ssanabri
	 */
	public List<Contact> getContactsTriesByWorkOrder(String woCode) throws DAOServiceException, DAOSQLException;
}