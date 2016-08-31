package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.IbsElementStatus;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

@Local
public interface MovCmdQueueDAOLocal {

	/**
	 * @param transientInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void save(MovCmdQueue transientInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param persistentInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void delete(MovCmdQueue persistentInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdQueue findById(Long id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdQueue> findByExample(MovCmdQueue instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdQueue> findByProperty(String propertyName,
			Object value) throws DAOServiceException, DAOSQLException;

	/**
	 * @param creationDate
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdQueue> findByCreationDate(Object creationDate)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param executeDate
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdQueue> findByExecuteDate(Object executeDate)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdQueue> findAll() throws DAOServiceException,
			DAOSQLException;

	/**
	 * @param detachedInstance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdQueue merge(MovCmdQueue detachedInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachDirty(MovCmdQueue instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachClean(MovCmdQueue instance)
			throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Obtiene los registros con estado pendiente
	 * 
	 * @param statusCode
	 * @return List<MovCmdQueue>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatus(String statusCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Obtiene los registros con estado pendiente paginado
	 * 
	 * @param statusCode
	 * @return List<MovCmdQueue>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPage(String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * 
	 * Método: 
	 * 
	 * @param statusCode
	 * @return List<MovCmdQueue>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author ialessan
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPageParallelSQL(String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * 
	 * Método: 
	 * 
	 * @param statusCode
	 * @return List<MovCmdQueue>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author ialessan
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPageParallel (String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException;	

	
	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * 
	 * Método: 
	 * 
	 * @param statusCode
	 * @return List<MovCmdQueue>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author ialessan
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPageParallel2 (String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException;	


	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * 
	 * Método: 
	 * 
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * 
	 * @author ialessan
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> findMinIdPerGroup(String statusCode,Long countryId) throws DAOServiceException, DAOSQLException;	
	/**
	 * 
	 * @param isSerialized
	 * @param elementId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdQueue getMovCmdQueueByElementId(boolean isSerialized, Long elementId)
			throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Elimina elementos serializados de la tabla 'mov_cmd_queue'
	 * @param serializedElementID
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	void deleteSerializedMovCmdQueueByElementId(Long serializedElementID) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Consulta el estado de IBS de un elemento para ser reportado por codigo y por pais
	 * @param ibsElementStatusCode
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public IbsElementStatus getIbsElementStatusByCodeAndCountry(String ibsElementStatusCode,String countryCode) throws DAOServiceException, DAOSQLException;
	
	
	
	/**
	 * 
	 * Método: operación encargada de la consulta del resultados de los
	 * movimiento del inventario
	 * @param ibsElementStatusCode
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waguilera
	 */
	public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(MovCmdQueueFilterDTO dto,  Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
	
	

	/**
	 * Operacion encargada de actualizar un registro de la tabla MovCmdQueue 
	 * @param obj
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public void updateMovCmdQueue(MovCmdQueue obj) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo encargado de verificar si este swap ya se procesó
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public Long countHistoryEvent(Long customerId, Long historyEventId, Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo encargado de validar si el movimiento de inventario ya se realizo
	 * correctamnete para la misma workOrder y con los mismos seriales
	 * 
	 * @param workOrderId
	 * @param elementId
	 * @param elementIdLinked
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public Long validateProcessForWorkOrder(Long workOrderId, Long elementId, Long elementIdLinked) 
		throws DAOServiceException, DAOSQLException;
	
}