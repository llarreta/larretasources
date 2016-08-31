package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.IbsContactDTO;
import co.com.directv.sdii.model.pojo.IbsContact;

/**
 * Interface Local para la gestión del CRUD de la
 * Entidad IbsContact
 * 
 * Fecha de Creación: 30/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface IbsContactDAOLocal {

	/**
	 * Metodo:  persiste la información de un IbsContact
	 * @param obj objeto que encapsula la información de un IbsContact
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createIbsContact(IbsContact obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un IbsContact
	 * @param obj objeto que encapsula la información de un IbsContact
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateIbsContact(IbsContact obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un IbsContact
	 * @param obj información del IbsContact a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteIbsContact(IbsContact obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un IbsContact por su identificador
	 * @param id identificador del IbsContact a ser consultado
	 * @return objeto con la información del IbsContact dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public IbsContact getIbsContactByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los IbsContact almacenados en la persistencia
	 * @return Lista con los IbsContact existentes, una lista vacia en caso que no existan IbsContact en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<IbsContact> getAllIbsContacts(Long countryId) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Se consulta un IbsContacta por codigo del ibscontact y el pais
	 * @param ibsContactCode
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public IbsContact getIbsContactByIbsContactCode(String ibsContactCode,Long countryId) throws DAOServiceException, DAOSQLException; 
	
	/**
	 * Metodo: Permite obtener una lista de contact de IBS
	 * @param workOrderId
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<IbsContactDTO> getIbsContactDTOByWorkOrderId(Long workOrderId,Long countryId) throws DAOServiceException, DAOSQLException;
	
}