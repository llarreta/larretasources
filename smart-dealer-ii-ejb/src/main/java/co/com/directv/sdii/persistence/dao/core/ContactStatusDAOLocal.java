package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ContactStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ContactStatus
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un ContactStatus
	 * @param obj objeto que encapsula la información de un ContactStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createContactStatus(ContactStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ContactStatus
	 * @param obj objeto que encapsula la información de un ContactStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateContactStatus(ContactStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ContactStatus
	 * @param obj información del ContactStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteContactStatus(ContactStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ContactStatus por su identificador
	 * @param id identificador del ContactStatus a ser consultado
	 * @return objeto con la información del ContactStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ContactStatus getContactStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ContactStatus por su codigo
	 * @param code codigo del ContactStatus a ser consultado
	 * @return objeto con la información del ContactStatus dado su codigo, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ContactStatus getContactStatusByCode(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ContactStatus almacenados en la persistencia
	 * @return Lista con los ContactStatus existentes, una lista vacia en caso que no existan ContactStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ContactStatus> getAllContactStatuss() throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Permite obtener un ContactStatus dependiendo de un ibsCode 
	 * @param ibsCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public ContactStatus getContactStatusByIbsCode(String ibsCode) throws DAOServiceException, DAOSQLException;


}