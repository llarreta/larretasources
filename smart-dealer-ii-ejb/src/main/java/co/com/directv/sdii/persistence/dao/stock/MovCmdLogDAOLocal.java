package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MovCmdLog;

public interface MovCmdLogDAOLocal {

	/**
	 * @param transientInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void save(MovCmdLog transientInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param persistentInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void delete(MovCmdLog persistentInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite eliminar el log de MovCmd filtrado por un id de un elemento serializado
	 * @param serializedElementID
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMovCmdLogBySerializedElementID(Long serializedElementID) throws DAOServiceException, DAOSQLException;
	
	/**
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdLog findById(java.math.BigDecimal id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdLog> findByExample(MovCmdLog instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdLog> findByProperty(String propertyName, Object value)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param description
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdLog> findByDescription(Object description)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param creationDate
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdLog> findByCreationDate(Object creationDate)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdLog> findAll() throws DAOServiceException, DAOSQLException;

	/**
	 * @param detachedInstance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdLog merge(MovCmdLog detachedInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachDirty(MovCmdLog instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachClean(MovCmdLog instance)
			throws DAOServiceException, DAOSQLException;
	
	/**
	 * Operacion encargada de consultar los resultados del proceso
	 * a partir de un identificador de movimiento
	 * @param movCmdQueueId
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public List<MovCmdLog> getLogByMovementId(Long movCmdQueueId)
		throws DAOServiceException, DAOSQLException;

}