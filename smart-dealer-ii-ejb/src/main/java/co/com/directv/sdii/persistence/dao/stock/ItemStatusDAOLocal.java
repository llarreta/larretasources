package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ItemStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ItemStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ItemStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un ItemStatus
	 * @param obj objeto que encapsula la información de un ItemStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createItemStatus(ItemStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ItemStatus
	 * @param obj objeto que encapsula la información de un ItemStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateItemStatus(ItemStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ItemStatus
	 * @param obj información del ItemStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteItemStatus(ItemStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ItemStatus por su identificador
	 * @param id identificador del ItemStatus a ser consultado
	 * @return objeto con la información del ItemStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ItemStatus getItemStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ItemStatus almacenados en la persistencia
	 * @return Lista con los ItemStatus existentes, una lista vacia en caso que no existan ItemStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ItemStatus> getAllItemStatuss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información del estado de un item por el código
	 * @param newItemStatusCode código del estado del item
	 * @return Estado del item por código
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public ItemStatus getItemStatusByCode(String newItemStatusCode)throws DAOServiceException, DAOSQLException;

	public ItemStatus getItemStatusSended() throws DAOServiceException, DAOSQLException, PropertiesException;
    public ItemStatus getItemStatusRecepted() throws DAOServiceException, DAOSQLException, PropertiesException;
    public ItemStatus getItemStatusCreated() throws DAOServiceException, DAOSQLException, PropertiesException;
    public ItemStatus getItemStatusInconsistency() throws DAOServiceException, DAOSQLException, PropertiesException;
    public ItemStatus getItemStatusPartial() throws DAOServiceException, DAOSQLException, PropertiesException;
	
}