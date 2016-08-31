package co.com.directv.sdii.persistence.dao.allocator;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AllocatorEventMaster;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 29/03/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AllocatorEventMasterDAOLocal {

	/**
	 * Metodo:  persiste la información de un AllocatorEventMaster
	 * @param obj objeto que encapsula la información de un AllocatorEventMaster
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createAllocatorEventMaster(AllocatorEventMaster obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un AllocatorEventMaster
	 * @param obj objeto que encapsula la información de un AllocatorEventMaster
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAllocatorEventMaster(AllocatorEventMaster obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AllocatorEventMaster
	 * @param obj información del AllocatorEventMaster a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAllocatorEventMaster(AllocatorEventMaster obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un AllocatorEventMaster por su identificador
	 * @param id identificador del AllocatorEventMaster a ser consultado
	 * @return objeto con la información del AllocatorEventMaster dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AllocatorEventMaster getAllocatorEventMasterByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el ultimo evento de asignador creado
	 * @param Long countryId
	 * @return AllocatorEventMaster
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author 
	 */
	public AllocatorEventMaster getLastAllocatorEventMaster(Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Retorna el proximo valor para el id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Long getMaxAllocatorEventMaster() throws DAOServiceException, DAOSQLException;
	
}