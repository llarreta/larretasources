package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ElementMovementDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.MovCmdConfig;

public interface MovCmdConfigDAOLocal {

	/**
	 * @param transientInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void save(MovCmdConfig transientInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param persistentInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void delete(MovCmdConfig persistentInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdConfig findById(java.math.BigDecimal id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdConfig> findByExample(MovCmdConfig instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdConfig> findByProperty(String propertyName,
			Object value) throws DAOServiceException, DAOSQLException;

	/**
	 * @param configDate
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdConfig> findByConfigDate(Object configDate)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdConfig> findAll() throws DAOServiceException,
			DAOSQLException;

	/**
	 * @param detachedInstance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdConfig merge(MovCmdConfig detachedInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachDirty(MovCmdConfig instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachClean(MovCmdConfig instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Consulta la configuracion de un movimiento de acuerdo a los parametros definidos en la tabla
	 * @param dto
	 * @return 
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova EliminarPorCambioProceso
	 * 
	 */
	public abstract Long getMovCmdConfigByElementMovementDTOCriteria(ElementMovementDTO dto) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * 
	 * Metodo: Consulta la configuracion de un movimiento de acuerdo a los parametros definidos en la tabla
	 * @param dto
	 * @return 
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public abstract Long getMovCmdConfigByElementMovementDTOCriteria(MovementElementDTO dto) throws DAOServiceException, DAOSQLException;

}