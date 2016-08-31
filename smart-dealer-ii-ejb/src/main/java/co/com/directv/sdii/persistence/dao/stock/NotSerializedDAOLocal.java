package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad NotSerialized
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerializedDAOLocal {

	/**
	 * Metodo:  persiste la información de un NotSerialized
	 * @param obj objeto que encapsula la información de un NotSerialized
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerialized(NotSerialized obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un NotSerialized
	 * @param obj objeto que encapsula la información de un NotSerialized
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerialized(NotSerialized obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un NotSerialized
	 * @param obj información del NotSerialized a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteNotSerialized(NotSerialized obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un NotSerialized por su identificador
	 * @param id identificador del NotSerialized a ser consultado
	 * @return objeto con la información del NotSerialized dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public NotSerialized getNotSerializedByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los NotSerialized almacenados en la persistencia
	 * @return Lista con los NotSerialized existentes, una lista vacia en caso que no existan NotSerialized en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<NotSerialized> getAllNotSerializeds() throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Obtiene los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @return Lista con los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public List<NotSerialized> getNotSerializedsByWareHouseId( Warehouse warehouseId ) throws DAOServiceException, DAOSQLException; 
	
	
	/**
	 * Metodo: Obtiene la suma de todos las cantidades asociadas a los objetos NotSerialized propios de un WareHouse
	 * @return Un double que representa la suma de todos las cantidades asociadas a los objetos NotSerialized propios de un WareHouse
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public Double getNotSerializedQuantityByWarehouseIdAndNotSerializedId( Long wareHouseId,Long notSerId ) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Obtiene la entidad NotSerialized por elementType
	 * @return Un double que representa la suma de todos las cantidades asociadas a los objetos NotSerialized propios de un WareHouse
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public NotSerialized getNotSerializedbyElementTypeID( Long elementTypeId, Long countryId) throws DAOServiceException, DAOSQLException;
	
	
	


}