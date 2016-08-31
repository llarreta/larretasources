package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ContactConfiguration;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ContactConfiguration
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactConfigurationDAOLocal {

	/**
	 * Metodo:  persiste la información de un ContactConfiguration
	 * @param obj objeto que encapsula la información de un ContactConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createContactConfiguration(ContactConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ContactConfiguration
	 * @param obj objeto que encapsula la información de un ContactConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateContactConfiguration(ContactConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ContactConfiguration
	 * @param obj información del ContactConfiguration a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteContactConfiguration(ContactConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ContactConfiguration por su identificador
	 * @param id identificador del ContactConfiguration a ser consultado
	 * @return objeto con la información del ContactConfiguration dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ContactConfiguration getContactConfigurationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ContactConfiguration almacenados en la persistencia
	 * @return Lista con los ContactConfiguration existentes, una lista vacia en caso que no existan ContactConfiguration en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ContactConfiguration> getAllContactConfigurations() throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Retorna la configuracion aplicando el filtro
	 * de estado de la wo y del tipo de wo.
	 * @param woTypeId Long - id del tipo de work order
	 * @param woStatusId Long - id del estado de la work order
	 * @param countryId Long - id del pais
	 * @return ContactConfiguration
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ContactConfiguration getContactConfigurationByRule(Long woTypeId, Long woStatusId, Long countryId) throws DAOServiceException, DAOSQLException;


}